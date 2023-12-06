package brickGame;

import javafx.animation.AnimationTimer;
import javafx.application.Platform;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Represents the game model for a brick game, holding all the game's state and logic.
 * It includes the game's score, block information, paddle and ball positions, and other game state variables.
 * @see <a href="https://github.com/Lzyl02/COMP2042_CW_hcyzl4/blob/main/src/main/java/brickGame/GameModel.java">GameModel.java</a>
 */
public class GameModel {
    /**
     * The score of the game.
     */
    public int score;

    /**
     * The speed at which bonuses fall.
     */
    private double fallingSpeed = 2.0;
    /**
     * List of all the bonuses (chocos) in the game.
     */
    private ArrayList<Bonus> chocos;
    /**
     * List of all the bombs in the game.
     */
    private ArrayList<Bombs> bombs = new ArrayList<>(); // 保存所有炸弹的列表


    private boolean isExistHeartBlock = false;
    private long time;
    public int  heart    =5;

    private static final List<Block> blocks = new ArrayList<>();
    private double yPaddle = 640.0f;
    private long hitTime  = 0;
    private int paddleWidth     = 120;
    private int paddleHeight    = 30;
    private int destroyedBlockCount = 0;
    private int       ballRadius = 10;
    private boolean goDownBall                  = true;

    private boolean goRightBall                 = true;
    private boolean collideToPaddle = false;
    private boolean collideToPaddleAndMoveToRight = true;
    private boolean collideToRightWall = false;
    private boolean collideToLeftWall = false;
    private boolean collideToRightBlock = false;
    private boolean collideToBottomBlock = false;
    private boolean collideToLeftBlock = false;
    private boolean collideToTopBlock = false;

    private long goldTime = 0;

    /**
     * The base horizontal speed of the game elements.
     */
    static final double baseHorizontalSpeed = 1.2; // adjust as needed
    private double vX = 4.000;
    /**
     * The vertical velocity of the ball.
     */
    private double vY = 4.000;

    private GameController controller = null;

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

    public static String savePath = "D:/save/save.mdds";
    public static String savePathDir = "D:/save/";

    private int sceneWidth = 500;
    private double updateInterval = 1000000000.0 / 240.0; // Adjust the frame rate (240 FPS in this example)
    private long lastUpdateTime = 0;
    private int sceneHeight = 700;


    private double centerPaddleX;
    private double xBall;
    private double yBall;
    private double xPaddle = sceneWidth /2;

    /**
     * Gets the current game score.
     * @return The current score.
     */
    public int getScore() {
        return score;
    }

    /**
     * Sets the current score of the game.
     * @param score The new score to be set.
     */
    public void setScore(int score) {
        this.score = score;
    }

    /**
     * Gets the falling speed of objects in the game.
     * @return The falling speed.
     */

    public double getFallingSpeed() {
        return fallingSpeed;
    }


    /**
     * Retrieves the game controller.
     * @return The GameController instance.
     */
    public GameController getController() {
        return controller;
    }

    /**
     * Gets the width of the game scene.
     * @return The width of the scene.
     */
    public int getSceneWidth() {
        return sceneWidth;
    }

    /**
     * Sets the x-coordinate of the center of the paddle.
     * @param centerPaddleX The new x-coordinate for the center of the paddle.
     */
    public void setCenterPaddleX(double centerPaddleX) {
        this.centerPaddleX = centerPaddleX;
    }
    /**
     * Sets the y-coordinate of the paddle.
     * @param yPaddle The new y-coordinate for the paddle.
     */
    public void setyPaddle(double yPaddle) {
        this.yPaddle = yPaddle;
    }

    /**
     * Gets the height of the game scene.
     * @return The height of the scene.
     */
    public int getSceneHeight() {
        return sceneHeight;
    }

    /**
     * Sets the last hit time in the game.
     * @param hitTime The time of the last hit.
     */

    public void setHitTime(long hitTime) {
        this.hitTime = hitTime;
    }

    /**
     * Sets the width of the paddle.
     * @param paddleWidth The new width of the paddle.
     */
    public void setPaddleWidth(int paddleWidth) {
        this.paddleWidth = paddleWidth;
    }

    /**
     * Sets the number of hearts or lives in the game.
     * @param heart The new number of hearts.
     */

    public  void setHeart(int heart) {
        this.heart = heart;
    }

    /**
     * Sets the x-coordinate of the ball.
     * @param xBall The new x-coordinate for the ball.
     */
    public void setxBall(double xBall) {
        this.xBall = xBall;
    }

    /**
     * Sets the y-coordinate of the ball.
     * @param yBall The new y-coordinate for the ball.
     */
    public void setyBall(double yBall) {
        this.yBall = yBall;
    }

    /**
     * Sets the count of destroyed blocks in the game.
     * @param destroyedBlockCount The new count of destroyed blocks.
     */
    public void setDestroyedBlockCount(int destroyedBlockCount) {
        this.destroyedBlockCount = destroyedBlockCount;
    }
    /**
     * Sets the gold status in the game.
     * @param goldStatus The new gold status (true for active, false for inactive).
     */
    public void setGoldStatus(boolean goldStatus) {
        isGoldStatus = goldStatus;
    }
    /**
     * Sets the current level in the game.
     * @param level The new level to be set.
     */

    public void setLevel(int level) {
        this.level = level;
    }
    /**
     * Sets the current game time.
     * @param time The new time to be set.
     */
    public void setTime(long time) {
        this.time = time;
    }

    /**
     * Sets the existence of a heart block in the game.
     * @param existHeartBlock True if a heart block exists, false otherwise.
     */
    public void setExistHeartBlock(boolean existHeartBlock) {
        isExistHeartBlock = existHeartBlock;
    }

    /**
     * Sets the direction of the ball to move downwards.
     * @param goDownBall True to make the ball move downwards, false otherwise.
     */

    public void setGoDownBall(boolean goDownBall) {
        this.goDownBall = goDownBall;
    }

    /**
     * Sets the direction of the ball to move to the right.
     * @param goRightBall True to make the ball move to the right, false otherwise.
     */
    public void setGoRightBall(boolean goRightBall) {
        this.goRightBall = goRightBall;
    }

    /**
     * Sets whether the ball has collided with the paddle.
     * @param collideToPaddle True if the ball has collided with the paddle, false otherwise.
     */
    public void setCollideToPaddle(boolean collideToPaddle) {
        this.collideToPaddle = collideToPaddle;
    }
    /**
     * Sets whether the ball has collided with the paddle and moved to the right.
     * @param collideToPaddleAndMoveToRight True if the ball has collided with the paddle and moved to the right, false otherwise.
     */
    public void setCollideToPaddleAndMoveToRight(boolean collideToPaddleAndMoveToRight) {
        this.collideToPaddleAndMoveToRight = collideToPaddleAndMoveToRight;
    }
    /**
     * Sets whether the ball has collided with the right wall.
     * @param collideToRightWall True if the ball has collided with the right wall, false otherwise.
     */
    public void setCollideToRightWall(boolean collideToRightWall) {
        this.collideToRightWall = collideToRightWall;
    }

    /**
     * Sets whether the ball has collided with the left wall.
     * @param collideToLeftWall True if the ball has collided with the left wall, false otherwise.
     */
    public void setCollideToLeftWall(boolean collideToLeftWall) {
        this.collideToLeftWall = collideToLeftWall;
    }
    /**
     * Sets whether the ball has collided with a block on its right.
     * @param collideToRightBlock True if the ball has collided with a right block, false otherwise.
     */
    public void setCollideToRightBlock(boolean collideToRightBlock) {
        this.collideToRightBlock = collideToRightBlock;
    }
    /**
     * Sets whether the ball has collided with a block at its bottom.
     * @param collideToBottomBlock True if the ball has collided with a bottom block, false otherwise.
     */
    public void setCollideToBottomBlock(boolean collideToBottomBlock) {
        this.collideToBottomBlock = collideToBottomBlock;
    }
    /**
     * Sets whether the ball has collided with a block on its left.
     * @param collideToLeftBlock True if the ball has collided with a left block, false otherwise.
     */
    public void setCollideToLeftBlock(boolean collideToLeftBlock) {
        this.collideToLeftBlock = collideToLeftBlock;
    }
    /**
     * Sets whether the ball has collided with a block at its top.
     * @param collideToTopBlock True if the ball has collided with a top block, false otherwise.
     */
    public void setCollideToTopBlock(boolean collideToTopBlock) {
        this.collideToTopBlock = collideToTopBlock;
    }
    /**
     * Checks if the game is over.
     * @return True if the game is over, false otherwise.
     */
    public boolean isGameOver() {
        return isGameOver;
    }
    /**
     * Sets the game over status.
     * @param gameOver True to indicate the game is over, false otherwise.
     */

    public void setGameOver(boolean gameOver) {
        isGameOver = gameOver;
    }
    /**
     * Sets the duration for which the gold status is active.
     * @param goldTime The duration of the gold status.
     */
    public void setGoldTime(long goldTime) {
        this.goldTime = goldTime;
    }

    /**
     * Sets the horizontal velocity of the ball.
     * @param vX The new horizontal velocity.
     */
    public void setvX(double vX) {
        this.vX = vX;
    }

    /**
     * Gets the vertical velocity of the ball.
     * @return The vertical velocity.
     */
    public double getvY() {
        return vY;
    }
    /**
     * Sets the vertical velocity of the ball.
     * @param vY The new vertical velocity.
     */
    public void setvY(double vY) {
        this.vY = vY;
    }
    /**
     * Retrieves the array of colors used in the game.
     * @return An array of Color objects.
     */
    public Color[] getColors() {
        return colors;
    }
    /**
     * Retrieves the list of blocks present in the game.
     * @return A list of Block objects.
     */

    public List<Block> getBlocks() {
        return blocks;
    }
    /**
     * Gets the x-coordinate of the paddle's position.
     * @return The x-coordinate of the paddle.
     */
    public double getxPaddle() {
        return xPaddle;
    }
    /**
     * Gets the x-coordinate of the center of the paddle.
     * @return The x-coordinate of the paddle's center.
     */
    public double getCenterPaddleX() {
        return centerPaddleX;
    }
    /**
     * Gets the y-coordinate of the paddle's position.
     * @return The y-coordinate of the paddle.
     */
    public double getyPaddle() {
        return yPaddle;
    }
    /**
     * Sets the x-coordinate of the paddle's position.
     * @param xPaddle The new x-coordinate of the paddle.
     */
    public void setxPaddle(double xPaddle) {
        this.xPaddle = xPaddle;
    }


    /**
     * Retrieves the width of the paddle.
     * @return The width of the paddle.
     */
    public int getPaddleWidth() {
        return paddleWidth;
    }
    /**
     * Retrieves the height of the paddle.
     * @return The height of the paddle.
     */
    public int getPaddleHeight() {
        return paddleHeight;
    }
    /**
     * Clears all blocks from the game model.
     */
    public void clearBlocks() {
        blocks.clear();
    }

    /**
     * Adds a block to the game model.
     * @param block The block to be added.
     */
    public void addBlock(Block block) {
        blocks.add(block);
    }
    /**
     * Retrieves the half width of the paddle.
     * @return The half width of the paddle.
     */
    public int getHalfPaddleWidth() {
        return halfPaddleWidth;
    }
    /**
     * The height of the paddle.
     */


    /**
     * The half width of the paddle, calculated as half of the paddle width.
     */
    private final int halfPaddleWidth = paddleWidth / 2;
    /**
     * The number of hearts or lives in the game.
     */

    /**
     * Retrieves the current number of hearts.
     * @return The current number of hearts.
     */

    public int getHeart() {
        return heart;
    }

    /**
     * Retrieves the current horizontal position of the ball.
     * @return The current horizontal position of the ball.
     */
    public double getxBall() {
        return xBall;
    }
    /**
     * Retrieves the current vertical position of the ball.
     * @return The current vertical position of the ball.
     */
    public double getyBall() {
        return yBall;
    }
    /**
     * Retrieves the list of bombs in the game.
     * @return The list of bombs.
     */
    public ArrayList<Bombs> getBombs() {
        return bombs;
    }

    /**
     * Retrieves the current game level.
     * @return The current game level.
     */
    public int getLevel() {
        return this.level;

    }
    /**
     * Retrieves the count of destroyed blocks in the game.
     * @return The count of destroyed blocks.
     */
    public int getDestroyedBlockCount() {
        return destroyedBlockCount;
    }

    /**
     * Indicates whether the gold status is active in the game.
     * @return True if gold status is active, false otherwise.
     */
    public boolean isGoldStatus() {
        return isGoldStatus;
    }


    private boolean isGoldStatus = false;
    /**
     * Retrieves the radius of the ball.
     * @return The ball's radius.
     */
    public int getBallRadius() {
        return ballRadius;
    }


    /**
     * The current game level.
     */
    public static int level = 1;
    /**
     * Retrieves the current game time.
     * @return The current game time.
     */
    public long getTime() {
        return time;
    }



    /**
     * Retrieves the list of bonuses in the game.
     * @return The list of bonuses.
     */

    public ArrayList<Bonus> getChocos() {
        return chocos;
    }


    /**
     * Indicates whether a heart block exists in the game.
     */


    // Methods related to ball movement and collision states

    /**
     * Indicates whether the ball is moving downwards.
     * @return True if the ball is moving downwards, false otherwise.
     */
    public boolean isGoDownBall() {
        return goDownBall;
    }
    /**
     * Indicates whether the ball is moving to the right.
     * @return True if the ball is moving to the right, false otherwise.
     */
    public boolean isGoRightBall() {
        return goRightBall;
    }
    /**
     * Indicates whether the ball has collided with the paddle.
     * @return True if the ball has collided with the paddle, false otherwise.
     */
    public boolean isCollideToPaddle() {
        return collideToPaddle;
    }
    /**
     * Indicates whether the ball has collided with the paddle and moved to the right.
     * @return True if the ball has collided with the paddle and moved to the right, false otherwise.
     */
    public boolean isCollideToPaddleAndMoveToRight() {
        return collideToPaddleAndMoveToRight;
    }
    /**
     * Indicates whether the ball has collided with the right wall.
     * @return True if the ball has collided with the right wall, false otherwise.
     */
    public boolean isCollideToRightWall() {
        return collideToRightWall;
    }
    /**
     * Indicates whether the ball has collided with the left wall.
     * @return True if the ball has collided with the left wall, false otherwise.
     */
    public boolean isCollideToLeftWall() {
        return collideToLeftWall;
    }
    /**
     * Indicates whether the ball has collided with a block on its right.
     * @return True if the ball has collided with a right block, false otherwise.
     */
    public boolean isCollideToRightBlock() {
        return collideToRightBlock;
    }
    /**
     * Indicates whether the ball has collided with a block at its bottom.
     * @return True if the ball has collided with a bottom block, false otherwise.
     */
    public boolean isCollideToBottomBlock() {
        return collideToBottomBlock;
    }
    /**
     * Indicates whether the ball has collided with a block on its left.
     * @return True if the ball has collided with a left block, false otherwise.
     */
    public boolean isCollideToLeftBlock() {
        return collideToLeftBlock;
    }
    /**
     * Indicates whether the ball has collided with a block at its top.
     * @return True if the ball has collided with a top block, false otherwise.
     */
    public boolean isCollideToTopBlock() {
        return collideToTopBlock;
    }



    /**
     * Indicates whether the game is over.
     */
    private boolean isGameOver = false;

    /**
     * The duration for which the gold status is active.
     */
    public long getGoldTime() {
        return goldTime;
    }



    public double getvX() {
        return vX;
    }
    /**
     * The horizontal velocity of the ball.
     */


    /**
     * Constructs a new GameModel and initializes the game state.
     */
    public GameModel() {
        this.chocos = new ArrayList<>();
        this.score = 0;

        // Initialization code...
    }

    /**
     * Sets the game controller.
     * @param controller The GameController to be set.
     */
    public void setController(GameController controller) {
        this.controller = controller;
    }


}

