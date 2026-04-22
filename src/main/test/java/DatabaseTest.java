import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class DatabaseTest {
    private static final Path DATABASE_PATH = Path.of("users.db");

    private AuthService authService;

    @BeforeEach
    void setUp() throws IOException {
        Files.deleteIfExists(DATABASE_PATH);
        DatabaseManager.initializeDatabase();
        authService = new AuthService();
    }

    @AfterEach
    void tearDown() throws IOException {
        Files.deleteIfExists(DATABASE_PATH);
    }

    @Test
    void createUserInsertsNewRecord() {
        boolean created = authService.registerUser("sibe", "mypassword123");

        assertTrue(created);
    }

    @Test
    void readUserAllowsLoginWithCorrectPassword() {
        authService.registerUser("sibe", "mypassword123");

        boolean loggedIn = authService.loginUser("sibe", "mypassword123");

        assertTrue(loggedIn);
    }

    @Test
    void updateUserChangesTheStoredPassword() {
        authService.registerUser("sibe", "mypassword123");

        boolean updated = authService.updatePassword("sibe", "newpassword456");
        boolean oldPasswordWorks = authService.loginUser("sibe", "mypassword123");
        boolean newPasswordWorks = authService.loginUser("sibe", "newpassword456");

        assertTrue(updated);
        assertFalse(oldPasswordWorks);
        assertTrue(newPasswordWorks);
    }

    @Test
    void deleteUserRemovesTheRecord() {
        authService.registerUser("sibe", "mypassword123");

        boolean deleted = authService.deleteUser("sibe");
        boolean canStillLogIn = authService.loginUser("sibe", "mypassword123");

        assertTrue(deleted);
        assertFalse(canStillLogIn);
    }
}
