import javafx.application.Application;
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
        DatabaseManager.initializeDatabase();

        SceneManager.getInstance().setStage(stage);
        SceneManager.getInstance().navigateTo(SceneType.LOGIN);

        stage.setTitle("Student Productivity");
        stage.show();
    }

    public static void main(String[] args){
        launch();
    }
}