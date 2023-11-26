package brickGame;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class Main extends Application {
    @Override
    public void start(Stage primaryStage) {
        Pane root = new Pane();
        Scene scene = new Scene(root, 500, 700); // Scene dimensions as constants or configurable values

        GameModel model = new GameModel(); // Create the model
        GameView view = new GameView(root); // Create the view with the root pane
        GameController controller = new GameController(view); // Create the controller with the view

        model.setController(controller); // Set the controller in the model

        controller.setModel(model); // Set the model in the controller
        controller.setView(view); // Set the view in the controller
        view.setController(controller);

//        controller.startGame(); // Start the game logic

        primaryStage.setScene(scene);
        primaryStage.setTitle("Brick Game");
        primaryStage.show();

        scene.setOnKeyPressed(controller); // Set up event listeners
    }

    public static void main(String[] args) {
        launch(args);
    }
}
