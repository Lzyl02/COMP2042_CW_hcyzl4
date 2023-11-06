package brickGame;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class GameEngine {
    private OnAction onAction;

    public interface OnAction {
        void onUpdate();

        void onInit();

        void onPhysicsUpdate();

        void onTime(long time);
    }

    private int fps = 15;
    private ScheduledExecutorService executor;
    private boolean isStopped = true;

    public void setOnAction(OnAction onAction) {
        this.onAction = onAction;
    }

    public void setFps(int fps) {
        this.fps = (int) 1000 / fps;
    }

    private void Update() {
        executor = Executors.newScheduledThreadPool(2);

        executor.scheduleAtFixedRate(() -> {
            onAction.onUpdate();
        }, 0, fps, TimeUnit.MILLISECONDS);
    }

    private void Initialize() {
        onAction.onInit();
    }

    private void PhysicsCalculation() {
        executor.scheduleAtFixedRate(() -> {
            onAction.onPhysicsUpdate();
        }, 0, fps, TimeUnit.MILLISECONDS);
    }

    public void start() {
        Initialize();
        Update();
        PhysicsCalculation();
        isStopped = false;
    }

    public void stop() {
        if (!isStopped) {
            isStopped = true;
            executor.shutdownNow();
        }
    }
}
