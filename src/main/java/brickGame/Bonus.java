package brickGame;

import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;

import java.io.Serializable;
import java.util.Random;
public class Bonus implements Serializable {
    public Rectangle choco;
    private double x;
    double y;
    int fallingSpeed = 1;
    public long timeCreated;
    public boolean taken = false;

    public Bonus(int row, int column) {
        x = (column * (Block.getWidth())) + Block.getPaddingH() + (Block.getWidth() / 2) - 15;
        y = (row * (Block.getHeight())) + Block.getPaddingTop() + (Block.getHeight() / 2) - 15;

        draw();
        System.out.println("Bonus created at: Row = " + row + ", Column = " + column + ", x = " + x + ", y = " + y);

    }

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

    public void fallDown() {
        this.y += fallingSpeed; // Define fallingSpeed as needed
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
