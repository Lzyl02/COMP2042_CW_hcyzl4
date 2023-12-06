package brickGame;

import javafx.animation.AnimationTimer;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import static brickGame.GameModel.*;

/**
 * Central controller class for a brick-style game. Manages game logic, handles user input,
 * and coordinates with various other controllers.
 */

public class GameController implements EventHandler<KeyEvent>,  GameEngine.OnAction {
    Stage primaryStage;
    private final GameEngine engine;
    private GameModel model;

    private GameBonusController gameBonusController;

    private static boolean isExistHeartBlock = false;
    private Pane root; // Assuming root is of type Pane

    private GameView view;
    private boolean movingLeft = false;
    private boolean movingRight = false;
    private GameCollisionController gameCollisionController;
    private GameInitializationController gameInitializationController;

    /**
     * Constructor to initialize the GameController with necessary components.
     *
     * @param root Root pane of the game's UI.
     * @param view The game view for managing UI components.
     * @param gameModel The model representing the game's state.
     */
    public GameController(Pane root, GameView view, GameModel gameModel) {
        this.root = root;

        this.view = view;
        this.engine = new GameEngine();

        // Get the GameModel instance using the singleton pattern
        this.model = gameModel;

        // Set this GameController instance in the model
        this.model.setController(this);
        this.gameBonusController = new GameBonusController(model,view);
        this.gameCollisionController = new GameCollisionController(model,view);
        this.gameInitializationController = new GameInitializationController(model,view,engine,gameBonusController);
//        this.gameEventController = gameEventController;
    }


    /**
     * Removes a bomb from the game view.
     *
     * @param bomb The bomb to remove.
     */
    public void removeBombFromView(Bombs bomb) {
        view.removeBomb(bomb); // 从视图中移除炸弹
    }

    /**
     * Starts the game by initiating the game initialization controller.
     */
    public void startGame() {
        gameInitializationController.startGame();
    }
    /**
     * Handles key events for game control, such as moving the paddle or saving the game.
     *
     * @param event The KeyEvent triggered by user interaction.
     */
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


    /**
     * Moves the paddle to the left by a predefined amount.
     * The method checks if the new position is within the boundaries of the game scene,
     * and if so, updates the paddle's position in the model and view.
     */
        public void moveLeft() {
        int moveAmount = 15;
        if (model.getxPaddle() - moveAmount >= 0) {
            double newXPosition = model.getxPaddle() - moveAmount;

            model.setxPaddle(newXPosition);
            model.setCenterPaddleX(model.getxPaddle() + model.getHalfPaddleWidth());
            Platform.runLater(() -> view.updatePaddlePosition(model.getxPaddle(), model.getyPaddle()));
        }


    }
    /**
     * Moves the paddle to the right by a predefined amount.
     * The method checks if the new position is within the boundaries of the game scene,
     * and if so, updates the paddle's position in the model and view.
     */
    public void moveRight() {
        int moveAmount = 15;
        if (model.getxPaddle() + moveAmount <= (model.getSceneWidth() - model.getPaddleWidth())) {
            double newXPosition = model.getxPaddle() + moveAmount;

            model.setxPaddle(newXPosition);
            model.setCenterPaddleX(model.getxPaddle() + model.getHalfPaddleWidth()) ;
            Platform.runLater(() -> view.updatePaddlePosition(model.getxPaddle(), model.getyPaddle()));
        }

    }


    /**
     * Stops the leftward movement of the paddle.
     * This method should be called when the left movement control (like a key) is released.
     * It typically sets a boolean flag indicating that leftward movement should cease.
     */
    private void stopPaddleLeft() {
        // Add your logic to stop the leftward movement of the paddle
        // For example, set movingLeft to false if you use a boolean flag
        movingLeft = false;
    }

    /**
     * Stops the rightward movement of the paddle.
     * This method should be called when the right movement control (like a key) is released.
     * It typically sets a boolean flag indicating that rightward movement should cease.
     */
    private void stopPaddleRight() {
        // Add your logic to stop the rightward movement of the paddle
        // For example, set movingRight to false if you use a boolean flag
        movingRight = false;
    }
    /**
     * Updates the score view.
     * Displays a score label at the specified coordinates in the game view.
     *
     * @param x The x-coordinate where the score label should be displayed.
     * @param y The y-coordinate where the score label should be displayed.
     * @param score The score to display.
     */
    public void updateScoreView(double x, double y, int score) {
        view.showScoreLabel(x, y, score);
    }

    /**
     * Updates the appearance of the ball based on the given status.
     * This could involve changing the ball's color or texture to reflect different game states.
     *
     * @param status A boolean indicating the new state of the ball.
     */
    void updateBallAppearance(boolean status) {
        view.updateBallAppearance(status);
    }

    ;


    /**
     * Shows the game over screen on the UI.
     */
    public void showGameOver() {
        if (view != null) {
            Platform.runLater(() -> view.showGameOver());
        }
    }
    ;

    /**
     * Updates the game's UI components based on the game's state, like ball position, score, and heart count.
     */
    public void updateUI() {
        // Get the necessary state from the model
        double xBall = model.getxBall();
        double yBall = model.getyBall();
        int score = model.getScore();
        int heart = model.getHeart();

        // Use Platform.runLater to ensure that UI update is performed on the JavaFX Application Thread
        Platform.runLater(() -> {
            view.updateBallPosition(xBall, yBall);
            System.out.println("updating ball position");
            view.updateScoreAndHeart(score, heart);
            System.out.println("Debug: Score label in view should be updated");

        });
    }


    /**
     * Prepares and transitions the game to the next level.
     */
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


    /**
     * Restarts the game, resetting the game model and reinitializing UI components.
     */

    public void restartGame() {
        System.out.println("Restarting game...");

        // Reset the game model to its initial state
        resetGame();
        System.out.println("Debug: Game model reset");

        // Clear existing game elements from the view
        view.clearGameElements();
        System.out.println("Debug: Game elements cleared from view");

        // Reinitialize the UI components
        view.resetUIComponents();
        view.displayBlocks();
        System.out.println("Debug: UI components reinitialized");

        // Debug: Check score after resetting
        System.out.println("Debug: Score after reset is " + model.getScore());

        // Update the view with the reset score
        view.updateScoreAndHeart(model.getScore(), model.getHeart());
        System.out.println("Debug: Score label in view should be updated");

        // Start or restart the game logic (e.g., start timers, game loops, etc.)
        engine.start();
        System.out.println("Game restarted successfully.");
    }

    /**
     * Updates the paddle's view in the game.
     */
    public void updatePaddleView() {
        int newWidth = model.getPaddleWidth();
        double xPaddle = model.getxPaddle();
        Platform.runLater(() -> {
            view.updatePaddleSize(xPaddle, newWidth);
        });
    }

    /**
     * Updates the bonus objects on the screen.
     * Iterates through all bonuses and either updates their position or removes them if they are taken.
     */
    public void updateBonuses() {
        for (Bonus bonus : model.getChocos()) {
            if (!bonus.isTaken()) {
                view.updateBonusPosition(bonus); // Reflect position update in the view
            } else {
                view.removeBonus(bonus); // Remove caught chocolate from UI
            }
        }
    }

    /**
     * Updates the view to reflect the caught chocolate bonuses.
     * Removes caught chocolates from the UI.
     *
     * @param caughtChocos List of caught chocolate bonuses.
     */
    public void updateCaughtChocos(List<Bonus> caughtChocos) {
        for (Bonus choco : caughtChocos) {
            // Update the view to reflect the caught chocolate
            view.removeBonus(choco); // This will remove the chocolate from the UI
        }
    }



    /**
     * Saves the current game state to a file.
     * Serializes the game model data including level, score, and game objects.
     */
    void saveGame() {
        List<Block> blocks = model.getBlocks(); // Static method call to get blocks

        System.out.println("Beginning the saving process...");
        System.out.println("Current Level: " + model.getLevel());
        System.out.println("Current Score: " + model.getScore());
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
                    outputStream.writeInt(model.getScore());
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

    /**
     * Loads the game state from a saved file.
     * Sets the loaded data in the model and updates the view to reflect the loaded state.
     */
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
        model.clearBlocks();

        // Re-create blocks from saved data
        for (BlockSerializable ser : loadSave.blocks) {
            int r = new Random().nextInt(model.getColors().length);
            Block newBlock = new Block(ser.row, ser.j, model.getColors()[r], ser.type);
            model.addBlock(newBlock); // Static method to add blocks to GameModel
            System.out.println("Loaded Block: Row " + ser.row + ", Column: " + ser.j + ", Type: " + ser.type);
        }

        System.out.println("Total Blocks Loaded: " + model.getBlocks().size());

        // Update view based on loaded data
//        view.initBallView(model.getxBall(), model.getyBall(), model.getBallRadius());
//        view.initBreakView(model.getxPaddle(), model.getyPaddle(), model.getPaddleWidth(), model.getPaddleHeight());
        System.out.println("Blocks to display: " + model.getBlocks().size());

        view.displayBlocks();
        view.updateScoreAndHeart(model.getScore(), model.getHeart());

        // Ensure onAction is set for the game engine
        this.engine.setOnAction(this);

        // Start game engine with loaded state
        engine.restart();
    }

    /**
     * Updates the game's UI and checks for block collisions.
     * Called continuously to ensure the game's UI is up-to-date.
     */

    @Override
    public void onUpdate() {

        Platform.runLater(() -> {
//            controller.updateBallPosition(xBall,yBall);
//            controller.updatePaddlePosition(xPaddle,yPaddle);

            updateUI();
            handleBlockCollision();

        });

    }
    /**
     * Handles the block collision.
     * Delegates the collision handling to the GameCollisionController.
     */
    private void handleBlockCollision() {
        gameCollisionController.handleBlockCollision();
    }


    /**
     * Updates the game's physics.
     * Invoked regularly to handle game logic related to physics, such as moving objects and checking collisions.
     */
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

    /**
     * Sets the current game time.
     * Invoked regularly to update the game's timing logic.
     *
     * @param time The current time in the game.
     */
    @Override
    public void onTime(long time) {
        model.setTime(time);
    }

    /**
     * Initializes the game.
     * Invoked at the start of the game to set initial conditions.
     */
    @Override
    public void onInit() {

    }

    /**
     * Resets collision flags in the game model.
     * Used to reset the state of collisions after they have been processed.
     */
    private void resetColideFlags() {

        model.setCollideToPaddle(false);
        model.setCollideToPaddleAndMoveToRight(false);
        model.setCollideToRightWall(false);
        model.setCollideToLeftWall(false);

        model.setCollideToRightBlock(false);
        model.setCollideToBottomBlock(false);
        model.setCollideToLeftBlock(false);
        model.setCollideToTopBlock(false);
    }


    /**
     * Handles the movement of chocolate bonuses.
     * Updates the position of chocolates as they fall.
     */
    private void handleChocoMovement() {
        for (Bonus choco : model.getChocos()) {
            if (!choco.taken) {
                choco.y += model.getFallingSpeed();
                // Log the new position for debugging
                System.out.println("Choco updated: x = " + choco.getX() + ", y = " + choco.y);
            }
        }
    }

    /**
     * Handles the movement of bombs.
     * Updates the position of bombs and removes them if necessary.
     */
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

    /**
     * Handles catching bombs in the game.
     * Checks if bombs have collided with the paddle and updates the game state accordingly.
     */
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
                model.setScore(model.getScore()-1);
                System.out.println("Score deducted. New score: " + model.getScore());
                updateScoreView(bomb.getX(), bomb.getY(), -1);

                break; // 中断循环
            }
        }
    }


    /**
     * Checks if all blocks have been destroyed and progresses to the next level.
     * If all blocks are destroyed, triggers the transition to the next level.
     */
    private void checkDestroyedCount() {
        if (model.getDestroyedBlockCount() == model.getBlocks().size()) {
            //TODO win level todo...
            //System.out.println("You Win");
            if (this.model.getController() != null)
                model.getController().nextLevel();
        }
    }

    /**
     * Sets the physics for the ball, including its movement and collision handling.
     * This method orchestrates the overall physics of the ball, including invoking methods to handle its movement and collision.
     */
    private void setPhysicsToBall() {
        moveBall();
        handleWallCollision();
        handlePaddleCollision();
        // Handle collisions with blocks
        //
    }

    /**
     * Moves the ball in the game, updating its position based on its current velocity and direction.
     * Handles both horizontal and vertical movements of the ball.
     */
    private void moveBall() {
// Horizontal movement
        model.setxBall(model.getxBall() + (model.isGoRightBall() ? model.getvX() : -model.getvX()));

// Vertical movement
        model.setyBall(model.getyBall() + (model.isGoDownBall() ? model.getvY() : -model.getvY()));

    }

    /**
     * Catches chocolates in the game.
     * Checks if chocolates have collided with the paddle and updates the game state accordingly.
     */
    public void catchChoco() {
        ArrayList<Bonus> removedChocos = new ArrayList<>();
        for (Bonus choco : model.getChocos()) {
            if (choco.y >= model.getyPaddle() && choco.y <= model.getyPaddle() + model.getPaddleHeight() &&
                    choco.getX() >= model.getxPaddle() && choco.getX() <= model.getxPaddle() + model.getPaddleWidth() && !choco.isTaken()) {

                // Chocolate is caught
                System.out.println("Chocolate caught: x = " + choco.getX() + ", y = " + choco.y);
                choco.setTaken(true);
                model.setScore(model.getScore()+3);
                updateScoreView(choco.getX(), choco.y, 3);
                removedChocos.add(choco);
            } else if (choco.y >= model.getSceneHeight() && !choco.isTaken()) {
                // Chocolate reached the bottom and was not caught
                System.out.println("Chocolate missed: x = " + choco.getX() + ", y = " + choco.y);
                choco.setTaken(true); // Mark it as handled
                removedChocos.add(choco);
            }
        }

        // Remove handled chocolates from the list
        model.getChocos().removeAll(removedChocos);

        // Notify the controller to update the view

        updateCaughtChocos(removedChocos);

    }


    /**
     * Handles collision of the ball with the walls of the game area.
     * Adjusts the ball's movement based on collisions with the top, bottom, left, and right walls.
     */
    private void handleWallCollision() {
        // Collision with top wall
        if (model.getyBall() <= model.getBallRadius()) {
            model.setGoDownBall(true);
            model.setyBall(model.getBallRadius()); // Prevent sticking to the wall
        }

        // Collision with bottom wall
        if (model.getyBall() >= model.getSceneHeight() - model.getBallRadius() && !model.isGameOver()) { // 添加检查 isGameOver
            model.setGoDownBall(false);
            model.setyBall(model.getSceneHeight() - model.getBallRadius());
            if (!model.isGoldStatus()) {
                model.setHeart(model.getHeart()-1);
                if (model.getHeart() == 0) {
                    model.setGameOver(true); // 设置游戏结束标志
                    if (this != null) {
                        Platform.runLater(() -> showGameOver()); // 确保在 JavaFX 线程中执行
                    }

                    engine.stop();// 停止游戏逻辑

                }
            }
        }
        // Collision with left wall
        if (model.getxBall() <= model.getBallRadius()) {
            model.setGoRightBall(true);
            model.setxBall(model.getBallRadius()) ; // Adjust for left wall
        }

        // Collision with right wall
        if (model.getxBall()  >= model.getSceneWidth() - model.getBallRadius()) {
            model.setGoRightBall(false);
            model.setxBall(model.getSceneWidth()- model.getBallRadius()); // Adjust for right wall
        }
    }

    /**
     * Handles the collision of the ball with the paddle.
     * Checks if the ball collides with the paddle and adjusts its direction and velocity based on the point of impact.
     */
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


    /**
     * Updates the paddle size in the game view.
     * Adjusts the paddle width and updates its view to reflect any changes.
     */
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
    /**
     * Prepares the game for the next level.
     * Resets game state variables, clears blocks, and initializes the game for the next level.
     */
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
        model.setLevel(model.getLevel()+1);
        System.out.println("Level incremented to: " + model.getLevel());


        model.getBlocks().clear();
        model.setDestroyedBlockCount(0);
        initializeGame();
        System.out.println("Total blocks created: " + model.getBlocks().size());

    }
    /**
     * Resets the game to its initial state.
     * Resets scores, hearts, levels, and other game parameters to their default values, and initializes the game.
     */
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

    /**
     * Sets game properties from a loaded game state.
     * Updates the model with game properties such as level, score, and position of objects, based on a loaded game state.
     *
     * @param level The loaded level.
     * @param score The loaded score.
     * @param heart The loaded heart count.
     * @param destroyedBlockCount The loaded count of destroyed blocks.
     * @param xBall The loaded X position of the ball.
     * @param yBall The loaded Y position of the ball.
     * @param xPaddle The loaded X position of the paddle.
     * @param yPaddle The loaded Y position of the paddle.
     * @param centerPaddleX The loaded center X position of the paddle.
     * @param time The loaded game time.
     * @param goldTime The loaded gold time.
     * @param vX The loaded X velocity of the ball.
     * @param isExistHeartBlock The loaded state of the heart block existence.
     * @param isGoldStatus The loaded gold status.
     * @param goDownBall The loaded direction of the ball going down.
     * @param goRightBall The loaded direction of the ball going right.
     * @param collideToPaddle The loaded state of collision to the paddle.
     * @param collideToPaddleAndMoveToRight The loaded state of collision to the paddle and moving to the right.
     * @param collideToRightWall The loaded state of collision to the right wall.
     * @param collideToLeftWall The loaded state of collision to the left wall.
     * @param collideToRightBlock The loaded state of collision to the right block.
     * @param collideToBottomBlock The loaded state of collision to the bottom block.
     * @param collideToLeftBlock The loaded state of collision to the left block.
     * @param collideToTopBlock The loaded state of collision to the top block.
     */

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


    /**
     * Initializes the game.
     * Invokes the game initialization controller to set up the game for the first time or after a reset.
     */
    public void initializeGame() {
        gameInitializationController.initializeGame();
    }

}

