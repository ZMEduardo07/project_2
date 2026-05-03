import javafx.scene.Scene;
import javafx.stage.Stage;


public class SceneManager {

    private static SceneManager instance;
    private Stage stage;

    private SceneManager() {

    }

        public static SceneManager getInstance () {
            if (instance == null) {
                instance = new SceneManager();
            }
            return instance;
        }

        public void setStage(Stage stage) {
            this.stage = stage;

        }

        public void navigateTo(SceneType sceneType) {
        Scene scene = SceneFactory.create(sceneType);
        stage.setScene(scene);
        }

        public void navigateFresh(SceneType sceneType) {
        Scene scene = SceneFactory.create(sceneType);
        stage.setScene(scene);
        }






}
