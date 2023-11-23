package brickGame;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

public class Main extends Application {
    @Override
    public void start(Stage primaryStage) {
        GameModel model = new GameModel();
        GameView view = new GameView(primaryStage, model);
        GameController controller = new GameController(model, view);

        model.initializeGameState();
        view.initializeGameView();

        primaryStage.addEventHandler(KeyEvent.KEY_PRESSED, controller::handle);

        primaryStage.setTitle("Game");
        primaryStage.setScene(view.getScene());
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
