package spw4.game2048;

import java.util.Random;

public class GameRandomGeneratorImpl implements GameRandomGenerator {
  private Random random;

  public GameRandomGeneratorImpl() {
    this.random = new Random();
  }

  public GameRandomGeneratorImpl(Random random) {
    this.random = random;
  }

  /**
   * Generates a random number 2 or 4
   * @return 2 with 90% probability or 4 with 10% probability
   */
  @Override
  public int generateTile() {
    // caution: the first parameter is inclusive bound, the second one is exclusive
    int value = random.nextInt(1, 11);
    return value < 10 ? 2 : 4;
  }

  /**
   * Generates a random number for tile indexes between 0 and 3
   * @return int in range 0 - 3
   */
  @Override
  public int generateIndex() {
    return random.nextInt(0, 4);
  }
}
