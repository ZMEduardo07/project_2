import javafx.scene.Scene;

public class SceneFactory {
    public static Scene create(SceneType sceneType) {
        return switch(sceneType) {
            case MAIN -> new MainController().buildScene();
            case ADD_ITEM -> new AddItemController().buildScene();
            case DASHBOARD -> new DashboardController().buildScene();
        };
    }
}
