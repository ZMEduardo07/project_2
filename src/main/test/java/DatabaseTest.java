import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Siberaja Nadar
 * 4/21/26
 * 
 * 
 */

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

    @Test
    void initializeDatabaseCreatesTaskTables() throws SQLException {
        try (Connection conn = DatabaseManager.connect()) {
            assertTrue(tableExists(conn, "tasks"));
            assertTrue(tableExists(conn, "task_steps"));
        }
    }

    @Test
    void todoRepositoryPersistsTasksAndSteps() {
        TodoRepository repository = TodoRepository.getInstance();

        repository.addTodo("cook chicken rice");
        TodoItem savedTask = repository.getTodos().get(0);
        List<String> steps = List.of(
                "Open the rice bag.",
                "Take out the chicken.",
                "Place a pan on the stove."
        );

        repository.saveTaskSteps(savedTask, steps);

        assertEquals("cook chicken rice", repository.getTodos().get(0).getTitle());
        assertEquals(steps, repository.getTaskSteps(savedTask));
    }

    private boolean tableExists(Connection conn, String tableName) throws SQLException {
        String sql = "SELECT name FROM sqlite_master WHERE type = 'table' AND name = ?";

        try (var pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, tableName);

            try (ResultSet rs = pstmt.executeQuery()) {
                return rs.next();
            }
        }
    }
}
