package brickGame;

import javafx.application.Platform;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;

import java.util.List;
/**
 * The GameViewInit class is responsible for initializing and managing the UI components of the game.
 */
public class GameViewInit {
    private Pane root;
    private GameModel model;
    private Button loadGameButton;
    private Button newGameButton;


    private Label scoreLabel;
    private Label heartLabel;
    private Label levelLabel;
    private Circle ball;
    private Rectangle rect;

    /**
     * Constructs a new GameViewInit with the specified root pane and game model.
     *
     * @param root  The root pane of the application.
     * @param model The game model to be used for initializing UI components.
     */
    public GameViewInit(Pane root, GameModel model) {
        this.root = root;
        this.model = model;
    }
    /**
     * Initializes the UI components of the game.
     * This includes labels, buttons, and styles.
     *
     * @param isRestart Indicates if the game is being restarted.
     */
    public void initUIComponents(boolean isRestart) {
        // Initialize labels
        // Initialize labels dynamically
        scoreLabel = new Label();
        heartLabel = new Label();
        levelLabel = new Label();
        updateLabels(); // Update labels with current model values

        // Set label positions and other properties
        heartLabel.setTranslateX(model.getSceneWidth() - 100); // Assuming root has a predefined width
        levelLabel.setTranslateY(20);
        setSceneStyle();
        // Initialize buttons
        loadGameButton = new Button("Load Game");
        newGameButton = new Button("Start New Game");

        // Set button positions and other properties
        loadGameButton.setTranslateX(220);
        loadGameButton.setTranslateY(300);
        newGameButton.setTranslateX(220);
        newGameButton.setTranslateY(340);

        // Add components to the root
        // Add components to the root
        root.getChildren().addAll(scoreLabel, heartLabel, levelLabel);

        // Add buttons only if it's not a restart
        if (!isRestart) {
            root.getChildren().addAll(loadGameButton, newGameButton);
        }

    }

    // Getters for UI components to allow GameView to set actions or update them
    public Button getLoadGameButton() {
        return loadGameButton;
    }

    public Button getNewGameButton() {
        return newGameButton;
    }

    public Label getScoreLabel() {
        return scoreLabel;
    }

    public Label getHeartLabel() {
        return heartLabel;
    }

    public Label getLevelLabel() {
        return levelLabel;
    }

    /**
     * Applies a stylesheet and a default style class to the root pane.
     */
    public void setSceneStyle() {
        // Apply the stylesheet to the root pane
        root.getStylesheets().add("style.css"); // Ensure the path is correct

        // Apply a default style class to the root, if needed
        root.getStyleClass().add("defaultStyle"); // This should match a class in your CSS
    }

    /**
     * Initializes the view for the ball.
     *
     * @param xBall      The initial X coordinate of the ball.
     * @param yBall      The initial Y coordinate of the ball.
     * @param ballRadius The radius of the ball.
     */
    public void initBallView(double xBall, double yBall, int ballRadius) {
        System.out.println("Initializing ball view at x: " + xBall + ", y: " + yBall + ", radius: " + ballRadius);

        ball = new Circle();
        ball.setRadius(ballRadius);
        ball.setFill(new ImagePattern(new Image("ball.png")));
        ball.setCenterX(xBall);
        ball.setCenterY(yBall);

        ball.setId("ballId");  // Set an ID for the ball
        root.getChildren().add(ball);
    }

    /**
     * Initializes the view for the paddle (break).
     *
     * @param xBreak      The initial X coordinate of the paddle.
     * @param yBreak      The initial Y coordinate of the paddle.
     * @param breakWidth  The width of the paddle.
     * @param breakHeight The height of the paddle.
     */
    public void initBreakView(double xBreak, double yBreak, int breakWidth, int breakHeight) {
        rect = new Rectangle();
        rect.setWidth(breakWidth);
        rect.setHeight(breakHeight);
        rect.setX(xBreak);
        rect.setY(yBreak);
        rect.setFill(new ImagePattern(new Image("block.jpg")));

        rect.setId("paddleId");  // Set an ID for the paddle
        root.getChildren().add(rect);
    }


    // Getters for the ball and rectangle
    public Circle getBall() {
        return ball;
    }

    public Rectangle getRect() {
        return rect;
    }

    /**
     * Displays all blocks on the game screen.
     */
    public void displayBlocks() {
        Platform.runLater(() -> {
            List<Block> blocks = model.getBlocks(); // 获取所有方块，包括 Daemon block

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

    /**
     * Updates the text and positioning of the score, heart, and level labels.
     */
    private void updateLabels() {
        scoreLabel.setText("Score: " + model.getScore());
        heartLabel.setText("Heart: " + model.getHeart());
        levelLabel.setText("Level: " + model.getLevel());
        // Set label positions and other properties
        // ...
    }


}
