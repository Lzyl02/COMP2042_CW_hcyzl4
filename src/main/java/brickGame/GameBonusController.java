package brickGame;

import javafx.animation.AnimationTimer;
import java.util.ArrayList;
import java.util.List;
public class GameBonusController {
    private AnimationTimer bonusUpdater;
    private GameModel model; // Assuming there's a GameModel class managing game state
    private GameView view; // Assuming there's a GameView class for the UI
    private int score;
    public GameBonusController(GameModel model, GameView view) {
        this.model = model;
        this.view = view;
        this.score = 0;
    }
    public void initializeBonusUpdater() {
        bonusUpdater = new AnimationTimer() {
            @Override
            public void handle(long now) {
                List<Bonus> bonuses = model.getChocos();
                for (Bonus bonus : bonuses) {
                    bonus.fallDown(); // 更新位置
                    view.updateBonusPosition(bonus); // 反映位置更新到视图
                    if (shouldRemoveBonus(bonus)) {
                        view.removeBonus(bonus); // 从视图中移除
                    }
                }
            }
        };
        bonusUpdater.start();
    }
    public void catchChoco() {
        ArrayList<Bonus> removedChocos = new ArrayList<>();
        for (Bonus choco : model.getChocos()) {
            if (choco.y >= model.getyPaddle() && choco.y <= model.getyPaddle() + model.getPaddleHeight() &&
                    choco.getX() >= model.getxPaddle() && choco.getX() <= model.getxPaddle() + model.getPaddleWidth() && !choco.isTaken()) {
                // Chocolate is caught
                System.out.println("Chocolate caught: x = " + choco.getX() + ", y = " + choco.y);
                choco.setTaken(true);
                score += 3;
                updateScoreView(choco.getX(), choco.y, 3);
                removedChocos.add(choco);
            } else if (choco.y >= model.getSceneHeight() && !choco.isTaken()) {
                // Chocolate reached the bottom and was not caught
                System.out.println("Chocolate missed: x = " + choco.getX() + ", y = " + choco.y);
                choco.setTaken(true); // Mark it as handled
                removedChocos.add(choco);
            }
        }
        // Remove handled chocolates from the list
        model.getChocos().removeAll(removedChocos);
        // Notify the controller to update the view
        updateCaughtChocos(removedChocos);
    }
    private void handleChocoMovement() {
        for (Bonus choco : model.getChocos()) {
            if (!choco.taken) {
                choco.y += model.getFallingSpeed();
                // Log the new position for debugging
                System.out.println("Choco updated: x = " + choco.getX() + ", y = " + choco.y);
            }
        }
    }
    public boolean shouldRemoveBonus(Bonus bonus) {
        // 示例逻辑: 如果 bonus 的 Y 坐标超过了屏幕底部或者它被标记为 taken，则应该移除
        return bonus.getY() > model.getSceneHeight()  || bonus.isTaken();
    }
    public void updateScoreView(double x, double y, int score) {
        view.showScoreLabel(x, y, score);
    }
    public void updateCaughtChocos(List<Bonus> caughtChocos) {
        for (Bonus choco : caughtChocos) {
            // Update the view to reflect the caught chocolate
            view.removeBonus(choco); // This will remove the chocolate from the UI
        }
    }
    // Additional methods like shouldRemoveBonus, updateScoreView, updateCaughtChocos would be defined here...
}
