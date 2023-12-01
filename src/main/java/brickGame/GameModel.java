package brickGame;

import javafx.animation.AnimationTimer;
import javafx.application.Platform;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GameModel implements GameEngine.OnAction {
    public static int score;
    private static GameModel instance;
    private double fallingSpeed = 2.0; // Adjust this value as needed
    private static final List<Block> blocks = new ArrayList<>();

    private ArrayList<Bombs> bombs = new ArrayList<>();



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
    private int sceneWidth = 500;

    private double xPaddle = sceneWidth/2;
    private double centerPaddleX;
    private double yPaddle = 640.0f;
    private int sceneHeight = 700;
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
    private double xBall;
    private double yBall;
    public int getLevel() {
        return this.level;

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

    private ArrayList<Bonus> chocos;

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
    private static final double baseHorizontalSpeed = 1.2; // adjust as needed
    private static final double baseVerticalSpeed = 1.2;   // adjust as needed

    public double getvX() {
        return vX;
    }

    private double vX = 4.000;
    private double vY = 4.000;


    private Color[]          colors = new Color[]{
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

    public GameModel() {
        this.chocos = new ArrayList<>();

        // Initialization code...
    }



    public void setController(GameController controller) {
        this.controller = controller;
    }

    public void initializeGame() {
        initializeBlocks(level); // 初始化所有类型的方块，包括 Daemon block

        // 初始化球和挡板的位置
        xPaddle = sceneWidth / 2; // 挡板在屏幕中央
        yPaddle = sceneHeight - 50; // 挡板位置在屏幕底部
        xBall = xPaddle + paddleWidth / 2; // 球开始在挡板上
        yBall = yPaddle - ballRadius - 1; // 球的位置在挡板之上
    }




    public void initializeBlocks(int newLevel) {
        this.level = newLevel;
        blocks.clear(); // 清除现有的方块

        Random random = new Random();
        int rows = level; // 行数基于级别
        int columns = 4; // 固定为 4 列

        // 在每个级别中创建相应数量的 Daemon block
        for (int i = 0; i < level; i++) {
            int daemonRow = random.nextInt(rows);
            int daemonColumn = random.nextInt(columns);
            Block daemonBlock = new Block(daemonRow, daemonColumn, Color.RED, Block.BLOCK_DAEMON);
            blocks.add(daemonBlock);
        }

        // 创建其他类型的方块
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                final int currentRow = i;
                final int currentColumn = j;

                // 检查是否已经在这个位置创建了 Daemon block
                boolean isDaemonBlockPresent = blocks.stream()
                        .anyMatch(b -> b.getRow() == currentRow && b.getColumn() == currentColumn && b.getType() == Block.BLOCK_DAEMON);

                if (!isDaemonBlockPresent) {
                    int r = random.nextInt(colors.length);
                    int type = determineBlockType(r, random);
                    Block block = new Block(currentRow, currentColumn, colors[r], type);
                    blocks.add(block);
                    System.out.println("Block created: Row " + currentRow + ", Column " + currentColumn +
                            ", Color: " + colors[r] + ", Type: " + type);
                }
            }
        }

        System.out.println("Total blocks created for level " + level + ": " + blocks.size());
    }



    public void resetGame() {
        // 重置游戏状态
        score = 0;
        heart = 5;
        level = 1;
        destroyedBlockCount = 0;
        isGoldStatus = false;

        // 重置球和挡板的位置和速度
        xBall = sceneWidth / 2;
        yBall = sceneHeight - paddleHeight - ballRadius - 1;
        xPaddle = sceneWidth / 2 - paddleWidth / 2;
        yPaddle = sceneHeight - 50;
        vX = 4;
        vY = 4;

        // 清除所有区块和奖励
        blocks.clear();
        chocos.clear();

        // 重置游戏结束标志
        isGameOver = false;

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

    @Override
    public void onUpdate() {

        Platform.runLater(() -> {
//            controller.updateBallPosition(xBall,yBall);
//            controller.updatePaddlePosition(xPaddle,yPaddle);

            updateUI();
            handleBlockCollision();

        });

    }


    // Public static method for getting the instance
    public static GameModel getInstance() {
        if (instance == null) {
            instance = new GameModel(); // Use the default constructor
        }
        return instance;
    }
    private void updateUI() {

        updateGameState();
    }

    private void resetColideFlags() {

        collideToPaddle = false;
        collideToPaddleAndMoveToRight = false;
        collideToRightWall = false;
        collideToLeftWall = false;

        collideToRightBlock = false;
        collideToBottomBlock = false;
        collideToLeftBlock = false;
        collideToTopBlock = false;
    }

    @Override
    public void onPhysicsUpdate() {
        checkDestroyedCount();
        setPhysicsToBall();

        if(time - goldTime > 5000) {
            controller.updateBallAppearance(isGoldStatus);
            isGoldStatus = false;
        }
        catchChoco();

        catchBomb();
        handleChocoMovement();
        handleBombMovement();
        if (controller != null) {
            controller.updateBonuses();
        }

    }

    private void handleChocoMovement() {
        for (Bonus choco : chocos) {
            if (!choco.taken) {
                choco.y += fallingSpeed;
                // Log the new position for debugging
                System.out.println("Choco updated: x = " + choco.getX() + ", y = " + choco.y);
            }
        }
    }

    private void handleBombMovement() {
        ArrayList<Bombs> bombsToRemove = new ArrayList<>();
        for (Bombs bomb : bombs) {
            bomb.fallDown(); // 更新炸弹的位置

            // 判断炸弹是否需要被移除（比如，如果炸弹已经到达底部或者被接住）
            if (bomb.getY() > sceneHeight || bomb.isTaken()) {
                bombsToRemove.add(bomb);
            }
        }

        // 移除标记的炸弹
        bombs.removeAll(bombsToRemove);
        bombsToRemove.forEach(b -> {
            controller.removeBombFromView(b); // 通知控制器从视图中移除炸弹
        });
    }
    public void catchBomb() {
        long currentTime = System.currentTimeMillis(); // 获取当前时间
        long catchCooldown = 1000; // 设置冷却时间（例如500毫秒）

        for (Bombs bomb : bombs) {
            if (bomb.getY() >= yPaddle && bomb.getY() <= yPaddle + paddleHeight &&
                    bomb.getX() >= xPaddle && bomb.getX() <= xPaddle + paddleWidth &&
                    !bomb.isTaken() && (currentTime - bomb.getLastCaughtTime() > catchCooldown)) {

                System.out.println("Bomb caught: x = " + bomb.getX() + ", y = " + bomb.getY());
                bomb.setTaken(true);
                bomb.setLastCaughtTime(currentTime); // 更新炸弹被捕获的时间
                score -= 1;
                System.out.println("Score deducted. New score: " + score);
                controller.updateScoreView(bomb.getX(), bomb.getY(), -1);

                break; // 中断循环
            }
        }
    }



    private void checkDestroyedCount() {
        if (destroyedBlockCount == blocks.size()) {
            //TODO win level todo...
            //System.out.println("You Win");
            if (controller != null)
                controller.nextLevel();
        }
        }


    private void setPhysicsToBall() {
        moveBall();
        handleWallCollision();
        handlePaddleCollision();
        // Handle collisions with blocks
        //
        }

    private void moveBall() {
        // Horizontal movement
        xBall += goRightBall ? vX : -vX;

        // Vertical movement
        yBall += goDownBall ? vY : -vY;

    }

    public void catchChoco() {
        ArrayList<Bonus> removedChocos = new ArrayList<>();
        for (Bonus choco : chocos) {
            if (choco.y >= yPaddle && choco.y <= yPaddle + paddleHeight &&
                    choco.getX() >= xPaddle && choco.getX() <= xPaddle + paddleWidth && !choco.isTaken()) {

                // Chocolate is caught
                System.out.println("Chocolate caught: x = " + choco.getX() + ", y = " + choco.y);
                choco.setTaken(true);
                score += 3;
                controller.updateScoreView(choco.getX(), choco.y, 3);
                removedChocos.add(choco);
            } else if (choco.y >= sceneHeight && !choco.isTaken()) {
                // Chocolate reached the bottom and was not caught
                System.out.println("Chocolate missed: x = " + choco.getX() + ", y = " + choco.y);
                choco.setTaken(true); // Mark it as handled
                removedChocos.add(choco);
            }
        }

        // Remove handled chocolates from the list
        chocos.removeAll(removedChocos);

        // Notify the controller to update the view
        if (controller != null) {
            controller.updateCaughtChocos(removedChocos);
        }
    }

    private void handleWallCollision() {
        // Collision with top wall
        if (yBall <= ballRadius) {
            goDownBall = true;
            yBall = ballRadius; // Prevent sticking to the wall
        }

        // Collision with bottom wall
        if (yBall >= sceneHeight - ballRadius && !isGameOver) { // 添加检查 isGameOver
            goDownBall = false;
            yBall = sceneHeight - ballRadius;
            if (!isGoldStatus) {
                heart--;
                if (heart == 0) {
                    isGameOver = true; // 设置游戏结束标志
                    if (controller != null) {
                        Platform.runLater(() -> controller.showGameOver()); // 确保在 JavaFX 线程中执行
                    }

                    engine.stop();// 停止游戏逻辑

                }
            }
        }
        // Collision with left wall
        if (xBall <= ballRadius) {
            goRightBall = true;
            xBall = ballRadius; // Adjust for left wall
        }

        // Collision with right wall
        if (xBall >= sceneWidth - ballRadius) {
            goRightBall = false;
            xBall = sceneWidth - ballRadius; // Adjust for right wall
        }
    }

    // Collision logic you want to use
    private void handlePaddleCollision() {

        // Calculate the ball's bottom edge
        double ballBottomEdge = yBall + ballRadius;

        // Calculate the paddle's horizontal range
        double paddleLeftEdge = xPaddle;
        double paddleRightEdge = xPaddle + paddleWidth;

        // Check if the ball is at the same vertical level as the paddle's top
        if (ballBottomEdge >= yPaddle && ballBottomEdge <= yPaddle + paddleHeight) {

            if (xBall >= paddleLeftEdge && xBall <= paddleRightEdge) {
                hitTime = time;
                resetColideFlags();
                collideToPaddle = true;
                goDownBall = false;

                // Calculate the relative position of the ball impact on the paddle
                double relativeImpactPosition = (xBall - paddleLeftEdge) / paddleWidth - 0.5; // Range: -0.5 to 0.5

                // Apply a scaling factor to control the change in velocity
                double scalingFactor = 0.8; // Adjust this value to control the maximum velocity change
                vX = baseHorizontalSpeed + scalingFactor * relativeImpactPosition * baseHorizontalSpeed;

                // Ensure that the velocity does not exceed a maximum value
                double maxVelocity = 2.0; // Adjust this value to set the maximum allowed horizontal velocity
                vX = Math.min(Math.max(vX, -maxVelocity), maxVelocity);

                // Update the direction based on the impact position
                goRightBall = relativeImpactPosition > 0;
            }
        }
    }


    private void handleBlockCollision() {
        if (yBall >= Block.getPaddingTop() && yBall <= (Block.getHeight() * (level + 1)) + Block.getPaddingTop()) {
            for (Block block : blocks) {
                int hitCode = block.checkHitToBlock(xBall, yBall);
                if (hitCode != Block.NO_HIT) {
                    processBlockHit(block, hitCode);
                    // Adjust ball's direction based on collision
                    handleSpecialBlocks(block);
                    adjustBallDirectionAfterBlockHit(hitCode);
                }
            }
        }
    }

    private void adjustBallDirectionAfterBlockHit(int hitCode) {
        switch (hitCode) {
            case Block.HIT_TOP:
            case Block.HIT_BOTTOM:
                goDownBall = !goDownBall; // Reverse vertical direction
                break;
            case Block.HIT_LEFT:
            case Block.HIT_RIGHT:
                goRightBall = !goRightBall; // Reverse horizontal direction
                break;
        }
    }

    private void processBlockHit(Block block, int hitCode) {
        System.out.println("Block hit detected at: x = " + block.getX() + ", y = " + block.getY());
        score += 1;
        block.setDestroyed(true);
        destroyedBlockCount++;

        if (controller != null) {
            controller.updateScoreView(block.getX(), block.getY(), 1);
            controller.handleBlockHit(block);
        }

        // 处理 Daemon 方块生成炸弹的逻辑
        if (block.getType() == Block.BLOCK_DAEMON && !block.hasGeneratedBomb()) {
            controller.createAndDropBomb(block);
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
                goldTime = time;
                if (controller != null) {
                    controller.changeBallAppearance("goldball.png");
                    controller.changeSceneStyleClass("goldRoot");                }

                isGoldStatus = true;
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
        choco.timeCreated = time;

        // Ensure that adding to the UI is done on the JavaFX Application Thread
        if(controller!=null) {
            controller.addChocoToView(choco);
            System.out.println("Chocolate bonus added to UI.");
        }

        chocos.add(choco);
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
        if (paddleWidth > 30) {
            paddleWidth -= 10;
        }
        if (paddleWidth < 30) {
            paddleWidth = 30;
        }

        // 更新视图
        controller.updatePaddleView();
    }

    public void prepareNextLevel() {
        // Reset game state variables
        updatePaddleSize();
        vX = 1.000;
        resetColideFlags();
        goDownBall = true;
        isGoldStatus = false;
        isExistHeartBlock = false;
        hitTime = 0;
        time = 0;
        goldTime = 0;
        level++;
        System.out.println("Level incremented to: " + level);


        blocks.clear();
        destroyedBlockCount = 0;
        initializeGame();
        System.out.println("Total blocks created: " + blocks.size());

    }







    @Override
    public void onTime(long time) {
        this.time = time;
    }

    @Override
    public void onInit() {

    }




    // Modify methods that use the controller to check for null
    private void updateGameState() {
        if (controller != null) {
            controller.updateUI();
        }
    }

    public void setGamePropertiesFromLoad(int level, int score, int heart, int destroyedBlockCount,
                                          double xBall, double yBall, double xPaddle, double yPaddle,
                                          double centerPaddleX, long time, long goldTime, double vX,
                                          boolean isExistHeartBlock, boolean isGoldStauts, boolean goDownBall,
                                          boolean goRightBall, boolean colideToPaddle, boolean colideToPaddleAndMoveToRight,
                                          boolean colideToRightWall, boolean colideToLeftWall, boolean colideToRightBlock,
                                          boolean colideToBottomBlock, boolean colideToLeftBlock, boolean colideToTopBlock) {
        // Set all properties here
        GameModel.level = level;
        GameModel.score = score;
        GameModel.heart = heart;
        this.destroyedBlockCount = destroyedBlockCount;

        this.xBall = xBall;
        this.yBall = yBall;
        this.xPaddle = xPaddle;
        this.yPaddle = yPaddle;
        this.centerPaddleX = centerPaddleX;
        this.time = time;
        this.goldTime = goldTime;
        this.vX = vX;

        this.isExistHeartBlock = isExistHeartBlock;
        this.isGoldStatus = isGoldStauts;
        this.goDownBall = goDownBall;
        this.goRightBall = goRightBall;
        this.collideToPaddle = colideToPaddle;
        this.collideToPaddleAndMoveToRight = colideToPaddleAndMoveToRight;
        this.collideToRightWall = colideToRightWall;
        this.collideToLeftWall = colideToLeftWall;
        this.collideToRightBlock = colideToRightBlock;
        this.collideToBottomBlock = colideToBottomBlock;
        this.collideToLeftBlock = colideToLeftBlock;
        this.collideToTopBlock = colideToTopBlock;
    }

}

