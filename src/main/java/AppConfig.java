import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;
import java.util.Properties;

public class AppConfig {
    private static final String LOCAL_CONFIG_FILE = "local.properties";
    private static Properties localProperties;

    private AppConfig() {
    }

    public static Optional<String> getValue(String key) {
        String environmentValue = System.getenv(key);

        if (environmentValue != null && !environmentValue.isBlank()) {
            return Optional.of(environmentValue.trim());
        }

        String systemValue = System.getProperty(key);

        if (systemValue != null && !systemValue.isBlank()) {
            return Optional.of(systemValue.trim());
        }

        String localValue = getLocalProperties().getProperty(key);

        if (localValue != null && !localValue.isBlank()) {
            return Optional.of(localValue.trim());
        }

        return Optional.empty();
    }

    private static Properties getLocalProperties() {
        if (localProperties == null) {
            localProperties = loadLocalProperties();
        }

        return localProperties;
    }

    private static Properties loadLocalProperties() {
        Properties properties = new Properties();
        Path configPath = Path.of(LOCAL_CONFIG_FILE);

        if (!Files.exists(configPath)) {
            return properties;
        }

        try (InputStream inputStream = Files.newInputStream(configPath)) {
            properties.load(inputStream);
        } catch (IOException e) {
            System.out.println("Could not read local.properties: " + e.getMessage());
        }

        return properties;
    }
}
