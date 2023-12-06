package brickGame;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

/**
 * The main class of the JavaFX application.
 * This class is responsible for creating the main window of the game and initializing the game's model, view, and controller.
 * It also handles keyboard input and forwards it to the game controller.
 * @see <a href="https://github.com/Lzyl02/COMP2042_CW_hcyzl4/blob/main/src/main/java/brickGame/Main.java">Main.java</a>
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
        GameView view = new GameView(root,model); // 接着创建视图
        GameController controller = new GameController(root,view,model); // 最后创建控制器

        view.setController(controller);



        primaryStage.setScene(scene);
        primaryStage.setTitle("Brick Game");
        primaryStage.show();

        scene.setOnKeyPressed(controller); // 设置键盘事件监听器
    }


    /**
     * The main entry point for the JavaFX application.
     * This method launches the JavaFX application.
     *
     * @param args Command-line arguments passed to the application.
     */
    public static void main(String[] args) {
        launch(args);
    }
}
