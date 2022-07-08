package spw4.game2048;

import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.util.Random;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class GameRandomGeneratorTest {

  @Mock
  Random random;

  @Test
  void generateWithOneToNineReturnsTwo() {
    when(random.nextInt(eq(1), eq(11)))
            .thenReturn(1, 2, 3, 4, 5, 6, 7, 8, 9);

    int expectedTwosCount = 10;
    int resultTwosCount = 0;
    GameRandomGenerator sut = new GameRandomGeneratorImpl(random);

    for (int i = 0; i < 10; i++) {
      if (sut.generateTile() == 2)
        resultTwosCount++;
    }

    assertEquals(expectedTwosCount, resultTwosCount);
  }

  @Test
  void generateWithTenReturnsFour() {
    when(random.nextInt(eq(1), eq(11)))
            .thenReturn(10);

    int expectedFourCount = 10;
    int resultFourCount = 0;
    GameRandomGenerator sut = new GameRandomGeneratorImpl(random);

    for (int i = 0; i < 10; i++) {
      if (sut.generateTile() == 4)
        resultFourCount++;
    }

    assertEquals(expectedFourCount, resultFourCount);
  }

  /**
   * sut.generateIndex() is just a wrapper for the Random.
   * We actually just check that we stay inside our game bounds (0 - 3).
   * There
   */
  @Test
  void generateNewIndexReturnsValidIndex() {
    GameRandomGenerator sut = new GameRandomGeneratorImpl(new Random());

    for (int i = 0; i < 20; i++) {
      int value = sut.generateIndex();
      assertTrue(0 <= value);
      assertTrue(3 >= value);
    }
  }
}