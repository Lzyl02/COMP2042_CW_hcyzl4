package brickGame;

import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import java.util.ArrayList;

public class GameView {
    private GameModel model; // 添加对 GameModel 的引用
    private Stage primaryStage;
    private Pane root;
    private Label scoreLabel, heartLabel, levelLabel;
    private Circle ballView; // 用于显示球的视图
    private Rectangle paddleView; // 用于显示挡板的视图
    private ArrayList<Rectangle> blockViews; // 用于显示方块的视图

    private Scene scene;

    public GameView(Stage primaryStage, GameModel model) {
        this.primaryStage = primaryStage;
        this.model = model;
        this.root = new Pane();
        this.blockViews = new ArrayList<>();
        initializeGameView(); // 初始化游戏视图
    }



    public void initializeGameView() {
        // 初始化 UI 元素
        initializeRoot();
        createGameElements();
        createAndShowScene();
    }

    private void initializeRoot() {
        root = new Pane();
        scoreLabel = new Label("Score: " + model.getScore());
        heartLabel = new Label("Heart: " + model.getHeart());
        levelLabel = new Label("Level: " + model.getLevel());
        // ... 设置标签的位置等 ...
    }

    private void createGameElements() {
        // 创建球、挡板和方块的视图
        ballView = new Circle(model.getBallX(), model.getBallY(), model.getBallRadius());
        paddleView = new Rectangle(model.getPaddleX(), model.getPaddleY(), model.getPaddleWidth(), model.getPaddleHeight());
        // ... 创建方块视图 ...
        addGameElementsToRoot(); // 将元素添加到根 Pane
    }

    private void addGameElementsToRoot() {
        root.getChildren().addAll(ballView, paddleView, scoreLabel, heartLabel, levelLabel);
        // ... 将方块视图添加到 root ...
    }

    private void createAndShowScene() {
        Scene scene = new Scene(root, model.getSceneWidth(), model.getSceneHeight());
        // ... 设置场景样式和事件处理 ...
        primaryStage.setTitle("Game");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    /**
     * 获取用于存放游戏元素的根 Pane。
     * @return 游戏的根 Pane。
     */
    public Pane getRootPane() {
        return root;
    }
    public void updateForNextLevel() {
        // 清除旧的方块和其他游戏元素，为新级别做准备
        // ... 更新 UI 以反映新级别 ...
    }

    // 添加一个方法来获取 Scene 对象
    public Scene getScene() {
        return this.scene;
    }



    public void showScore(int x, int y, int scoreDelta) {
        // 显示得分的逻辑
        new Score().show(x, y, scoreDelta, primaryStage); // 假设你有对 primaryStage 的引用
    }

    private void updateBlocks() {
        // Clear old block views
        blockViews.forEach(root.getChildren()::remove);
        blockViews.clear();

        // Add new block views based on the model's current blocks
        for (Block block : model.getBlocks()) {
            Rectangle blockView = new Rectangle(block.getX(), block.getY(), Block.getWidth(), Block.getHeight());
            blockView.setFill(block.getColors()); // Assuming Block has a getColor() method
            blockViews.add(blockView);
            root.getChildren().add(blockView);
        }
    }

    // ... 其他更新 UI 的方法 ...
}
