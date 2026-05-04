
public class TodoItem {

    private String title;
    private boolean completed;

    public TodoItem(String title){
        this.title = title;
        this.completed = false;

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
            return" Task Completed " + title;
        }
        return " To Do " + title;
    }
}
