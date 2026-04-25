import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

/**
 * Eduardo Zamora-Melgoza
 * 4/24/26
 *
 *
 */

public class LoginScreen {

    private VBox layout;

    public LoginScreen(){
        createLoginScreen();
    }

    private void createLoginScreen(){
        Label titleLabel  = new Label("Student Productivity \n           Welcome!");
        titleLabel.setStyle(AppStyleManager.TITLE_STYLE);

        TextField usernameField = new TextField();
        usernameField.setPromptText("Username");
        usernameField.setMaxWidth(450);
        usernameField.setPrefWidth(450);
        usernameField.setPrefHeight(50);
        usernameField.setStyle(AppStyleManager.TEXT_FIELD_STYLE);

        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Password");
        passwordField.setMaxWidth(450);
        passwordField.setPrefWidth(450);
        passwordField.setPrefHeight(50);
        passwordField.setStyle(AppStyleManager.TEXT_FIELD_STYLE);

        Button loginButton = new Button("Log in");
        Button signUpButton = new Button("Sign up");

        loginButton.setPrefWidth(150);
        loginButton.setPrefHeight(50);

        signUpButton.setPrefWidth(150);
        signUpButton.setPrefHeight(50);

        AppStyleManager.applyButtonStyle(loginButton);
        AppStyleManager.applyButtonStyle(signUpButton);

        Label messageLabel = new Label();
        messageLabel.setStyle(AppStyleManager.MESSAGE_STYLE);

        loginButton.setOnAction(e -> {
            String username = usernameField.getText();
            String password = passwordField.getText();

            if(username.equals("EZM_Admin") && password.equals("Zamora")) {
            messageLabel.setText("Login Successful!");
            } else {
                messageLabel.setText(" Invalid Username or Password.\n                     Try again.");
            }
        });

        signUpButton.setOnAction(e -> {
            messageLabel.setText(" You are being redirected to the Sign Up page. ");
        });

        HBox buttonBox = new HBox(30, loginButton, signUpButton);
        buttonBox.setAlignment(Pos.CENTER);

        layout = new VBox(30);
        layout.setAlignment(Pos.CENTER);
        layout.setPadding(new Insets(30));
        layout.setStyle(AppStyleManager.BACKGROUND_STYLE);

        layout.getChildren().addAll(
                titleLabel,
                usernameField,
                passwordField,
                buttonBox,
                messageLabel
        );
    }

    public VBox getLayout(){
        return layout;
    }

}