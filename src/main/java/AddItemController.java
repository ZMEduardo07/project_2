import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class AddItemController {
    private VBox layout;
    private Stage stage;

    public AddItemController(Stage stage) {
        this.stage = stage;
        createAddItemScreen();
    }

    private void createAddItemScreen() {
        Label titleLabel = new Label("Add Item");
        titleLabel.setStyle(AppStyleManager.TITLE_STYLE);

        TextField itemField = new TextField();
        itemField.setPromptText("Enter task/item...");
        itemField.setStyle(AppStyleManager.TEXT_FIELD_STYLE);
        itemField.setMaxWidth(250);

        Label messageLabel = new Label();
        messageLabel.setStyle(AppStyleManager.MESSAGE_STYLE);

        Button addButton = new Button("Add");
        addButton.setPrefWidth(150);
        addButton.setPrefHeight(40);
        AppStyleManager.applyButtonStyle(addButton);
        
        addButton.setOnAction(e -> {
            String item = itemField.getText();
            if (!item.isBlank()) {
                DashboardController.addTask(item);
                SceneManager.getInstance().showDashboardScreen();
            } else {
                messageLabel.setText("Item cannot be empty.");
            }
        });

        Button cancelButton = new Button("Cancel");
        cancelButton.setPrefWidth(150);
        cancelButton.setPrefHeight(40);
        AppStyleManager.applyButtonStyle(cancelButton);

        cancelButton.setOnAction(e -> {
            SceneManager.getInstance().showDashboardScreen();
        });

        layout = new VBox(20);
        layout.setAlignment(Pos.CENTER);
        layout.setPadding(new Insets(30));
        layout.setStyle(AppStyleManager.BACKGROUND_STYLE);

        layout.getChildren().addAll(
                titleLabel,
                itemField,
                addButton,
                cancelButton,
                messageLabel
        );
    }

    public VBox getLayout() {
        return layout;
    }
}
