import javafx.scene.Scene;
import javafx.stage.Stage;


public class SceneManager {

    private static SceneManager instance;
    private Stage stage;

    private SceneManager() {}

        public static SceneManager getInstance () {
            if (instance == null) {
                instance = new SceneManager();
            }
            return instance;
        }

        public void setStage(Stage stage) {
            this.stage = stage;

        }

        public void showLoginScreen() {
        LoginScreen loginScreen = new LoginScreen(stage);
        stage.setScene(new Scene(loginScreen.getLayout()));
        }

        public void showSignUpScreen() {
        SignUpScreen signUpScreen = new SignUpScreen(stage);
        stage.getScene().setRoot(signUpScreen.getLayout());
        }

        public void showHomeScreen() {
        HomeScreen homeScreen = new HomeScreen(stage);
        stage.getScene().setRoot(homeScreen.getLayout());
        }






}
