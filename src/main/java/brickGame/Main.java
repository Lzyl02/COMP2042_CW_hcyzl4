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

        GameModel model = new GameModel(); // 先创建模型
        GameView view = new GameView(root); // 接着创建视图
        GameController controller = new GameController(view); // 最后创建控制器

        // 设置模型、视图和控制器之间的关系
        model.setController(controller);
        controller.setModel(model);
        controller.setView(view);
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
