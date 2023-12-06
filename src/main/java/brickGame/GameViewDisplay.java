package brickGame;

import javafx.animation.PauseTransition;
import javafx.application.Platform;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.control.Button;
import javafx.util.Duration;
/**
 * The GameViewDisplay class handles the display of messages and notifications within the game view.
 * @see <a href="https://github.com/Lzyl02/COMP2042_CW_hcyzl4/blob/main/src/main/java/brickGame/GameViewDisplay.java">GameViewDisplay.java</a>
 */
public class GameViewDisplay {
    private Pane root;
    /**
     * Constructs a new GameViewDisplay with the specified root pane.
     *
     * @param root The root pane of the application, used for displaying game messages and notifications.
     */
    public GameViewDisplay(Pane root) {

        this.root = root;
    }

    /**
     * Displays a temporary message on the game screen at specified coordinates.
     * The message disappears after a set duration.
     *
     * @param message The message to display.
     * @param x       The X coordinate where the message will be displayed.
     * @param y       The Y coordinate where the message will be displayed.
     */
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

    /**
     * Displays a "You Win" message on the game screen.
     * The position and size of the message are pre-set within the method.
     */
    public void showWin() {
        Label winLabel = new Label("You Win :)");
        winLabel.setTranslateX(200);
        winLabel.setTranslateY(250);
        winLabel.setScaleX(2);
        winLabel.setScaleY(2);
        // 设置 winLabel 的位置和大小
        root.getChildren().add(winLabel);
    }
}
