module brickGame {
    requires javafx.fxml;
    requires javafx.controls;
    requires java.desktop;

    opens brickGame to javafx.fxml;
    exports brickGame;
}