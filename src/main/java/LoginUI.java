import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Eduardo Zamora-Melgoza
 * 4/25/26
 *
 *
 */

public class LoginUI extends Application {

    @Override
    public void start(Stage stage) {
        LoginScreen loginScreen = new LoginScreen(stage);

        Scene scene = new Scene(loginScreen.getLayout(), 600, 600);

        stage.setTitle("Login");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args){
        DatabaseManager.initializeDatabase();
        launch();
    }
}