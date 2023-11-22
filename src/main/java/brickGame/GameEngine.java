package brickGame;

import javafx.application.Platform;

public class GameEngine {

    public interface OnAction {
        void onUpdate();
        void onPhysicsUpdate();
        void onTime(long time);
        void onInit();
    }

    private OnAction onAction;
    private int fps = 60;

    private Thread gameThread;
    private long time = 0;
    private volatile boolean running = false;

    public void setOnAction(OnAction onAction) {
        this.onAction = onAction;
    }

    public void setFps(int fps) {
        this.fps = fps;
    }

    private void initialize() {
        // Ensure onInit is called on the JavaFX Application Thread
        Platform.runLater(() -> onAction.onInit());
    }

    private void gameLoop() {
        while (running) {
            // Ensure onUpdate is called on the JavaFX Application Thread
            Platform.runLater(() -> onAction.onUpdate());

            // Ensure onPhysicsUpdate is called on the JavaFX Application Thread
            Platform.runLater(() -> onAction.onPhysicsUpdate());

            // Ensure onTime is called on the JavaFX Application Thread
            Platform.runLater(() -> onAction.onTime(time));

            time++;

            try {
                Thread.sleep(1000L / fps);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt(); // Restore the interrupted status
                break; // Exit the loop to stop the thread
            }
        }
    }

    public void start() {
        if (running) {
            return; // The game is already running
        }
        running = true;
        initialize();
        gameThread = new Thread(this::gameLoop);
        gameThread.start();
    }

    public void stop() {
        running = false;
        if (gameThread != null) {
            gameThread.interrupt();
            try {
                gameThread.join(); // Wait for the thread to finish
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt(); // Restore the interrupted status
            }
        }
    }
}
