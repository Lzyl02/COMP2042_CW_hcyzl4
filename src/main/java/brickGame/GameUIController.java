package brickGame;

public class GameUIController {
    private GameModel model;
    private GameView view;

    public GameUIController(GameModel model, GameView view) {
        this.model = model;
        this.view = view;
    }

    public void updateUI() {
        // 更新游戏界面的方法，例如更新球和挡板的位置
        view.updateBallPosition(model.getxBall(), model.getyBall());
        view.updatePaddlePosition(model.getxPaddle(), model.getyPaddle());
        // 其他 UI 更新...
    }

    public void updateScoreView(double x, double y, int score) {
        // 在游戏界面上更新分数的显示
        view.showScoreLabel(x, y, score);
    }

    public void updatePaddleView() {
        // 更新挡板的视图
        view.updatePaddleSize(model.getxPaddle(), model.getPaddleWidth());
    }



    // 可以添加更多与界面更新相关的方法
}