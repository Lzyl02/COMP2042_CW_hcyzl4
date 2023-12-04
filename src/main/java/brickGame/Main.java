package brickGame;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

/**
 * The main class of the JavaFX application.
 * This class is responsible for creating the main window of the game and initializing the game's model, view, and controller.
 * It also handles keyboard input and forwards it to the game controller.
 */
public class Main extends Application {
    /**
     * Starts the main stage of the application.
     * This method sets up the initial scene and layout of the application, creates the game model, view, and controller, and establishes their relationships.
     * It is also responsible for displaying the main window and setting up the keyboard event listener.
     *
     * @param primaryStage The primary stage of the application.
     */
    @Override
    public void start(Stage primaryStage) {
        Pane root = new Pane();
        Scene scene = new Scene(root, 500, 700); // Scene dimensions as constants or configurable values

        GameModel model = new GameModel(); // 先创建模型
        GameView view = new GameView(root); // 接着创建视图
        GameController controller = new GameController(view, model); // 最后创建控制器

        // 设置模型、视图和控制器之间的关系
//        model.setController(controller);
//        controller.setModel(model);
//        controller.setView(view);
        view.setController(controller);

        // 可以启动游戏逻辑
        // controller.startGame();

        primaryStage.setScene(scene);
        primaryStage.setTitle("Brick Game");
        primaryStage.show();

        scene.setOnKeyPressed(controller); // 设置键盘事件监听器
    }




    public static void main(String[] args) {
        launch(args);
    }
}
