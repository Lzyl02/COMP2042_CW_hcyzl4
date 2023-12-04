package brickGame;

import javafx.animation.AnimationTimer;
import javafx.application.Platform;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GameModel {
    public static int score;
    private static GameModel instance;
    private double fallingSpeed = 2.0; // Adjust this value as needed
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
    public static String getSavePath() {
        return savePath;
    }

    public static void setSavePath(String savePath) {
        GameModel.savePath = savePath;
    }

    public static String getSavePathDir() {
        return savePathDir;
    }

    public static void setSavePathDir(String savePathDir) {
        GameModel.savePathDir = savePathDir;
    }

    public double getUpdateInterval() {
        return updateInterval;
    }

    public void setUpdateInterval(double updateInterval) {
        this.updateInterval = updateInterval;
    }

    public long getLastUpdateTime() {
        return lastUpdateTime;
    }

    public void setLastUpdateTime(long lastUpdateTime) {
        this.lastUpdateTime = lastUpdateTime;
    }

    public static String savePath = "D:/save/save.mdds";
    public static String savePathDir = "D:/save/";

    private int sceneWidth = 500;
    private double updateInterval = 1000000000.0 / 240.0; // Adjust the frame rate (240 FPS in this example)
    private long lastUpdateTime = 0;
    private int sceneHeight = 700;


    private double centerPaddleX;
    private double xBall;
    private double yBall;
    public static int getScore() {
        return score;
    }

    public static void setScore(int score) {
        GameModel.score = score;
    }

    public static void setInstance(GameModel instance) {
        GameModel.instance = instance;
    }

    public double getFallingSpeed() {
        return fallingSpeed;
    }

    public void setFallingSpeed(double fallingSpeed) {
        this.fallingSpeed = fallingSpeed;
    }

    public void setBombs(ArrayList<Bombs> bombs) {
        this.bombs = bombs;
    }

    public GameController getController() {
        return controller;
    }

    public int getSceneWidth() {
        return sceneWidth;
    }

    public void setSceneWidth(int sceneWidth) {
        this.sceneWidth = sceneWidth;
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

    public void setSceneHeight(int sceneHeight) {
        this.sceneHeight = sceneHeight;
    }

    public long getHitTime() {
        return hitTime;
    }

    public void setHitTime(long hitTime) {
        this.hitTime = hitTime;
    }

    public GameEngine getEngine() {
        return engine;
    }

    public void setEngine(GameEngine engine) {
        this.engine = engine;
    }

    public void setPaddleWidth(int paddleWidth) {
        this.paddleWidth = paddleWidth;
    }

    public void setPaddleHeight(int paddleHeight) {
        this.paddleHeight = paddleHeight;
    }

    public double getMaxBounceAngle() {
        return maxBounceAngle;
    }

    public void setMaxBounceAngle(double maxBounceAngle) {
        this.maxBounceAngle = maxBounceAngle;
    }

    public static void setHeart(int heart) {
        GameModel.heart = heart;
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

    public void setBallRadius(int ballRadius) {
        this.ballRadius = ballRadius;
    }

    public static void setLevel(int level) {
        GameModel.level = level;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public boolean isExistHeartBlock() {
        return isExistHeartBlock;
    }

    public void setExistHeartBlock(boolean existHeartBlock) {
        isExistHeartBlock = existHeartBlock;
    }

    public void setChocos(ArrayList<Bonus> chocos) {
        this.chocos = chocos;
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



    public static List<Block> getBlocks() {
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
    private GameEngine engine;

    private int paddleWidth     = 120;

    public int getPaddleWidth() {
        return paddleWidth;
    }

    public int getPaddleHeight() {
        return paddleHeight;
    }
    public static void clearBlocks() {
        blocks.clear();
    }

    public static void addBlock(Block block) {
        blocks.add(block);
    }
    public int getHalfPaddleWidth() {
        return halfPaddleWidth;
    }

    private int paddleHeight    = 30;
    private final int halfPaddleWidth = paddleWidth / 2;
    private double maxBounceAngle;
    public static int  heart    =5;


    public int getHeart() {
        return heart;
    }

    public double getxBall() {
        return xBall;
    }

    public double getyBall() {
        return yBall;
    }

    public ArrayList<Bombs> getBombs() {
        return bombs;
    }
    // 游戏状态变量
    public int getLevel() {
        return level;

    }

    public int getDestroyedBlockCount() {
        return destroyedBlockCount;
    }

    private int destroyedBlockCount = 0;

    public boolean isGoldStatus() {
        return isGoldStatus;
    }


    private boolean isGoldStatus = false;

    public int getBallRadius() {
        return ballRadius;
    }

    private int       ballRadius = 10;

    public static int level = 1;

    public long getTime() {
        return time;
    }

    private long time;


    // 游戏对象

    public ArrayList<Bonus> getChocos() {
        return chocos;
    }



    private boolean isExistHeartBlock = false;


    public static final int BLOCK_DAEMON = 3;

    public boolean isGoDownBall() {
        return goDownBall;
    }

    public boolean isGoRightBall() {
        return goRightBall;
    }

    public boolean isCollideToPaddle() {
        return collideToPaddle;
    }

    public boolean isCollideToPaddleAndMoveToRight() {
        return collideToPaddleAndMoveToRight;
    }

    public boolean isCollideToRightWall() {
        return collideToRightWall;
    }

    public boolean isCollideToLeftWall() {
        return collideToLeftWall;
    }

    public boolean isCollideToRightBlock() {
        return collideToRightBlock;
    }

    public boolean isCollideToBottomBlock() {
        return collideToBottomBlock;
    }

    public boolean isCollideToLeftBlock() {
        return collideToLeftBlock;
    }

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

    private boolean isGameOver = false;

    public long getGoldTime() {
        return goldTime;
    }

    private long goldTime = 0;
    static final double baseHorizontalSpeed = 1.2; // adjust as needed


    private static final double baseVerticalSpeed = 1.2;   // adjust as needed

    public double getvX() {
        return vX;
    }

    private double vX = 4.000;
    private double vY = 4.000;



    public GameModel() {
        this.chocos = new ArrayList<>();

        // Initialization code...
    }



    public void setController(GameController controller) {
        this.controller = controller;
    }








    // Public static method for getting the instance
    public static GameModel getInstance() {
        if (instance == null) {
            instance = new GameModel(); // Use the default constructor
        }
        return instance;
    }












    // Modify methods that use the controller to check for null


}

