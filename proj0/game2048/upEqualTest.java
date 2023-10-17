package game2048;


import static org.junit.Assert.*;
import org.junit.Test;

public class upEqualTest {

    @Test
    public void lst1001() {
        int[] test_lst = new int[]{1, 0, 0, 1};
        boolean actual = Model.up_equal(test_lst, 2, 0);
        assertTrue(actual);
    }

    @Test
    public void lst1011() {
        int[] test_lst = new int[]{1, 0, 1, 1};
        boolean actual = Model.up_equal(test_lst, 1, 0);
        assertTrue(actual);
    }

    @Test
    public void over() {
        int[] test_lst = new int[]{1, 0, 0, 0};
        boolean actual = Model.up_equal(test_lst, 3, 0);
        assertFalse(actual);
    }
}
