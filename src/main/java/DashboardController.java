import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class DashboardController {
    private static final ObservableList<String> TASKS = FXCollections.observableArrayList(
            "Task 1",
            "Task 2",
            "Task 3"
    );

    private VBox layout;
    private Stage stage;

    public DashboardController(Stage stage) {
        this.stage = stage;
        createDashboard();
    }

    private void createDashboard() {
        Label titleLabel = new Label("Dashboard");
        titleLabel.setStyle(AppStyleManager.TITLE_STYLE);

        ListView<String> taskList = new ListView<>();
        taskList.setItems(TASKS);
        taskList.setPrefHeight(200);

        Button addItemButton = new Button("Add Item");
        addItemButton.setPrefWidth(150);
        addItemButton.setPrefHeight(40);
        AppStyleManager.applyButtonStyle(addItemButton);

        addItemButton.setOnAction(e -> {
            AddItemController addItemController = new AddItemController(stage);
            stage.getScene().setRoot(addItemController.getLayout());
        });

        Button logoutButton = new Button("Log out");
        logoutButton.setPrefWidth(150);
        logoutButton.setPrefHeight(40);
        AppStyleManager.applyButtonStyle(logoutButton);

        logoutButton.setOnAction(e -> {
            LoginScreen loginScreen = new LoginScreen(stage);
            stage.getScene().setRoot(loginScreen.getLayout());
        });

        layout = new VBox(20);
        layout.setAlignment(Pos.CENTER);
        layout.setPadding(new Insets(30));
        layout.setStyle(AppStyleManager.BACKGROUND_STYLE);

        layout.getChildren().addAll(
                titleLabel,
                taskList,
                addItemButton,
                logoutButton
        );
    }

    public VBox getLayout() {
        return layout;
    }

    public static void addTask(String task) {
        if (task != null && !task.isBlank()) {
            TASKS.add(task.trim());
        }
    }
}
