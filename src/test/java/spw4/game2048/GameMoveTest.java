package spw4.game2048;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class GameMoveTest {

  GameImpl sut;

  @Mock
  GameRandomGeneratorStub randomStub;

  @BeforeEach
  void setup() {
    randomStub = new GameRandomGeneratorStub(List.of(0), List.of(3, 3));
    sut = new GameImpl(randomStub);
  }

  @DisplayName("Game.move with direction right from [0,2,0,2] to [0,0,0,4]")
  @Test
  void moveRightDev1() {
    GameImpl.Tile[][] board = new GameImpl.Tile[GameImpl.TILE_COUNT][GameImpl.TILE_COUNT];
    // [0, 2, 0, 2]
    board[0][1] = sut.new Tile(2, 0, 1);
    board[0][3] = sut.new Tile(2, 0, 3);

    sut.board = board;
    sut.move(Direction.right);
    assertEquals(sut.new Tile(4, 0, 3), sut.board[0][3]);
    assertEquals(null, sut.board[0][0]);
    assertEquals(null, sut.board[0][1]);
    assertEquals(null, sut.board[0][2]);
  }

  @DisplayName("Game.move with direction right from [0,4,0,2] to [4,2,0,0]")
  @Test
  void moveRightDev2() {
    GameImpl.Tile[][] board = new GameImpl.Tile[GameImpl.TILE_COUNT][GameImpl.TILE_COUNT];
    // [0, 4, 0, 2]
    board[0][1] = sut.new Tile(4, 0, 1);
    board[0][3] = sut.new Tile(2, 0, 3);
    sut.board = board;
    sut.move(Direction.right);
    assertEquals(sut.new Tile(4), sut.board[0][2]);
    assertEquals(sut.new Tile(2), sut.board[0][3]);
    assertEquals(null, sut.board[0][0]);
    assertEquals(null, sut.board[0][1]);
  }


  @DisplayName("Game.move with direction right from [4,4,4,4] to [0,0,8,8]")
  @Test
  void moveRightDev3() {
    GameImpl.Tile[][] board = new GameImpl.Tile[GameImpl.TILE_COUNT][GameImpl.TILE_COUNT];

    board[2][0] = sut.new Tile(4, 2, 0);
    board[2][1] = sut.new Tile(4, 2, 1);
    board[2][2] = sut.new Tile(4, 2, 2);
    board[2][3] = sut.new Tile(4, 2, 3);
    sut.board = board;

    sut.move(Direction.right);

    assertEquals(null, sut.board[2][0]);
    assertEquals(null, sut.board[2][1]);
    assertEquals(sut.new Tile(8), sut.board[2][2]);
    assertEquals(sut.new Tile(8), sut.board[2][3]);

  }

  @DisplayName("Game.move with direction right from [2,4,2,4] to [2,4,2,4]")
  @Test
  void moveRightDev4() {
    GameImpl.Tile[][] board = new GameImpl.Tile[GameImpl.TILE_COUNT][GameImpl.TILE_COUNT];

    board[0][0] = sut.new Tile(2, 0, 0);
    board[0][1] = sut.new Tile(4, 0, 1);
    board[0][2] = sut.new Tile(2, 0, 2);
    board[0][3] = sut.new Tile(4, 0, 3);
    sut.board = board;
    sut.move(Direction.right);
    assertEquals(sut.new Tile(2), sut.board[0][0]);
    assertEquals(sut.new Tile(4), sut.board[0][1]);
    assertEquals(sut.new Tile(2), sut.board[0][2]);
    assertEquals(sut.new Tile(4), sut.board[0][3]);
  }

  @DisplayName("Game.move with direction right from [2,2,0,2] to [0,0,2,4]")
  @Test
  void moveRightDev5() {
    GameImpl.Tile[][] board = new GameImpl.Tile[GameImpl.TILE_COUNT][GameImpl.TILE_COUNT];

    board[0][0] = sut.new Tile(2, 0, 0);
    board[0][1] = sut.new Tile(2, 0, 1);
    board[0][3] = sut.new Tile(2, 0, 3);
    sut.board = board;
    sut.move(Direction.right);
    assertEquals(null, sut.board[0][0]);
    assertEquals(null, sut.board[0][1]);
    assertEquals(sut.new Tile(2), sut.board[0][2]);
    assertEquals(sut.new Tile(4), sut.board[0][3]);

  }

  @DisplayName("Game.move with direction left from [0,2,0,2] to [4,0,0,0]")
  @Test
  void moveLeftDev1() {
    GameImpl.Tile[][] board = new GameImpl.Tile[GameImpl.TILE_COUNT][GameImpl.TILE_COUNT];
    board[0][1] = sut.new Tile(2, 0, 1);
    board[0][3] = sut.new Tile(2, 0, 3);

    sut.board = board;
    sut.move(Direction.left);
    assertEquals(sut.new Tile(4), sut.board[0][0]);
    assertEquals(null, sut.board[0][1]);
    assertEquals(null, sut.board[0][2]);
    assertEquals(null, sut.board[0][3]);
  }

  @DisplayName("Game.move with direction left from [0,4,0,2] to [4,2,0,0]")
  @Test
  void moveLeftDev2() {
    GameImpl.Tile[][] board = new GameImpl.Tile[GameImpl.TILE_COUNT][GameImpl.TILE_COUNT];
    // [0, 4, 0, 2]
    board[0][1] = sut.new Tile(4, 0, 1);
    board[0][3] = sut.new Tile(2, 0, 3);
    sut.board = board;

    sut.move(Direction.left);

    assertEquals(sut.new Tile(4), sut.board[0][0]);
    assertEquals(sut.new Tile(2), sut.board[0][1]);
    assertEquals(null, sut.board[0][2]);
    assertEquals(null, sut.board[0][3]);
  }

  @DisplayName("Game.move with direction left from [4,4,4,4] to [8,8,0,0]")
  @Test
  void moveLeftDev3() {
    GameImpl.Tile[][] board = new GameImpl.Tile[GameImpl.TILE_COUNT][GameImpl.TILE_COUNT];

    board[2][0] = sut.new Tile(4, 2, 0);
    board[2][1] = sut.new Tile(4, 2, 1);
    board[2][2] = sut.new Tile(4, 2, 2);
    board[2][3] = sut.new Tile(4, 2, 3);
    sut.board = board;
    sut.move(Direction.left);

    assertEquals(sut.new Tile(8), sut.board[2][0]);
    assertEquals(sut.new Tile(8), sut.board[2][1]);
    assertEquals(null, sut.board[2][2]);
    assertEquals(null, sut.board[2][3]);

  }

  @DisplayName("Game.move with direction left from [2,4,2,4] to [2,4,2,4]")
  @Test
  void moveLeftDev4() {
    GameImpl.Tile[][] board = new GameImpl.Tile[GameImpl.TILE_COUNT][GameImpl.TILE_COUNT];
    // [2, 4, 2, 4]
    board[0][0] = sut.new Tile(2, 0, 0);
    board[0][1] = sut.new Tile(4, 0, 1);
    board[0][2] = sut.new Tile(2, 0, 2);
    board[0][3] = sut.new Tile(4, 0, 3);
    sut.board = board;
    sut.move(Direction.left);
    assertEquals(sut.new Tile(2), sut.board[0][0]);
    assertEquals(sut.new Tile(4), sut.board[0][1]);
    assertEquals(sut.new Tile(2), sut.board[0][2]);
    assertEquals(sut.new Tile(4), sut.board[0][3]);
  }

  @DisplayName("Game.move with direction left from [2,2,4,8] to [4,4,8,0]")
  @Test
  void moveLeftDev5() {
    GameImpl.Tile[][] board = new GameImpl.Tile[GameImpl.TILE_COUNT][GameImpl.TILE_COUNT];
    // 2 | 2 | 4 | 8 |
    board[0][0] = sut.new Tile(2, 0, 0);
    board[0][1] = sut.new Tile(2, 0, 1);
    board[0][2] = sut.new Tile(4, 0, 2);
    board[0][3] = sut.new Tile(8, 0, 3);
    sut.board = board;
    sut.move(Direction.left);

    assertEquals(sut.new Tile(4), sut.board[0][0]);
    assertEquals(sut.new Tile(4), sut.board[0][1]);
    assertEquals(sut.new Tile(8), sut.board[0][2]);
    assertEquals(null, sut.board[0][3]);
  }

  @DisplayName("Game.move with direction up from [2,0,2,0] to [4,0,0,0]")
  @Test
  void moveUpDev1() {
    GameImpl.Tile[][] board = new GameImpl.Tile[GameImpl.TILE_COUNT][GameImpl.TILE_COUNT];

    board[0][0] = sut.new Tile(2, 0, 0);
    board[2][0] = sut.new Tile(2, 2, 0);


    sut.board = board;

    sut.move(Direction.up);
    assertEquals(
            sut.new Tile(4),
            sut.board[0][0]
    );

    assertEquals(null, sut.board[1][0]);
    assertEquals(null, sut.board[2][0]);
    assertEquals(null, sut.board[3][0]);
  }

  @DisplayName("Game.move with direction up from [0,2,2,0] to [4,0,0,0]")
  @Test
  void moveUpDev2() {
    GameImpl.Tile[][] board = new GameImpl.Tile[GameImpl.TILE_COUNT][GameImpl.TILE_COUNT];

    board[1][0] = sut.new Tile(2, 1, 0);
    board[2][0] = sut.new Tile(2, 2, 0);


    sut.board = board;
    sut.move(Direction.up);
    assertEquals(
            sut.new Tile(4),
            sut.board[0][0]
    );

    assertEquals(null, sut.board[1][0]);
    assertEquals(null, sut.board[2][0]);
    assertEquals(null, sut.board[3][0]);
  }

  @DisplayName("Game.move with direction up from [2,2,2,2] to [4,4,0,0]")
  @Test
  void moveUpDev3() {
    GameImpl.Tile[][] board = new GameImpl.Tile[GameImpl.TILE_COUNT][GameImpl.TILE_COUNT];

    board[0][0] = sut.new Tile(2, 0, 0);
    board[1][0] = sut.new Tile(2, 1, 0);
    board[2][0] = sut.new Tile(2, 2, 0);
    board[3][0] = sut.new Tile(2, 3, 0);

    board[0][1] = sut.new Tile(2, 0, 1);
    board[1][1] = sut.new Tile(2, 1, 1);
    board[2][1] = sut.new Tile(2, 2, 1);
    board[3][1] = sut.new Tile(2, 3, 1);

    sut.board = board;
    sut.move(Direction.up);
    assertEquals(
            sut.new Tile(4),
            sut.board[0][0]
    );
    assertEquals(
            sut.new Tile(4),
            sut.board[1][0]
    );
    assertEquals(
            sut.new Tile(4),
            sut.board[0][1]
    );
    assertEquals(
            sut.new Tile(4),
            sut.board[1][1]
    );

    assertEquals(null, sut.board[2][0]);
    assertEquals(null, sut.board[3][0]);
  }

  @DisplayName("Game.move with direction down from [2,0,2,0] to [4,0,0,0]")
  @Test
  void moveDownDev1() {
    GameImpl.Tile[][] board = new GameImpl.Tile[GameImpl.TILE_COUNT][GameImpl.TILE_COUNT];

    board[0][0] = sut.new Tile(2, 0, 0);
    board[2][0] = sut.new Tile(2, 2, 0);


    sut.board = board;
    sut.move(Direction.down);
    assertEquals(
            sut.new Tile(4),
            sut.board[3][0]
    );

    assertEquals(null, sut.board[0][0]);
    assertEquals(null, sut.board[1][0]);
    assertEquals(null, sut.board[2][0]);
  }
}
