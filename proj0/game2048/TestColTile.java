package game2048;

import static org.junit.Assert.*;
import org.junit.Test;
public class TestColTile {

    static Board b;

    @Test
    public void test() {
        int[][] rawVals = new int[][] {
                {0, 11, 4, 1},
                {0, 0, 0, 2},
                {0, 2, 0, 4},
                {0, 10, 0, 1},
        };

        b = new Board(rawVals, 0);

        int[] expect1 = new int[]{10, 2, 0, 11};
        int[] acutal1 = Model.col_tile(b, 1);
        assertArrayEquals(expect1, acutal1);

        int[] expect2 = new int[]{0, 0, 0, 4};
        int[] acutal2 = Model.col_tile(b, 2);
        assertArrayEquals(expect2, acutal2);

        int[] expect3 = new int[]{1, 4, 2, 1};
        int[] acutal3 = Model.col_tile(b, 3);
        assertArrayEquals(expect3, acutal3);
    }
}
