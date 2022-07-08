package spw4.game2048;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

class BoardUtilTest {

  GameImpl game;

  @BeforeEach
  void setup() {
    game = new GameImpl();
  }

  @Test
  void isFullWhenIsFullReturnsTrue() {
    GameImpl.Tile[][] board = new GameImpl.Tile[4][4];
    Arrays.stream(board).forEach(x -> Arrays.fill(x, game.new Tile(1, 0, 0)));
    BoardUtil sut = new BoardUtilImpl(board);

    assertTrue(sut.isFull());
  }

  @Test
  void isFullWhenIsEmptyReturnsFalse() {
    GameImpl.Tile[][] board = new GameImpl.Tile[4][4];
    BoardUtil sut = new BoardUtilImpl(board);
    assertFalse(sut.isFull());
  }

  @Test
  void isFullWhenIsNotFullReturnsFalse() {
    GameImpl.Tile[][] board = new GameImpl.Tile[4][4];
    Arrays.stream(board).forEach(x -> Arrays.fill(x,game.new Tile(1, 0, 0)));
    board[0][0] = null;
    BoardUtil sut = new BoardUtilImpl(board);

    assertFalse(sut.isFull());
  }

  @Test
  void cleanWhenIsNotEmpty() {
    GameImpl.Tile[][] board = new GameImpl.Tile[4][4];
    GameImpl.Tile[][] expectedBoard = new GameImpl.Tile[4][4];
    Arrays.stream(board).forEach(x -> Arrays.fill(x,  game.new Tile(1, 0, 0)));
    BoardUtil sut = new BoardUtilImpl(board);
    sut.clear();

    assertArrayEquals(expectedBoard, board);
  }

  @Test
  void isWonContainsWinValueReturnsTrue() {
    GameImpl.Tile[][] board = new GameImpl.Tile[4][4];
    Arrays.stream(board).forEach(x -> Arrays.fill(x, game.new Tile(1, 0, 0)));
    board[0][0] = game.new Tile(2048, 0, 3);
    BoardUtil sut = new BoardUtilImpl(board);

    assertTrue(sut.isWon());
  }

  @Test
  void isWonContainsNoWinValueReturnsFalse() {
    GameImpl.Tile[][] board = new GameImpl.Tile[4][4];
    Arrays.stream(board).forEach(x -> Arrays.fill(x, game.new Tile(1,0 ,0)));
    BoardUtil sut = new BoardUtilImpl(board);

    assertFalse(sut.isWon());
  }

}