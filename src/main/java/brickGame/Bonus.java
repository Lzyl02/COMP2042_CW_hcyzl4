package brickGame;

import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;

import java.io.Serializable;
import java.util.Random;

/**
 * Represents a bonus item in the game.
 * Bonus items have a position, falling speed, and a state. This class provides methods to create the bonus item,
 * make it fall down, and check if it has been obtained.
 * <p>
 * This class implements the {@link java.io.Serializable} interface, to support serialization and deserialization of bonus item objects.
 * </p>
 */
public class Bonus implements Serializable {
    public Rectangle choco;
    private double x;
    double y;
    int fallingSpeed = 1;
    public long timeCreated;
    public boolean taken = false;

    /**
     * Constructs a new bonus item instance.
     * This constructor initializes the position of the bonus item based on the specified row and column positions,
     * and calls the {@link #draw()} method to create the graphical representation of the bonus item.
     *
     * @param row The row position of the bonus item.
     * @param column The column position of the bonus item.
     */
    public Bonus(int row, int column) {
        x = (column * (Block.getWidth())) + Block.getPaddingH() + (Block.getWidth() / 2) - 15;
        y = (row * (Block.getHeight())) + Block.getPaddingTop() + (Block.getHeight() / 2) - 15;


        draw();
        System.out.println("Bonus created at: Row = " + row + ", Column = " + column + ", x = " + x + ", y = " + y);

    }


    /**
     * Draws the graphical representation of the bonus item.
     * This method creates a rectangle to represent the bonus item and sets its size, coordinates, and pattern.
     * The pattern of the bonus item is defined by randomly choosing between the "bonus1.png" or "bonus2.png" image files.
     */
    private void draw() {
        choco = new Rectangle();
        choco.setWidth(30);
        choco.setHeight(30);
        choco.setX(x);
        choco.setY(y);

        String url = new Random().nextInt(20) % 2 == 0 ? "bonus1.png" : "bonus2.png";
        choco.setFill(new ImagePattern(new Image(url)));
    }


    public Rectangle getRectangle() {
        return choco;
    }
    public double getY() {
        return y;
    }

    /**
     * Makes the bonus item fall down.
     * This method updates the bonus item's y-coordinate to simulate a falling effect.
     */
    public void fallDown() {
        this.y += fallingSpeed;
        if (choco != null) {
            choco.setY(this.y);
        }
    }

    public boolean isTaken() {
        return taken;
    }

    public void setTaken(boolean taken) {
        this.taken = taken;
    }

    public double getX() {
        return x;
    }


}
