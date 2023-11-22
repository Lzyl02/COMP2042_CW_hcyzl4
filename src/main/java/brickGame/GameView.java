package brickGame;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import java.util.ArrayList;

public class GameView {

    private int level = 0;
    private double xBreak = 0.0f;
    private double centerBreakX;
    private double yBreak = 640.0f;
    private int breakWidth = 130;
    private int breakHeight = 30;
    private int halfBreakWidth = breakWidth / 2;
    public int sceneWidth = 500;
    public int sceneHeight = 700;
    private static int LEFT = 1;
    private static int RIGHT = 2;

    // 游戏元素
    private Circle ball;
    private double xBall;
    private double yBall;
    private Rectangle rect;
    private int ballRadius = 10;
    private int heart = 3;
    private int score = 0;
    private long time = 0;
    private long hitTime = 0;
    private long goldTime = 0;

    // 游戏状态
    private boolean isGoldStatus = false;
    private boolean isExistHeartBlock = false;
    private int destroyedBlockCount = 0;
    private double v = 1.000;
    private boolean loadFromSave = false;

    // 存档路径
    public static String savePath = "D:/save/save.mdds";
    public static String savePathDir = "D:/save/";

    // 游戏元素列表
    private ArrayList<Block> blocks = new ArrayList<Block>();
    private ArrayList<Bonus> chocos = new ArrayList<Bonus>();
    private Color[] colors = new Color[] {
            Color.MAGENTA,
            Color.RED,
            Color.GOLD,
            Color.CORAL,
            Color.AQUA,
            Color.VIOLET,
            Color.GREENYELLOW,
            Color.ORANGE,
            Color.PINK,
            Color.SLATEGREY,
            Color.YELLOW,
            Color.TOMATO,
            Color.TAN,
    };

    // 游戏界面
    public Pane root;
    private Label scoreLabel;
    private Label heartLabel;
    private Label levelLabel;
    private Stage primaryStage;
    private Button load = null;
    private Button newGame = null;

    // 游戏引擎
    private GameEngine engine;


    //ball
    private boolean goDownBall                  = true;
    private boolean goRightBall                 = true;
    private boolean collideToBreak = false;
    private boolean collideToBreakAndMoveToRight = true;
    private boolean collideToRightWall = false;
    private boolean collideToLeftWall = false;
    private boolean collideToRightBlock = false;
    private boolean collideToBottomBlock = false;
    private boolean collideToLeftBlock = false;
    private boolean collideToTopBlock = false;

    private double vX = 1.000;
    private double vY = 1.000;


    public void updateForNextLevel() {
        // 更新界面以反映新的游戏级别
        // 例如，清除屏幕上的方块和奖励，显示新的方块等
        // ... UI 更新逻辑 ...
    }

    private void initializeRoot() {
        root = new Pane();
        scoreLabel = new Label("Score: " + score);
        levelLabel = new Label("Level: " + level);
        levelLabel.setTranslateY(20);
        heartLabel = new Label("Heart : " + heart);
        heartLabel.setTranslateX(sceneWidth - 70);
    }

    private void addGameElementsToRoot() {
        if (!loadFromSave) {
            root.getChildren().addAll(rect, ball, scoreLabel, heartLabel, levelLabel, newGame);
        } else {
            root.getChildren().addAll(rect, ball, scoreLabel, heartLabel, levelLabel);
        }
        for (Block block : blocks) {
            root.getChildren().add(block.rect);
        }
    }

    private void createAndShowScene() {
        Scene scene = new Scene(root, sceneWidth, sceneHeight);
        scene.getStylesheets().add("style.css");
        scene.setOnKeyPressed(this);

        primaryStage.setTitle("Game");
        primaryStage.setScene(scene);
        primaryStage.show();
    }


}
