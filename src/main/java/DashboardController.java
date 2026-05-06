import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import java.time.LocalTime;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class DashboardController implements TodoObserver {
    private ListView<TodoItem> taskList;
    private final TodoRepository repository;
    private final TaskInitiationService taskInitiationService;
    private ProgressBar taskProgressBar;
    private Label progressLabel;
    private VBox startStepsBox;


    public DashboardController(){
        repository = TodoRepository.getInstance();
        taskInitiationService = new TaskInitiationService();
        repository.addObserver(this);
    }

    public Scene buildScene() {
        Label titleLabel = new Label(getGreeting());
        titleLabel.setStyle(AppStyleManager.TITLE_STYLE);

        Label subtitleLabel = new Label("Here are your tasks for today.");
        subtitleLabel.setStyle(AppStyleManager.SUBTITLE_STYLE);

        VBox greetingBox = new VBox(6);
        greetingBox.setAlignment(Pos.CENTER_LEFT);
        greetingBox.getChildren().addAll(titleLabel, subtitleLabel);

        taskList = new ListView<>();
        taskList.setPrefHeight(220);
        taskList.setMaxWidth(720);
        taskList.setPlaceholder(createEmptyTaskLabel());
        taskList.setStyle(AppStyleManager.TASK_LIST_STYLE);
        taskList.getSelectionModel().selectedItemProperty().addListener((observable, oldTask, selectedTask) -> {
            showStartSteps(selectedTask);
        });

        taskProgressBar = new ProgressBar(0);
        taskProgressBar.setPrefWidth(720);

        progressLabel = new Label("0% complete");
        progressLabel.setStyle(AppStyleManager.MESSAGE_STYLE);


        Button completeButton = new Button("Complete");
        completeButton.setPrefWidth(120);
        completeButton.setPrefHeight(38);
        AppStyleManager.applyButtonStyle(completeButton);

        completeButton.setOnAction(e -> {
            TodoItem selectedTask = taskList.getSelectionModel().getSelectedItem();
            repository.markCompleted(selectedTask);
        });

        taskList.setCellFactory(listView -> new ListCell<>(){



            @Override
            protected void updateItem(TodoItem task, boolean empty) {
                super.updateItem(task, empty);

                if (empty || task == null) {
                    setText(null);
                    setStyle("-fx-background-color: transparent; -fx-padding: 6px 0;");
                } else {
                    setText(task.toString());
                    setStyle(AppStyleManager.TASK_CELL_STYLE + "-fx-background-insets: 6px 0;");
                }
            }
        });
        refreshTasks();

        startStepsBox = new VBox(8);
        startStepsBox.setMaxWidth(720);
        startStepsBox.setStyle(AppStyleManager.START_STEPS_PANEL_STYLE);
        startStepsBox.setVisible(false);
        startStepsBox.setManaged(false);

        Button addItemButton = new Button("+");
        AppStyleManager.applyFloatingButtonStyle(addItemButton);

        addItemButton.setOnAction(e -> {
            SceneManager.getInstance().navigateTo(SceneType.ADD_ITEM);
        });

        Button logoutButton = new Button("Log out");
        logoutButton.setPrefWidth(120);
        logoutButton.setPrefHeight(38);
        AppStyleManager.applyButtonStyle(logoutButton);

        logoutButton.setOnAction(e -> {
            SceneManager.getInstance().navigateTo(SceneType.LOGIN);
        });

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        HBox topRow = new HBox(20);
        topRow.setAlignment(Pos.CENTER);
        topRow.setMaxWidth(760);
        topRow.getChildren().addAll(greetingBox, spacer, completeButton, logoutButton);

        VBox content = new VBox(14);
        content.setAlignment(Pos.TOP_CENTER);
        content.setPadding(new Insets(32, 30, 25, 30));
        content.setStyle(AppStyleManager.BACKGROUND_STYLE);
        content.getChildren().addAll(
                topRow,
                taskProgressBar,
                progressLabel,
                taskList,
                startStepsBox
        );

        StackPane layout = new StackPane();
        layout.setPadding(new Insets(30));
        layout.setStyle(AppStyleManager.BACKGROUND_STYLE);
        layout.getChildren().addAll(content, addItemButton);
        StackPane.setAlignment(addItemButton, Pos.BOTTOM_RIGHT);
        StackPane.setMargin(addItemButton, new Insets(0, 35, 35, 0));

        return new Scene(layout, 600, 600);
    }

    private void refreshTasks() {
        taskList.getItems().clear();
        taskList.getItems().addAll(repository.getTodos());

        int totalTasks = repository.getTodos().size();
        int completedTasks = 0;

        for(TodoItem item : repository.getTodos()){
            if(item.isCompleted()){
                completedTasks++;
            }
        }

        double progress = 0.0;
        if (totalTasks > 0){
            progress = (double) completedTasks / totalTasks;
        }
        taskProgressBar.setProgress(progress);
        int percent = (int)(progress * 100);
        progressLabel.setText(percent + "% complete");

    }

    private void showStartSteps(TodoItem task) {
        if (startStepsBox == null) {
            return;
        }

        startStepsBox.getChildren().clear();

        if (task == null) {
            startStepsBox.setVisible(false);
            startStepsBox.setManaged(false);
            return;
        }

        showLoadingSteps(task);

        CompletableFuture
                .supplyAsync(() -> getOrCreateStartSteps(task))
                .whenComplete((steps, error) -> Platform.runLater(() -> {
                    if (taskList.getSelectionModel().getSelectedItem() != task) {
                        return;
                    }

                    if (error != null) {
                        showStartStepsError(task, getErrorMessage(error));
                    } else {
                        showStartSteps(task, steps);
                    }
                }));
    }

    private List<String> getOrCreateStartSteps(TodoItem task) {
        List<String> savedSteps = repository.getTaskSteps(task);

        if (savedSteps.size() == 3) {
            return savedSteps;
        }

        List<String> generatedSteps = taskInitiationService.createStartSteps(task.getTitle());
        repository.saveTaskSteps(task, generatedSteps);
        return generatedSteps;
    }

    private void showLoadingSteps(TodoItem task) {
        Label startTitle = new Label("Start: " + task.getTitle());
        startTitle.setStyle(AppStyleManager.START_STEPS_TITLE_STYLE);
        startTitle.setWrapText(true);
        startTitle.setMaxWidth(680);

        Label loadingLabel = new Label("Getting Google AI start steps...");
        loadingLabel.setStyle(AppStyleManager.START_STEP_LABEL_STYLE);
        loadingLabel.setWrapText(true);
        loadingLabel.setMaxWidth(680);

        startStepsBox.getChildren().addAll(startTitle, loadingLabel);
        startStepsBox.setVisible(true);
        startStepsBox.setManaged(true);
    }

    private void showStartSteps(TodoItem task, List<String> steps) {
        startStepsBox.getChildren().clear();

        Label startTitle = new Label("Start: " + task.getTitle());
        startTitle.setStyle(AppStyleManager.START_STEPS_TITLE_STYLE);
        startTitle.setWrapText(true);
        startTitle.setMaxWidth(680);

        startStepsBox.getChildren().add(startTitle);

        int stepNumber = 1;
        for (String step : steps) {
            Label stepLabel = new Label(stepNumber + ". " + step);
            stepLabel.setStyle(AppStyleManager.START_STEP_LABEL_STYLE);
            stepLabel.setWrapText(true);
            stepLabel.setMaxWidth(680);
            startStepsBox.getChildren().add(stepLabel);
            stepNumber++;
        }

        startStepsBox.setVisible(true);
        startStepsBox.setManaged(true);
    }

    private void showStartStepsError(TodoItem task, String message) {
        startStepsBox.getChildren().clear();

        Label startTitle = new Label("Start: " + task.getTitle());
        startTitle.setStyle(AppStyleManager.START_STEPS_TITLE_STYLE);
        startTitle.setWrapText(true);
        startTitle.setMaxWidth(680);

        Label errorLabel = new Label(message);
        errorLabel.setStyle(AppStyleManager.START_STEP_LABEL_STYLE);
        errorLabel.setWrapText(true);
        errorLabel.setMaxWidth(680);

        startStepsBox.getChildren().addAll(startTitle, errorLabel);
        startStepsBox.setVisible(true);
        startStepsBox.setManaged(true);
    }

    private String getErrorMessage(Throwable error) {
        Throwable currentError = error;

        while (currentError.getCause() != null) {
            currentError = currentError.getCause();
        }

        if (currentError.getMessage() == null || currentError.getMessage().isBlank()) {
            return "Could not get start steps from Google AI.";
        }

        return currentError.getMessage();
    }

    @Override
    public void update(){
        refreshTasks();
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
