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


public class GameViewInit {

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

    public GameViewInit(Pane root) {
        this.root = root;
    }

    public void initUIComponents() {
        // 初始化UI组件
        scoreLabel = new Label("Score: "+ GameModel.score);
        heartLabel = new Label("Heart : "+ GameModel.heart);
        levelLabel = new Label("Level: " + GameModel.level );

        // 设置标签位置等
        heartLabel.setTranslateX(sceneWidth - 70);
        levelLabel.setTranslateY(20);
        setSceneStyle();
        root.getChildren().addAll(scoreLabel, heartLabel, levelLabel);
        loadGameButton = new Button("Load Game");
        newGameButton = new Button("Start New Game");

        // Set positions and other properties
        loadGameButton.setTranslateX(220);
        loadGameButton.setTranslateY(300);
        newGameButton.setTranslateX(220);
        newGameButton.setTranslateY(340);

        root.getChildren().addAll(loadGameButton, newGameButton);
    }

    public void setSceneStyle() {
        // Apply the stylesheet to the root pane
        root.getStylesheets().add("style.css"); // Ensure the path is correct

        // Apply a default style class to the root, if needed
        root.getStyleClass().add("defaultStyle"); // This should match a class in your CSS
    }

    public void initBallView(double xBall, double yBall, int ballRadius) {
        System.out.println("Initializing ball view at x: " + xBall + ", y: " + yBall + ", radius: " + ballRadius);

        Random random = new Random();

        ball = new Circle();
        ball.setRadius(12);
        ball.setFill(new ImagePattern(new Image("ball.png")));
        ball.setCenterX(xBall);
        ball.setCenterY(yBall);
        root.getChildren().add(ball);
    }
    public void initPaddle(double xPaddle, double yPaddle, int paddleWidth, int paddleHeight) {
        paddle = new Rectangle();
        // 设置挡板的属性，例如位置、大小、颜色等
        paddle.setWidth(100); // 示例宽度
        paddle.setHeight(20); // 示例高度
        paddle.setX(root.getWidth() / 2 - paddle.getWidth() / 2); // 挡板初始化位置 (水平居中)
        paddle.setY(root.getHeight() - 30); // 挡板初始化位置 (底部)
        paddle.setFill(javafx.scene.paint.Color.BLUE); // 挡板颜色

        root.getChildren().add(paddle); // 将挡板添加到游戏界面
    }

    public void initBreakView(double xBreak, double yBreak, int breakWidth, int breakHeight) {
        rect = new Rectangle();
        rect.setWidth(breakWidth);
        rect.setHeight(breakHeight);
        rect.setX(xBreak);
        rect.setY(yBreak);
        rect.setFill(new ImagePattern(new Image("block.jpg")));
        root.getChildren().add(rect);
    }





}