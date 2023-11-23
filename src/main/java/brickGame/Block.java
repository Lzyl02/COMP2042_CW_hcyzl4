package brickGame;


import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;

import java.io.Serializable;

public class Block implements Serializable {
    private static Block block = new Block(-1, -1, Color.TRANSPARENT, 99);

    public int row;
    public int column;


    public boolean isDestroyed = false;

    private Color color;
    public int type;

    public int x;
    public int y;

    private int width = 100;
    private int height = 30;
    private int paddingTop = height * 2;
    private int paddingH = 50;
    public Rectangle rect;


    public static int NO_HIT = -1;
    public static int HIT_RIGHT = 0;
    public static int HIT_BOTTOM = 1;
    public static int HIT_LEFT = 2;
    public static int HIT_TOP = 3;

    public static int BLOCK_NORMAL = 99;
    public static int BLOCK_CHOCO = 100;
    public static int BLOCK_STAR = 101;
    public static int BLOCK_HEART = 102;


    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public Color getColors() {
        return color;
    }

    public Block(int row, int column, Color color, int type) {
        this.row = row;
        this.column = column;
        this.color = color;
        this.type = type;

        draw();
    }

    private void draw() {
        x = (column * width) + paddingH;
        y = (row * height) + paddingTop;

        rect = new Rectangle();
        rect.setWidth(width);
        rect.setHeight(height);
        rect.setX(x);
        rect.setY(y);

        if (type == BLOCK_CHOCO) {
            Image image = new Image("choco.jpg");
            ImagePattern pattern = new ImagePattern(image);
            rect.setFill(pattern);
        } else if (type == BLOCK_HEART) {
            Image image = new Image("heart.jpg");
            ImagePattern pattern = new ImagePattern(image);
            rect.setFill(pattern);
        } else if (type == BLOCK_STAR) {
            Image image = new Image("star.jpg");
            ImagePattern pattern = new ImagePattern(image);
            rect.setFill(pattern);
        } else {
            rect.setFill(color);
        }

    }


    public int checkHitToBlock(double xBall, double yBall) {
        if (isDestroyed || (xBall < x || xBall > x + width) || (yBall < y || yBall > y + height)) {
            return NO_HIT;
        }

        double xCenter = x + width / 2;
        double yCenter = y + height / 2;
        int ballRadius = 10;
        double dx = Math.abs(xBall - xCenter);
        double dy = Math.abs(yBall - yCenter);
        double halfWidth = width / 2;
        double halfHeight = height / 2;

        if (xBall >= x && xBall <= x + width) {
            if (yBall == y + height) {
                return HIT_BOTTOM;
            } else if (yBall == y) {
                return HIT_TOP;
            }
        }

        if (yBall >= y && yBall <= y + height) {
            if (xBall == x + width) {
                return HIT_RIGHT;
            } else if (xBall == x) {
                return HIT_LEFT;
            }
        }

        if (dx <= halfWidth + ballRadius && dy <= halfHeight + ballRadius) {
            if (dx > dy) {
                return (xBall < xCenter) ? HIT_LEFT : HIT_RIGHT;
            } else {
                return (yBall < yCenter) ? HIT_TOP : HIT_BOTTOM;
            }
        }

        return NO_HIT;
    }

    public static int getPaddingTop() {
        return block.paddingTop;
    }

    public static int getPaddingH() {
        return block.paddingH;
    }

    public static int getHeight() {
        return block.height;
    }

    public static int getWidth() {
        return block.width;
    }

}
