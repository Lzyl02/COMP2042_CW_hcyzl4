package brickGame;

import javafx.application.Platform;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
/**
 * The GameViewAnimation class is responsible for animating UI elements in the game view.
 *
 * @see <a href="https://github.com/Lzyl02/COMP2042_CW_hcyzl4/blob/main/src/main/java/brickGame/GameViewAnimation.java">GameViewAnimation.java</a>
 */
public class GameViewAnimation {
    private Pane root;
    /**
     * Constructs a GameViewAnimation object with the specified root pane.
     *
     * @param root The root pane of the application, used for adding or removing UI elements.
     */
    public GameViewAnimation(Pane root) {
        this.root = root;
    }
    /**
     * Animates a label with a scaling and fading effect, and then removes it from the view.
     * The animation occurs on a separate thread to avoid blocking the UI thread.
     *
     * @param label The label to be animated.
     */
    public void animateLabel(Label label) {
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

}
