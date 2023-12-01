package brickGame;

import javafx.animation.AnimationTimer;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import static brickGame.GameModel.level;
import static brickGame.GameModel.score;


public class GameController implements EventHandler<KeyEvent> {
    Stage primaryStage;
    private final GameEngine engine;
    private GameModel model;

    public static String savePath = "D:/save/save.mdds";
    public static String savePathDir = "D:/save/";

    private int sceneWidth = 500;
    private double updateInterval = 1000000000.0 / 240.0; // Adjust the frame rate (240 FPS in this example)
    private long lastUpdateTime = 0;
    private int sceneHeight = 700;


    private double centerPaddleX;
    private double xBall;
    private double yBall;


    private AnimationTimer bonusUpdater;
    private AnimationTimer bombUpdater;

    private static boolean isExistHeartBlock = false;
    private ArrayList<Bonus> chocos;
    private ArrayList<Bombs> bombs = new ArrayList<>(); // 保存所有炸弹的列表



    // Define custom key release events

    private static Color[] colors = new Color[]{
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
    private GameView view;
    private boolean movingLeft = false;
    private boolean movingRight = false;

    public GameController(GameView view) {
        this.view = view;
        this.engine = new GameEngine();

        // Get the GameModel instance using the singleton pattern
        this.model = GameModel.getInstance();

        // Set this GameController instance in the model
        this.model.setController(this);

        // Set GameModel as the action handler for GameEngine
        // Initialization...
        this.chocos = new ArrayList<>();
    }



    public void initializeBonusUpdater() {
        bonusUpdater = new AnimationTimer() {
            @Override
            public void handle(long now) {
                List<Bonus> bonuses = model.getChocos();
                for (Bonus bonus : bonuses) {
                    bonus.fallDown(); // 更新位置
                    view.updateBonusPosition(bonus); // 反映位置更新到视图

                    if (shouldRemoveBonus(bonus)) {
                        view.removeBonus(bonus); // 从视图中移除
                    }
                }
            }
        };
        bonusUpdater.start();
    }
    private boolean shouldRemoveBonus(Bonus bonus) {
        // 示例逻辑: 如果 bonus 的 Y 坐标超过了屏幕底部或者它被标记为 taken，则应该移除
        return bonus.getY() > sceneHeight  || bonus.isTaken();
    }


    // 获取炸弹列表的方法

    public void initializeBombUpdater() {
        bombUpdater = new AnimationTimer() {
            @Override
            public void handle(long now) {
                Iterator<Bombs> iterator = model.getBombs().iterator();
                while (iterator.hasNext()) {
                    Bombs bomb = iterator.next();
                    bomb.fallDown(); // 更新位置
                    view.updateBombPosition(bomb); // 反映位置更新到视图

                    if (bomb.getY() >= sceneHeight || bomb.isTaken()) {
                        iterator.remove(); // 使用迭代器移除炸弹
                        view.removeBomb(bomb); // 从视图中移除炸弹
                    }
                }
            }
        };
        bombUpdater.start();
    }

    private boolean shouldRemoveBomb(Bombs bomb) {
        // 示例逻辑: 如果 bomb 的 Y 坐标超过了屏幕底部或者它被标记为 exploded，则应该移除
        return bomb.getY() > sceneHeight || bomb.isTaken();
    }
    public void addBombToView(Bombs bomb) {
        view.addBomb(bomb); // 直接传递 Bombs 对象给视图
    }


    public void removeBombFromView(Bombs bomb) {
        view.removeBomb(bomb); // 从视图中移除炸弹
    }
    public void createAndDropBomb(Block block) {
        Bombs bomb = new Bombs(block.getRow(), block.getColumn());
        model.getBombs().add(bomb); // 将炸弹添加到模型中
        addBombToView(bomb); // 将炸弹添加到视图中
    }
    public void startGame() {
        try {
            System.out.println("Starting game initialization...");
            System.out.println("Current Level: " + level);
            view.hideGameControlButtons();

            // Initialize game state in the model
            model.initializeGame();

            // 初始化球和挡板视图基于模型状态
            view.initBallView(model.getxBall(), model.getyBall(), model.getBallRadius());
            view.initBreakView(model.getxPaddle(), model.getyPaddle(), model.getPaddleWidth(), model.getPaddleHeight());

            // 显示所有方块（包括 Daemon block）
            System.out.println("Blocks to display: " + model.getBlocks().size());
            view.displayBlocks(); // 这将显示所有类型的方块，包括 Daemon block

            // 初始化并启动更新器
            initializeBonusUpdater();
            initializeBombUpdater();


            // 确保在启动游戏引擎之前处理所有UI更新
            Platform.runLater(() -> {
                System.out.println("Game initialization completed.");
                System.out.println("Starting game engine...");
                this.engine.setOnAction(this.model);

                // 启动游戏引擎
                engine.setFps(120);
                engine.start();
            });

        } catch (Exception e) {
            System.err.println("Error during game initialization: " + e.getMessage());
            // 相应地处理错误
        }
    }






    @Override
    public void handle(KeyEvent event) {
        if (event.getEventType() == KeyEvent.KEY_RELEASED) {
            switch (event.getCode()) {
                case LEFT:
                    stopPaddleLeft();
                    break;
                case RIGHT:
                    stopPaddleRight();
                    break;
                // Handle other key releases, if necessary
            }
        } else if (event.getEventType() == KeyEvent.KEY_PRESSED) {
            // Handle key presses (e.g., for moving the paddle)
            switch (event.getCode()) {
                case LEFT:
                    moveLeft();
                    break;
                case RIGHT:
                    moveRight();
                    break;
                case S:
                    saveGame();
                    break;

                // Handle other key presses, if necessary
            }
        }
    }


    public void moveLeft() {
        int moveAmount = 15;
        if (model.getxPaddle() - moveAmount >= 0) {
            double newXPosition = model.getxPaddle() - moveAmount;

            model.setxPaddle(newXPosition);
            centerPaddleX = model.getxPaddle() + model.getHalfPaddleWidth();
            Platform.runLater(() -> view.updatePaddlePosition(model.getxPaddle(), model.getyPaddle()));
        }


    }

    public void moveRight() {
        int moveAmount = 15;
        if (model.getxPaddle() + moveAmount <= (sceneWidth - model.getPaddleWidth())) {
            double newXPosition = model.getxPaddle() + moveAmount;

            model.setxPaddle(newXPosition);
            centerPaddleX = model.getxPaddle() + model.getHalfPaddleWidth();
            Platform.runLater(() -> view.updatePaddlePosition(model.getxPaddle(), model.getyPaddle()));
        }

    }



    private void stopPaddleLeft() {
        // Add your logic to stop the leftward movement of the paddle
        // For example, set movingLeft to false if you use a boolean flag
        movingLeft = false;
    }

    // Handle right paddle stop when the right arrow key is released
    private void stopPaddleRight() {
        // Add your logic to stop the rightward movement of the paddle
        // For example, set movingRight to false if you use a boolean flag
        movingRight = false;
    }

    public void updateScoreView(double x, double y, int score) {
        view.showScoreLabel(x, y, score);
    }

    public void setView(GameView view) {
        this.view = view;
    }

    void updateBallAppearance(boolean status) {
        view.updateBallAppearance(status);
    }

    ;
    // 更多控制器方法


    public void showGameOver() {
        if (view != null) {
            Platform.runLater(() -> view.showGameOver());
        }
    }

    void changeBallAppearance(String appearance) {
        // Change the appearance of the ball
        view.changeBallAppearance(appearance);
    }

    void changeBallAppearanceNormal(String appearance) {
        // Change the appearance of the ball
        view.changeBallAppearance(appearance);
    }

    // Setters and Getters
    public void setModel(GameModel model) {
        this.model = model;
    }

    public GameModel getModel() {
        return this.model;
    }


    void changeSceneStyleClass(String style) {
        view.changeSceneStyleClass(style);
    }

    ;

    public void updateUI() {
        // Get the necessary state from the model
        double xBall = model.getxBall();
        double yBall = model.getyBall();
        int score = GameModel.score;
        int heart = model.getHeart();

        // Update the view
        Platform.runLater(() -> {
            view.updateBallPosition(xBall, yBall);
            view.updateScoreAndHeart(score, heart);
            // Other UI updates...
        });
    }

    public void handleBlockHit(Block block) {
        if (block.isDestroyed) {
            view.updateBlockVisibility(block, false);
        }
    }

    public void nextLevel() {
        Platform.runLater(() -> {
            try {
                System.out.println("Preparing for next level...");

                // Check if the current level is 18 (win condition)
                if (level == 18) {
                    // Stop the game engine
                    if (engine != null) {
                        engine.stop();
                    }
                    // Show win message
                    view.showWin();
                    // Return early since the game is won
                    return;
                }


                // Continue with next level preparation if level is not 18
                updateBallAppearance(false);
                model.prepareNextLevel();
                view.updateLevelLabel(level);
                System.out.println("Current Level: " + level);
                System.out.println("Blocks to display: " + model.getBlocks().size());

                view.displayBlocks();

                System.out.println("Next level ready. Restarting game engine...");

                // Restart the game engine for the next level
                Platform.runLater(() -> {
                    System.out.println("Game nextlevel completed.");
                    System.out.println("newlevel game engine...");
                    engine.restart();
                });

            } catch (Exception e) {
                System.err.println("Error preparing next level: " + e.getMessage());
            }
        });
    }




    public void restartGame() {
        System.out.println("Restarting game...");

        // 重置游戏模型的状态
        model.resetGame();

        // 清除视图中的旧游戏元素
        view.clearGameElements();

        // 重新初始化游戏状态
        model.initializeGame();

        // 更新视图
        Platform.runLater(() -> {
            view.initBallView(model.getxBall(), model.getyBall(), model.getBallRadius());
        view.initBreakView(model.getxPaddle(), model.getyPaddle(), model.getPaddleWidth(), model.getPaddleHeight());
        });

        view.displayBlocks();

        // 重新启动游戏引擎
        engine.start();
        System.out.println("Game restarted successfully.");

    }


    public void updatePaddleView() {
        int newWidth = model.getPaddleWidth();
        double xPaddle = model.getxPaddle();
        Platform.runLater(() -> {
            view.updatePaddleSize(xPaddle, newWidth);
        });
    }

    public void updateBonuses() {
        for (Bonus bonus : model.getChocos()) {
            if (!bonus.isTaken()) {
                view.updateBonusPosition(bonus); // Reflect position update in the view
            } else {
                view.removeBonus(bonus); // Remove caught chocolate from UI
            }
        }
    }

    public void addChocoToView(Bonus choco) {
        view.addBonus(choco.getRectangle()); // Add the Rectangle to the view
    }

    public void updateCaughtChocos(List<Bonus> caughtChocos) {
        for (Bonus choco : caughtChocos) {
            // Update the view to reflect the caught chocolate
            view.removeBonus(choco); // This will remove the chocolate from the UI
        }
    }

    private void saveGame() {
        List<Block> blocks = GameModel.getBlocks(); // Static method call to get blocks

        System.out.println("Beginning the saving process...");
        System.out.println("Current Level: " + level);
        System.out.println("Current Score: " + score);
        System.out.println("Current Heart Count: " + model.getHeart());
        System.out.println("Number of Blocks Before Filtering: " + blocks.size());

        new Thread(new Runnable() {
            @Override
            public void run() {

                new File(savePathDir).mkdirs();
                File file = new File(savePath);
                ObjectOutputStream outputStream = null;
                try {
                    outputStream = new ObjectOutputStream(new FileOutputStream(file));

                    outputStream.writeInt(level);
                    outputStream.writeInt(score);
                    outputStream.writeInt(model.getHeart());
                    outputStream.writeInt(model.getDestroyedBlockCount());


                    outputStream.writeDouble(xBall);
                    outputStream.writeDouble(yBall);
                    outputStream.writeDouble(model.getxPaddle());
                    outputStream.writeDouble(model.getyPaddle());
                    outputStream.writeDouble(model.getCenterPaddleX());
                    outputStream.writeLong(model.getTime());
                    outputStream.writeLong(model.getGoldTime());
                    outputStream.writeDouble(model.getvX());


                    outputStream.writeBoolean(isExistHeartBlock);
                    outputStream.writeBoolean(model.isGoldStatus());
                    outputStream.writeBoolean(model.isGoDownBall());
                    outputStream.writeBoolean(model.isGoRightBall());
                    outputStream.writeBoolean(model.isCollideToPaddle());
                    outputStream.writeBoolean(model.isCollideToPaddleAndMoveToRight());
                    outputStream.writeBoolean(model.isCollideToRightWall());
                    outputStream.writeBoolean(model.isCollideToLeftWall());
                    outputStream.writeBoolean(model.isCollideToRightBlock());
                    outputStream.writeBoolean(model.isCollideToBottomBlock());
                    outputStream.writeBoolean(model.isCollideToLeftBlock());
                    outputStream.writeBoolean(model.isCollideToTopBlock());

                    ArrayList<BlockSerializable> blockSerializables = new ArrayList<BlockSerializable>();
                    for (Block block : blocks) {
                        if (block.isDestroyed) {
                            System.out.println("Skipping destroyed block at Row " + block.getRow() + ", Column " + block.getColumn());
                            continue;
                        }
                        System.out.println("Saving Block: Row " + block.getRow() + ", Column " + block.getColumn() + ", Type " + block.getType());
                        blockSerializables.add(new BlockSerializable(block.getRow(), block.getColumn(), block.getType()));
                    }


                    outputStream.writeObject(blockSerializables);


                    view.showMessage("Game Saved", 220, 340);

                    System.out.println("Saving game...");
                    System.out.println("Total blocks to save: " + blocks.size());
                    for (BlockSerializable ser : blockSerializables) {
                        System.out.println("Saving Block: Row " + ser.row + ", Column " + ser.j + ", Type " + ser.type);
                    }
                    System.out.println("Total blocks serialized: " + blockSerializables.size());

                } catch (FileNotFoundException e) {
                    System.err.println("Save file not found: " + e.getMessage());
                    e.printStackTrace();
                } catch (IOException e) {
                    System.err.println("IOException during save: " + e.getMessage());
                    e.printStackTrace();
                } finally {
                    try {
                        outputStream.flush();
                        outputStream.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
        System.out.println("Game save process completed.");

    }

    public void loadFromSave() {
        // Load game state from the model
        LoadSave loadSave = new LoadSave();
        loadSave.read();
        view.hideGameControlButtons();

        // Set the loaded data in the model
        model.setGamePropertiesFromLoad(
                loadSave.level, loadSave.score, loadSave.heart, loadSave.destroyedBlockCount,
                loadSave.xBall, loadSave.yBall, loadSave.xBreak, loadSave.yBreak,
                loadSave.centerBreakX, loadSave.time, loadSave.goldTime, loadSave.vX,
                loadSave.isExistHeartBlock, loadSave.isGoldStauts, loadSave.goDownBall,
                loadSave.goRightBall, loadSave.colideToBreak, loadSave.colideToBreakAndMoveToRight,
                loadSave.colideToRightWall, loadSave.colideToLeftWall, loadSave.colideToRightBlock,
                loadSave.colideToBottomBlock, loadSave.colideToLeftBlock, loadSave.colideToTopBlock
        );

        // Clear existing blocks before loading new ones
        GameModel.clearBlocks();

        // Re-create blocks from saved data
        for (BlockSerializable ser : loadSave.blocks) {
            int r = new Random().nextInt(colors.length);
            Block newBlock = new Block(ser.row, ser.j, colors[r], ser.type);
            GameModel.addBlock(newBlock); // Static method to add blocks to GameModel
            System.out.println("Loaded Block: Row " + ser.row + ", Column: " + ser.j + ", Type: " + ser.type);
        }

        System.out.println("Total Blocks Loaded: " + GameModel.getBlocks().size());

        // Update view based on loaded data
        view.initBallView(model.getxBall(), model.getyBall(), model.getBallRadius());
        view.initBreakView(model.getxPaddle(), model.getyPaddle(), model.getPaddleWidth(), model.getPaddleHeight());
        System.out.println("Blocks to display: " + GameModel.getBlocks().size());

        view.displayBlocks();
        view.updateScoreAndHeart(score, model.getHeart());

        // Ensure onAction is set for the game engine
        this.engine.setOnAction(this.model);

        // Start game engine with loaded state
        engine.restart();
    }




}

