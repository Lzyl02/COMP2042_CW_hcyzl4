package brickGame;


public class GameEngine {
    private OnAction onAction;
    public interface OnAction {
        void onUpdate();

        void onInit();

        void onPhysicsUpdate();

        void onTime(long time);
    }
    private int fps = 15;
    private Thread updateThread;
    private Thread physicsThread;
    private volatile boolean isStopped = true;

    public void setOnAction(OnAction onAction) {
        this.onAction = onAction;
    }

    public void setFps(int fps) {
        this.fps = (int) 1000 / fps;
    }

    private void Update() {
        updateThread = new Thread(() -> {
            while (!Thread.currentThread().isInterrupted()) {
                try {
                    onAction.onUpdate();
                    Thread.sleep(fps);
                } catch (InterruptedException e) {
                    // 处理线程被中断的情况
                    Thread.currentThread().interrupt(); // 重新中断线程
                }
            }
        });
        updateThread.start();
    }

    private void Initialize() {
        onAction.onInit();
    }

    private void PhysicsCalculation() {
        physicsThread = new Thread(() -> {
            while (!Thread.currentThread().isInterrupted()) {
                try {
                    onAction.onPhysicsUpdate();
                    Thread.sleep(fps);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        });
        physicsThread.start();
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
            updateThread.interrupt(); // 使用interrupt方法中断线程
            physicsThread.interrupt();
        }
    }

    // 其他成员变量和方法...
}
