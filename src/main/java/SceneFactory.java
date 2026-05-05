import javafx.scene.Scene;

public class SceneFactory {
    public static Scene create(SceneType sceneType) {
        return switch(sceneType) {
            case LOGIN -> new LoginController().buildScene();
            case SIGN_UP -> new SignUpController().buildScene();
            case DASHBOARD -> new DashboardController().buildScene();
            case ADD_ITEM -> new AddItemController().buildScene();
        };
    }
}
