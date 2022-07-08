package spw4.game2048;

import java.util.Arrays;
import java.util.stream.IntStream;

public class BoardUtilImpl implements BoardUtil {
  GameImpl.Tile[][] board;

  public BoardUtilImpl(GameImpl.Tile[][] board) {
    this.board = board;
  }

  @Override
  public boolean isWon() {
    for (int row = 0; row < GameImpl.TILE_COUNT; row++) {
      for (int col = 0; col < GameImpl.TILE_COUNT; col++) {
        if (board[row][col] != null && board[row][col].getValue() == 2048)
          return true;
      }
    }
    return false;
  }

  @Override
  public boolean isFull() {
    for (int row = 0; row < GameImpl.TILE_COUNT; row++) {
      for (int col = 0; col < GameImpl.TILE_COUNT; col++) {
        if (board[row][col] == null)
          return false;
      }
    }
    return true;
  }

  public void clear() {
    Arrays.stream(board).forEach(x -> Arrays.fill(x, null));
  }
}
