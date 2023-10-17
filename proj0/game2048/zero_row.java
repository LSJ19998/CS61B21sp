package game2048;

import  static org.junit.Assert.*;
import org.junit.Assert;
import org.junit.Test;

public class zero_row {

    @Test
    public void test_1201() {
        int[] test_lst = new int[]{1, 2, 0, 1};

        int expect = 2;
        int acutal = Model.zero_row(test_lst, 1);
        assertEquals(expect, acutal);
    }

    @Test
    public void test_1000() {
        int[] test_lst = new int[]{1, 0, 0, 0};

        int expect = 3;
        int acutal = Model.zero_row(test_lst, 0);
        assertEquals(expect, acutal);
    }

    @Test
    public void test_0000() {
        int[] test_lst = new int[]{0, 0, 0, 0};

        int expect = 3;
        int acutal = Model.zero_row(test_lst, 0);
        assertEquals(expect, acutal);
    }
}
