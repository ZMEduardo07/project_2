import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import javafx.application.Platform;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import org.junit.jupiter.api.*;

public class UITest {

    private Button testButton;
    private LoginScreen loginScreen;
    private SignUpScreen signUpScreen;
    @BeforeAll
    static void startJavaFX(){
        Platform.startup(() -> {});
    }

    @BeforeEach
    void setUp(){
        testButton = new Button("Test");
        loginScreen = new LoginScreen(null);
        signUpScreen = new SignUpScreen(null);
    }

    @AfterEach
    void tearDown(){
        testButton = null;
        loginScreen = null;
        signUpScreen = null;
    }

    @Test
    void LoginScreenIsNotNull(){
        assertNotNull(loginScreen.getLayout());
    }

    @Test
    void signUpScreenHasChildren(){
        assertTrue(signUpScreen.getLayout().getChildren().size() > 0);
    }

    @Test
    void backgroundStyleContainsGradient(){
        assertTrue(
                AppStyleManager.BACKGROUND_STYLE.contains("linear-gradient"));
    }

    @Test
    void applyButtonStyleSetsNormalStyle(){
        AppStyleManager.applyButtonStyle(testButton);
        assertEquals(AppStyleManager.NORMAL_BUTTON_STYLE, testButton.getStyle());
    }

    @Test
    void hoverButtonStyleIsDifferentFromNormalStyle(){
        assertNotEquals(
                AppStyleManager.NORMAL_BUTTON_STYLE,
                AppStyleManager.HOVER_BUTTON_STYLE
        );
    }

    @Test
    void loginScreenUsesVBoxLayout(){
        assertEquals(VBox.class, loginScreen.getLayout().getClass());
    }

    @Test
    void loginScreenHasFiveElements(){
        assertEquals(5, loginScreen.getLayout().getChildren().size());
    }

    @Test
    void signUpScreenUsesVBoxLayout(){
        assertEquals(VBox.class, signUpScreen.getLayout().getClass());
    }

    @Test
    void signUpScreenHasSevenElements(){
        assertEquals(7, signUpScreen.getLayout().getChildren().size());
    }

    @Test
    void screensUseSameBackgroundStyle(){
        assertEquals(
                loginScreen.getLayout().getStyle(),
                signUpScreen.getLayout().getStyle()
        );
    }
}
