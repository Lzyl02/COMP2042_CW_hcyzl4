# Project Title

## Table of Contents
- Compilation Instructions
- Implemented and Working Properly
- Implemented but Not Working Properly
- Features Not Implemented
- New Java Classes
- Modified Java Classes
- Unexpected Problems
  
## Compilation Instructions

### Prerequisites
- **Java Development Kit**: Ensure JDK 21 is installed on system. Download it from [Oracle Java SE Downloads](https://www.oracle.com/java/technologies/javase-downloads.html) or another vendor.
- **IntelliJ IDEA**: The project is configured to use IntelliJ IDEA. Download and install it from [JetBrains' official website](https://www.jetbrains.com/idea/download/).

### Project Setup in IntelliJ IDEA

#### Cloning the Repository
1. Obtain the URL of GitHub repository.
2. Use the command `git clone [https://github.com/Lzyl02/COMP2042_CW_hcyzl4.git]`, to clone it to local system.

#### Opening and Configuring the Project
1. Launch IntelliJ IDEA.
2. Select `Open or Import` and navigate to the cloned project directory.
3. Open the project by clicking on the `pom.xml` file and selecting `Open as Project`.

#### Configuring JDK for the Project
1. Go to `File` > `Project Structure` > `SDKs`.
2. Click the `+` button, select `JDK`, and navigate to the JDK 21 installation path.
3. Apply the changes.
<img width="836" alt="截屏2023-12-05 17 34 23" src="https://github.com/Lzyl02/COMP2042_CW_hcyzl4/assets/87651207/3354bab2-933f-4b8f-a967-a96e715e46cf">

#### Setting Project JDK and Language Level
1. Navigate to `File` > `Project Structure` > `Project`.
2. Choose the configured JDK 21 as the `Project SDK`.
3. Set `Language level` to 19.<img width="825" alt="截屏2023-12-05 17 34 15" src="https://github.com/Lzyl02/COMP2042_CW_hcyzl4/assets/87651207/7ab0b680-11de-49ff-93dc-610b4960ea18">


#### Adding JavaFX Library to the Project
1. Navigate to `File` > `Project Structure` > `Libraries`.
2. Click the `+` button and select `Java`.
3. Navigate to the JavaFX SDK `lib` directory, select all JAR files, and add them to the project.

#### Maven Configuration
1. Ensure that your `pom.xml` file is present at the root of the project directory.
2. IntelliJ IDEA should automatically recognize the Maven project and load its dependencies.

### Building and Running the Project
1. Use the Maven Projects tool window in IntelliJ IDEA and run the `clean` and `install` lifecycle phases to build the project.
2. Right-click the `src/main/java` directory and select `Run 'All Tests'` to compile and execute the application.


## Implemented and Working Properly
List and describe the features that are successfully implemented and functioning correctly.

### Feature 1
- Brief description of Feature 1.

### Feature 2
- Brief description of Feature 2.

(Continue listing other features...)

## Implemented but Not Working Properly
Enumerate the features that are implemented but not functioning correctly. Describe the issues and any troubleshooting steps attempted.

### Feature X
- Description of the issue.
- Steps taken to address it.

(Continue listing other features with issues...)

## Features Not Implemented
Identify and explain the features that were not implemented, with reasons for their exclusion.

### Feature Y
- Reason for non-implementation.

(Continue listing other unimplemented features...)

## New Java Classes
Enumerate and describe the new Java classes introduced in the project.

### Bombs Class
#### Description and Purpose
The `Bombs` class represents a bomb in a brick game. It manages the bomb's position, falling speed, and state. This class facilitates the creation of the bomb, its falling movement, and status checks to ensure it is handled correctly. It also implements the `Serializable` interface for object serialization.

#### Location in the Code
Located in the `brickGame` package, the `Bombs` class uses JavaFX's `Circle` and `ImagePattern` for graphical representation and implements `Serializable` for data persistence.

#### Methods and Properties
- `fallDown()`: Simulates the bomb's falling motion.
- `draw()`: Renders the bomb's graphical representation.
- `getCircle()`: Returns the bomb's visual form.
- Other getters and setters for bomb properties.

(Continue listing other new classes...)



## Modified Java Classes

### Block Class
- **Description of Changes**:
  1. **Collision Detection Improvement**: Enhanced the `checkHitToBlock` method for more accurate collision detection between the ball and block. The new implementation considers the position and dimensions of both the ball and block, as well as the direction of collision.
  2. **Graphical Representation Enhancement**: Modified the `draw` method to improve how blocks are rendered. The method now initializes the `Rectangle` object more efficiently and applies images to the blocks based on their type, using the `setBlockImage` method for better image handling.
  3. **Image Loading Error Handling**: Introduced error handling in the `setBlockImage` method. This method now checks for image loading errors, prints an error message if an issue occurs, and handles exceptions to prevent crashes.
  4. **Additional Block Types**: Added new block types (`BLOCK_DAEMON` and others) to the class, allowing for a greater variety of blocks in the game.
  5. **Bomb Generation Flag**: Introduced a `hasGeneratedBomb` boolean variable to track whether a bomb has been generated from a block.
  
- **Reason for Modifications**:
  1. **Collision Detection Improvement**: To provide a more realistic and responsive gaming experience by accurately detecting collisions from all sides of the block.
  2. **Graphical Representation Enhancement**: To optimize the creation of block graphics and allow for more diverse visual representation of different block types.
  3. **Image Loading Error Handling**: To ensure robustness in image loading and prevent runtime errors or crashes due to missing or corrupt image files.
  4. **Additional Block Types**: To enhance the game's variety and challenge by introducing new block types with different behaviors or effects.
  5. **Bomb Generation Flag**: To manage the game's logic related to bomb generation, ensuring that bombs are generated in a controlled manner.

### BlockSerializable Class
- **Description of Changes**:
  1. **Documentation Enhancement**: Added comprehensive documentation to the class and its constructor. This documentation provides a clear description of the class purpose, its fields, and the parameters of the constructor.
  2. **Code Readability Improvement**: Although the core functionality of the class remains unchanged, the addition of comments significantly improves the readability and maintainability of the code.

- **Reason for Modifications**:
  1. **Documentation Enhancement**: To ensure that other developers or users of the code can easily understand the purpose and usage of the `BlockSerializable` class. Good documentation is crucial for effective team collaboration and future maintenance of the code.
  2. **Code Readability Improvement**: Enhancing the readability of the code makes it easier for others (and for your future self) to review, maintain, and potentially extend the functionality of the class. Clear and descriptive comments are an integral part of writing maintainable code.


### Bonus Class
- **Description of Changes**:
  1. **Refactoring Variable Declaration**: Changed the scope of the `fallingSpeed` variable from `public` to `private` and modified the visibility of `x` and `y` coordinates from `public` to `private` and `package-private` respectively. This change improves data encapsulation.
  2. **Optimization of Image URL Selection**: Simplified the random selection logic for choosing between "bonus1.png" and "bonus2.png". The new implementation utilizes a more streamlined ternary operator, enhancing code readability and efficiency.
  3. **Additional Console Logging**: Added a console log statement in the constructor to output the creation position of each `Bonus` instance. This aids in debugging and tracking object creation.
  4. **Minor Code Cleanup**: Removed unnecessary initializations (like `timeCreated` variable) and organized the code for better readability.

- **Reason for Modifications**:
  1. **Refactoring Variable Declaration**: To adhere to good object-oriented programming practices by properly encapsulating the class properties, thereby making the class more secure and robust.
  2. **Optimization of Image URL Selection**: To streamline the code for selecting the bonus item's image, making it more concise and readable.
  3. **Additional Console Logging**: To provide real-time feedback during development and debugging, helping to identify and fix issues related to the creation and positioning of bonus items.
  4. **Minor Code Cleanup**: To enhance the clarity and maintainability of the code by removing redundant elements and organizing the structure more logically.

### GameEngine Class
- **Description of Changes**:
  1. **Thread Management**: Replaced individual `Thread` objects (`updateThread`, `physicsThread`, `timeThread`) with an `ExecutorService` thread pool for better thread management and control. This change allows for more efficient and safer handling of multiple threads.
  2. **Enhanced Thread Safety**: Modified some class variables (e.g., `fps`, `isStopped`) to improve thread safety and synchronization. The `volatile` keyword was added to `isStopped` to ensure visibility of its changes across threads.
  3. **Method Refactoring**: The game's update, physics calculation, and time tracking functionalities have been refactored into separate methods (`Update`, `PhysicsCalculation`, `TimeStart`), improving code organization and readability.
  4. **Additional Logging**: Added console log statements to provide more information during the game engine's operation, aiding in debugging and monitoring.
  5. **Improved FPS Handling**: Changed the calculation for `fps` in the `setFps` method for better accuracy and readability.
  6. **New Methods**: Added new methods like `stopAndWait` and `restart` for enhanced control over the game engine's lifecycle.

- **Reason for Modifications**:
  1. **Thread Management**: To optimize performance and reliability by using a thread pool, which provides better resource management compared to manually managing individual threads.
  2. **Enhanced Thread Safety**: To ensure that updates to shared variables are properly visible to all threads, preventing potential synchronization issues.
  3. **Method Refactoring**: To organize the code more logically, making it easier to understand and maintain.
  4. **Additional Logging**: To facilitate easier tracking of the game engine's state and behavior during execution, which is particularly useful for identifying and resolving issues.
  5. **Improved FPS Handling**: To ensure the frame rate setting is more intuitive and correctly implemented.
  6. **New Methods**: To provide additional functionality for managing the game engine, allowing for smoother start, stop, and restart operations.

### LoadSave Class
- **Description of Changes**:
  1. **Addition of Game Model References**: Introduced references to `GameModel` and `GameController` classes, allowing for a more integrated approach to loading saved game data into the current game state.
  2. **Enhanced Console Logging**: Added detailed console log statements throughout the `read` method. These logs provide real-time feedback on the loading process, such as reading levels, scores, and block data, enhancing the debuggability and transparency of the loading process.
  3. **Error Handling Improvements**: Implemented more robust error handling for the reading of blocks within the `read` method. This includes catching and logging `ClassNotFoundException` specifically for block data, providing clearer insights into potential issues encountered during deserialization.
  4. **File Path Reference Change**: Updated the file path reference from `Main.savePath` to `GameModel.savePath`, aligning the save file path with the game model's context.

- **Reason for Modifications**:
  1. **Addition of Game Model References**: To facilitate better interaction with the game's core logic and state management by directly integrating the loading mechanism with the game model and controller.
  2. **Enhanced Console Logging**: To improve the observability of the game's state loading process, aiding in quickly identifying and addressing any issues or anomalies encountered during deserialization.
  3. **Error Handling Improvements**: To provide more detailed and specific feedback in case of errors during the deserialization process, especially concerning the loading of block data.
  4. **File Path Reference Change**: To ensure consistency and coherence in file path references across the game's codebase, thereby reducing the likelihood of errors related to file access.

### Main Class
- **Description of Changes**:
  1. **Refactoring Game Components**: The functionality within the `Main` class has been distributed to separate classes: `GameModel`, `GameView`, and `GameController`. This modular approach separates concerns and makes the codebase more organized and maintainable.
  2. **Simplification of `start` Method**: The `start` method in the `Main` class has been streamlined to focus on initializing the JavaFX application. The detailed game logic and component initializations have been moved to their respective classes.
  3. **Removal of Direct Game Logic**: Direct handling of game logic, such as movement handling, ball initialization, and collision detection, has been removed from the `Main` class. This logic is now handled in the dedicated `GameModel`, `GameView`, and `GameController` classes.
  4. **Scene and Event Handling**: The `Main` class now primarily sets up the JavaFX scene and delegates event handling to the `GameController`.

- **Reason for Modifications**:
  1. **Refactoring Game Components**: To adhere to the principles of clean architecture, making the code easier to read, debug, and maintain. Separating concerns allows for independent development and testing of each component.
  2. **Simplification of `start` Method**: To enhance clarity and focus of the `Main` class, making it responsible only for initiating the JavaFX application and delegating the game's core functionality to the respective classes.
  3. **Removal of Direct Game Logic**: To decouple the game's logic from the application's entry point, allowing for a more structured and scalable codebase.
  4. **Scene and Event Handling**: To streamline the `Main` class as the entry point of the application, focusing on graphical user interface setup and interaction handling.

### Score Class
- **Description of Changes**:
  1. **Class Removal**: The `Score` class has been completely removed from the project.
  2. **Functionality Redistribution**: The functionalities previously encapsulated in the `Score` class, such as displaying scores, game over messages, and win messages, have been redistributed to the new `view` and `model` classes.

- **Reason for Modifications**:
  1. **Class Removal**: To streamline the codebase by eliminating redundant or single-use classes, thereby simplifying the project's structure.
  2. **Functionality Redistribution**: To adhere to a more structured and organized MVC (Model-View-Controller) architecture. This change ensures that each aspect of the game's functionality is handled by the appropriate component, improving code maintainability and scalability.


## Unexpected Problems
Discuss any unexpected problems encountered during the project and how you addressed or attempted to resolve them.

### Problem 1
- Description of the problem.
- Solutions attempted.

(Continue listing other problems and solutions...)
