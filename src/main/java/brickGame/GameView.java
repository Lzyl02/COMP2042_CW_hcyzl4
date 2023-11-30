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


public class GameView {
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


    public void updatePaddleSize(double xPaddle, int newWidth) {
        Platform.runLater(() -> {
            if (rect != null) {
                rect.setWidth(newWidth);
                // 更新 paddle 位置以保持其居中
                rect.setX(xPaddle - newWidth / 2.0);
            }
        });
    }


    public GameView(Pane root) {
        this.root = root;
        initUIComponents();
    }
    public void displayBlocks() {
        Platform.runLater(() -> {
            List<Block> blocks = GameModel.getBlocks(); // 获取所有方块，包括 Daemon block

            System.out.println("Displaying blocks: Total count = " + blocks.size());
            System.out.println("Initial root children count: " + root.getChildren().size());

            // 移除与 Block 相关的 Rectangle
            root.getChildren().removeIf(node -> node instanceof Rectangle && blocks.contains(((Rectangle) node).getUserData()));

            // 添加所有类型的方块
            for (Block block : blocks) {
                Rectangle blockRect = block.getRect();
                // 特别处理 Daemon block，如果需要的话
                if (block.getType() == Block.BLOCK_DAEMON) {
                    // 比如设置不同的样式或行为
                    // blockRect.setStyle(...); // 例如设置不同的样式
                }
                blockRect.setUserData(block); // 关联 Block 与 Rectangle
                root.getChildren().add(blockRect);
            }

            System.out.println("All blocks added to root.");
            System.out.println("Final root children count: " + root.getChildren().size());
        });
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

    public void initBallView(double xBall, double yBall, int ballRadius) {
        System.out.println("Initializing ball view at x: " + xBall + ", y: " + yBall + ", radius: " + ballRadius);

        Random random = new Random();

        ball = new Circle();
        ball.setRadius(ballRadius);
        ball.setFill(new ImagePattern(new Image("ball.png")));
        ball.setCenterX(xBall);
        ball.setCenterY(yBall);
        root.getChildren().add(ball);
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


    public void initBreakView(double xBreak, double yBreak, int breakWidth, int breakHeight) {
        rect = new Rectangle();
        rect.setWidth(breakWidth);
        rect.setHeight(breakHeight);
        rect.setX(xBreak);
        rect.setY(yBreak);
        rect.setFill(new ImagePattern(new Image("block.jpg")));
        root.getChildren().add(rect);
    }


    public void updateLevelLabel(int level) {
        Platform.runLater(() -> {
            levelLabel.setText("Level: " + level);
        });
    }



    public void showScoreLabel(double x, double y, int score) {
        String sign = score >= 0 ? "+" : "";
        Label label = new Label(sign + score);
        label.setTranslateX(x);
        label.setTranslateY(y);
        Platform.runLater(() -> {
            animateLabel(label);
            root.getChildren().add(label);
        });
    }

    public void showMessage(String message, double x, double y) {
        Platform.runLater(() -> {
            Label label = new Label(message);
            label.setTranslateX(x);
            label.setTranslateY(y);
            root.getChildren().add(label);

            PauseTransition delay = new PauseTransition(Duration.seconds(1)); // 3 seconds
            delay.setOnFinished(event -> Platform.runLater(() -> root.getChildren().remove(label)));
            delay.play();
        });
    }

    public void clearGameElements() {
        Platform.runLater(() -> {
            // 移除所有的 Circle 和 Rectangle 实例（包括球和挡板）
            root.getChildren().removeIf(node -> node instanceof Circle || node instanceof Rectangle);

            // 移除游戏结束标签和重启按钮
            root.getChildren().removeIf(node -> node instanceof Label || node instanceof Button);

            // 重新添加基础 UI 组件
            initUIComponents();
        });
    }

    public void showGameOver() {
        System.out.println("Showing game over screen...");

        Label gameOverLabel = new Label("Game Over :(");
        gameOverLabel.setTranslateX(200);
        gameOverLabel.setTranslateY(250);
        gameOverLabel.setScaleX(2);
        gameOverLabel.setScaleY(2);

        Button restartButton = new Button("Restart");
        restartButton.setTranslateX(220);
        restartButton.setTranslateY(300);
        restartButton.setOnAction(event -> {
            System.out.println("Restart button clicked");

//            clearGameElements(); // 清除旧游戏元素
            controller.restartGame(); // 重启游戏
            hideGameControlButtons();
            System.out.println("Game controls hidden, restarting game");

        });

        Platform.runLater(() -> {
            root.getChildren().addAll(gameOverLabel, restartButton);
        });
        System.out.println("Game over screen displayed.");

    }



    // 设置 controller 的方法
    public void setController(GameController controller) {
        newGameButton.setOnAction(event -> controller.startGame());
        loadGameButton.setOnAction(event -> controller.loadFromSave());
        this.controller = controller;
    }

    public void showWin() {
        Label winLabel = new Label("You Win :)");
        winLabel.setTranslateX(200);
        winLabel.setTranslateY(250);
        winLabel.setScaleX(2);
        winLabel.setScaleY(2);
        // 设置 winLabel 的位置和大小
        root.getChildren().add(winLabel);
    }

    private void animateLabel(Label label) {
        // 创建一个新线程来处理标签的动画
        new Thread(new Runnable() {
            @Override
            public void run() {
                // 设置动画的持续时间和步骤
                int duration = 315; // 总持续时间（假设）
                int steps = 21; // 总步数

                for (int i = 0; i < steps; i++) {
                    final int finalI = i;
                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            // 根据当前步数调整标签的缩放和透明度
                            double scale = 1.0 + finalI * 0.05; // 标签的缩放比例
                            double opacity = 1.0 - (finalI / (double) steps); // 标签的透明度
                            label.setScaleX(scale);
                            label.setScaleY(scale);
                            label.setOpacity(opacity);
                        }
                    });

                    try {
                        // 暂停一小段时间后再进行下一步
                        Thread.sleep(duration / steps);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

                // 动画完成后从界面中移除标签
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        root.getChildren().remove(label);
                    }
                });
            }
        }).start();
    }

    public void changeBallAppearance(String imageFileName) {
        Platform.runLater(() -> ball.setFill(new ImagePattern(new Image("goldball.png"))));
    }


    public void updateScoreAndHeart(int score, int heart) {
        Platform.runLater(() -> {
            scoreLabel.setText("Score: " + score);
            heartLabel.setText("Heart : " + heart);
        });
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


    public void hideGameControlButtons() {
        Platform.runLater(() -> {

            newGameButton.setVisible(false);
        loadGameButton.setVisible(false);
        });

    }


    public void setSceneStyle() {
        // Apply the stylesheet to the root pane
        root.getStylesheets().add("style.css"); // Ensure the path is correct

        // Apply a default style class to the root, if needed
        root.getStyleClass().add("defaultStyle"); // This should match a class in your CSS
    }

    public void changeSceneStyleClass(String newStyleClass) {
        Platform.runLater(() -> {
            // Remove existing style classes
            root.getStyleClass().clear();

            // Add the new style class
            if (newStyleClass != null && !newStyleClass.isEmpty()) {
                root.getStyleClass().add(newStyleClass);
            }
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


    public void addBonus(Rectangle bonus) {
        Platform.runLater(() -> {
            root.getChildren().add(bonus);
            System.out.println("Chocolate bonus added to UI.");
        });
    }


    public void updateBonusPosition(Bonus bonus) {
        Rectangle chocoRectangle = bonus.getRectangle();
    }

    public void removeBonus(Bonus bonus) {
        Rectangle rect = bonus.getRectangle();
        Platform.runLater(() -> root.getChildren().remove(rect));
    }

    public void addBomb(Bombs bomb) {
        Platform.runLater(() -> {
            Rectangle bombRect = bomb.getRectangle(); // 获取炸弹的图形
            root.getChildren().add(bombRect); // 将炸弹添加到游戏界面
            System.out.println("Bomb added to UI.");
        });
    }

    public void updateBombPosition(Bombs bomb) {
        Platform.runLater(() -> {
            Rectangle bombRect = bomb.getRectangle();
            if (bombRect != null) {
                bombRect.setX(bomb.getX()); // 使用 getX() 方法获取炸弹的 x 坐标
                bombRect.setY(bomb.getY()); // 使用 getY() 方法获取炸弹的 y 坐标
            }
        });
    }
    public void removeBomb(Bombs bomb) {
        Rectangle bombRect = bomb.getRectangle();
        Platform.runLater(() -> {
            root.getChildren().remove(bombRect); // 从游戏界面移除炸弹
        });
    }

}



