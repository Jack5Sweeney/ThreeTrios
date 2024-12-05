package gameconsole;

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
    } else {
      System.out.println("Invalid command format. Use: 'enableHints red' or 'disableHints blue'");
    }
  } catch (IllegalArgumentException e) {
    System.out.println("Invalid command format. Use: 'enableHints red' or 'disableHints blue'");
  }
  }

  @Override
  public void addFeatures(Features features) {
    this.features.add(features);
  }
}
