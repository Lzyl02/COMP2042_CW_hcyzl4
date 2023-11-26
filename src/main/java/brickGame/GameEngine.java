package brickGame;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

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

    public void setOnAction(OnAction onAction) {
        System.out.println("OnAction set in GameEngine.");

        this.onAction = onAction;
    }

    public void setFps(int fps) {
        this.fps = 1000 / fps;
        System.out.println("FPS set to: " + this.fps + " ms per frame");
    }

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
