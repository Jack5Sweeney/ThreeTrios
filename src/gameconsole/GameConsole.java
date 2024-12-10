package gameconsole;

import cardcomparison.FallenAce;
import cardcomparison.NormalComparisonStrategy;
import cardcomparison.Reverse;
import controller.Features;
import flipcriteria.PlusRule;
import flipcriteria.SameRule;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.Scanner;

/**
 * The {@code GameConsole} class handles the game's command-line interface,
 * allowing players to interact with the game using typed commands.
 *
 * <p>This class manages commands such as enabling hints, setting variant rules, and
 * applying flip criteria like "Same" or "Plus". It uses {@link Features} to interact with
 * the game model and views.
 */
public class GameConsole implements IGameConsole {

  /**
   * List of {@link Features} for each player.
   * <p>Index 0 corresponds to the red player's features, and index 1 corresponds
   * to the blue player's features.</p>
   */
  private final List<Features> features;

  /**
   * Constructs a new {@code GameConsole}.
   */
  public GameConsole() {
    this.features = new ArrayList<>();
  }

  /**
   * Starts the game and begins listening for commands on a new thread.
   */
  public void runGame() {
    Scanner scanner = new Scanner(System.in);
    Thread commandThread = new Thread(() -> listenForCommands(scanner));
    commandThread.start();
  }

  /**
   * Continuously listens for commands from the player.
   *
   * @param scanner the {@link Scanner} instance to read player input
   */
  public void listenForCommands(Scanner scanner) {
    while (true) {
      String command = scanner.nextLine();
      processCommand(command);
    }
  }

  /**
   * Processes a single command from the player and executes the corresponding action.
   *
   * @param command the player's command
   */
  public void processCommand(String command) {
    try {
      Function<String, Features> stringToFeatures = color -> {
        switch (color.toLowerCase()) {
          case "red":
            return this.features.get(0);
          case "blue":
            return this.features.get(1);
          default:
            throw new IllegalArgumentException("Invalid player color: " + color);
        }
      };

      String[] parts = command.split(" ");
      if (parts.length == 2) {
        String action = parts[0];
        Features playerFeatures = stringToFeatures.apply(parts[1]);
        switch (action.toLowerCase()) {
          case "enablehints":
            playerFeatures.enableHints();
            break;
          case "disablehints":
            playerFeatures.disableHints();
            break;
          default:
            System.out.println("Unknown command: " + command);
        }
      } else if (parts.length == 1) {
        String action = parts[0].trim();
        switch (action.toLowerCase()) {
          case "reverse":
            this.features.get(0).setVariantRule(new Reverse());
            this.features.get(1).setVariantRule(new Reverse());
            break;
          case "fallenace":
            this.features.get(0).setVariantRule(new FallenAce(new NormalComparisonStrategy()));
            this.features.get(1).setVariantRule(new FallenAce(new NormalComparisonStrategy()));
            break;
          case "reversefallenace":
            this.features.get(0).setVariantRule(new FallenAce(new Reverse()));
            this.features.get(1).setVariantRule(new FallenAce(new Reverse()));
            break;
          case "same":
            this.features.get(0).setFlipCriteria(new SameRule());
            this.features.get(1).setFlipCriteria(new SameRule());
            System.out.println("Same rule activated.");
            break;
          case "plus":
            this.features.get(0).setFlipCriteria(new PlusRule());
            this.features.get(1).setFlipCriteria(new PlusRule());
            System.out.println("Plus rule activated.");
            break;
          case "resetrules":
            this.features.get(0).setVariantRule(new NormalComparisonStrategy());
            this.features.get(1).setVariantRule(new NormalComparisonStrategy());
            break;
          default:
            System.out.println("Unknown Command: " + command);
        }
      } else {
        System.out.println("Invalid command format. Use: 'enableHints red' or 'disableHints " +
                "blue'\n or 'reverse', 'fallenAce', 'reverse fallenAce', 'same', 'plus'.");
      }
    } catch (IllegalArgumentException e) {
      System.out.println("Invalid command format. Use: 'enableHints red' or 'disableHints blue'\n" +
              "or 'reverse', 'fallenAce', 'reverse fallenAce', 'same', 'plus'.");
    }
  }

  /**
   * Adds a {@link Features} instance to the list of features for a player.
   *
   * @param features the {@link Features} instance to add
   */
  @Override
  public void addFeatures(Features features) {
    this.features.add(features);
  }
}
