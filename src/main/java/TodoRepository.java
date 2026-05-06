import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class TodoRepository {

    private static TodoRepository instance;

    private final List<TodoObserver> observers = new ArrayList<>();

    private TodoRepository() {

    }

    public static TodoRepository getInstance() {
        if (instance == null) {
            instance = new TodoRepository();
        }
        return instance;
    }

    public void addObserver(TodoObserver observer) {
        observers.add(observer);
    }

    public void removeObserver(TodoObserver observer) {
        observers.remove(observer);
    }

    public List<TodoItem> getTodos() {
        List<TodoItem> todos = new ArrayList<>();
        String sql = "SELECT id, title, completed FROM tasks ORDER BY id ASC";

        try (Connection conn = DatabaseManager.connect();
                PreparedStatement pstmt = conn.prepareStatement(sql);
                ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                todos.add(new TodoItem(
                        rs.getInt("id"),
                        rs.getString("title"),
                        rs.getInt("completed") == 1
                ));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return todos;
    }

    public void addTodo(String title) {
        if (title == null || title.isBlank()){
            return;
        }

        String sql = "INSERT INTO tasks (title, completed) VALUES (?, 0)";

        try (Connection conn = DatabaseManager.connect();
                PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setString(1, title.trim());
            pstmt.executeUpdate();
            notifyObservers();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void markCompleted(TodoItem item) {
        if (item == null) {
            return;
        }

        String sql = "UPDATE tasks SET completed = 1 WHERE id = ?";

        try (Connection conn = DatabaseManager.connect();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, item.getId());
            pstmt.executeUpdate();
            item.markCompleted();
            notifyObservers();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void deleteTodo(TodoItem item) {
        if (item == null) {
            return;
        }

        String sql = "DELETE FROM tasks WHERE id = ?";

        try (Connection conn = DatabaseManager.connect();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, item.getId());
            pstmt.executeUpdate();
            notifyObservers();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public List<String> getTaskSteps(TodoItem item) {
        List<String> steps = new ArrayList<>();

        if (item == null) {
            return steps;
        }

        String sql = """
                SELECT description
                FROM task_steps
                WHERE task_id = ?
                ORDER BY step_number
                """;

        try (Connection conn = DatabaseManager.connect();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, item.getId());

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    steps.add(rs.getString("description"));
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return steps;
    }

    public void saveTaskSteps(TodoItem item, List<String> steps) {
        if (item == null || steps == null || steps.isEmpty()) {
            return;
        }

        String deleteSql = "DELETE FROM task_steps WHERE task_id = ?";
        String insertSql = """
                INSERT INTO task_steps (task_id, step_number, description)
                VALUES (?, ?, ?)
                """;

        try (Connection conn = DatabaseManager.connect()) {
            conn.setAutoCommit(false);

            try (PreparedStatement deleteStatement = conn.prepareStatement(deleteSql)) {
                deleteStatement.setInt(1, item.getId());
                deleteStatement.executeUpdate();
            }

            try (PreparedStatement insertStatement = conn.prepareStatement(insertSql)) {
                for (int index = 0; index < steps.size(); index++) {
                    insertStatement.setInt(1, item.getId());
                    insertStatement.setInt(2, index + 1);
                    insertStatement.setString(3, steps.get(index));
                    insertStatement.addBatch();
                }

                insertStatement.executeBatch();
            }

            conn.commit();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    private void notifyObservers() {
        for (TodoObserver observer : observers) {
            observer.update();
        }
    }

}
