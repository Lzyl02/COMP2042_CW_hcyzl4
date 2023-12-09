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
 * GameView is responsible for managing and updating the graphical components of the brick game.
 * It interacts with GameModel for game state and GameController for event handling.
 *@see <a href="https://github.com/Lzyl02/COMP2042_CW_hcyzl4/blob/main/src/main/java/brickGame/GameView.java">GameView.java</a>
 */
public class GameView {
    private GameController controller;
    private Pane root;
    private Label scoreLabel;
    private Label heartLabel;
    private Label levelLabel;

    public Circle getBall() {
        return ball;
    }

    private Circle ball;
    private Rectangle rect;
    private int sceneWidth = 500;
    private Button loadGameButton;
    private Button newGameButton;
    private GameModel model;
    private GameViewInit gameViewInit;
    private GameViewUpdate gameViewUpdate;
    private GameViewDisplay gameViewDisplay;

    private GameViewAnimation gameViewAnimation;
    public void updatePaddleSize(double xPaddle, int newWidth) {
        gameViewUpdate.updatePaddleSize(xPaddle,newWidth);
    }
    /**
     * Constructs a new GameView with the specified root pane and game model.
     *
     * @param root  The root pane of the application.
     * @param model The game model that holds the game's state.
     */

    public GameView(Pane root, GameModel model) {
        this.root = root;
        this.model = model;
        this.gameViewInit = new GameViewInit(root, model);

        // Initialize UI components
        gameViewInit.initUIComponents(false);

        // Retrieve UI components after they have been initialized
        loadGameButton = gameViewInit.getLoadGameButton();
        newGameButton = gameViewInit.getNewGameButton();
        scoreLabel = gameViewInit.getScoreLabel();
        heartLabel = gameViewInit.getHeartLabel();
        levelLabel = gameViewInit.getLevelLabel();

        // Initialize ball and break views
        gameViewInit.initBallView(model.getxBall(), model.getyBall(), model.getBallRadius());
        gameViewInit.initBreakView(model.getxPaddle(), model.getyPaddle(), model.getPaddleWidth(), model.getPaddleHeight());

        // Retrieve ball and rect
        this.ball = gameViewInit.getBall();
        this.rect = gameViewInit.getRect();


        this.gameViewUpdate = new GameViewUpdate(root, ball, rect, scoreLabel, heartLabel, levelLabel);

        // Create GameViewDisplay
        this.gameViewDisplay = new GameViewDisplay(root);
        this.gameViewAnimation = new GameViewAnimation(root);


    }

    /**
     * Displays the game blocks on the screen.
     */
    public void displayBlocks() {
        gameViewInit.displayBlocks();
    }


    /**
     * Updates the position of the ball on the game screen.
     *
     * @param x The new X coordinate of the ball.
     * @param y The new Y coordinate of the ball.
     */
    public void updateBallPosition(double x, double y) {
        gameViewUpdate.updateBallPosition(x, y);
    }

    /**
     * Updates the position of the paddle.
     *
     * @param x The new X coordinate of the paddle.
     * @param y The new Y coordinate of the paddle.
     */
    public void updatePaddlePosition(double x, double y) {
        gameViewUpdate.updatePaddlePosition(x,y);
    }



    /**
     * Updates the label displaying the current level.
     *
     * @param level The current level to display.
     */
    public void updateLevelLabel(int level) {
        gameViewUpdate.updateLevelLabel(level);
    }


    /**
     * Shows a temporary score label at a specified location.
     *
     * @param x     The X coordinate for the score label.
     * @param y     The Y coordinate for the score label.
     * @param score The score to display.
     */
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
        gameViewDisplay.showMessage(message,x,y);
    }

    public void clearGameElements() {
        Platform.runLater(() -> {

            root.getChildren().removeIf(node ->
                    (node instanceof Circle && !node.getId().equals("ballId")) ||
                            (node instanceof Rectangle && !node.getId().equals("paddleId")) ||
                            (node instanceof Label) ||
                            (node instanceof Button && (node.getId() == null || !node.getId().equals("restartGameButtonId"))));


            gameViewInit.initUIComponents(true);
        });
    }



    public void showGameOver() {
        System.out.println("Showing game over screen...");

        Label gameOverLabel = new Label("Game Over :(");
        gameOverLabel.setTranslateX(200);
        gameOverLabel.setTranslateY(250);
        gameOverLabel.setScaleX(2);
        gameOverLabel.setScaleY(2);

        Button restartButton = new Button("God Mode");
        restartButton.setTranslateX(220);
        restartButton.setTranslateY(300);
        restartButton.setOnAction(event -> {
            clearGameElements(); // 清除旧游戏元素
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
        gameViewDisplay.showWin();
    }

    private void animateLabel(Label label) {
        gameViewAnimation.animateLabel(label);
    }

    /**
     * Changes the appearance of the ball in the game.
     *
     * @param imageFileName The filename of the new image to be applied to the ball.
     */
    public void changeBallAppearance(String imageFileName) {
        gameViewUpdate.changeBallAppearance(imageFileName);
        }

    /**
     * Updates the score and heart labels.
     *
     * @param score The new score to display.
     * @param heart The new heart count to display.
     */
    public void updateScoreAndHeart(int score, int heart) {
        gameViewUpdate.updateScoreAndHeart(score,heart);
    }
    /**
     * Updates the appearance of the ball based on the given gold status.
     *
     * @param isGoldStatus If true, updates the ball to its gold appearance; otherwise, uses its standard appearance.
     */

    public void updateBallAppearance(boolean isGoldStatus) {
        gameViewUpdate.updateBallAppearance(isGoldStatus);
    }


    /**
     * Hides the game control buttons from the user interface.
     */
    public void hideGameControlButtons() {
        Platform.runLater(() -> {

            newGameButton.setVisible(false);
            loadGameButton.setVisible(false);
        });

    }

    /**
     * Changes the style class of the scene to the specified class.
     *
     * @param newStyleClass The new style class to apply to the scene. If null or empty, no new style is applied.
     */

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

    /**
     * Updates the visibility of a given block in the game.
     *
     * @param block    The block whose visibility is to be updated.
     * @param isVisible True to make the block visible; false to hide it.
     */
public void updateBlockVisibility(Block block, boolean isVisible) {
        gameViewUpdate.updateBlockVisibility(block,isVisible);
    }

    /**
     * Adds a bonus item to the game interface.
     *
     * @param bonus The graphical representation of the bonus item.
     */
    public void addBonus(Rectangle bonus) {
        Platform.runLater(() -> {
            root.getChildren().add(bonus);
            System.out.println("Chocolate bonus added to UI.");
        });
    }

    /**
     * Updates the position of a bonus item in the game.
     *
     * @param bonus The bonus item whose position is to be updated.
     */
    public void updateBonusPosition(Bonus bonus) {
        gameViewUpdate.updateBonusPosition(bonus);
        }
    /**
     * Removes a bonus item from the game interface.
     *
     * @param bonus The bonus item to be removed.
     */
    public void removeBonus(Bonus bonus) {
        Rectangle rect = bonus.getRectangle();
        Platform.runLater(() -> root.getChildren().remove(rect));
    }
    /**
     * Adds a bomb to the game interface.
     *
     * @param bomb The bomb to be added to the game.
     */
    public void addBomb(Bombs bomb) {
        Platform.runLater(() -> {
            Circle bombRect = bomb.getCircle(); // 获取炸弹的图形
            root.getChildren().add(bombRect); // 将炸弹添加到游戏界面
            System.out.println("Bomb added to UI.");
        });
    }
    /**
     * Updates the position of a bomb in the game.
     *
     * @param bomb The bomb whose position is to be updated.
     */
    public void updateBombPosition(Bombs bomb) {
        gameViewUpdate.updateBombPosition(bomb);
    }
    /**
     * Removes a bomb from the game interface.
     *
     * @param bomb The bomb to be removed.
     */
    public void removeBomb(Bombs bomb) {
        Circle bombRect = bomb.getCircle();
        Platform.runLater(() -> {
            root.getChildren().remove(bombRect); // 从游戏界面移除炸弹
        });
    }
    /**
     * Resets and reinitializes the UI components of the game.
     */

    public void resetUIComponents() {
        // Clear existing components
        root.getChildren().clear();

        System.out.println("Initializing UI Components...");
        // Reinitialize basic UI components
        gameViewInit.initUIComponents(false);

        System.out.println("UI Components initialized.");

        // Reinitialize ball and paddle views
        System.out.println("Initializing Ball View...");
        gameViewInit.initBallView(model.getxBall(), model.getyBall(), model.getBallRadius());
        System.out.println("Ball initialized at x: " + model.getxBall() + ", y: " + model.getyBall());

        System.out.println("Initializing Paddle View...");
        gameViewInit.initBreakView(model.getxPaddle(), model.getyPaddle(), model.getPaddleWidth(), model.getPaddleHeight());
        System.out.println("Paddle initialized at x: " + model.getxPaddle() + ", y: " + model.getyPaddle());

        // Initialize new labels
        scoreLabel = new Label("Score: " + model.getScore());
        heartLabel = new Label("Heart: " + model.getHeart());
        levelLabel = new Label("Level: " + model.getLevel());


        // Add new labels to root
        root.getChildren().addAll(scoreLabel, heartLabel, levelLabel);

        // Update references with the newly created instances
        ball = gameViewInit.getBall();
        rect = gameViewInit.getRect();

        System.out.println("Ball hash code: " + System.identityHashCode(ball));
        System.out.println("Paddle hash code: " + System.identityHashCode(rect));

        // Update GameViewUpdate instance with new references
        System.out.println("Reinitializing GameViewUpdate...");
        gameViewUpdate = new GameViewUpdate(root, ball, rect, scoreLabel, heartLabel, levelLabel);
        System.out.println("GameViewUpdate reinitialized.");
        System.out.println("Current children count in root: " + root.getChildren().size());
    }

}



