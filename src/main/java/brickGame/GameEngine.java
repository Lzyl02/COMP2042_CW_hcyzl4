package brickGame;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * GameEngine class is responsible for managing the main game loop and coordinating various game updates.
 * It provides methods to start, stop, and restart the game, and allows setting the frame rate.
 */
public class GameEngine {

    private OnAction onAction;
    private volatile boolean isStopped = true;
    private ExecutorService executorService;
    private long time = 0;
    private int frameTimeMillis;

    /**
     * Interface defining callback methods for game actions.
     */
    public interface OnAction {
        /**
         * Called on every frame to update game state.
         */
        void onUpdate();
        /**
         * Called when the game is initialized.
         */
        void onInit();
        /**
         * Called regularly for physics calculations.
         */
        void onPhysicsUpdate();

        /**
         * Called to update game time.
         * @param time The current game time.
         */
        void onTime(long time);
    }

    /**
     * Sets the OnAction callback to handle game actions.
     * @param onAction The OnAction implementation to be used by the game engine.
     */
    public void setOnAction(OnAction onAction) {
        this.onAction = onAction;
    }

    /**
     * Sets the frames per second for the game loop.
     * @param fps The desired frames per second.
     */
    public void setFps(int fps) {
        frameTimeMillis = 1000 / fps;
    }

    /**
     * Starts the game loop. Initializes the executor service and submits game loop tasks.
     */
    public void start() {
        if (executorService != null && !executorService.isTerminated()) {
            stopAndWait();
        }

        time = 0;
        isStopped = false;
        executorService = Executors.newFixedThreadPool(4);

        submitTasks();
    }

    private void submitTasks() {
        executorService.submit(this::initialize);
        executorService.submit(this::update);
        executorService.submit(this::physicsCalculation);
        executorService.submit(this::timeTracking);
    }

    /**
     * Stops the game loop immediately without waiting for tasks to complete.
     */

    public void stop() {
        isStopped = true;
        shutdownExecutorService();
    }

    /**
     * Stops the game loop and waits for all tasks to complete before returning.
     */
    public void stopAndWait() {
        isStopped = true;
        shutdownExecutorService();
        try {
            if (!executorService.awaitTermination(1, TimeUnit.SECONDS)) {
                executorService.shutdownNow();
                if (!executorService.awaitTermination(1, TimeUnit.SECONDS)) {
                    System.err.println("Executor did not terminate");
                }
            }
        } catch (InterruptedException ie) {
            Thread.currentThread().interrupt();
            executorService.shutdownNow();
        }
    }

    /**
     * Shuts down the executor service associated with the game loop.
     * This method is called to cleanly terminate the game loop's threads.
     */
    private void shutdownExecutorService() {
        if (executorService != null) {
            executorService.shutdown();
        }
    }

    /**
     * Restarts the game loop by first stopping it and then starting it again.
     */
    public void restart() {
        stopAndWait();
        start();
    }

    /**
     * Initializes the game. This method is called once when the game loop starts.
     * It triggers the onInit() method of the OnAction interface if it's not null.
     */
    private void initialize() {
        if (onAction != null) {
            onAction.onInit();
        }
    }

    /**
     * Continuously updates the game state. This method runs in a loop as long as the game is not stopped.
     * It calls the onUpdate() method of the OnAction interface at regular intervals defined by frameTimeMillis.
     * If interrupted, it handles the interruption by setting the thread's interrupt flag.
     */
    private void update() {
        while (!isStopped && onAction != null) {
            try {
                onAction.onUpdate();
                Thread.sleep(frameTimeMillis);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

    /**
     * Handles the physics calculations of the game. This method runs in a loop and is responsible for physics updates.
     * It calls the onPhysicsUpdate() method of the OnAction interface at a fixed interval (approximately 60 times per second).
     * If interrupted, it handles the interruption by setting the thread's interrupt flag.
     */
    private void physicsCalculation() {
        while (!isStopped && onAction != null) {
            try {
                onAction.onPhysicsUpdate();
                Thread.sleep(16); // 60 times per second
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

    /**
     * Tracks and updates the game time. This method increments the time variable every millisecond and
     * calls the onTime() method of the OnAction interface with the updated time.
     * If interrupted, it handles the interruption by setting the thread's interrupt flag.
     */
    private void timeTracking() {
        while (!isStopped) {
            try {
                time++;
                if (onAction != null) {
                    onAction.onTime(time);
                }
                Thread.sleep(1);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }
}
