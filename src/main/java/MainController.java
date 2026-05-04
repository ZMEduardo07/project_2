import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class MainController {

    public Scene buildScene() {
        Label titleLabel = new Label("Student Productivity\n Welcome!");
        titleLabel.setStyle(AppStyleManager.TITLE_STYLE);

        Button startButton = new Button("Go to Dashboard");
        startButton.setPrefWidth(230);
        startButton.setPrefHeight(50);
        AppStyleManager.applyButtonStyle(startButton);

        startButton.setOnAction(event -> {
            SceneManager.getInstance().navigateTo(SceneType.DASHBOARD);
        });

        VBox layout = new VBox(30);
        layout.setAlignment(Pos.CENTER);
        layout.setPadding(new Insets(30));
        layout.setStyle(AppStyleManager.BACKGROUND_STYLE);
        layout.getChildren().addAll(titleLabel, startButton);

        return new Scene(layout, 600, 600);

    }


}
