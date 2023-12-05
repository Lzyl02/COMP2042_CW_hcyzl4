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

    /**
     * Gets the current game score.
     * @return The current score.
     */
    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }


    public double getFallingSpeed() {
        return fallingSpeed;
    }


    public GameController getController() {
        return controller;
    }

    public int getSceneWidth() {
        return sceneWidth;
    }


    public void setCenterPaddleX(double centerPaddleX) {
        this.centerPaddleX = centerPaddleX;
    }

    public void setyPaddle(double yPaddle) {
        this.yPaddle = yPaddle;
    }

    public int getSceneHeight() {
        return sceneHeight;
    }


    public void setHitTime(long hitTime) {
        this.hitTime = hitTime;
    }


    public void setPaddleWidth(int paddleWidth) {
        this.paddleWidth = paddleWidth;
    }


    public  void setHeart(int heart) {
        this.heart = heart;
    }

    public void setxBall(double xBall) {
        this.xBall = xBall;
    }

    public void setyBall(double yBall) {
        this.yBall = yBall;
    }

    public void setDestroyedBlockCount(int destroyedBlockCount) {
        this.destroyedBlockCount = destroyedBlockCount;
    }

    public void setGoldStatus(boolean goldStatus) {
        isGoldStatus = goldStatus;
    }


    public void setLevel(int level) {
        this.level = level;
    }

    public void setTime(long time) {
        this.time = time;
    }


    public void setExistHeartBlock(boolean existHeartBlock) {
        isExistHeartBlock = existHeartBlock;
    }


    public void setGoDownBall(boolean goDownBall) {
        this.goDownBall = goDownBall;
    }

    public void setGoRightBall(boolean goRightBall) {
        this.goRightBall = goRightBall;
    }

    public void setCollideToPaddle(boolean collideToPaddle) {
        this.collideToPaddle = collideToPaddle;
    }

    public void setCollideToPaddleAndMoveToRight(boolean collideToPaddleAndMoveToRight) {
        this.collideToPaddleAndMoveToRight = collideToPaddleAndMoveToRight;
    }

    public void setCollideToRightWall(boolean collideToRightWall) {
        this.collideToRightWall = collideToRightWall;
    }

    public void setCollideToLeftWall(boolean collideToLeftWall) {
        this.collideToLeftWall = collideToLeftWall;
    }

    public void setCollideToRightBlock(boolean collideToRightBlock) {
        this.collideToRightBlock = collideToRightBlock;
    }

    public void setCollideToBottomBlock(boolean collideToBottomBlock) {
        this.collideToBottomBlock = collideToBottomBlock;
    }

    public void setCollideToLeftBlock(boolean collideToLeftBlock) {
        this.collideToLeftBlock = collideToLeftBlock;
    }

    public void setCollideToTopBlock(boolean collideToTopBlock) {
        this.collideToTopBlock = collideToTopBlock;
    }

    public boolean isGameOver() {
        return isGameOver;
    }

    public void setGameOver(boolean gameOver) {
        isGameOver = gameOver;
    }

    public void setGoldTime(long goldTime) {
        this.goldTime = goldTime;
    }

    public void setvX(double vX) {
        this.vX = vX;
    }

    public double getvY() {
        return vY;
    }

    public void setvY(double vY) {
        this.vY = vY;
    }

    public Color[] getColors() {
        return colors;
    }

    public void setColors(Color[] colors) {
        this.colors = colors;
    }

    private static final List<Block> blocks = new ArrayList<>();



    public List<Block> getBlocks() {
        return blocks;
    }
    private GameController controller = null;


    public double getxPaddle() {
        return xPaddle;
    }

    public double getCenterPaddleX() {
        return centerPaddleX;
    }

    public double getyPaddle() {
        return yPaddle;
    }

    public void setxPaddle(double xPaddle) {
        this.xPaddle = xPaddle;
    }

    private double xPaddle = sceneWidth/2;
    private double yPaddle = 640.0f;
    private long hitTime  = 0;
    private int paddleWidth     = 120;

    public int getPaddleWidth() {
        return paddleWidth;
    }

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
    private int paddleHeight    = 30;
    /**
     * The half width of the paddle, calculated as half of the paddle width.
     */
    private final int halfPaddleWidth = paddleWidth / 2;
    /**
     * The number of hearts or lives in the game.
     */
    public int  heart    =5;
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

    private int destroyedBlockCount = 0;
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

    private int       ballRadius = 10;
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

    private long time;


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
    private boolean isExistHeartBlock = false;

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

    private long goldTime = 0;

    /**
     * The base horizontal speed of the game elements.
     */
    static final double baseHorizontalSpeed = 1.2; // adjust as needed


    public double getvX() {
        return vX;
    }
    /**
     * The horizontal velocity of the ball.
     */
    private double vX = 4.000;
    /**
     * The vertical velocity of the ball.
     */
    private double vY = 4.000;
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

