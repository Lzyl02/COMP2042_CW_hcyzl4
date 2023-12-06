package brickGame;

import java.io.Serializable;
/**
 * Represents a serializable block entity in the game.
 * This class is used for serializing block data, allowing it to be saved, transferred, or loaded across different sessions or systems.
 *
 * It contains essential attributes of a block, such as its row and column positions within the grid, and its type.
 * The type attribute can indicate different characteristics or behaviors of the block in the game.
 * Implementing the {@link java.io.Serializable} interface allows instances of this class to be serialized and deserialized.
 * @see <a href="https://github.com/Lzyl02/COMP2042_CW_hcyzl4/blob/main/src/main/java/brickGame/BlockSerializable.java">BlockSerializable.java</a>
 */
public class BlockSerializable implements Serializable {
    public final int row;
    public final int j;
    public final int type;

    /**
     * Represents a serializable block entity in the game.
     * This class contains basic attributes of a block, such as row position, column position, and type,
     * and can be used to transfer block information between different systems or environments.
     * <p>
     * This class implements the {@link java.io.Serializable} interface, allowing block objects to be serialized and deserialized.
     * </p>
     *
     * @param row the row position of the block in the grid.
     * @param j the column position of the block in the grid.
     * @param type the type of the block, indicating different characteristics or behaviors in the game.
     */
    public BlockSerializable(int row , int j , int type) {
        this.row = row;
        this.j = j;
        this.type = type;
    }
}
