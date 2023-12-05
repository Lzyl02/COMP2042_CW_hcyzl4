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


## New Java Classes

### Bombs Class
#### Description and Purpose
- The `Bombs` class in our brick game represents the bombs encountered during gameplay. It's primarily responsible for managing the bomb's position, falling speed, and state throughout the game. This class is essential for creating bomb objects, controlling their downward movement during the game, and checking their status to ensure proper interaction within the game environment. Additionally, it implements the `Serializable` interface, enabling object serialization which is critical for saving and loading game states or transmitting game data.

#### Location in the Code
- The `Bombs` class is part of the `brickGame` package. It utilizes JavaFX's `Circle` and `ImagePattern` classes for its graphical representation, which provides a visually appealing and intuitive understanding of the bombs in the game. The implementation of the `Serializable` interface in this class is crucial for data persistence, allowing game states to be saved and restored effectively.

#### Methods and Properties
- **fallDown()**: This method is responsible for simulating the bomb's falling motion. It updates the bomb's position, making it appear as if it's dropping down the screen, adding a dynamic element to the gameplay.
- **draw()**: A key method for rendering the bomb's graphical representation on the screen. It ensures that the bomb is displayed correctly, enhancing the visual aspect of the game.
- **getCircle()**: Returns the bomb's visual form as a `Circle` object, which is used for displaying the bomb in the game's GUI.
- **Other getters and setters**: These methods are used for accessing and modifying various properties of the bomb, such as its position, speed, and state, allowing for fine control over the bomb's behavior in the game.

### GameBonusController Class
#### Description and Purpose
- The `GameBonusController` class plays a central role in a brick-style game by controlling and managing game bonuses and bombs. It ensures seamless coordination between the game's model and view layers, updating game elements like bonuses and bombs in real-time. This class significantly enhances the game's interactive dynamics, making it more engaging for players.

#### Location in the Code
- Located within the `brickGame` package, this class interacts closely with other components of the game, such as `GameModel` for managing the game's state and `GameView` for the user interface. It utilizes JavaFX's `AnimationTimer` for real-time updates, ensuring a smooth and responsive gaming experience.

#### Methods and Properties
- **Constructor (`GameBonusController(GameModel model, GameView view)`)**: Initializes the controller with references to the game model and view.
- **initializeBonusUpdater()**: Sets up an `AnimationTimer` to regularly update the position and status of bonuses on the screen, handling their movement and removal as needed.
- **shouldRemoveBonus(Bonus bonus)**: Determines if a bonus should be removed from the game, based on its position and state.
- **initializeBombUpdater()**: Similar to bonuses, this method establishes another `AnimationTimer` for updating the position and state of bombs, including their removal from the game.
- Private fields like `bonusUpdater`, `model`, `view`, `score`, and `bombUpdater` for internal state management.

### GameCollisionController Class
#### Description and Purpose
- The `GameCollisionController` class is pivotal in managing collision interactions in a brick-style game. It handles collision detection between the ball and blocks, updating the game state and view accordingly. This class is crucial for maintaining the game's physical accuracy and responsiveness, enhancing the player's experience by providing realistic collision effects.

#### Location in the Code
- This class is part of the `brickGame` package. It works closely with `GameModel` for accessing and updating game state, and with `GameView` for reflecting changes in the user interface. The class efficiently handles the complexity of collision logic, thereby centralizing and simplifying collision management in the game.

#### Methods and Properties
- **Constructor (`GameCollisionController(GameModel model, GameView view)`)**: Initializes the controller with the game model and view.
- **handleBlockCollision()**: Detects and processes collisions between the ball and blocks, adjusting the game state as necessary.
- **processBlockHit(Block block, int hitCode)**: Manages the effects of a block hit, including updating the score and handling block destruction.
- **adjustBallDirectionAfterBlockHit(int hitCode)**: Adjusts the ball's direction after it hits a block, ensuring realistic bounce-back effects.
- **handleSpecialBlocks(Block block)**: Deals with unique effects triggered by hitting special blocks, such as changing ball appearance or game view styles.
- **updateScoreView(double x, double y, int score)**: Updates the score display on the game view.
- **handleBlockHit(Block block)**: Updates the visibility of blocks in the view when they are hit.
- **createAndDropBomb(Block block)**: Creates and drops a bomb from a hit block, updating both the model and the view.
- **handleChocoBlock(Block block)**: Handles specific actions when a chocolate block is hit, like creating a chocolate bonus.
- **changeBallAppearance(String appearance)** and **changeSceneStyleClass(String style)**: Methods for changing the visual appearance of the ball and the scene based on game events.
  
### GameController Class
#### Description and Purpose
- `GameController` serves as the central controller for a brick-style game. It integrates various aspects of game logic, handling user input, and coordinating with other controllers like `GameBonusController`, `GameCollisionController`, and `GameInitializationController`. This class is pivotal in managing the game flow, responding to user actions, and updating the game state and UI in real time.

#### Location in the Code
- Part of the `brickGame` package, `GameController` utilizes JavaFX libraries for UI interaction and game logic execution. It directly interacts with `GameModel` for game state management and `GameView` for UI updates.

#### Key Features and Methods
- **Event Handling**: Implements `EventHandler<KeyEvent>` for keyboard input, managing paddle movements, and game interactions like saving.
- **Game Engine Integration**: Works with `GameEngine` for continuous game updates and physics handling.
- **Game State Management**: Coordinates game state changes, including score updates, level transitions, and game restarts.
- **Collision Handling**: Delegates collision detection and handling to `GameCollisionController`.
- **Bonus and Bomb Management**: Manages game bonuses and bombs, coordinating with `GameBonusController` for real-time updates.
- **Game Initialization**: Utilizes `GameInitializationController` for setting up game levels, blocks, and initial settings.
- **User Input Response**: Handles paddle movements, game saving/loading, and other player interactions.
- **UI Updates**: Continuously updates the game UI based on the game state, including ball position, score, and special effects.

#### Constructor and Initialization
- Constructs the controller with the root pane, game view, and model, initializing all necessary components and controllers for game management.

### GameInitializationController Class
#### Description and Purpose
- `GameInitializationController` is responsible for the setup and initialization of a brick-style game. This includes configuring the game model, view, engine, and bonus controller. Its primary role is to prepare the game environment, including generating levels with blocks of various types, and setting initial positions for game elements like the paddle and ball.

#### Location in the Code
- Located within the `brickGame` package, this class interacts with `GameModel` for state management, `GameView` for UI setup, `GameEngine` for game loop control, and `GameBonusController` for managing game bonuses.

#### Key Methods and Functionalities
- **initializeBlocks(int newLevel)**: Generates blocks for each level of the game, with a specific layout and distribution based on the level number.
- **initializeGame()**: Sets up initial positions for the paddle and ball, and initializes blocks for the current level.
- **startGame()**: Starts the game by initializing the game state, views, and beginning the game engine process. This method is essential for kicking off the game's main loop and interactions.
- **determineBlockType(int randomNumber, Random random)**: Randomly determines the type of each block to be placed in the game, ensuring variety and unpredictability in block distribution.
- **determineSpecialBlockType(Random random)**: Specifically assigns special types to blocks, adding unique gameplay elements like extra points, extra lives, or power-ups.

#### Constructor
- Initializes the controller with references to the game model, view, engine, and bonus controller, setting the stage for a cohesive game setup.


### GameModel Class
#### Description and Purpose
- `GameModel` acts as the central data repository for a brick-style game, encapsulating all game-related state and logic. It includes key information such as the game's score, block information, paddle and ball positions, and other vital game state variables. This class serves as the backbone of the game, providing a centralized point of management for the game's state.

#### Key Features and Properties
- **Game Score**: Manages the player's score throughout the game.
- **Game Elements**: Maintains lists of game bonuses (`chocos`) and bombs, along with their behaviors and interactions.
- **Game Physics and Mechanics**: Controls the ball's movement, paddle position, and collision detection logic.
- **Game State Variables**: Includes variables for tracking game level, heart count, gold status, game over status, and other essential game states.
- **Color Array**: Defines a set of colors used for various game elements, enhancing visual diversity.
- **Game Dimensions**: Specifies the game scene width and height, influencing the layout and scale of the game.
- **Game Controllers**: Interacts with `GameController` for updating game logic and rendering changes.

#### Constructor
- Initializes the game model, setting default values for game properties and preparing the game for initial play.

#### Methods
- Provides a comprehensive set of getters and setters for manipulating game state, including methods for handling the game's physics, updating positions, and managing game elements like blocks, bonuses, and bombs.
- Includes methods for adding and clearing blocks, adjusting game levels, and handling game progression and game over scenarios.

### GameView Class
#### Description and Purpose
- `GameView` is responsible for managing and updating the graphical components of a brick-style game. It acts as the visual interface between the player and the game mechanics, rendering the game state onto the screen. The class interacts with `GameModel` for game state information and `GameController` for handling user events and actions.

#### Key Features and Elements
- **UI Component Management**: Handles the initialization and updating of graphical elements like the ball, paddle, score and heart labels, level indicators, and game control buttons.
- **Event Handling**: Integrates with `GameController` to respond to player inputs and game events.
- **Game Element Display**: Manages the visualization of game elements such as blocks, bonuses, and bombs, including their dynamic positioning and interactions.
- **Animation and Visual Effects**: Implements visual effects like score pop-ups and animations to enhance the player's experience.

#### Constructor and Initialization
- Constructs `GameView` with a root pane and `GameModel`, initializing UI components and setting up the game scene.

#### Methods
- **updatePaddleSize(double xPaddle, int newWidth)**: Adjusts the size of the paddle based on game events.
- **displayBlocks()**: Displays game blocks on the screen, based on the current level and game state.
- **updateBallPosition(double x, double y)**: Updates the position of the ball on the game screen.
- **showScoreLabel(double x, double y, int score)**: Displays a temporary score label at specified coordinates.
- **updateLevelLabel(int level)**: Updates the label displaying the current game level.
- **resetUIComponents()**: Resets and reinitializes UI components, particularly useful for game restarts or level changes.

#### Visual and Animation Functions
- **changeBallAppearance(String imageFileName)**: Changes the appearance of the ball, allowing for dynamic visual changes based on game events.
- **updateScoreAndHeart(int score, int heart)**: Updates the score and heart count displays based on the current game state.
- **showGameOver()** and **showWin()**: Display game over and win screens, providing feedback on the game's outcome.


### GameViewAnimation Class
#### Description and Purpose
- `GameViewAnimation` specializes in animating UI elements within the game view of a brick-style game. This class is responsible for adding visual flair to the game by animating elements such as labels with scaling and fading effects. It enhances the player's visual experience and provides dynamic feedback on in-game events.

#### Key Features
- **Animation of UI Elements**: Implements animations for various UI elements, focusing on labels. Uses scaling and fading effects to create a dynamic and visually appealing experience.
- **Thread Management**: Animations are executed on separate threads to ensure smooth operation without blocking the main UI thread.

#### Constructor
- Initializes `GameViewAnimation` with the root pane of the application, allowing the class to add or remove UI elements as part of the animation process.

#### Methods
- **animateLabel(Label label)**: Animates a given label with scaling and fading effects. This method creates a new thread for each animation to avoid UI disruptions, gradually increasing the label's size and decreasing its opacity before removing it from the view.


### GameViewDisplay Class
#### Description and Purpose
- `GameViewDisplay` is dedicated to handling the display of messages and notifications within the game view of a brick-style game. This class plays a crucial role in communicating game states and player achievements to the user, such as displaying temporary messages and win notifications on the game screen.

#### Key Features
- **Display of Game Messages**: Manages the presentation of various game messages and notifications, enhancing player interaction and feedback.
- **Temporary Message Display**: Implements functionality to show temporary messages at specified screen coordinates, which disappear after a predefined duration.
- **Win Message Display**: Specifically handles the display of a "You Win" message, indicating player success in a visually prominent manner.

#### Constructor
- Initializes `GameViewDisplay` with the root pane of the application, allowing the class to add messages and notifications directly to the game's UI.

#### Methods
- **showMessage(String message, double x, double y)**: Displays a temporary message at given screen coordinates, which auto-dismisses after a short duration. This method is vital for transient notifications like score updates or level completions.
- **showWin()**: Specifically displays a "You Win" message on the game screen, marking the player's victory. This method sets the position and size of the win message for optimal visibility and impact.


### GameViewInit Class
#### Description and Purpose
- `GameViewInit` is focused on initializing and managing the User Interface (UI) components for a brick-style game. Its primary role is to set up the visual elements of the game, including labels, buttons, and game objects like the ball and paddle (break). This class ensures that the game's visual elements are correctly configured and displayed according to the game's state.

#### Key Features and Elements
- **UI Component Initialization**: Sets up labels for score, hearts, and level, along with buttons for game control.
- **Game Object Visualization**: Initializes the visual representation of the ball and paddle, including their positions and styles.
- **Scene Styling**: Applies CSS styling to the game's root pane to enhance the visual appeal.
- **Block Display**: Manages the display of game blocks on the screen, dynamically adjusting for different game states and levels.

#### Constructor
- Initializes `GameViewInit` with a root pane and `GameModel`, laying the groundwork for UI component setup and configuration.

#### Methods
- **initUIComponents(boolean isRestart)**: Initializes the game's UI components, including labels and buttons. This method also handles UI re-initialization in case of game restarts.
- **initBallView(double xBall, double yBall, int ballRadius)** and **initBreakView(double xBreak, double yBreak, int breakWidth, int breakHeight)**: Set up the visual representation of the ball and paddle, placing them on the game screen with appropriate dimensions and styles.
- **displayBlocks()**: Displays all game blocks on the screen, with special handling for different block types, including daemon blocks.
- **updateLabels()**: Updates the text and positioning of score, heart, and level labels based on the current game state.

#### Accessors
- Provides getters for game control buttons (`loadGameButton` and `newGameButton`), labels (`scoreLabel`, `heartLabel`, `levelLabel`), and game objects (`ball`, `rect`) to enable interaction and updates from other components of the game.

### GameViewUpdate Class
#### Description and Purpose
- `GameViewUpdate` is specifically tasked with updating the graphical components of the game view in a brick-style game. Its role is vital in ensuring that the visual representation of the game elements like the ball, paddle, and various labels is accurately synchronized with the game's state as dictated by the `GameModel`.

#### Key Features and Responsibilities
- **Dynamic Updating of Game Elements**: Responsible for updating the positions and appearances of dynamic game elements such as the ball and paddle.
- **Label Management**: Updates score, heart, and level labels to reflect the current state of the game.
- **Visual Representation Adjustment**: Alters the appearance of the ball and paddle, including changes in size and color, based on specific game events.

#### Constructor
- Initializes `GameViewUpdate` with essential UI components, including the root pane, ball, paddle (rectangle), and labels for score, heart, and level.

#### Methods
- **updateBallPosition(double x, double y)**: Moves the ball to a new position on the screen.
- **updatePaddlePosition(double x, double y)**: Adjusts the paddle's position.
- **updateScoreAndHeart(int score, int heart)**: Reflects changes in the game's score and the player's heart count.
- **updatePaddleSize(double xPaddle, int newWidth)**: Modifies the paddle's size, typically in response to game events.
- **updateLevelLabel(int level)**: Updates the display of the current game level.
- **updateBlockVisibility(Block block, boolean isVisible)**: Changes the visibility of a specific block, useful for showing or hiding blocks as they are hit.
- **updateBonusPosition(Bonus bonus)**: Moves a bonus item on the screen.
- **updateBombPosition(Bombs bomb)**: Adjusts the position of a bomb.
- **changeBallAppearance(String imageFileName)**: Alters the ball's appearance based on the provided image file name.
- **updateBallAppearance(boolean isGoldStatus)**: Updates the ball's appearance to indicate a special status, such as a 'gold' mode.



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
