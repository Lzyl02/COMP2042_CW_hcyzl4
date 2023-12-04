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


public class GameViewUpdate {

    private GameController controller;
    private Pane root;
    private Label scoreLabel;
    private Label heartLabel;
    private Label levelLabel;
    private Circle ball;
    private Rectangle rect;
    private int sceneWidth = 500;
    private Button loadGameButton;
    private Button newGameButton;
    private Rectangle paddle;

    public GameViewUpdate(Pane root) {
        this.root = root;
    }

    public void updateBallPosition(double x, double y) {
        Platform.runLater(() -> {
            if (ball != null) {
                ball.setCenterX(x);
                ball.setCenterY(y);
            } else {
                System.out.println("Ball not initialized when attempting to update position");
            }
        });
    }

    public void updatePaddlePosition(double x, double y) {
        Platform.runLater(() -> {
            rect.setX(x);
            rect.setY(y);
        });
    }

    public void updateScoreAndHeart(int score, int heart) {
        Platform.runLater(() -> {
            scoreLabel.setText("Score: " + score);
            heartLabel.setText("Heart : " + heart);
        });
    }


    public void updatePaddleSize(double xPaddle, int newWidth) {
        Platform.runLater(() -> {
            if (rect != null) {
                rect.setWidth(newWidth);
                // 更新 paddle 位置以保持其居中
                rect.setX(xPaddle - newWidth / 2.0);
            }
        });
    }
    public void updateLevelLabel(int level) {
        Platform.runLater(() -> {
            levelLabel.setText("Level: " + level);
        });
    }
    public void updateBlockVisibility(Block block, boolean isVisible) {
        Platform.runLater(() -> {
            Rectangle rect = block.getRect();
            if (rect != null) {
                rect.setVisible(isVisible);
            }
        });
    }
    public void updateBonusPosition(Bonus bonus) {
        Rectangle chocoRectangle = bonus.getRectangle();
    }

    public void updateBombPosition(Bombs bomb) {
        Platform.runLater(() -> {
            Circle bombCircle = bomb.getCircle();
            if (bombCircle != null) {
                bombCircle.setCenterX(bomb.getX()); // Use getX() method to get the bomb's x coordinate
                bombCircle.setCenterY(bomb.getY()); // Use getY() method to get the bomb's y coordinate
            }
        });
    }
    public void changeBallAppearance(String imageFileName) {
        Platform.runLater(() -> ball.setFill(new ImagePattern(new Image("goldball.png"))));
    }


    public void updateBallAppearance(boolean isGoldStatus) {
        Platform.runLater(() -> {
            if (isGoldStatus) {
                ball.setFill(new ImagePattern(new Image("goldball.png")));
                root.getStyleClass().remove("defaultStyle");
                root.getStyleClass().add("goldRoot");
            } else {
                ball.setFill(new ImagePattern(new Image("ball.png")));
                root.getStyleClass().remove("goldRoot");
                root.getStyleClass().add("defaultStyle");
            }
        });
    }

}
