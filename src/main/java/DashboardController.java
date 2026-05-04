import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import java.time.LocalTime;

public class DashboardController {
    private static final ObservableList<String> TASKS = FXCollections.observableArrayList();

    private StackPane layout;
    private Stage stage;

    public DashboardController(Stage stage) {
        this.stage = stage;
        createDashboard();
    }

    private void createDashboard() {
        Label titleLabel = new Label(getGreeting());
        titleLabel.setStyle(AppStyleManager.TITLE_STYLE);

        Label subtitleLabel = new Label("Here are your tasks for today.");
        subtitleLabel.setStyle(AppStyleManager.SUBTITLE_STYLE);

        VBox greetingBox = new VBox(6);
        greetingBox.setAlignment(Pos.CENTER_LEFT);
        greetingBox.getChildren().addAll(titleLabel, subtitleLabel);

        ListView<String> taskList = new ListView<>();
        taskList.setItems(TASKS);
        taskList.setPrefHeight(360);
        taskList.setMaxWidth(720);
        taskList.setPlaceholder(createEmptyTaskLabel());
        taskList.setStyle(AppStyleManager.TASK_LIST_STYLE);
        taskList.setCellFactory(listView -> new ListCell<>() {
            @Override
            protected void updateItem(String task, boolean empty) {
                super.updateItem(task, empty);

                if (empty || task == null) {
                    setText(null);
                    setStyle("-fx-background-color: transparent; -fx-padding: 6px 0;");
                } else {
                    setText(task);
                    setStyle(AppStyleManager.TASK_CELL_STYLE + "-fx-background-insets: 6px 0;");
                }
            }
        });

        Button addItemButton = new Button("+");
        AppStyleManager.applyFloatingButtonStyle(addItemButton);

        addItemButton.setOnAction(e -> {
            SceneManager.getInstance().showAddItemScreen();
        });

        Button logoutButton = new Button("Log out");
        logoutButton.setPrefWidth(120);
        logoutButton.setPrefHeight(38);
        AppStyleManager.applyButtonStyle(logoutButton);

        logoutButton.setOnAction(e -> {
            SceneManager.getInstance().showLoginScreen();
        });

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        HBox topRow = new HBox(20);
        topRow.setAlignment(Pos.CENTER);
        topRow.setMaxWidth(760);
        topRow.getChildren().addAll(greetingBox, spacer, logoutButton);

        VBox content = new VBox(18);
        content.setAlignment(Pos.TOP_CENTER);
        content.setPadding(new Insets(45, 30, 30, 30));
        content.setStyle(AppStyleManager.BACKGROUND_STYLE);
        content.getChildren().addAll(
                topRow,
                taskList
        );

        layout = new StackPane();
        layout.setPadding(new Insets(30));
        layout.setStyle(AppStyleManager.BACKGROUND_STYLE);
        layout.getChildren().addAll(content, addItemButton);
        StackPane.setAlignment(addItemButton, Pos.BOTTOM_RIGHT);
        StackPane.setMargin(addItemButton, new Insets(0, 35, 35, 0));
    }

    public StackPane getLayout() {
        return layout;
    }

    public static void addTask(String task) {
        if (task != null && !task.isBlank()) {
            TASKS.add(task.trim());
        }
    }

    private String getGreeting() {
        int hour = LocalTime.now().getHour();

        if (hour < 12) {
            return "Good morning";
        } else if (hour < 18) {
            return "Good afternoon";
        }

        return "Good evening";
    }

    private Label createEmptyTaskLabel() {
        Label emptyLabel = new Label("No tasks yet");
        emptyLabel.setStyle(AppStyleManager.EMPTY_TASK_STYLE);
        emptyLabel.setMaxWidth(720);
        emptyLabel.setAlignment(Pos.CENTER);
        return emptyLabel;
    }
}
