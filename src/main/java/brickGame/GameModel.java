package brickGame;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.Random;

public class GameModel {


    // 游戏状态变量
    private int level = 0;
    private double PaddleX = 0.0f;
    private double centerBreakX;
    private double PaddleY = 640.0f;
    private int PaddleWidth = 130;
    private int PaddleHeight = 30;
    private int halfBreakWidth = PaddleWidth / 2;
    public int sceneWidth = 500;
    public int sceneHeight = 700;
    private static int LEFT = 1;
    private static int RIGHT = 2;

    // 游戏元素
    private Circle ball;
    private double BallX;
    private double BallY;
    private Rectangle rect;

    public int getBallRadius() {
        return BallRadius;
    }

    private int BallRadius = 10;
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

    public ArrayList<Block> getBlocks() {
        return Blocks;
    }

    // 游戏元素列表
    private ArrayList<Block> Blocks = new ArrayList<Block>();
    private ArrayList<Bonus> chocos = new ArrayList<Bonus>();
    private Color[] Colors = new Color[] {
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

    Score scoreDisplay = new Score();

    
    public int getSceneWidth() {
        return sceneWidth;
    }

    public int getSceneHeight() {
        return sceneHeight;
    }

    public double getPaddleX() {
        return PaddleX;
    }

    public double getPaddleY() {
        return PaddleY;
    }

    public int getPaddleWidth() {
        return PaddleWidth;
    }

    public int getPaddleHeight() {
        return PaddleHeight;
    }

    public double getBallX() {
        return BallX;
    }

    public double getBallY() {
        return BallY;
    }

    private void initBall() {
        Random random = new Random();
        // 初始化球的位置
        BallX = random.nextInt(sceneWidth) + 1;
        BallY = random.nextInt(sceneHeight - 200) + ((level + 1) * Block.getHeight()) + 15;

        // 初始化球的其他属性，如速度和方向（如果游戏中需要）
        // 例如：
        // velocityX = initialVelocityX;
        // velocityY = initialVelocityY;
    }

    public int getLevel() {
        return level;
    }

    public int getHeart() {
        return heart;
    }

    public int getScore() {
        return score;
    }

    private void initBoard() {
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < level + 1; j++) {
                int r = new Random().nextInt(500);
                if (r % 5 == 0) {
                    continue;
                }
                int type;
                if (r % 10 == 1) {
                    type = Block.BLOCK_CHOCO;
                } else if (r % 10 == 2) {
                    if (!isExistHeartBlock) {
                        type = Block.BLOCK_HEART;
                        isExistHeartBlock = true;
                    } else {
                        type = Block.BLOCK_NORMAL;
                    }
                } else if (r % 10 == 3) {
                    type = Block.BLOCK_STAR;
                } else {
                    type = Block.BLOCK_NORMAL;
                }
                Blocks.add(new Block(j, i, Colors[r % (Colors.length)], type));
                //System.out.println("colors " + r % (colors.length));
            }
        }
    }

    private void resetCollideFlags() {

        collideToBreak = false;
        collideToBreakAndMoveToRight = false;
        collideToRightWall = false;
        collideToLeftWall = false;

        collideToRightBlock = false;
        collideToBottomBlock = false;
        collideToLeftBlock = false;
        collideToTopBlock = false;
    }

    public void initializeGameState() {
        // 初始化或重置游戏的基础状态
        level = 0;  // 初始级别
        heart = 3;  // 初始心数（生命值）
        score = 0;  // 初始分数
        vX = 1.000; // 初始水平速度（如果需要）

        // 初始化球的位置和其他相关状态
        initBall();

        // 初始化方块的排列
        initBoard();

        // 重置其他游戏相关的状态标志
        isGoldStatus = false;
        isExistHeartBlock = false;
        destroyedBlockCount = 0;
        hitTime = 0;
        time = 0;
        goldTime = 0;

        // 清除可能存在的上一局游戏的残留状态
        Blocks.clear();
        chocos.clear();
    }
    public void updateScore(int scoreDelta) {
        // 更新分数逻辑
        this.score += scoreDelta;
        // 可能还需要通知视图进行更新
    }

    private void setPhysicsToBall() {
        //v = ((time - hitTime) / 1000.000) + 1.000;

        if (goDownBall) {
            BallY += vY;
        } else {
            BallY -= vY;
        }

        if (goRightBall) {
            BallX += vX;
        } else {
            BallX -= vX;
        }

        if (BallY <= 0) {
            //vX = 1.000;
            resetCollideFlags();
            goDownBall = true;
            return;
        }
        if (BallY >= sceneHeight) {
            goDownBall = false;
            if (!isGoldStatus) {
                //TODO gameover
                heart--;
                GameView view;
                scoreDisplay.show(sceneWidth / 2, sceneHeight / 2, -1, view.getRootPane()); // 使用 GameView 的根 Pane

                if (heart == 0) {
                    new Score().showGameOver(this);
                    engine.stop();
                }

            }
            //return;
        }

        if (BallY >= PaddleY - BallRadius) {
            //System.out.println("Colide1");
            if (BallX >= PaddleX && BallX <= PaddleX + PaddleWidth) {
                hitTime = time;
                resetCollideFlags();
                collideToBreak = true;
                goDownBall = false;

                double relation = (BallX - centerBreakX) / (PaddleWidth / 2);

                if (Math.abs(relation) <= 0.3) {
                    //vX = 0;
                    vX = Math.abs(relation);
                } else if (Math.abs(relation) > 0.3 && Math.abs(relation) <= 0.7) {
                    vX = (Math.abs(relation) * 1.5) + (level / 3.500);
                    //System.out.println("vX " + vX);
                } else {
                    vX = (Math.abs(relation) * 2) + (level / 3.500);
                    //System.out.println("vX " + vX);
                }

                if (BallX - centerBreakX > 0) {
                    collideToBreakAndMoveToRight = true;
                } else {
                    collideToBreakAndMoveToRight = false;
                }
                //System.out.println("Collide2");
            }
        }

        if (BallX >= sceneWidth) {
            resetCollideFlags();
            //vX = 1.000;
            collideToRightWall = true;
        }

        if (BallX <= 0) {
            resetCollideFlags();
            //vX = 1.000;
            collideToLeftWall = true;
        }

        if (collideToBreak) {
            if (collideToBreakAndMoveToRight) {
                goRightBall = true;
            } else {
                goRightBall = false;
            }
        }

        //Wall Collide

        if (collideToRightWall) {
            goRightBall = false;
        }

        if (collideToLeftWall) {
            goRightBall = true;
        }

        //Block Collide

        if (collideToRightBlock) {
            goRightBall = true;
        }

        if (collideToLeftBlock) {
            goRightBall = false;
        }

        if (collideToTopBlock) {
            goDownBall = false;
        }

        if (collideToBottomBlock) {
            goDownBall = true;
        }


    }

    void nextLevel() {
        // 增加游戏级别
        level++;

        // 重置游戏状态变量
        vX = 1.000;
        resetCollideFlags();
        goDownBall = true;
        isGoldStatus = false;
        isExistHeartBlock = false;
        hitTime = 0;
        time = 0;
        goldTime = 0;
        destroyedBlockCount = 0;

        // 清除当前级别的方块和奖励
        Blocks.clear();
        chocos.clear();

        // 初始化新级别的方块
        initBoard();

        // 可以添加更多与开始新级别相关的逻辑
    }

    private void checkDestroyedCount() {
        if (destroyedBlockCount == Blocks.size()) {
            //TODO win level todo...
            //System.out.println("You Win");

            nextLevel();
        }
    }

    public void restartGame() {
        level = 0;
        heart = 3;
        score = 0;
        vX = 1.000;
        destroyedBlockCount = 0;
        resetCollideFlags();
        goDownBall = true;
        isGoldStatus = false;
        isExistHeartBlock = false;
        hitTime = 0;
        time = 0;
        goldTime = 0;

        Blocks.clear();
        chocos.clear();

        initBoard();  // 重新初始化游戏板块
    }


    public void onUpdate() {
        if (BallY >= Block.getPaddingTop() && BallY <= (Block.getHeight() * (level + 1)) + Block.getPaddingTop()) {
            for (Block block : Blocks) {
                int hitCode = block.checkHitToBlock(BallX, BallY);
                if (hitCode != Block.NO_HIT && !block.isDestroyed) {
                    handleBlockCollision(block, hitCode);
                }
            }
        }
        // ... 其他游戏逻辑 ...
    }
    public void onInit() {

    }
    public void onTime(long time) {
        this.time = time;
    }
    private void handleBlockCollision(Block block, int hitCode) {
        score += 1; // 示例：增加分数
        block.isDestroyed = true;
        destroyedBlockCount++;

        // 根据方块类型处理特殊逻辑
        // ... 处理逻辑 ...
    }

    public void onPhysicsUpdate() {
        checkDestroyedCount();
        setPhysicsToBall();

        if (time - goldTime > 5000) {
            isGoldStatus = false;
        }

        for (Bonus choco : chocos) {
            if (choco.y > sceneHeight || choco.taken) {
                continue;
            }
            if (choco.y >= PaddleY && choco.y <= PaddleY + PaddleHeight && choco.x >= PaddleX && choco.x <= PaddleX + PaddleWidth) {
                choco.taken = true;
                score += 3; // 更新分数
            }
            // 更新奖励位置
            choco.y += ((time - choco.timeCreated) / 1000.000) + 1.000;
        }

        // ... 其他物理更新逻辑 ...
    }


}
