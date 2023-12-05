package brickGame;

import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import java.io.Serializable;

/**
 * Represents a bomb in the game.
 * Bombs have a position, falling speed, and a state. This class provides methods to create the bomb,
 * make it fall down, and check if it has been dealt with.
 * <p>
 * This class implements the {@link java.io.Serializable} interface, to support serialization and deserialization of bomb objects.
 * </p>
 */
public class Bombs implements Serializable {
    private Circle bomb;
    private double x;
    private double y;
    private int fallingSpeed = 1; // Falling speed
    private long lastCaughtTime = 0; // Timestamp of when the bomb was last caught

    private boolean taken = false; // Represents whether the bomb has been dealt with


    /**
     * Constructs a new bomb instance.
     * This constructor initializes the position of the bomb based on the specified row and column positions,
     * and calls the {@link #draw()} method to create the graphical representation of the bomb.
     *
     * @param row The row position of the bomb.
     * @param column The column position of the bomb.
     */
    public Bombs(int row, int column) {
        x = (column * Block.getWidth()) + Block.getPaddingH() + (Block.getWidth() / 2);
        y = (row * Block.getHeight()) + Block.getPaddingTop() + (Block.getHeight() / 2);
        draw(); // Create the bomb's graphic
        System.out.println("Bomb created at: Row = " + row + ", Column = " + column + ", x = " + x + ", y = " + y);
    }

    /**
     * Draws the graphical representation of the bomb.
     * This method creates a circle to represent the bomb and sets its radius, center coordinates, and pattern.
     * The pattern of the bomb is defined using the "Bombs.png" image file.
     */
    private void draw() {
        bomb = new Circle();
        bomb.setRadius(20); // Change the radius here
        bomb.setCenterX(x);
        bomb.setCenterY(y);
        bomb.setFill(new ImagePattern(new Image("Bombs.png"))); // Set the graphic
    }


    /**
     * Makes the bomb fall down.
     * This method updates the bomb's y-coordinate to simulate a falling effect.
     */
    public void fallDown() {
        this.y += fallingSpeed;
        if (bomb != null) {
            bomb.setCenterY(this.y); // Update the bomb's position
        }
    }

    // Return the bomb's graphic
    public Circle getCircle() {
        return bomb;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public boolean isTaken() {
        return taken;
    }

    public void setTaken(boolean taken) {
        this.taken = taken;
    }

    public void setLastCaughtTime(long time) {
        this.lastCaughtTime = time;
    }

    public long getLastCaughtTime() {
        return this.lastCaughtTime;
    }
}
