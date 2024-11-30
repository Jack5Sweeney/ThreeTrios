package provider.src.threetrios.model;

import java.util.ArrayList;
import java.util.List;

import provider.src.threetrios.controller.ModelStatusListener;

/**
 * Model implementation for a Game of Three trios.
 */
public class ThreeTriosModel implements Model {

  private List<Card> redHand;
  private List<Card> blueHand;
  private List<Card> deck;
  private Board board;


  private boolean gameStarted = false;
  private boolean playerRedturn;
  private int numberOfCardCells;


  private List<ModelStatusListener> listeners;

  /**
   * When you make a model, initialize a deck, and a hand for red/blue player.
   */
  public ThreeTriosModel() {
    this.deck = new ArrayList<>();
    this.redHand = new ArrayList<>();
    this.blueHand = new ArrayList<>();
    this.listeners = new ArrayList<>();
  }

  @Override
  public void startGame(int rows, int columns, String holesAndCells, String cardFile) {
    if (gameStarted) {
      throw new IllegalStateException("cannot start game, game has already started.");
    }
    if (holesAndCells == null) {
      throw new IllegalArgumentException("given card grid holesandcells cannot be null");
    }
    if (cardFile == null) {
      throw new IllegalArgumentException("given card file string cardFile cannot be null");
    }
    if (rows * columns != holesAndCells.length()) {
      throw new IllegalArgumentException("given dimensions and grid string do not " +
              "match dimensions");
    }

    board = new ThreeTriosBoard(rows, columns, holesAndCells);

    numberOfCardCells = board.setUp();

    fillDeck(cardFile);

    for (Card card : deck) {
      if (card.getColor() == TeamColor.RED) {
        redHand.add(card);
      } else if (card.getColor() == TeamColor.BLUE) {
        blueHand.add(card);
      }
    }

    gameStarted = true;
    playerRedturn = true;
  }

  private void fillDeck(String cardFile) {
    // for however many cardcells you have + 1, make a card with info from the read file.
    String[] splitCardFile = cardFile.split("\n");

    int cardEvenCounter = 0;
    int cardCounter = 0;

    for (String cardString : splitCardFile) {

      if (cardCounter <= numberOfCardCells) {

        String[] splitCard = cardString.split(" ");

        if (cardEvenCounter % 2 == 0) {
          deck.add(new ThreeTriosCardAndColor(Integer.valueOf(splitCard[1]),
                  Integer.valueOf(splitCard[2]),
                  Integer.valueOf(splitCard[3]), Integer.valueOf(splitCard[4]),
                  TeamColor.RED, splitCard[0]));
        } else {
          deck.add(new ThreeTriosCardAndColor(Integer.valueOf(splitCard[1]),
                  Integer.valueOf(splitCard[2]),
                  Integer.valueOf(splitCard[3]), Integer.valueOf(splitCard[4]),
                  TeamColor.BLUE, splitCard[0]));
        }

        cardEvenCounter++;
        cardCounter++;
      }
    }


  }

  @Override
  public void placeCard(int rows, int columns, Card card) {
    if (card == null) {
      throw new IllegalArgumentException("cannot place a null card");
    }
    if (!gameStarted) {
      throw new IllegalStateException("cannot place card, the game has not started yet.");
    }
    if (board.getCardAt(rows, columns) == CellType.HOLE) {
      throw new IllegalArgumentException("Cannot place card on a hole! Inside of placeCard");
    }
    if (board.getCardAt(rows, columns) instanceof Card) {
      System.err.println("this is where you are trying to illegally place your card:" +
              board.getCardAt(rows, columns));
      throw new IllegalArgumentException("Cannot place card on another card");
    }


    // Class invariant: If playerRedTurn, only color red cards from redHand can be placed.
    // And when it is not playerRedTurn, only color blue cards from blueHand can be placed.
    if (playerRedturn && card.getColor() == TeamColor.RED && redHand.contains(card)) {
      board.setCardAt(rows, columns, card);
      redHand.remove(card);
    }

    // Class invariant: If playerRedTurn, only color red cards from redHand can be placed.
    // And when it is not playerRedTurn, only color blue cards from blueHand can be placed.
    else if (!playerRedturn && card.getColor() == TeamColor.BLUE && blueHand.contains(card)) {
      board.setCardAt(rows, columns, card);
      blueHand.remove(card);
    }
  }

  @Override
  public void flipCard(Card card) {
    if (!gameStarted) {
      throw new IllegalStateException("cannot flip card, the game has not started yet.");
    }
    if (card == null) {
      throw new IllegalArgumentException("cannot flip a null card");
    }
    card.flip();
  }

  @Override
  public boolean isGameOver() {
    if (!gameStarted) {
      throw new IllegalStateException("cannot flip card, the game has not started yet.");
    }
    // return !board.boardToString().contains("CARDCELL");

    return redHand.isEmpty() || blueHand.isEmpty();
  }

  @Override
  public boolean redPlayerWinsModel() {
    if (!gameStarted) {
      throw new IllegalStateException("cannot checkwinner, the game has not started yet.");
    }
    if (!isGameOver()) {
      throw new IllegalStateException("cannot checkwinner, the game is not over yet.");
    }

    return board.redPlayerWins();
  }

  @Override
  public int getGridSize() {
    return board.getRow() * board.getCol();
  }

  @Override
  public Object getCardAt(int row, int col) {
    return board.getCardAt(row, col);
  }

  @Override
  public TeamColor whoOwnsCardAt(int row, int col) {
    Object cell = board.getCardAt(row, col);
    if (cell == CellType.CARDCELL || cell == CellType.HOLE) {
      throw new IllegalArgumentException("you are not checking ownership of a valid card location");
    }

    return ((Card) cell).getColor();
  }

  @Override
  public int getPlayerScore(TeamColor teamColor) {

    int handSize = 0;
    if (teamColor == TeamColor.RED) {
      handSize = redHand.size();
    } else if (teamColor == TeamColor.BLUE) {
      handSize = blueHand.size();
    }

    return board.getPlayerScore(teamColor) + handSize;
  }

  @Override
  public int adjacentBattle(Card card, int row, int col, boolean flipCard,
                            int howManyCardsCouldFlip) {
    if (!gameStarted) {
      throw new IllegalStateException("cannot adjacent battle card, the game has not started yet.");
    }
    if (card == null) {
      throw new IllegalArgumentException("cannot adjacent battle a null card");
    }

    int cardWeAreComparingTo = 1;
    List<Card> cardsToFlip = new ArrayList<>();

    List<Object> adjacentCards;

    if (flipCard) {
      adjacentCards = board.getAdjacentCards(card);
    } else {
      adjacentCards = board.getAdjacentCards(row, col);
    }

    for (Object adjacentCard : adjacentCards) {
      if (adjacentCard == CellType.HOLE || adjacentCard == CellType.CARDCELL) {
        // do not do anything, you cannot battle a hole or a cardcell,
        // move onto the next object in your adjacent cards list
      } else {

        if (card.getColor() == ((Card) adjacentCard).getColor()) {
          // do not do anything, you cannot battle a card that is on your same team, so ignore this
          // and move onto the next object in your adjacent card list
        } else { // the adjacent item is a card, and it has the opposite color
          if (battleHelper(card, (Card) adjacentCard, cardWeAreComparingTo)) {
            cardsToFlip.add((Card) adjacentCard);
          }
        }
      }
      cardWeAreComparingTo++;
    }
    // for every card in cards to flip, flip them
    for (Card toBeFlipped : cardsToFlip) {
      // Issue we are trying to prevent, flipping original card
      if (toBeFlipped.getColor() != card.getColor()) {
        if (flipCard) {
          toBeFlipped.flip();
        }
        howManyCardsCouldFlip++;
      }
    }
    // for every card in cards to flip, call adjacent battle on them
    for (Card toBattle : cardsToFlip) {
      adjacentBattle(toBattle, row, col, flipCard, howManyCardsCouldFlip);
    }
    return howManyCardsCouldFlip;
  }


  // true means, our card won, and adjacent should be flipped
  private boolean battleHelper(Card card, Card adjacentCard, int cardWeAreComparingTo) {
    if (cardWeAreComparingTo == 1) { // comparing card.north to adjacentCard.south
      return card.getNorthValue() > adjacentCard.getSouthValue();
    } else if (cardWeAreComparingTo == 2) {
      return card.getEastValue() > adjacentCard.getWestValue();
    } else if (cardWeAreComparingTo == 3) {
      return card.getSouthValue() > adjacentCard.getNorthValue();
    } else if (cardWeAreComparingTo == 4) {
      return card.getWestValue() > adjacentCard.getEastValue();
    }

    // you should neva get here
    return false;
  }

  @Override
  public List<Card> getRedHand() {
    return new ArrayList<>(redHand);
  }

  @Override
  public List<Card> getBlueHand() {
    return new ArrayList<>(blueHand);
  }

  @Override
  public String getBoard() {
    return board.boardToString();
  }

  @Override
  public Object[][] getBoardArray() {
    return board.getBoardArray();
  }

  @Override
  public boolean getTurn() {
    return playerRedturn;
  }

  @Override
  public void changeTurn() {
    playerRedturn = !playerRedturn;
  }


  @Override
  public void addListener(ModelStatusListener listener) {
    this.listeners.add(listener);
  }

  @Override
  public void updateListeners() {
    //
  }

  @Override
  public void setBoard(Object[][] board) {
    this.board.setBoard(board);
  }
}
