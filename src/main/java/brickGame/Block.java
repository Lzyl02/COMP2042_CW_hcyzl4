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


    public void setDestroyed(boolean destroyed) {
        isDestroyed = destroyed;
    }

    public boolean isDestroyed = false;

    private Color color;

    public static Block getBlock() {
        return block;
    }

    public int getType() {
        return type;
    }

    public int type;

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int x;
    public int y;

    private int width = 100;
    private int height = 30;
    private int paddingTop = height * 2;
    private int paddingH = 50;

    public Rectangle getRect() {
        return rect;
    }

    public Rectangle rect;


    public final static int NO_HIT = -1;
    public final static int HIT_RIGHT = 0;
    public final static int HIT_BOTTOM = 1;
    public final static int HIT_LEFT = 2;
    public final static int HIT_TOP = 3;

    public static final int BLOCK_NORMAL = 99;
    public static final int BLOCK_CHOCO = 100;
    public static final int BLOCK_STAR = 101;
    public static final int BLOCK_HEART = 102;


    public Block(int row, int column, Color color, int type) {
        this.row = row;
        this.column = column;
        this.color = color;
        this.type = type;

        draw();
    }

    private void draw() {
        x = paddingH + (column * width); // Corrected position calculation
        y = paddingTop + (row * height); // Corrected position calculation

        rect = new Rectangle(x, y, width, height); // Corrected Rectangle initialization

        switch (type) {
            case BLOCK_CHOCO:
                setBlockImage("choco.jpg");
                break;
            case BLOCK_HEART:
                setBlockImage("heart.jpg");
                break;
            case BLOCK_STAR:
                setBlockImage("star.jpg");
                break;
            default:
                rect.setFill(color);
                break;
        }
    }

    private void setBlockImage(String imagePath) {
        try {
            Image image = new Image(getClass().getResourceAsStream("/" + imagePath));
            if (image.isError()) {
                System.out.println("Error loading image: " + imagePath);
            } else {
                rect.setFill(new ImagePattern(image));
                System.out.println("Star image loaded successfully");
            }
        } catch (Exception e) {
            System.out.println("Exception loading image: " + imagePath);
            e.printStackTrace();
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

    public Color getColor() {
        return color;
    }

    public int getColumn() {
        return column;
    }

    public int getRow() {
        return row;
    }


}

