package brickGame;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;

/**
 * Responsible for reading and loading saved game data.
 * This class contains various attributes and methods needed to restore the game state from a save file,
 * including level, score, hearts, number of destroyed blocks, and physical states in the game.
 * @see <a href="https://github.com/Lzyl02/COMP2042_CW_hcyzl4/blob/main/src/main/java/brickGame/LoadSave.java">LoadSave.java</a>
 */
public class LoadSave {

    public boolean          isExistHeartBlock;
    public boolean          isGoldStauts;
    public boolean          goDownBall;
    public boolean          goRightBall;
    public boolean          colideToBreak;
    public boolean          colideToBreakAndMoveToRight;
    public boolean          colideToRightWall;
    public boolean          colideToLeftWall;
    public boolean          colideToRightBlock;
    public boolean          colideToBottomBlock;
    public boolean          colideToLeftBlock;
    public boolean          colideToTopBlock;
    public int              level;
    public int              score;
    public int              heart;
    public int              destroyedBlockCount;
    public double           xBall;
    public double           yBall;
    public double           xBreak;
    public double           yBreak;
    public double           centerBreakX;
    public long             time;
    public long             goldTime;
    public double           vX;
    public ArrayList<BlockSerializable> blocks = new ArrayList<BlockSerializable>();


    /**
     * Reads the saved game data.
     * This method loads the game state from a specified file path, including game level, score, positions, and status information.
     * Any issues encountered during the reading process are printed to the console.
     */
    public void read() {
        System.out.println("Starting to read saved game data...");


        try {
            ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(new File(GameModel.savePath)));


            level = inputStream.readInt();
            System.out.println("Read level: " + level);

            score = inputStream.readInt();
            heart = inputStream.readInt();
            destroyedBlockCount = inputStream.readInt();


            xBall = inputStream.readDouble();
            yBall = inputStream.readDouble();
            xBreak = inputStream.readDouble();
            yBreak = inputStream.readDouble();
            centerBreakX = inputStream.readDouble();
            time = inputStream.readLong();
            goldTime = inputStream.readLong();
            vX = inputStream.readDouble();


            isExistHeartBlock = inputStream.readBoolean();
            isGoldStauts = inputStream.readBoolean();
            goDownBall = inputStream.readBoolean();
            goRightBall = inputStream.readBoolean();
            colideToBreak = inputStream.readBoolean();
            colideToBreakAndMoveToRight = inputStream.readBoolean();
            colideToRightWall = inputStream.readBoolean();
            colideToLeftWall = inputStream.readBoolean();
            colideToRightBlock = inputStream.readBoolean();
            colideToBottomBlock = inputStream.readBoolean();
            colideToLeftBlock = inputStream.readBoolean();
            colideToTopBlock = inputStream.readBoolean();


            System.out.println("Attempting to read blocks...");
            try {
                blocks = (ArrayList<BlockSerializable>) inputStream.readObject();
                System.out.println("Blocks read successfully. Total blocks: " + blocks.size());
            } catch (ClassNotFoundException e) {
                System.out.println("Error reading blocks: " + e.getMessage());
                e.printStackTrace();
            }

        } catch (IOException e) {
                System.out.println("IO Exception while reading saved game: " + e.getMessage());
                e.printStackTrace();
            }
        System.out.println("Finished reading saved game data.");

    }
}
