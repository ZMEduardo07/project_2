import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * Eduardo Zamora-Melgoza
 * 04/25/26
 *
 *
 */

public class SignUpScreen {

    private VBox layout;
    private Stage stage;

    public SignUpScreen(Stage stage) {
        this.stage = stage;
        createScreen();
    }

    private void createScreen() {

        Label title = new Label("Create Account");
        title.setStyle(AppStyleManager.TITLE_STYLE);

        TextField usernameField = new TextField();
        usernameField.setPromptText("New Username");
        usernameField.setMaxWidth(450);
        usernameField.setPrefWidth(450);
        usernameField.setPrefHeight(50);
        usernameField.setStyle(AppStyleManager.TEXT_FIELD_STYLE);

        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("New Password");
        passwordField.setMaxWidth(450);
        passwordField.setPrefWidth(450);
        passwordField.setPrefHeight(50);
        passwordField.setStyle(AppStyleManager.TEXT_FIELD_STYLE);

        PasswordField confirmField = new PasswordField();
        confirmField.setPromptText("Confirm Password");
        confirmField.setMaxWidth(450);
        confirmField.setPrefWidth(450);
        confirmField.setPrefHeight(50);
        confirmField.setStyle(AppStyleManager.TEXT_FIELD_STYLE);

        Button createButton = new Button("Create Account");
        createButton.setPrefWidth(180);
        createButton.setPrefHeight(50);

        Button backButton = new Button("Back");
        backButton.setPrefWidth(150);
        backButton.setPrefHeight(50);

        AppStyleManager.applyButtonStyle(createButton);
        AppStyleManager.applyButtonStyle(backButton);

        Label messageLabel = new Label();
        messageLabel.setStyle(AppStyleManager.MESSAGE_STYLE);

        createButton.setOnAction(e -> {

            String username = usernameField.getText();
            String password = passwordField.getText();
            String confirm = confirmField.getText();

            AuthService authService = new AuthService();

            if (username.isEmpty() || password.isEmpty() || confirm.isEmpty()) {
                messageLabel.setText("           There are fields to be completed.\n      Please make sure to complete all fields.");
            } else if (!password.equals(confirm)) {
                messageLabel.setText("Passwords do not match.");
            }
            else {

                boolean created = authService.registerUser(username, password);
                if (created) {
                    messageLabel.setText("Account created successfully!");
                } else {
                    messageLabel.setText("Account already exists.");
                }
            }
        });

        backButton.setOnAction(e -> {
            LoginScreen loginScreen = new LoginScreen(stage);
            stage.getScene().setRoot(loginScreen.getLayout());
        });

        layout = new VBox(25);
        layout.setAlignment(Pos.CENTER);
        layout.setPadding(new Insets(30));
        layout.setStyle(AppStyleManager.BACKGROUND_STYLE);

        layout.getChildren().addAll(title, usernameField, passwordField, confirmField,
                createButton, backButton, messageLabel);

    }
    public VBox getLayout(){
        return layout;
    }
}
