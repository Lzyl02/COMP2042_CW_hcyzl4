package brickGame;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Game engine class responsible for managing and controlling the main game loop.
 * Offers functionality to start, stop, and restart the game, along with the option to set the game's frame rate.
 * <p>
 * This class uses a thread pool to handle game initialization, updates, physics calculations, and time tracking concurrently.
 * </p>
 */
public class GameEngine {

    private OnAction onAction;
    private volatile int fps = 15;
    private boolean isStopped = true;
    private ExecutorService executorService;
    private long time = 0;
    private boolean isRunning;

    public boolean isRunning() {
        return isRunning;
    }
    public interface OnAction {
        void onUpdate();
        void onInit();
        void onPhysicsUpdate();
        void onTime(long time);
    }

    /**
     * Sets the action callbacks for the game.
     * This method allows specifying the behavior of the game at different stages such as update, initialization, physics calculation, and time tracking.
     *
     * @param onAction The callback interface for game actions.
     */
    public void setOnAction(OnAction onAction) {
        System.out.println("OnAction set in GameEngine.");

        this.onAction = onAction;
    }

    /**
     * Sets the frame rate of the game.
     * This method is used to control the frequency of the game update loop.
     *
     * @param fps Frames per second, indicating the speed of game updates.
     */
    public void setFps(int fps) {
        this.fps = 1000 / fps;
        System.out.println("FPS set to: " + this.fps + " ms per frame");
    }

    /**
     * Starts the game engine.
     * This method initializes the thread pool and starts various components of the game such as game updates, physics calculations, and time tracking.
     */
    public void start() {
        System.out.println("Game engine is starting...");

        time = 0;
        isStopped = false;
        executorService = Executors.newFixedThreadPool(3);

        executorService.submit(this::Initialize);
        executorService.submit(this::Update);
        executorService.submit(this::PhysicsCalculation);
        executorService.submit(this::TimeStart);

        System.out.println("Game engine started and threads initiated.");
    }

    /**
     * Stops the game engine.
     * This method shuts down the thread pool and stops all game updates and calculations.
     */
    public void stop() {
        System.out.println("Stopping game engine...");

        if (!isStopped) {
            isStopped = true;
            executorService.shutdown();
            try {
                if (!executorService.awaitTermination(800, TimeUnit.MILLISECONDS)) {
                    executorService.shutdownNow();
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }

            System.out.println("Game engine stopped.");
        }
    }

    /**
     * Stops the game engine and waits for it to completely stop.
     * This method shuts down the thread pool and waits until all tasks are completed or timeout occurs.
     */
    public void stopAndWait() {
        System.out.println("Stopping game engine and waiting for termination...");

        if (!isStopped) {
            isStopped = true;
            executorService.shutdown();
            try {
                if (!executorService.awaitTermination(1, TimeUnit.SECONDS)) {
                    executorService.shutdownNow();
                }
                System.out.println("Game engine stopped and all threads terminated.");
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                System.out.println("Interruption while stopping game engine.");
            }
        }
    }
    public void restart() {
        stopAndWait(); // Ensure previous instance is stopped
        start();       // Start a new instance
    }
    private void Initialize() {
        System.out.println("Initializing game...");
        onAction.onInit();
        System.out.println("Initialization complete.");
    }
    /**
     * The update thread of the game.
     * This method is responsible for periodically invoking the game's update logic.
     */
    private void Update() {
        System.out.println("Update thread started.");
        while (!isStopped) {
            try {
                onAction.onUpdate();
                Thread.sleep(fps);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                System.out.println("Update thread interrupted.");
                break;
            }
        }
        System.out.println("Update thread ended.");
    }

    /**
     * The physics calculation thread of the game.
     * This method regularly performs physics calculations to support the game's physical interactions.
     */
    private void PhysicsCalculation() {
        if (onAction == null) {
            System.out.println("Error: onAction is null in GameEngine.");
            return;
        }
        System.out.println("Physics calculation thread started.");
        int physicsFps = 1000 / 60; // Example: Set this to 60 times per second
        while (!isStopped) {
            try {

                onAction.onPhysicsUpdate();

                Thread.sleep(physicsFps); // Sleep for shorter duration for more frequent checks
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                System.out.println("Physics calculation thread interrupted.");
                break;
            }
        }
        System.out.println("Physics calculation thread ended.");
    }

    /**
     * The time tracking thread of the game.
     * This method is responsible for regularly updating the game time, which is used for time-related operations in game logic.
     */
    private void TimeStart() {
        System.out.println("Time tracking thread started.");
        while (!isStopped) {
            try {
                time++;
                onAction.onTime(time);
                Thread.sleep(1);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                System.out.println("Time tracking thread interrupted.");
                break;
            }
        }
        System.out.println("Time tracking thread ended.");
    }
}
