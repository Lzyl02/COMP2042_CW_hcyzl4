package brickGame;

import javafx.application.Platform;

import static brickGame.GameModel.baseHorizontalSpeed;
import static brickGame.GameModel.heart;

public class GameUpdateController {
    private GameModel model;
    private GameView view;

    public GameUpdateController(GameModel model, GameView view) {
        this.model = model;
        this.view = view;
    }

    public void moveBall() {
// Horizontal movement
        model.setxBall(model.getxBall() + (model.isGoRightBall() ? model.getvX() : -model.getvX()));

// Vertical movement
        model.setyBall(model.getyBall() + (model.isGoDownBall() ? model.getvY() : -model.getvY()));

    }


    public void handleWallCollision() {
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
                heart--;
                if (model.getHeart() == 0) {
                    model.setGameOver(true); // 设置游戏结束标志
                    if (this != null) {
                        Platform.runLater(() -> showGameOver()); // 确保在 JavaFX 线程中执行
                    }

                    model.getEngine().stop();// 停止游戏逻辑

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

    public void showGameOver() {
        if (view != null) {
            Platform.runLater(() -> view.showGameOver());
        }
    }
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


    public void resetColideFlags() {

        model.setCollideToPaddle(false);
        model.setCollideToPaddleAndMoveToRight(false);
        model.setCollideToRightWall(false);
        model.setCollideToLeftWall(false);

        model.setCollideToRightBlock(false);
        model.setCollideToBottomBlock(false);
        model.setCollideToLeftBlock(false);
        model.setCollideToTopBlock(false);
    }



   public void checkDestroyedCount() {
        if (model.getDestroyedBlockCount() == GameModel.getBlocks().size()) {
            //TODO win level todo...
            //System.out.println("You Win");
            if (this.model.getController() != null)
                model.getController().nextLevel();
        }
    }

    // 可以添加更多与游戏状态更新相关的方法
}
