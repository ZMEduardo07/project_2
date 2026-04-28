import javafx.scene.control.Button;

/**
 * Eduardo Zamora-Melgoza
 * 4/25/26
 *
 *
 */

public class AppStyleManager {
        public static final String BACKGROUND_STYLE = "-fx-background-color: #1A1A1A;" + "-fx-padding: 40;";

        public static final String TITLE_STYLE = "-fx-font-family: sans-serif;" +
                        "-fx-font-size: 28px;" +
                        "-fx-font-weight: bold;" +
                        "-fx-text-fill: #FFFFFF;";

        public static final String MESSAGE_STYLE = "-fx-font-family: sans-serif;" +
                        "-fx-text-fill: #A0A0A0;" +
                        "-fx-font-size: 14px;";

        public static final String TEXT_FIELD_STYLE = "-fx-background-color: #242424;" +
                        "-fx-text-fill: white;" +
                        "-fx-font-family: sans-serif;" +
                        "-fx-font-size: 16px;" +
                        "-fx-background-radius: 6;" +
                        "-fx-border-color: #333333;" +
                        "-fx-border-radius: 6;" +
                        "-fx-border-width: 1px;" +
                        "-fx-padding: 10px;";

        public static final String NORMAL_BUTTON_STYLE = "-fx-background-color: #FFFFFF;" +
                        "-fx-text-fill: #0A0A0A;" +
                        "-fx-font-family: sans-serif;" +
                        "-fx-font-size: 16px;" +
                        "-fx-font-weight: bold;" +
                        "-fx-background-radius: 6;";

        public static final String HOVER_BUTTON_STYLE = "-fx-background-color: #E0E0E0;" +
                        "-fx-text-fill: #0A0A0A;" +
                        "-fx-font-family: sans-serif;" +
                        "-fx-font-size: 16px;" +
                        "-fx-font-weight: bold;" +
                        "-fx-background-radius: 6;";

        public static void applyButtonStyle(Button button) {

                button.setStyle(NORMAL_BUTTON_STYLE);

                button.setOnMouseEntered(e -> button.setStyle(HOVER_BUTTON_STYLE));
                button.setOnMouseExited(e -> button.setStyle(NORMAL_BUTTON_STYLE));
        }

}
