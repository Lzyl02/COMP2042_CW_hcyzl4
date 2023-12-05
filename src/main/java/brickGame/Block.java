package brickGame;


import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;

import java.io.Serializable;


/**
 * Represents an individual block entity in the game.
 * This class is responsible for managing the block's position, size, color, and type,
 * and provides methods for detecting and handling collisions with the ball.
 * <p>
 * Blocks can have different types, such as normal blocks, chocolate blocks, star blocks, etc.
 * </p>
 */
public class Block implements Serializable {

    /**
     * Represents a block in a brick game.
     * This class encapsulates the properties and behaviors of a game block.
     */
    private static Block block = new Block(-1, -1, Color.TRANSPARENT, 99);

    public int row;
    public int column;
    private boolean hasGeneratedBomb = false;


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
    public static final int BLOCK_DAEMON = 103;


    /**
     * Creates a new block with specified parameters.
     *
     * @param row    The row in which the block is located.
     * @param column The column in which the block is located.
     * @param color  The color of the block.
     * @param type   The type of the block.
     */
    public Block(int row, int column, Color color, int type) {
        this.row = row;
        this.column = column;
        this.color = color;
        this.type = type;

        draw();
    }

    /**
     * Draws the block.
     * This method calculates the position of the block based on its row, column, and type information,
     * creates a rectangle to represent the block, and sets its color or image based on the block's type.
     * Supports various types of blocks, including normal blocks, chocolate blocks, star blocks, etc.
     * If the block type corresponds to a specific image (such as chocolate or star),
     * the {@link #setBlockImage(String)} method is called to set the image.
     */
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
            case BLOCK_DAEMON: // Handling for Daemon block
                setBlockImage("daemon.png");
                break;
            default:
                rect.setFill(color);
                break;
        }
    }


    /**
     * Sets an image for the block.
     * This method attempts to load an image from the specified file path and applies it to the block.
     * If the image is successfully loaded, the block is filled with this image;
     * otherwise, an error message is printed to the console, and the block remains unchanged.
     *
     * @param imagePath The path to the image file. Should be a path relative to the classpath.
     */
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



    /**
     * Checks for collision between the ball and this block.
     * Determines whether the ball has collided with the block based on the positions and dimensions
     * of both the ball and the block, and returns the type of collision.
     *
     * @param xBall The x-coordinate of the ball.
     * @param yBall The y-coordinate of the ball.
     * @return An integer code for the type of collision. Possible values are {@link #NO_HIT}, {@link #HIT_RIGHT},
     *         {@link #HIT_BOTTOM}, {@link #HIT_LEFT}, {@link #HIT_TOP}.
     *         Returns {@link #NO_HIT} if no collision occurs.
     */

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
        double collisionThreshold = ballRadius; // 碰撞阈值

        // 检查水平碰撞
        if (xBall >= x && xBall <= x + width) {
            if (Math.abs(yBall - (y + height)) <= collisionThreshold) {
                return HIT_BOTTOM;
            } else if (Math.abs(yBall - y) <= collisionThreshold) {
                return HIT_TOP;
            }
        }

        // 检查垂直碰撞
        if (yBall >= y && yBall <= y + height) {
            if (Math.abs(xBall - (x + width)) <= collisionThreshold) {
                return HIT_RIGHT;
            } else if (Math.abs(xBall - x) <= collisionThreshold) {
                return HIT_LEFT;
            }
        }

        // 检查复合碰撞
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

    public boolean hasGeneratedBomb() {
        return hasGeneratedBomb;
    }

    public void setGeneratedBomb(boolean hasGeneratedBomb) {
        this.hasGeneratedBomb = hasGeneratedBomb;
    }


}

