package brickGame;

import java.util.Random;

import javafx.application.Platform;
import javafx.scene.paint.Color;

/**
 * Controls the initialization process of the brick game.
 * This includes setting up the game model, view, engine, and bonus controller.
 *
 *  @see <a href="https://github.com/Lzyl02/COMP2042_CW_hcyzl4/blob/main/src/main/java/brickGame/GameInitializationController.java">GameInitializationController.java</a>
 */
public class GameInitializationController {
    private GameModel model;
    private GameView view;
    private GameEngine engine;
    private GameBonusController gameBonusController;



    /**
     * Constructs a new GameInitializationController.
     *
     * @param model The game model.
     * @param view The game view.
     * @param engine The game engine.
     * @param gameBonusController The game bonus controller.
     */
    public GameInitializationController(GameModel model, GameView view, GameEngine engine, GameBonusController gameBonusController) {
        this.model = model;
        this.view = view;
        this.engine = engine;
        this.gameBonusController = gameBonusController;

    }

    /**
     * Initializes the blocks for a new level.
     *
     * @param newLevel The level for which blocks are to be initialized.
     */
    public void initializeBlocks(int newLevel) {
        model.setLevel(newLevel);
        model.getBlocks().clear(); // 清除现有的方块

        Random random = new Random();
        int rows = model.getLevel(); // 行数基于级别
        int columns = 4; // 固定为 4 列

        // 在每个级别中创建相应数量的 Daemon block
        for (int i = 0; i < model.getLevel(); i++) {
            int daemonRow = random.nextInt(rows);
            int daemonColumn = random.nextInt(columns);
            Block daemonBlock = new Block(daemonRow, daemonColumn, Color.RED, Block.BLOCK_DAEMON);
            model.getBlocks().add(daemonBlock);
        }

        // 创建其他类型的方块
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                final int currentRow = i;
                final int currentColumn = j;

                // 检查是否已经在这个位置创建了 Daemon block
                boolean isDaemonBlockPresent = model.getBlocks().stream()
                        .anyMatch(b -> b.getRow() == currentRow && b.getColumn() == currentColumn && b.getType() == Block.BLOCK_DAEMON);

                if (!isDaemonBlockPresent) {
                    int r = random.nextInt(model.getColors().length);
                    int type = determineBlockType(r, random);
                    Block block = new Block(currentRow, currentColumn, model.getColors()[r], type);
                    model.getBlocks().add(block);
                    System.out.println("Block created: Row " + currentRow + ", Column " + currentColumn +
                            ", Color: " + model.getColors()[r] + ", Type: " + type);
                }
            }
        }

        System.out.println("Total blocks created for level " + model.getLevel() + ": " + model.getBlocks().size());
    }

    /**
     * Determines the type of a block based on a random number.
     *
     * @param randomNumber The random number used to determine the block type.
     * @param random The Random instance used for generating random numbers.
     * @return The type of the block.
     */
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

    /**
     * Determines the type of a special block.
     *
     * @param random The Random instance used for generating random numbers.
     * @return The type of the special block.
     */
    private int determineSpecialBlockType(Random random) {
        int specialType = random.nextInt(3); // Assuming 3 types of special blocks
        switch (specialType) {
            case 0:
                return Block.BLOCK_CHOCO;
            case 1:
                return Block.BLOCK_HEART;
            case 2:
                return Block.BLOCK_STAR;
            default:
                return Block.BLOCK_NORMAL; // Fallback to a normal block
        }
    }

    /**
     * Initializes the game by setting up blocks, paddle, and ball positions.
     */
    public void initializeGame() {
        initializeBlocks(model.getLevel()); // 初始化所有类型的方块，包括 Daemon block

        // 初始化球和挡板的位置
        model.setxPaddle((double) model.getSceneWidth() / 2); // 挡板在屏幕中央
        model.setyPaddle(model.getSceneHeight() - 50); // 挡板位置在屏幕底部
        model.setxBall(model.getxPaddle() + (double) model.getPaddleWidth() / 2); // 球开始在挡板上
        model.setyBall(model.getyPaddle() - model.getBallRadius() - 1); // 球的位置在挡板之上
    }

    /**
     * Starts the game. This includes initializing the game state, views, and starting the game engine.
     */
    public void startGame() {
        try {
            System.out.println("Starting game initialization...");
            System.out.println("Current Level: " + model.getLevel());
            view.hideGameControlButtons();

            // Initialize game state in the model
            initializeGame();

            // 初始化球和挡板视图基于模型状态

            // 显示所有方块（包括 Daemon block）
            System.out.println("Blocks to display: " + model.getBlocks().size());
            view.displayBlocks(); // 这将显示所有类型的方块，包括 Daemon block

            // 初始化并启动更新器
            gameBonusController.initializeBonusUpdater();
            gameBonusController.initializeBombUpdater();


            // 确保在启动游戏引擎之前处理所有UI更新
            Platform.runLater(() -> {
                System.out.println("Game initialization completed.");
                System.out.println("Starting game engine...");
                this.engine.setOnAction(model.getController());

                // 启动游戏引擎
                engine.setFps(120);
                engine.start();
            });

        } catch (Exception e) {
            System.err.println("Error during game initialization: " + e.getMessage());
            // 相应地处理错误
        }
    }

}
