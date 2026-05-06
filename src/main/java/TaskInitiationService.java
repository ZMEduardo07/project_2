import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

 /**
 * Siberaja Nadar 
 * 
 * 
 */

public class TaskInitiationService {
    private static final String GEMINI_API_URL =
            "https://generativelanguage.googleapis.com/v1beta/models/%s:generateContent";
    private static final String DEFAULT_MODEL = "gemini-2.5-flash";
    private static final int TIMEOUT_SECONDS = 25;

    private final HttpClient httpClient;

    public TaskInitiationService() {
        httpClient = HttpClient.newBuilder()
                .connectTimeout(Duration.ofSeconds(TIMEOUT_SECONDS))
                .build();
    }

    public List<String> createStartSteps(String taskTitle) {
        String apiKey = AppConfig.getValue("GOOGLE_API_KEY")
                .orElseThrow(() -> new TaskInitiationException("Missing GOOGLE_API_KEY in local.properties."));
        String model = AppConfig.getValue("GEMINI_MODEL").orElse(DEFAULT_MODEL);
        String responseBody = callGemini(apiKey, model, cleanTitle(taskTitle));
        String modelText = extractJsonString(responseBody, "text");
        List<String> steps = extractStringArray(modelText, "steps");

        if (steps.size() != 3) {
            steps = extractNumberedSteps(modelText);
        }

        if (steps.size() != 3) {
            throw new TaskInitiationException("Google API did not return exactly 3 start steps.");
        }

        return steps;
    }

    private String callGemini(String apiKey, String model, String taskTitle) {
        String endpoint = String.format(GEMINI_API_URL, model);
        String prompt = """
                You help people with ADHD start tasks.
                For the task below, return exactly three tiny first steps.
                Each step must be concrete, kind, and easy to do immediately.
                Return only JSON in this exact shape: {"steps":["step one","step two","step three"]}
                Task: %s
                """.formatted(taskTitle);

        String requestBody = "{"
                + "\"contents\":[{\"role\":\"user\",\"parts\":[{\"text\":" + toJsonString(prompt) + "}]}],"
                + "\"generationConfig\":{"
                + "\"temperature\":0.3,"
                + "\"maxOutputTokens\":512,"
                + "\"responseMimeType\":\"application/json\","
                + "\"thinkingConfig\":{\"thinkingBudget\":0}"
                + "}"
                + "}";

        HttpRequest request = HttpRequest.newBuilder(URI.create(endpoint))
                .timeout(Duration.ofSeconds(TIMEOUT_SECONDS))
                .header("x-goog-api-key", apiKey)
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                .build();

        try {
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() < 200 || response.statusCode() >= 300) {
                String errorMessage = extractJsonString(response.body(), "message");

                if (errorMessage == null || errorMessage.isBlank()) {
                    errorMessage = "Google API request failed with status " + response.statusCode() + ".";
                }

                throw new TaskInitiationException(errorMessage);
            }

            return response.body();
        } catch (IOException e) {
            throw new TaskInitiationException("Could not reach the Google API.", e);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new TaskInitiationException("Google API request was interrupted.", e);
        }
    }

    private List<String> extractStringArray(String json, String key) {
        List<String> values = new ArrayList<>();

        if (json == null || json.isBlank()) {
            return values;
        }

        String field = "\"" + key + "\"";
        int fieldIndex = json.indexOf(field);

        if (fieldIndex < 0) {
            return values;
        }

        int arrayStartIndex = json.indexOf('[', fieldIndex + field.length());
        if (arrayStartIndex < 0) {
            return values;
        }

        boolean escaped = false;
        boolean inString = false;
        StringBuilder currentValue = new StringBuilder();

        for (int index = arrayStartIndex + 1; index < json.length(); index++) {
            char current = json.charAt(index);

            if (inString) {
                if (escaped) {
                    currentValue.append(unescapeJsonCharacter(current));
                    escaped = false;
                } else if (current == '\\') {
                    escaped = true;
                } else if (current == '"') {
                    values.add(currentValue.toString());
                    currentValue.setLength(0);
                    inString = false;
                } else {
                    currentValue.append(current);
                }
            } else if (current == '"') {
                inString = true;
            } else if (current == ']') {
                return values;
            }
        }

        return values;
    }

    private List<String> extractNumberedSteps(String text) {
        List<String> steps = new ArrayList<>();

        if (text == null || text.isBlank()) {
            return steps;
        }

        String normalizedText = text
                .replace("\r", "\n")
                .replaceAll("\\s+(?=[1-3][.)]\\s)", "\n");

        for (String line : normalizedText.split("\\n")) {
            String step = line.trim()
                    .replaceFirst("^(\\d+[.)]\\s*|[-*]\\s*)", "")
                    .trim();

            if (!step.isBlank()) {
                steps.add(step);
            }

            if (steps.size() == 3) {
                break;
            }
        }

        return steps;
    }

    private String cleanTitle(String taskTitle) {
        if (taskTitle == null || taskTitle.isBlank()) {
            return "this task";
        }

        return taskTitle.trim();
    }

    private String extractJsonString(String json, String key) {
        if (json == null || json.isBlank()) {
            return null;
        }

        String field = "\"" + key + "\"";
        int fieldIndex = json.indexOf(field);

        if (fieldIndex < 0) {
            return null;
        }

        int colonIndex = json.indexOf(':', fieldIndex + field.length());
        if (colonIndex < 0) {
            return null;
        }

        int firstQuoteIndex = -1;
        for (int index = colonIndex + 1; index < json.length(); index++) {
            if (!Character.isWhitespace(json.charAt(index))) {
                if (json.charAt(index) == '"') {
                    firstQuoteIndex = index;
                }
                break;
            }
        }

        if (firstQuoteIndex < 0) {
            return null;
        }

        StringBuilder value = new StringBuilder();
        boolean escaped = false;

        for (int index = firstQuoteIndex + 1; index < json.length(); index++) {
            char current = json.charAt(index);

            if (escaped) {
                value.append(unescapeJsonCharacter(current));
                escaped = false;
            } else if (current == '\\') {
                escaped = true;
            } else if (current == '"') {
                return value.toString();
            } else {
                value.append(current);
            }
        }

        return null;
    }

    private char unescapeJsonCharacter(char current) {
        return switch (current) {
            case 'n' -> '\n';
            case 'r' -> '\r';
            case 't' -> '\t';
            case 'b' -> '\b';
            case 'f' -> '\f';
            default -> current;
        };
    }

    private String toJsonString(String value) {
        if (value == null) {
            return "null";
        }

        StringBuilder json = new StringBuilder("\"");

        for (int index = 0; index < value.length(); index++) {
            char current = value.charAt(index);

            switch (current) {
                case '"' -> json.append("\\\"");
                case '\\' -> json.append("\\\\");
                case '\n' -> json.append("\\n");
                case '\r' -> json.append("\\r");
                case '\t' -> json.append("\\t");
                case '\b' -> json.append("\\b");
                case '\f' -> json.append("\\f");
                default -> {
                    if (current < 32) {
                        json.append(String.format("\\u%04x", (int) current));
                    } else {
                        json.append(current);
                    }
                }
            }
        }

        json.append('"');
        return json.toString();
    }
}
