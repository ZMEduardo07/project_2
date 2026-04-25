import javafx.scene.control.Button;

/**
 * Eduardo Zamora-Melgoza
 * 4/25/26
 *
 *
 */

public class AppStyleManager {
    public static final String BACKGROUND_STYLE =
            "-fx-background-color: linear-gradient(to bottom, #4B146B, #1B103A, #0A1F44);" + "-fx-padding: 40;";

    public static final String TITLE_STYLE =
            "-fx-font-size: 28px;" +
            "-fx-font-weight: bold;" +
            "-fx-text-fill: white;";

    public static final String MESSAGE_STYLE =
            "-fx-text-fill: white;" +
            "-fx-font-size: 14px;";

    public static final String TEXT_FIELD_STYLE =
            "-fx-background-color: white;" +
            "-fx-font-size: 16px;" +
            "-fx-background-radius: 8;";

    public static final String NORMAL_BUTTON_STYLE =
            "-fx-background-color: #0A1F44;" +
            "-fx-text-fill: white;" +
            "-fx-font-size: 16px;" +
            "-fx-font-weight: bold;" +
            "-fx-border-color: linear-gradient(#ffb347, #ff4500);" +
            "-fx-border-width: 3px;" +
            "-fx-border-radius: 10;" +
            "-fx-background-radius: 10;";

    public static final String HOVER_BUTTON_STYLE =
            "-fx-background-color: #050B1F;" +
            "-fx-text-fill: white;" +
            "-fx-font-size: 16px;" +
            "-fx-font-weight: bold;" +
            "-fx-border-color: linear-gradient(#ffcc70, #ff3300);" +
            "-fx-border-width: 3px;" +
            "-fx-border-radius: 10;" +
            "-fx-background-radius: 10;";

    public static void applyButtonStyle(Button button){

        button.setStyle(NORMAL_BUTTON_STYLE);

        button.setOnMouseEntered(e -> button.setStyle(HOVER_BUTTON_STYLE));
        button.setOnMouseExited(e -> button.setStyle(NORMAL_BUTTON_STYLE));
    }

}
