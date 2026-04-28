import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class HomeScreen {

    private VBox layout;
    private Stage stage;

    public HomeScreen(Stage stage) {
        this.stage = stage;
        createHomeScreen();
    }

    private void createHomeScreen() {
        Label titleLabel = new Label("Home Screen");
        titleLabel.setStyle(AppStyleManager.TITLE_STYLE);

        Label welcomeMessage = new Label("Welcome back to your productivity dashboard!");
        welcomeMessage.setStyle(AppStyleManager.MESSAGE_STYLE);

        Button logoutButton = new Button("Log out");
        logoutButton.setPrefWidth(150);
        logoutButton.setPrefHeight(50);
        AppStyleManager.applyButtonStyle(logoutButton);

        logoutButton.setOnAction(e -> {
            LoginScreen loginScreen = new LoginScreen(stage);
            stage.getScene().setRoot(loginScreen.getLayout());
        });

        layout = new VBox(30);
        layout.setAlignment(Pos.CENTER);
        layout.setPadding(new Insets(30));
        layout.setStyle(AppStyleManager.BACKGROUND_STYLE);

        layout.getChildren().addAll(
                titleLabel,
                welcomeMessage,
                logoutButton
        );
    }

    public VBox getLayout() {
        return layout;
    }
}
