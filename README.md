# Customizable Game: ThreeTrios

Welcome to the **ThreeTrios** game! This project allows users to create, configure, and play a highly customizable card-based strategy game. The game supports multiple player types, including human and AI players, with strategies tailored for competitive gameplay.

---

## Table of Contents
1. [Game Overview](#game-overview)
2. [Creating a Customizable Game](#creating-a-customizable-game)
3. [How the Game Works](#how-the-game-works)
4. [Executing the Game](#executing-the-game)
5. [Player Types](#player-types)
   - [Human Player](#human-player)
   - [AI Players](#ai-players)
6. [AI Strategies](#ai-strategies)

---

## Game Overview
ThreeTrios is a strategy game where players take turns placing cards on a grid to gain points by flipping opponent cards. The game ends when no valid moves remain, and the player with the most flipped cards wins.

The game can be configured using external configuration files, making it easy to customize gameplay mechanics and rules.

---

## Creating a Customizable Game
To customize the game, follow these steps:

1. **Locate Configuration Files**:
   - Configuration files are located in the `docs` directory.
     - `board.config`: Defines the board size and layout.
     - `card.database`: Specifies available card types, values, and attributes.

2. **Edit Configuration Files**:
   - Open `board.config` to adjust the board dimensions (e.g., `3x3` or `5x5`). Example:
     ```
     BOARD_SIZE=3x3
     STARTING_CARDS=5
     MAX_TURNS=20
     ```
   - Edit `card.database` to define card attributes, including directions and values. Example:
     ```
     CARD_1: NORTH=2, EAST=3, SOUTH=1, WEST=0
     CARD_2: NORTH=0, EAST=1, SOUTH=3, WEST=2
     ```

3. **Save Changes**:
   - Ensure all changes are saved in the respective configuration files.

---

## How the Game Works
1. **Setup**:
   - The game initializes based on `board.config` and `card.database`.
   - Players receive a hand of cards to place on the board.

2. **Gameplay**:
   - Players take turns placing cards.
   - When a card is placed, it may flip adjacent opponent cards if the placed card’s values dominate.

3. **Winning Condition**:
   - The game ends when no valid moves remain or the turn limit is reached.
   - The player with the most flipped cards wins.

---

## Executing the Game
To execute the game, follow these steps:

1. **Build the JAR File**:
   - Ensure the project is compiled and packaged into a JAR file (e.g., `ThreeTrios.jar`).

2. **Run the Game**:
   - Use the following command to start the game:
     ```
     java -jar ThreeTrios.jar --players PLAYER1:PLAYER2
     ```
   - Replace `PLAYER1` and `PLAYER2` with the desired player types (e.g., `human`, `ai_flipmost`, `ai_corner`).

3. **Example**:
   - To play a game with one human and one AI using the "Flip The Most" strategy:
     ```
     java -jar ThreeTrios.jar --players human:ai_flipmost
     ```

---

## Player Types

### Human Player
- **Description**: Human players interact with the game via a graphical or text-based interface, manually selecting card placements.

### AI Players
- **Description**: AI players automatically decide their moves based on predefined strategies. This allows for dynamic and competitive gameplay without requiring human input.

---

## AI Strategies

### Flip The Most Strategy (`ai_flipmost`)
- **Purpose**: Maximizes the number of opponent cards flipped in a single turn.
- **Mechanics**:
  - Evaluates all possible moves.
  - Chooses the move that flips the maximum number of cards.
  - In case of a tie, selects the uppermost-leftmost position and the first card in hand.

### Corner Strategy (`ai_corner`)
- **Purpose**: Prioritizes placing cards in board corners to minimize the chance of being flipped.
- **Mechanics**:
  - Identifies corner positions.
  - Selects the move that minimizes risk based on opponent card values.
  - Resolves ties by selecting the uppermost-leftmost position and the first card in hand.

---

Enjoy customizing and playing ThreeTrios! If you encounter issues or have questions, please refer to the source code or contact the project maintainers.

