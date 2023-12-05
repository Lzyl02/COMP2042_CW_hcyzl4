package brickGame;


/**
 * Handles collision interactions in a brick-style game.
 * Manages collision detection between the ball and blocks, updating the game state and view accordingly.
 */
public class GameCollisionController {

    private GameModel model; // Assuming a GameModel class managing game state
    private GameView gameView;

    /**
     * Constructor to initialize the controller with game model and view.
     *
     * @param model The game model managing game state.
     * @param view The game view for the UI.
     */
    public GameCollisionController(GameModel model,GameView view ) {
        this.model = model;
        this.gameView = view;
    }

    /**
     * Handles collisions between the ball and blocks.
     * Determines if the ball has hit any blocks and processes the hit accordingly.
     */
    public void handleBlockCollision() {
        if (model.getyBall() >= Block.getPaddingTop() && model.getyBall() <= (Block.getHeight() * (model.getLevel() + 1)) + Block.getPaddingTop()) {
            for (Block block : model.getBlocks()) {
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

    /**
     * Processes a block hit, updating the score and handling block destruction.
     *
     * @param block The block that was hit.
     * @param hitCode The code representing the side of the block that was hit.
     */
    private void processBlockHit(Block block, int hitCode) {
        System.out.println("Block hit detected at: x = " + block.getX() + ", y = " + block.getY());
        model.setScore(model.getScore()+1);
        block.setDestroyed(true);
        model.setDestroyedBlockCount(model.getDestroyedBlockCount()+1);

        if (this != null) {
            updateScoreView(block.getX(), block.getY(), 1);
            handleBlockHit(block);
        }

        // Handle Daemon block generating bomb logic
        if (block.getType() == Block.BLOCK_DAEMON && !block.hasGeneratedBomb()) {
            createAndDropBomb(block);
            block.setGeneratedBomb(true);
        }
    }

    /**
     * Adjusts the direction of the ball after hitting a block.
     *
     * @param hitCode The code representing the side of the block that was hit.
     */
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

    /**
     * Handles special blocks with unique effects when hit.
     *
     * @param block The special block that was hit.
     */
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
                model.setHeart(model.getHeart()+1);
                break;
            case Block.BLOCK_DAEMON:
                break;
        }
    }


    /**
     * Updates the score view on the screen.
     *
     * @param x The X-coordinate for the score label.
     * @param y The Y-coordinate for the score label.
     * @param score The score to display.
     */
    public void updateScoreView(double x, double y, int score) {
        gameView.showScoreLabel(x, y, score);
    }

    /**
     * Updates the view to reflect the status of a hit block.
     *
     * @param block The block that was hit.
     */
    public void handleBlockHit(Block block) {
        if (block.isDestroyed) {
            gameView.updateBlockVisibility(block, false);
        }
    }

    /**
     * Creates and drops a bomb from a specific block.
     *
     * @param block The block from which the bomb is created and dropped.
     */
    public void createAndDropBomb(Block block) {
        Bombs bomb = new Bombs(block.getRow(), block.getColumn());
        model.getBombs().add(bomb); // 将炸弹添加到模型中
        addBombToView(bomb); // 将炸弹添加到视图中
    }

    /**
     * Handles the behavior when a chocolate block is hit by the ball.
     * This method is responsible for creating a chocolate bonus and updating the game model and view.
     *
     * @param block The chocolate block that was hit.
     */
    private void handleChocoBlock(Block block) {
        System.out.println("Handling chocolate block at row: " + block.row + ", column: " + block.column);
        final Bonus choco = new Bonus(block.row, block.column);
        choco.timeCreated = model.getTime();

        if(this!=null) {
            addChocoToView(choco);
            System.out.println("Chocolate bonus added to UI.");
        }

        model.getChocos().add(choco);
        System.out.println("Chocolate bonus added to list.");
    }

    void changeBallAppearance(String appearance) {
        // Change the appearance of the ball
        gameView.changeBallAppearance(appearance);
    }

    public void addBombToView(Bombs bomb) {
        gameView.addBomb(bomb); // 直接传递 Bombs 对象给视图
    }
    public void addChocoToView(Bonus choco) {
        gameView.addBonus(choco.getRectangle()); // Add the Rectangle to the view
    }

    void changeSceneStyleClass(String style) {
        gameView.changeSceneStyleClass(style);
    }

}
