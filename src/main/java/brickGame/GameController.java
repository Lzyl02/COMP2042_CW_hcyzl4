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

import static brickGame.GameModel.*;


public class GameController implements EventHandler<KeyEvent>,GameEngine.OnAction {
    Stage primaryStage;
    private final GameEngine engine;
    private GameModel model;

    private GameUpdateController gameUpdateController;

    private AnimationTimer bonusUpdater;
    private AnimationTimer bombUpdater;

    public static boolean isIsExistHeartBlock() {
        return isExistHeartBlock;
    }

    private static boolean isExistHeartBlock = false;

    private GameView view;
    private boolean movingLeft = false;
    private boolean movingRight = false;
    private GameBonusController gameBonusController;
    //private GameInitializationController gameInitializationController;


    public GameController(GameView view,GameModel model) {
        this.view = view;
        this.engine = new GameEngine();

        // Get the GameModel instance using the singleton pattern
        this.model = model;

        // Set this GameController instance in the model
        this.model.setController(this);
        this.gameUpdateController = new GameUpdateController(model,view);
        this.gameBonusController = new GameBonusController(model,view);


        // Set GameModel as the action handler for GameEngine
        // Initializatio
    }



    public void initializeBonusUpdater() {
        gameBonusController.initializeBonusUpdater();
    }
    private boolean shouldRemoveBonus(Bonus bonus) {
        // 示例逻辑: 如果 bonus 的 Y 坐标超过了屏幕底部或者它被标记为 taken，则应该移除
       return  gameBonusController.shouldRemoveBonus(bonus);
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

                    if (bomb.getY() >= model.getSceneHeight() || bomb.isTaken()) {
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
        return bomb.getY() > model.getSceneHeight() || bomb.isTaken();
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
            System.out.println("Current Level: " + model.getLevel());
            view.hideGameControlButtons();

            // Initialize game state in the model
            initializeGame();

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
                this.engine.setOnAction(this);

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
            model.setCenterPaddleX(model.getxPaddle() + model.getHalfPaddleWidth());
            Platform.runLater(() -> view.updatePaddlePosition(model.getxPaddle(), model.getyPaddle()));
        }


    }

    public void moveRight() {
        int moveAmount = 15;
        if (model.getxPaddle() + moveAmount <= (model.getSceneWidth() - model.getPaddleWidth())) {
            double newXPosition = model.getxPaddle() + moveAmount;

            model.setxPaddle(newXPosition);
            model.setCenterPaddleX(model.getxPaddle() + model.getHalfPaddleWidth()) ;
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
        gameBonusController.updateScoreView(x, y, score);
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

        gameUpdateController.showGameOver();
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
                if (model.getLevel() == 18) {
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
                prepareNextLevel();
                view.updateLevelLabel(model.getLevel());
                System.out.println("Current Level: " + model.getLevel());
                System.out.println("Blocks to display: " + getBlocks().size());

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
    public void prepareNextLevel() {
        // Reset game state variables
        updatePaddleSize();
        model.setvX( 1.000);
        resetColideFlags();
        model.setGoDownBall(true);
        model.setGoldStatus(false);
        isExistHeartBlock = false;
        model.setHitTime(0);
        model.setTime(0);
        model.setGoldTime(0);
        setLevel(model.getLevel()+1);
        System.out.println("Level incremented to: " + model.getLevel());


        getBlocks().clear();
        model.setDestroyedBlockCount(0);
        initializeGame();
        System.out.println("Total blocks created: " + getBlocks().size());

    }




    public void restartGame() {
        System.out.println("Restarting game...");

        // 重置游戏模型的状态
        resetGame();

        // 清除视图中的旧游戏元素
        view.clearGameElements();

        // 重新初始化游戏状态
        initializeGame();

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
      gameBonusController.updateCaughtChocos(caughtChocos);
    }

    void saveGame() {
        List<Block> blocks = GameModel.getBlocks(); // Static method call to get blocks

        System.out.println("Beginning the saving process...");
        System.out.println("Current Level: " + model.getLevel());
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

                    outputStream.writeInt(model.getLevel());
                    outputStream.writeInt(score);
                    outputStream.writeInt(model.getHeart());
                    outputStream.writeInt(model.getDestroyedBlockCount());


                    outputStream.writeDouble(model.getxBall());
                    outputStream.writeDouble(model.getyBall());
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
        setGamePropertiesFromLoad(
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
            int r = new Random().nextInt(model.getColors().length);
            Block newBlock = new Block(ser.row, ser.j, model.getColors()[r], ser.type);
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
        this.engine.setOnAction(this);

        // Start game engine with loaded state
        engine.restart();
    }


    @Override
    public void onUpdate() {

        Platform.runLater(() -> {
//            controller.updateBallPosition(xBall,yBall);
//            controller.updatePaddlePosition(xPaddle,yPaddle);

            updateUI();
            handleBlockCollision();

        });

    }
    private void handleBlockCollision() {
        if (model.getyBall() >= Block.getPaddingTop() && model.getyBall() <= (Block.getHeight() * (model.getLevel() + 1)) + Block.getPaddingTop()) {
            for (Block block : GameModel.getBlocks()) {
                int hitCode = block.checkHitToBlock(model.getxBall(), model.getyBall());
                if (hitCode != Block.NO_HIT) {
                    processBlockHit(block, hitCode);
                    // Adjust ball's direction based on collision
                    handleSpecialBlocks(block);
                    adjustBallDirectionAfterBlockHit(hitCode);
                }
            }
        }
    }


    @Override
    public void onPhysicsUpdate() {
        checkDestroyedCount();
        setPhysicsToBall();

        if(model.getTime() - model.getGoldTime() > 5000) {
            updateBallAppearance(model.isGoldStatus());
            model.setGoldStatus(false);
        }
        catchChoco();

        catchBomb();
        handleChocoMovement();
        handleBombMovement();
        if (this != null) {
            this.updateBonuses();
        }

    }

    @Override
    public void onTime(long time) {
        model.setTime(time);
    }

    @Override
    public void onInit() {

    }

    private void resetColideFlags() {

    gameUpdateController.resetColideFlags();
    }



    private void handleChocoMovement() {
        for (Bonus choco : model.getChocos()) {
            if (!choco.taken) {
                choco.y += model.getFallingSpeed();
                // Log the new position for debugging
                System.out.println("Choco updated: x = " + choco.getX() + ", y = " + choco.y);
            }
        }
    }

    private void handleBombMovement() {
        ArrayList<Bombs> bombsToRemove = new ArrayList<>();
        for (Bombs bomb : model.getBombs()) {
            bomb.fallDown(); // 更新炸弹的位置

            // 判断炸弹是否需要被移除（比如，如果炸弹已经到达底部或者被接住）
            if (bomb.getY() > model.getSceneHeight() || bomb.isTaken()) {
                bombsToRemove.add(bomb);
            }
        }

        // 移除标记的炸弹
        model.getBombs().removeAll(bombsToRemove);
        // 通知控制器从视图中移除炸弹
        bombsToRemove.forEach(this::removeBombFromView);
    }
    public void catchBomb() {
        long currentTime = System.currentTimeMillis(); // 获取当前时间
        long catchCooldown = 1000; // 设置冷却时间（例如500毫秒）

        for (Bombs bomb : model.getBombs()) {
            if (bomb.getY() >= model.getyPaddle() && bomb.getY() <= model.getyPaddle() + model.getPaddleHeight() &&
                    bomb.getX() >= model.getxPaddle() && bomb.getX() <= model.getxPaddle() + model.getPaddleWidth() &&
                    !bomb.isTaken() && (currentTime - bomb.getLastCaughtTime() > catchCooldown)) {

                System.out.println("Bomb caught: x = " + bomb.getX() + ", y = " + bomb.getY());
                bomb.setTaken(true);
                bomb.setLastCaughtTime(currentTime); // 更新炸弹被捕获的时间
                score -= 1;
                System.out.println("Score deducted. New score: " + score);
                updateScoreView(bomb.getX(), bomb.getY(), -1);

                break; // 中断循环
            }
        }
    }



    public void checkDestroyedCount() {
        gameUpdateController.checkDestroyedCount();
        }



    private void setPhysicsToBall() {
        moveBall();
        handleWallCollision();
        handlePaddleCollision();
        // Handle collisions with blocks
        //
    }
    private void moveBall() {
        gameUpdateController.moveBall();
    }

//    private void moveBall() {
//// Horizontal movement
//        model.setxBall(model.getxBall() + (model.isGoRightBall() ? model.getvX() : -model.getvX()));
//
//// Vertical movement
//        model.setyBall(model.getyBall() + (model.isGoDownBall() ? model.getvY() : -model.getvY()));
//
//    }

    public void catchChoco() {
       gameBonusController.catchChoco();
        }

    public void handleWallCollision() {
        // Collision with top wall
       gameUpdateController.handleWallCollision();
    }

    // Collision logic you want to use
    private void handlePaddleCollision() {

        // Calculate the ball's bottom edge
        double ballBottomEdge = model.getyBall() + model.getBallRadius();

        // Calculate the paddle's horizontal range
        double paddleLeftEdge = model.getxPaddle();
        double paddleRightEdge = model.getxPaddle() + model.getPaddleWidth();

        // Check if the ball is at the same vertical level as the paddle's top
        if (ballBottomEdge >= model.getyPaddle() && ballBottomEdge <= model.getyPaddle() + model.getPaddleHeight()) {

            if (model.getxBall() >= paddleLeftEdge && model.getxBall() <= paddleRightEdge) {
                model.setHitTime(model.getTime());
                resetColideFlags();
                model.setCollideToPaddle(true);
                model.setGoDownBall(false);

                // Calculate the relative position of the ball impact on the paddle
                double relativeImpactPosition = (model.getxBall() - paddleLeftEdge) / model.getPaddleWidth() - 0.5; // Range: -0.5 to 0.5

                // Apply a scaling factor to control the change in velocity
                double scalingFactor = 0.8; // Adjust this value to control the maximum velocity change
                model.setvX(baseHorizontalSpeed+ scalingFactor * relativeImpactPosition * baseHorizontalSpeed);

                // Ensure that the velocity does not exceed a maximum value
                double maxVelocity = 2.0; // Adjust this value to set the maximum allowed horizontal velocity
                model.setvX(Math.min(Math.max(model.getvX(), -maxVelocity), maxVelocity));

                // Update the direction based on the impact position
                model.setGoRightBall(relativeImpactPosition > 0);
            }
        }
    }



    private void adjustBallDirectionAfterBlockHit(int hitCode) {
        switch (hitCode) {
            case Block.HIT_TOP:
            case Block.HIT_BOTTOM:
                model.setGoDownBall(!model.isGoDownBall()); // Reverse vertical direction
                break;
            case Block.HIT_LEFT:
            case Block.HIT_RIGHT:
                model.setGoRightBall(!model.isGoRightBall()); // This toggles the goRightBall value in the model
                break;
        }
    }

    private void processBlockHit(Block block, int hitCode) {
        System.out.println("Block hit detected at: x = " + block.getX() + ", y = " + block.getY());
        score += 1;
        block.setDestroyed(true);
        model.setDestroyedBlockCount(model.getDestroyedBlockCount()+1);

        if (this != null) {
            updateScoreView(block.getX(), block.getY(), 1);
            handleBlockHit(block);
        }

        // 处理 Daemon 方块生成炸弹的逻辑
        if (block.getType() == Block.BLOCK_DAEMON && !block.hasGeneratedBomb()) {
            createAndDropBomb(block);
            block.setGeneratedBomb(true);
        }
    }




    private void handleSpecialBlocks(Block block) {
        switch (block.type) {
            case Block.BLOCK_CHOCO:
                handleChocoBlock(block);
//                createAndHandleBomb(block);
                break;

            case Block.BLOCK_STAR:
                model.setGoldTime(model.getTime());
                if (this != null) {
                    changeBallAppearance("goldball.png");
                    changeSceneStyleClass("goldRoot");                }

                model.setGoldStatus(true);
                break;
            case Block.BLOCK_HEART:
                heart++;
                break;
            case Block.BLOCK_DAEMON:
//                Bombs bomb = new Bombs(block.getRow(), block.getColumn());
//                bombs.add(bomb); // 将新创建的炸弹添加到列表中
//                controller.addBombToView(bomb); // 调用控制器方法将炸弹添加到视图中
//                controller.createAndHandleBomb(block);
                break;
        }
    }
    private void handleChocoBlock(Block block) {
        System.out.println("Handling chocolate block at row: " + block.row + ", column: " + block.column);
        final Bonus choco = new Bonus(block.row, block.column);
        choco.timeCreated = model.getTime();

        // Ensure that adding to the UI is done on the JavaFX Application Thread
        if(this!=null) {
            addChocoToView(choco);
            System.out.println("Chocolate bonus added to UI.");
        }

        model.getChocos().add(choco);
        System.out.println("Chocolate bonus added to list.");
    }

//    private void createAndHandleBomb(Block block) {
//        Bombs bomb = new Bombs(block.getRow(), block.getColumn());
//        bombs.add(bomb); // 将炸弹添加到模型的列表中
//        if (controller != null) {
//            controller.addBombToView(bomb); // 在视图中显示炸弹
//        }
//    }

    private void updatePaddleSize() {
        if (model.getPaddleWidth() > 30) {
            model.setPaddleWidth(model.getPaddleWidth()-10);
        }
        if (model.getPaddleWidth() < 30) {
            model.setPaddleWidth(30);
        }

        // 更新视图
        updatePaddleView();
    }


    public void resetGame() {
        // 重置游戏状态
        model.setScore(0);
        model.setHeart(5);
        model.setLevel(1);
        model.setDestroyedBlockCount(0);
        model.setGoldStatus(false);

        // 重置球和挡板的位置和速度
        model.setxBall((double) model.getSceneWidth() / 2);
        model.setyBall(model.getSceneHeight() - model.getPaddleHeight() - model.getBallRadius() - 1);
        model.setxPaddle((double) model.getSceneWidth() / 2 - (double) model.getPaddleWidth() / 2);
        model.setyPaddle(model.getSceneHeight() - 50);
        model.setvX(4);
        model.setvY(4);

        // 清除所有区块和奖励
        model.getBlocks().clear();
        model.getChocos().clear();

        // 重置游戏结束标志
        model.setGameOver(false);

        // 重新初始化游戏
        initializeGame();
    }



    private int determineBlockType(int randomNumber, Random random) {
        // Logic to randomly assign different types of blocks
        if (randomNumber % 10 == 0) {
            // 10% chance of being a special block
            return determineSpecialBlockType(random);
        } else {
            // 90% chance of being a normal block
            return Block.BLOCK_NORMAL;
        }
    }

    private int determineSpecialBlockType(Random random) {
        int specialType = random.nextInt(3); // Assuming 3 types of special blocks
        switch (specialType) {
            case 0:
                return Block.BLOCK_CHOCO; // For example, a block that gives extra points
            case 1:
                return Block.BLOCK_HEART; // For example, a block that gives an extra life
            case 2:
                return Block.BLOCK_STAR;  // For example, a block that activates a power-up
            default:
                return Block.BLOCK_NORMAL; // Fallback to a normal block
        }
    }


    public void setGamePropertiesFromLoad(int level, int score, int heart, int destroyedBlockCount,
                                          double xBall, double yBall, double xPaddle, double yPaddle,
                                          double centerPaddleX, long time, long goldTime, double vX,
                                          boolean isExistHeartBlock, boolean isGoldStatus, boolean goDownBall,
                                          boolean goRightBall, boolean collideToPaddle, boolean collideToPaddleAndMoveToRight,
                                          boolean collideToRightWall, boolean collideToLeftWall, boolean collideToRightBlock,
                                          boolean collideToBottomBlock, boolean collideToLeftBlock, boolean collideToTopBlock) {
        // Set all properties in the model
        model.setLevel(level);
        model.setScore(score);
        model.setHeart(heart);
        model.setDestroyedBlockCount(destroyedBlockCount);

        model.setxBall(xBall);
        model.setyBall(yBall);
        model.setxPaddle(xPaddle);
        model.setyPaddle(yPaddle);
        model.setCenterPaddleX(centerPaddleX);
        model.setTime(time);
        model.setGoldTime(goldTime);
        model.setvX(vX);

        model.setExistHeartBlock(isExistHeartBlock);
        model.setGoldStatus(isGoldStatus);
        model.setGoDownBall(goDownBall);
        model.setGoRightBall(goRightBall);
        model.setCollideToPaddle(collideToPaddle);
        model.setCollideToPaddleAndMoveToRight(collideToPaddleAndMoveToRight);
        model.setCollideToRightWall(collideToRightWall);
        model.setCollideToLeftWall(collideToLeftWall);
        model.setCollideToRightBlock(collideToRightBlock);
        model.setCollideToBottomBlock(collideToBottomBlock);
        model.setCollideToLeftBlock(collideToLeftBlock);
        model.setCollideToTopBlock(collideToTopBlock);
    }


    public void initializeGame() {
        initializeBlocks(model.getLevel()); // 初始化所有类型的方块，包括 Daemon block

        // 初始化球和挡板的位置
        model.setxPaddle((double) model.getSceneWidth() / 2); // 挡板在屏幕中央
        model.setyPaddle(model.getSceneHeight() - 50); // 挡板位置在屏幕底部
        model.setxBall(model.getxPaddle() + (double) model.getPaddleWidth() / 2); // 球开始在挡板上
        model.setyBall(model.getyPaddle() - model.getBallRadius() - 1); // 球的位置在挡板之上
    }




    public void initializeBlocks(int newLevel) {
        setLevel(newLevel);
        getBlocks().clear(); // 清除现有的方块

        Random random = new Random();
        int rows = model.getLevel(); // 行数基于级别
        int columns = 4; // 固定为 4 列

        // 在每个级别中创建相应数量的 Daemon block
        for (int i = 0; i < model.getLevel(); i++) {
            int daemonRow = random.nextInt(rows);
            int daemonColumn = random.nextInt(columns);
            Block daemonBlock = new Block(daemonRow, daemonColumn, Color.RED, Block.BLOCK_DAEMON);
            getBlocks().add(daemonBlock);
        }

        // 创建其他类型的方块
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                final int currentRow = i;
                final int currentColumn = j;

                // 检查是否已经在这个位置创建了 Daemon block
                boolean isDaemonBlockPresent = getBlocks().stream()
                        .anyMatch(b -> b.getRow() == currentRow && b.getColumn() == currentColumn && b.getType() == Block.BLOCK_DAEMON);

                if (!isDaemonBlockPresent) {
                    int r = random.nextInt(model.getColors().length);
                    int type = determineBlockType(r, random);
                    Block block = new Block(currentRow, currentColumn, model.getColors()[r], type);
                    getBlocks().add(block);
                    System.out.println("Block created: Row " + currentRow + ", Column " + currentColumn +
                            ", Color: " + model.getColors()[r] + ", Type: " + type);
                }
            }
        }

        System.out.println("Total blocks created for level " + model.getLevel() + ": " + getBlocks().size());
    }



}

