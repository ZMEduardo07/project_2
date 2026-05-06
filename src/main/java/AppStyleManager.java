import javafx.scene.control.Button;

/**
 * Eduardo Zamora-Melgoza
 * 4/25/26
 *
 *
 */

public class AppStyleManager {
        public static final String BACKGROUND_STYLE = "-fx-background-color: linear-gradient(to bottom, #1C1C1E, #161616);" +
                        "-fx-padding: 40;";

        public static final String TITLE_STYLE = "-fx-font-family: sans-serif;" +
                        "-fx-font-size: 28px;" +
                        "-fx-font-weight: bold;" +
                        "-fx-text-fill: #FFFFFF;";

        public static final String MESSAGE_STYLE = "-fx-font-family: sans-serif;" +
                        "-fx-text-fill: #A0A0A0;" +
                        "-fx-font-size: 14px;";

        public static final String SUBTITLE_STYLE = "-fx-font-family: sans-serif;" +
                        "-fx-text-fill: #A0A0A0;" +
                        "-fx-font-size: 15px;";

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

        public static final String TASK_LIST_STYLE = "-fx-background-color: transparent;" +
                        "-fx-control-inner-background: transparent;" +
                        "-fx-background-insets: 0;" +
                        "-fx-padding: 0;" +
                        "-fx-border-color: transparent;";

        public static final String TASK_CELL_STYLE = "-fx-background-color: #242424;" +
                        "-fx-text-fill: #FFFFFF;" +
                        "-fx-font-family: sans-serif;" +
                        "-fx-font-size: 16px;" +
                        "-fx-background-radius: 10;" +
                        "-fx-padding: 14px 16px;";

        public static final String EMPTY_TASK_STYLE = "-fx-background-color: #242424;" +
                        "-fx-text-fill: #A0A0A0;" +
                        "-fx-font-family: sans-serif;" +
                        "-fx-font-size: 16px;" +
                        "-fx-background-radius: 10;" +
                        "-fx-padding: 20px;";

        public static final String START_STEPS_PANEL_STYLE = "-fx-background-color: #242424;" +
                        "-fx-background-radius: 8;" +
                        "-fx-border-color: #333333;" +
                        "-fx-border-radius: 8;" +
                        "-fx-border-width: 1px;" +
                        "-fx-padding: 16px;";

        public static final String START_STEPS_TITLE_STYLE = "-fx-font-family: sans-serif;" +
                        "-fx-text-fill: #FFFFFF;" +
                        "-fx-font-size: 17px;" +
                        "-fx-font-weight: bold;";

        public static final String START_STEP_LABEL_STYLE = "-fx-font-family: sans-serif;" +
                        "-fx-text-fill: #D6D6D6;" +
                        "-fx-font-size: 15px;" +
                        "-fx-padding: 4px 0;";

        public static final String FLOATING_BUTTON_STYLE = "-fx-background-color: #FFFFFF;" +
                        "-fx-text-fill: #0A0A0A;" +
                        "-fx-font-family: sans-serif;" +
                        "-fx-font-size: 30px;" +
                        "-fx-font-weight: bold;" +
                        "-fx-background-radius: 28;" +
                        "-fx-padding: 0;";

        public static final String FLOATING_BUTTON_HOVER_STYLE = "-fx-background-color: #E0E0E0;" +
                        "-fx-text-fill: #0A0A0A;" +
                        "-fx-font-family: sans-serif;" +
                        "-fx-font-size: 30px;" +
                        "-fx-font-weight: bold;" +
                        "-fx-background-radius: 28;" +
                        "-fx-padding: 0;";

        public static void applyButtonStyle(Button button) {

                button.setStyle(NORMAL_BUTTON_STYLE);

                button.setOnMouseEntered(e -> button.setStyle(HOVER_BUTTON_STYLE));
                button.setOnMouseExited(e -> button.setStyle(NORMAL_BUTTON_STYLE));
        }

        public static void applyFloatingButtonStyle(Button button) {
                button.setMinSize(56, 56);
                button.setPrefSize(56, 56);
                button.setMaxSize(56, 56);
                button.setStyle(FLOATING_BUTTON_STYLE);

                button.setOnMouseEntered(e -> button.setStyle(FLOATING_BUTTON_HOVER_STYLE));
                button.setOnMouseExited(e -> button.setStyle(FLOATING_BUTTON_STYLE));
        }

}
