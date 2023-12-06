package brickGame;

import javafx.animation.AnimationTimer;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Controls and manages game bonuses and bombs in a brick-style game.
 * Coordinates between the game model and view, updating game elements like bonuses and bombs in real-time.
 *
 * @see <a href="https://github.com/Lzyl02/COMP2042_CW_hcyzl4/blob/main/src/main/java/brickGame/GameBonusController.java">GameBonusController.java</a>
 */
public class GameBonusController {

    private AnimationTimer bonusUpdater;
    private GameModel model; // Reference to the game model.
    private GameView view; // Reference to the game view.
    private int score;
    private AnimationTimer bombUpdater;

    /**
     * Constructor to initialize the controller with game model and view.
     *
     * @param model The game model managing game state.
     * @param view The game view for the UI.
     */
    public GameBonusController(GameModel model, GameView view) {
        this.model = model;
        this.view = view;
        this.score = 0;
    }

    /**
     * Initializes and starts the bonus updater.
     * This method sets up an AnimationTimer to regularly update the position of bonuses on the screen.
     */
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
    /**
     * Determines if a bonus should be removed from the game.
     *
     * @param bonus The bonus to check.
     * @return true if the bonus is out of bounds or taken, false otherwise.
     */
    private boolean shouldRemoveBonus(Bonus bonus) {
        // 示例逻辑: 如果 bonus 的 Y 坐标超过了屏幕底部或者它被标记为 taken，则应该移除
        return bonus.getY() > model.getSceneHeight()  || bonus.isTaken();
    }

    /**
     * Initializes and starts the bomb updater.
     * This method sets up an AnimationTimer to regularly update the position of bombs on the screen.
     */

    public void initializeBombUpdater() {
        bombUpdater = new AnimationTimer() {
            @Override
            public void handle(long now) {
                Iterator<Bombs> iterator = model.getBombs().iterator();
                while (iterator.hasNext()) {
                    Bombs bomb = iterator.next();
                    bomb.fallDown(); // 更新位置
                    view.updateBombPosition(bomb); // 反映位置更新到视图

                    if (bomb.getY() >= model.getSceneHeight() || bomb.isTaken()) {
                        iterator.remove(); // 使用迭代器移除炸弹
                        view.removeBomb(bomb); // 从视图中移除炸弹
                    }
                }
            }
        };
        bombUpdater.start();
    }

}
