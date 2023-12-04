package brickGame;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

import static brickGame.GameModel.score;

public class GameSaveLoadController {
    private GameModel model;
    private GameController gameController;
    private GameView gameView;
    private String savePath;
    private String savePathDir;

    public GameSaveLoadController(GameView view, GameModel model, String savePath, String savePathDir) {
        this.gameView = view;
        this.model = model;
        this.savePath = savePath;
        this.savePathDir = savePathDir;
    }

    private void saveGame() {

    }



    public void loadFromSave() {
        try (ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(savePath))) {
            // Deserialize the game state
            deserializeGameState(inputStream);
            // Additional actions after loading, e.g., updating the UI
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void deserializeGameState(ObjectInputStream inputStream) throws IOException, ClassNotFoundException {
        // Implement deserialization logic
        // Example: GameState gameState = (GameState) inputStream.readObject();
        // model.setGameState(gameState);
    }

    // Additional helper methods if needed
}