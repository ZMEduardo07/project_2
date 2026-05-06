/**
 * Siberaja Nadar
 * 4/21/26
 * 
 * 
 */
public class TodoItem {

    private int id;
    private String title;
    private boolean completed;

    public TodoItem(String title) {
        this(0, title, false);
    }

    public TodoItem(int id, String title, boolean completed) {
        this.id = id;
        this.title = title;
        this.completed = completed;

    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void markCompleted() {
        completed = true;
    }

    @Override
    public String toString() {
        if (completed) {
            return " Task Completed " + title;
        }
        return " To Do " + title;
    }
}
