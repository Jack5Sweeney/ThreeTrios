package gameconsole;

import cardcomparison.FallenAce;
import cardcomparison.NormalComparisonStrategy;
import cardcomparison.Reverse;
import controller.Features;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;


import java.util.Scanner;

public class GameConsole implements IGameConsole {
  //index 0 is red features and index 1 is blue features.
  private final List<Features> features;

  public GameConsole() {
    this.features = new ArrayList<Features>();
  }

  public void runGame() {
    Scanner scanner = new Scanner(System.in);
    Thread commandThread = new Thread(() -> listenForCommands(scanner));
    commandThread.start();
  }

  public void listenForCommands(Scanner scanner) {
    while (true) {
      String command = scanner.nextLine();
      processCommand(command);
    }
  }

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
    }
    if(parts.length == 1) {
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
        case "resetrules":
          this.features.get(0).setVariantRule(new NormalComparisonStrategy());
          this.features.get(1).setVariantRule(new NormalComparisonStrategy());
          break;
        default:
          System.out.println("Unknown Command: " + command);
      }
    }
  } catch (IllegalArgumentException e) {
    System.out.println("Invalid command format. Use: 'enableHints red' or 'disableHints blue'\n " +
        "or 'reverse', 'fallenAce', 'reverse fallenAce'");
  }
  }

  @Override
  public void addFeatures(Features features) {
    this.features.add(features);
  }
}
