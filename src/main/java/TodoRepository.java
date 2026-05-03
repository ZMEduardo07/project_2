import java.util.ArrayList;
import java.util.List;

public class TodoRepository {

    private static TodoRepository instance;

    private final List<TodoItem> todos = new ArrayList<>();
    private final List<TodoObserver> observers = new ArrayList<>();

    private TodoRepository() {

    }

    private static TodoRepository getInstance() {
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
        return new ArrayList<>(todos);
    }

    public void addTodo(String title) {
        if (title == null || title.isBlank()){
            return;
        }

        todos.add(new TodoItem(title));
        notifyObservers();
    }

    public void markCompleted(TodoItem item) {
        if (item == null) {
            item.markCompleted();
            notifyObservers();
        }
    }

    public void deleteTodo(TodoItem item) {
        todos.remove(item);
        notifyObservers();
    }

    private void notifyObservers() {
        for (TodoObserver observer : observers) {
            observer.update();
        }
    }

}
