package brickGame;

import javafx.animation.PauseTransition;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.control.Button;

import javafx.util.Duration;

import java.util.List;
import java.util.Random;

/**
 * GameViewUpdate is responsible for updating the graphical components of the game view.
 */
public class GameViewUpdate {
    private Pane root;
    private Circle ball;
    private Rectangle rect;
    private Label scoreLabel;
    private Label heartLabel;


    private Label levelLabel;
    /**
     * Constructs a GameViewUpdate with the specified root pane, ball, paddle, and labels.
     *
     * @param root       The root pane of the application.
     * @param ball       The ball in the game.
     * @param rect       The paddle (rectangle) in the game.
     * @param scoreLabel The label displaying the score.
     * @param heartLabel The label displaying the heart count.
     * @param levelLabel The label displaying the level.
     */
    public GameViewUpdate(Pane root, Circle ball, Rectangle rect, Label scoreLabel, Label heartLabel, Label levelLabel) {
        this.root = root;
        this.ball = ball;
        this.rect = rect;
        this.scoreLabel = scoreLabel;
        this.heartLabel = heartLabel;
        this.levelLabel = levelLabel;
    }

    /**
     * Updates the position of the ball.
     *
     * @param x The new X coordinate of the ball.
     * @param y The new Y coordinate of the ball.
     */
    public void updateBallPosition(double x, double y) {
        Platform.runLater(() -> {
            if (ball != null) {
                ball.setCenterX(x);
                ball.setCenterY(y);
            }
        });
    }

    /**
     * Updates the position of the paddle.
     *
     * @param x The new X coordinate of the paddle.
     * @param y The new Y coordinate of the paddle.
     */
    public void updatePaddlePosition(double x, double y) {
        Platform.runLater(() -> {
            if (rect != null) {
                rect.setX(x);
                rect.setY(y);
            }
        });
    }

    /**
     * Updates the score and heart labels.
     *
     * @param score The new score to display.
     * @param heart The new heart count to display.
     */
    public void updateScoreAndHeart(int score, int heart) {
        Platform.runLater(() -> {
            scoreLabel.setText("Score: " + score);
            heartLabel.setText("Heart: " + heart);
            System.out.println("Updated Score: " + score + ", Updated Heart: " + heart); // Debugging log
        });
    }

    /**
     * Updates the size of the paddle.
     *
     * @param xPaddle   The new X position of the paddle.
     * @param newWidth  The new width of the paddle.
     */
    public void updatePaddleSize(double xPaddle, int newWidth) {
        Platform.runLater(() -> {
            if (rect != null) {
                rect.setWidth(newWidth);
                rect.setX(xPaddle - newWidth / 2.0);
            }
        });
    }

    /**
     * Updates the level label.
     *
     * @param level The new level to display.
     */
    public void updateLevelLabel(int level) {
        Platform.runLater(() -> {
            levelLabel.setText("Level: " + level);
        });
    }

    /**
     * Updates the visibility of a block in the game.
     *
     * @param block    The block to update.
     * @param isVisible True if the block should be visible, false otherwise.
     */
    public void updateBlockVisibility(Block block, boolean isVisible) {
        Platform.runLater(() -> {
            Rectangle blockRect = block.getRect();
            if (blockRect != null) {
                blockRect.setVisible(isVisible);
            }
        });
    }
    /**
     * Updates the position of a bonus item.
     *
     * @param bonus The bonus to update.
     */
    public void updateBonusPosition(Bonus bonus) {
        Rectangle bonusRect = bonus.getRectangle();
        Platform.runLater(() -> {
            if (bonusRect != null) {
                bonusRect.setX(bonus.getX());
                bonusRect.setY(bonus.getY());
            }
        });
    }
    /**
     * Updates the position of a bomb in the game.
     *
     * @param bomb The bomb to update.
     */
    public void updateBombPosition(Bombs bomb) {
        Platform.runLater(() -> {
            Circle bombCircle = bomb.getCircle();
            if (bombCircle != null) {
                bombCircle.setCenterX(bomb.getX());
                bombCircle.setCenterY(bomb.getY());
            }
        });
    }
    /**
     * Changes the appearance of the ball based on the specified image file name.
     *
     * @param imageFileName The file name of the image to apply to the ball.
     */
    public void changeBallAppearance(String imageFileName) {
        Platform.runLater(() -> {
            if (ball != null) {
                ball.setFill(new ImagePattern(new Image(imageFileName)));
            }
        });
    }
    /**
     * Updates the appearance of the ball based on its gold status.
     *
     * @param isGoldStatus If true, the ball appearance is changed to gold; otherwise, it's set to its default appearance.
     */
    public void updateBallAppearance(boolean isGoldStatus) {
        Platform.runLater(() -> {
            if (ball != null) {
                if (isGoldStatus) {
                    ball.setFill(new ImagePattern(new Image("goldball.png")));
                    root.getStyleClass().remove("defaultStyle");
                    root.getStyleClass().add("goldRoot");
                } else {
                    ball.setFill(new ImagePattern(new Image("ball.png")));
                    root.getStyleClass().remove("goldRoot");
                    root.getStyleClass().add("defaultStyle");
                }
            }
        });
    }
}
