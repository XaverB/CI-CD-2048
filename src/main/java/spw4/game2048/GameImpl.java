package spw4.game2048;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class GameImpl implements Game {

  public class Tile {

    private int row;
    private int col;
    private int value;
    private int lastMergedInRound = -1;

    public Tile(int value) {
      this.value = value;
    }

    public Tile(int value, int row, int col) {
      this.value = value;
      board[row][col] = this;
      this.row = row;
      this.col = col;
    }

    public void move(Direction direction) {

      boolean continueMove = true;

      int rowDelta = 0;
      int colDelta = 0;

      switch (direction) {

        case up -> {
          rowDelta--;
        }
        case down -> {
          rowDelta++;

        }
        case left -> {
          colDelta--;

        }
        case right -> {
          colDelta++;

        }
      }

      // right
      do {

        if (row + rowDelta >= TILE_COUNT || col + colDelta >= TILE_COUNT
                || row + rowDelta < 0 || col + colDelta < 0) {
          break;
        }


        Tile neighbour = board[row + rowDelta][col + colDelta];
        if (neighbour == null) {
          // move
          board[row][col] = null;
          row += rowDelta;
          col += colDelta;
          board[row][col] = this;

        } else {
          boolean mergeAllowed = this.lastMergedInRound < movesCounter && neighbour.lastMergedInRound < movesCounter;
          if (neighbour.equals(this) && mergeAllowed) {
            board[neighbour.row][neighbour.col] = null;
            neighbour = null;
            this.value *= 2;
            score += this.value;
            this.lastMergedInRound = movesCounter;

          } else {
            continueMove = false;
          }
        }
      }
      while (continueMove);

    }

    public void merge(Tile tile) {
      tile.value = tile.value * 2;
      board[row][col] = null;
    }

    public boolean isEmpty() {
      return value == 0;
    }

    public int getRow() {
      return row;
    }

    public void setRow(int row) {
      this.row = row;
    }

    public int getCol() {
      return col;
    }

    public void setCol(int col) {
      this.col = col;
    }

    public int getValue() {
      return value;
    }

    public void setValue(int value) {
      this.value = value;
    }

    @Override
    public boolean equals(Object o) {
      if (o == this) {
        return true;
      }
      if (!(o instanceof Tile)) {
        return false;
      }
      Tile t = (Tile) o;
      return this.value == t.value;
    }

    @Override
    public String toString() {
      return new String(value + " " + row + " " + col);
    }

    public int getLastMergedInRound() {
      return lastMergedInRound;
    }

    public void setLastMergedInRound(int lastMergedInRound) {
      this.lastMergedInRound = lastMergedInRound;
    }
  }

  /**
   * Index range for board access row is from 0 - 3
   */
  public static final int TILE_COUNT = 4;
  public static final int WIN_VALUE = 2048;
  private int score = 0;
  private int movesCounter = 0;
  private boolean isOver = false;

  protected Tile[][] board = new Tile[TILE_COUNT][TILE_COUNT];

  GameRandomGenerator generator;
  BoardUtil boardUtil;

  public GameImpl() {
    generator = new GameRandomGeneratorImpl();
    boardUtil = new BoardUtilImpl(board);
  }


  public GameImpl(GameRandomGenerator generator) {
    this.generator = generator;
    boardUtil = new BoardUtilImpl(board);
  }

  public GameImpl(BoardUtil boardUtil) {
    this.generator = new GameRandomGeneratorImpl();
    this.boardUtil = boardUtil;
  }

  /**
   * Initializes the internal board state with two valid tiles.
   * All other tiles are filled with 0 (=empty)
   */
  public void initialize() {
    boardUtil.clear();

    int setTileCount = 0;
    do {
      int row = generator.generateIndex();
      int col = generator.generateIndex();
      int tileValue = generator.generateTile();

      if (!isTileEmpty(row, col))
        continue;

      board[row][col] = new Tile(tileValue, row, col);
//      set(row, col, tileValue);
      setTileCount++;

    } while (setTileCount < 2);
  }

  public int getMoves() {
    return movesCounter;
  }

  public int getScore() {
    return score;
  }

  public int getValueAt(int row, int col) {
    checkBounds(row, col);
    return board[row][col] == null ? 0 : board[row][col].getValue();
  }


  public boolean isOver() {
    return isOver;
  }

  public boolean isWon() {
    return boardUtil.isWon();
  }

  public void move(Direction direction) {
    movesCounter++;
    switch (direction) {
      case up -> {
        for (int currentColumn = 0; currentColumn < TILE_COUNT; currentColumn++) {
          Tile toppest = null;
          for (int i = 0; i < TILE_COUNT; i++) {
            if (board[i][currentColumn] != null) {
              toppest = board[i][currentColumn];
              toppest.move(direction);
            }
          }
        }
      }
      case down -> {
        for (int currentColumn = 0; currentColumn < TILE_COUNT; currentColumn++) {
          Tile lowest = null;
          for (int i = TILE_COUNT - 1; i >= 0; i--) {
            if (board[i][currentColumn] != null) {
              lowest = board[i][currentColumn];
              lowest.move(direction);
            }
          }
        }
      }
      case left -> {
        for (int currentRow = 0; currentRow < TILE_COUNT; currentRow++) {
          Tile leftest = null;
          for (int i = 0; i < TILE_COUNT; i++) {
            if (board[currentRow][i] != null) {
              leftest = board[currentRow][i];
              leftest.move(direction);
            }
          }
        }
      }
      case right -> {
        // shift EACH row
        for (int currentRow = 0; currentRow < TILE_COUNT; currentRow++) {
          // we move right, so start with the rightest element
          // find the rightest element
          Tile rightest = null;
          for (int i = TILE_COUNT - 1; i >= 0; i--) {
            if (board[currentRow][i] != null) {
              rightest = board[currentRow][i];
              rightest.move(direction);
            }
          }
        }
      }
    }

    generateNewTile();
    return;
//
//    for(int currentRow = 0; currentRow < TILE_COUNT; currentRow++) {
//      int furthestEmptySpot = -1;
//      int currentNextNeighbour = -1;
//
//      // 1. find currentElement
//      for(int currentElement = 0; currentElement < TILE_COUNT; currentElement++) {
//
//        if(furthestEmptySpot == -1) {
//          for (int i = TILE_COUNT - 1; i >= 0; i--) {
//            if(board[currentRow][i] == 0) {
//              furthestEmptySpot = i;
//              break;
//            }
//          }
//        }
//
//        // [0, 2, 0, 2]
//        if(board[currentRow][currentElement] == 0)
//          continue;
//
//        // 2. find next neighbour
//        for (int i = currentElement+1; i < TILE_COUNT; i++) {
//          if(board[currentRow][i] == 0)
//            continue;
//          currentNextNeighbour = i;
//          break;
//        }
//
//        // we found the index from neighbour and from current element 😍
//
//        if(currentNextNeighbour == -1)
//          continue;
//
//        // 3. merge if equal
//        if(board[currentRow][currentElement] == board[currentRow][currentNextNeighbour]) {
//          board[currentRow][currentNextNeighbour] = 0;
//          if(furthestEmptySpot < currentNextNeighbour)
//            furthestEmptySpot = currentNextNeighbour;
//          currentNextNeighbour = -1;
//          board[currentRow][currentElement] = board[currentRow][currentElement] * 2;
//        }
//        // [0, 4, 1, 2]
//        // swap
//        board[currentRow][furthestEmptySpot] = board[currentRow][currentElement];
//        furthestEmptySpot = -1;
//        board[currentRow][currentElement] = 0;
//      }
//    }

    //Zeile ansehen [0,1,2,3][0]
    // ist Wert != 0, dann schaue auf nächsten Nachbar und merge
    // schiebe wert nach ganz außen (hilfsfunktion)
    //nächster Wert bis am ende


    //nächste Zeile
  }

  /**
   * Returns if the tile at row, col is empty (=value is 0)
   *
   * @param row
   * @param col
   * @return Returns true if the tile is empty, else false
   */
  private boolean isTileEmpty(int row, int col) {
    checkBounds(row, col);
//    return get(row, col) == 0;
    return board[row][col] == null ? true : board[row][col].getValue() == 0;
  }

  private void checkBounds(int row, int col) {
    if (row >= TILE_COUNT)
      throw new IndexOutOfBoundsException("Row index (" + row + ") is >= " + TILE_COUNT);

    if (col >= TILE_COUNT)
      throw new IndexOutOfBoundsException("Column index (" + col + ") is >= " + TILE_COUNT);
  }

  private void generateNewTile() {
    Tile newTile = null;

    if(boardUtil.isFull()) {
      isOver = true;
      return;
    }

    do {
      int row = generator.generateIndex();
      int col = generator.generateIndex();
      int tileValue = generator.generateTile();

      if (!isTileEmpty(row, col))
        continue;
      newTile = new Tile(tileValue, row, col);
    } while (newTile == null);

  }

  @Override
  public String toString() {
    StringBuilder boardString = new StringBuilder();
    boardString.append("---------------------------------\n");
    for (int row = 0; row < TILE_COUNT; row++) {
      for (int col = 0; col < TILE_COUNT; col++) {
        if (board[row][col] == null) {
          boardString.append("." + " | ");
        } else {
          boardString.append(board[row][col].getValue() + " | ");
        }
      }
      boardString.append("\n");
    }
    boardString.append("---------------------------------\n");
    return boardString.toString();
  }

//  private int index(int row, int col) {
//    return row * (TILE_COUNT) + col;
//  }
//
//  private int get(int row, int col) {
//    return board[index(row, col)];
//  }
//
//  private void set(int row, int col, int value) {
//    board[index(row, col)] = value;
//  }
}
