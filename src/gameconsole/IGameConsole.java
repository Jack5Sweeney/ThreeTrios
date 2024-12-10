package gameconsole;

import controller.Features;

import java.util.Scanner;

/**
 * Interface for a console-based game management system.
 */
public interface IGameConsole {

  /**
   * Runs the game, initializing necessary components and starting the command listener.
   */
  void runGame();

  /**
   * Listens for user commands from the console.
   * This method is typically invoked in a separate thread to handle user input concurrently.
   */
  void listenForCommands(Scanner scanner);

  /**
   * Processes a given command entered by the user.
   *
   * @param command the command string to process
   */
  void processCommand(String command);

  /**
   * Method to add features to this IGameController.
   * @param features features to add
   */
  void addFeatures(Features features);
}

