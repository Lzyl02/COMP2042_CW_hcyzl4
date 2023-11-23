package brickGame;

import brickGame.Block;
import brickGame.Bonus;
import brickGame.GameEngine;
import javafx.application.Application;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import java.io.*;
import java.util.ArrayList;
import java.util.Random;
import javafx.scene.input.KeyEvent;

import static javafx.application.Application.launch;


public class GameController {
    private GameModel model;
    private GameView view;
    private GameEngine engine;


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
    public GameController(GameModel model, GameView view) {
        this.model = model;
        this.view = view;
        setupGameEngine();
    }

    private void setupGame() {
        // 初始化游戏状态和界面
    }

    public void nextLevel() {
        model.nextLevel();  // 更新游戏逻辑状态

        // 在 UI 上反映这些变化
        view.updateForNextLevel();

        // 可能需要重新启动游戏引擎或更新场景
        // ... UI 更新逻辑 ...
    }
    private void move(final int direction) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                int sleepTime = 4;
                for (int i = 0; i < 30; i++) {
                    if (xBreak == (sceneWidth - breakWidth) && direction == RIGHT) {
                        return;
                    }
                    if (xBreak == 0 && direction == LEFT) {
                        return;
                    }
                    if (direction == RIGHT) {
                        xBreak++;
                    } else {
                        xBreak--;
                    }
                    centerBreakX = xBreak + halfBreakWidth;
                    try {
                        Thread.sleep(sleepTime);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    if (i >= 20) {
                        sleepTime = i;
                    }
                }
            }
        }).start();


    }

//    private void saveGame() {
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                new File(savePathDir).mkdirs();
//                File file = new File(savePath);
//                ObjectOutputStream outputStream = null;
//                try {
//                    outputStream = new ObjectOutputStream(new FileOutputStream(file));
//
//                    outputStream.writeInt(level);
//                    outputStream.writeInt(score);
//                    outputStream.writeInt(heart);
//                    outputStream.writeInt(destroyedBlockCount);
//
//
//                    outputStream.writeDouble(xBall);
//                    outputStream.writeDouble(yBall);
//                    outputStream.writeDouble(xBreak);
//                    outputStream.writeDouble(yBreak);
//                    outputStream.writeDouble(centerBreakX);
//                    outputStream.writeLong(time);
//                    outputStream.writeLong(goldTime);
//                    outputStream.writeDouble(vX);
//
//
//                    outputStream.writeBoolean(isExistHeartBlock);
//                    outputStream.writeBoolean(isGoldStatus);
//                    outputStream.writeBoolean(goDownBall);
//                    outputStream.writeBoolean(goRightBall);
//                    outputStream.writeBoolean(collideToBreak);
//                    outputStream.writeBoolean(collideToBreakAndMoveToRight);
//                    outputStream.writeBoolean(collideToRightWall);
//                    outputStream.writeBoolean(collideToLeftWall);
//                    outputStream.writeBoolean(collideToRightBlock);
//                    outputStream.writeBoolean(collideToBottomBlock);
//                    outputStream.writeBoolean(collideToLeftBlock);
//                    outputStream.writeBoolean(collideToTopBlock);
//
//                    ArrayList<BlockSerializable> blockSerializables = new ArrayList<BlockSerializable>();
//                    for (Block block : blocks) {
//                        if (block.isDestroyed) {
//                            continue;
//                        }
//                        blockSerializables.add(new BlockSerializable(block.row, block.column, block.type));
//                    }
//
//                    outputStream.writeObject(blockSerializables);
//
//                    new Score().showMessage("Game Saved", Main.this);
//
//
//                } catch (FileNotFoundException e) {
//                    e.printStackTrace();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                } finally {
//                    try {
//                        outputStream.flush();
//                        outputStream.close();
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
//                }
//            }
//        }).start();
//
//    }

    public void handle(KeyEvent event) {
        switch (event.getCode()) {
            case LEFT:
                move(LEFT);
                break;
            case RIGHT:

                move(RIGHT);
                break;
            case DOWN:
                //setPhysicsToBall();
                break;
            case S:
//                saveGame();
                break;
        }


    // ... 其他方法 ...
}

    private void setupEventHandlers(Stage stage) {
        // 设置键盘和鼠标事件处理
        stage.addEventHandler(KeyEvent.KEY_PRESSED, this::handle);
    }

    private void setupGameEngine() {
        engine = new GameEngine();
        engine.setOnAction(new GameEngine.OnAction() {
            @Override
            public void onUpdate() {
                GameController.this.onUpdate();
            }

            @Override
            public void onPhysicsUpdate() {
                GameController.this.onPhysicsUpdate();
            }

            @Override
            public void onTime(long time) {
                GameController.this.onTime(time);
            }

            @Override
            public void onInit() {
                GameController.this.onInit();
            }
        });
        engine.setFps(120);
        engine.start();
    }

//    private void loadGame() {
//
//        LoadSave loadSave = new LoadSave();
//        loadSave.read();
//
//
//        isExistHeartBlock = loadSave.isExistHeartBlock;
//        isGoldStatus = loadSave.isGoldStatus;
//        goDownBall = loadSave.goDownBall;
//        goRightBall = loadSave.goRightBall;
//        collideToBreak = loadSave.collideToBreak;
//        collideToBreakAndMoveToRight = loadSave.collideToBreakAndMoveToRight;
//        collideToRightWall = loadSave.collideToRightWall;
//        collideToLeftWall = loadSave.collideToLeftWall;
//        collideToRightBlock = loadSave.collideToRightBlock;
//        collideToBottomBlock = loadSave.collideToBottomBlock;
//        collideToLeftBlock = loadSave.collideToLeftBlock;
//        collideToTopBlock = loadSave.collideToTopBlock;
//        level = loadSave.level;
//        score = loadSave.score;
//        heart = loadSave.heart;
//        destroyedBlockCount = loadSave.destroyedBlockCount;
//        xBall = loadSave.xBall;
//        yBall = loadSave.yBall;
//        xBreak = loadSave.xBreak;
//        yBreak = loadSave.yBreak;
//        centerBreakX = loadSave.centerBreakX;
//        time = loadSave.time;
//        goldTime = loadSave.goldTime;
//        vX = loadSave.vX;
//
//        blocks.clear();
//        chocos.clear();
//
//        for (BlockSerializable ser : loadSave.blocks) {
//            int r = new Random().nextInt(200);
//            blocks.add(new Block(ser.row, ser.j, colors[r % colors.length], ser.type));
//        }
//
//
//        try {
//            loadFromSave = true;
//            start(primaryStage);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//
//    }

    private void handleGameStartActions() {
        if (level > 1 && level < 18) {
            load.setVisible(false);
            newGame.setVisible(false);
            setupGameEngine();
        }
        setupButtonActions();
    }

    private void setupButtonActions() {
        load.setOnAction(event -> {
//            loadGame();
            load.setVisible(false);
            newGame.setVisible(false);
        });

        newGame.setOnAction(event -> {
            engine = new GameEngine();
            engine.setOnAction(new GameEngine.OnAction() {
                @Override
                public void onUpdate() {
                    GameController.this.onUpdate();
                }

                @Override
                public void onPhysicsUpdate() {
                    GameController.this.onPhysicsUpdate();
                }

                @Override
                public void onTime(long time) {
                    GameController.this.onTime(time);
                }

                @Override
                public void onInit() {
                    GameController.this.onInit();
                }
            });
            engine.setFps(120);
            engine.start();
            load.setVisible(false);
            newGame.setVisible(false);
        });
    }

//    private void handleGameFromSave() {
//        engine = new GameEngine();
//        engine.setOnAction(this);
//        engine.setFps(120);
//        engine.start();
//        loadFromSave = false;
//    }
    // 更新视图

    // Delegate methods to model
    private void onUpdate() {
        model.onUpdate();
        view.updateView(); // 在模型更新后调用视图更新

        // Update view if necessary
    }

    public Color[] getcolors() {
        return colors;
    }

    private void onPhysicsUpdate() {
        model.onPhysicsUpdate();
        view.updateView(); // 在模型更新后调用视图更新

        // Update view if necessary
    }

    private void onTime(long time) {
        model.onTime(time);
        view.updateView(); // 在模型更新后调用视图更新

        // Update view if necessary
    }

    private void onInit() {
        model.onInit();
        view.updateView(); // 在模型更新后调用视图更新

        // Update view if necessary
    }



}



