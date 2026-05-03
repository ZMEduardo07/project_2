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

        SceneManager manager = SceneManager.getInstance();
        manager.setStage(stage);
        manager.showLoginScreen();

        stage.setTitle("Student Productivity");
        stage.show();
    }

    public static void main(String[] args){
        DatabaseManager.initializeDatabase();
        launch();
    }
}