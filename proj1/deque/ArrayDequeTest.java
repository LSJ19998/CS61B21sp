package deque;
import org.junit.Test;

import static org.junit.Assert.*;

public class ArrayDequeTest {

    /** 测试0 + 1 和 0 - 1 的结果   将item.length改成5测试即可 */
/*    @Test
    public void nextTest() {
        System.out.println("Make sure to uncomment the lines below (and delete this print statement).");
        ArrayDeque<String> lld1 = new ArrayDeque<>();

        assertEquals("should be same value", lld1.next(0,1), 1);
        assertEquals("should be same value", lld1.next(0, -1), 4);
    }*/

    @Test
    public void addLastBasicResizingTest() {
        ArrayDeque<Integer> lld1 = new ArrayDeque<>();

        for (int i = 0; i <= 10000; i++) {
            lld1.addLast(i);
        }

        assertEquals("Should be same value", 10000, lld1.removeLast(), 0.0);
        assertEquals("Should be same value", 0, lld1.removeFirst(), 0.0);

    }

    @Test
    public void addFirstBasicResizingTest() {
        ArrayDeque<Integer> lld2 = new ArrayDeque<>();
        for (int i = 10000; i >= 0; i--) {
            lld2.addFirst(i);
        }

        assertEquals("Should be same value", 0, lld2.removeFirst(), 0.0);
        assertEquals("Should be same value", 10000, lld2.removeLast(), 0.0);
    }

    @Test
    public void addBasicResiTest() {
        ArrayDeque<Integer> lld1 = new ArrayDeque<>();

        for (int i = 0; i <= 10000; i++) {
            lld1.addLast(i);
        }

        for (int i = 0; i >= -10000; i--) {
            lld1.addFirst(i);
        }
        assertEquals("Should be same value", 10000, lld1.removeLast(), 0.0);
        assertEquals("Should be same value", -10000, lld1.removeFirst(), 0.0);
    }


    @Test
    public void removeFirstBasicResizingTest() {
        ArrayDeque<Integer> lld1 = new ArrayDeque<>();

        for (int i = 0; i < 1000; i++) {
            lld1.addLast(i);
        }

        for (int i = 0; i < 999; i += 1) {
            lld1.removeFirst();
        }

        assertEquals("Should be same value", 0, lld1.removeFirst(), 999.0);
    }

    @Test
    public void removeLastBasicResizingTest() {
        ArrayDeque<Integer> lld1 = new ArrayDeque<>();

        for (int i = 0; i < 1000; i++) {
            lld1.addLast(i);
        }

        for (int i = 0; i < 999; i += 1) {
            lld1.removeLast();
        }

        assertEquals("Should be same value", 0, lld1.removeFirst(), 0.0);
    }

    @Test
    public void addIsEmptySizeTest() {
        System.out.println("Make sure to uncomment the lines below (and delete this print statement).");
        ArrayDeque<String> lld1 = new ArrayDeque<>();

        assertTrue("A newly initialized LLDeque should be empty", lld1.isEmpty());
        lld1.addFirst("front");

        // The && operator is the same as "and" in Python.
        // It's a binary operator that returns true if both arguments true, and false otherwise.
        assertEquals(1, lld1.size());
        assertFalse("lld1 should now contain 1 item", lld1.isEmpty());

        lld1.addLast("middle");
        assertEquals(2, lld1.size());

        lld1.addLast("back");
        assertEquals(3, lld1.size());

//        System.out.println("Printing out deque: ");
//        lld1.printDeque();
    }

    @Test
    /** Adds an item, then removes an item, and ensures that dll is empty afterwards. */
    public void addRemoveTest() {

        System.out.println("Make sure to uncomment the lines below (and delete this print statement).");
        ArrayDeque<Integer> lld1 = new ArrayDeque<Integer>();
        // should be empty
        assertTrue("lld1 should be empty upon initialization", lld1.isEmpty());

        lld1.addFirst(10);
        // should not be empty
        assertFalse("lld1 should contain 1 item", lld1.isEmpty());

        lld1.removeFirst();
        // should be empty
        assertTrue("lld1 should be empty after removal", lld1.isEmpty());
    }

    @Test
    /* Tests removing from an empty deque */
    public void removeEmptyTest() {

        System.out.println("Make sure to uncomment the lines below (and delete this print statement).");
        ArrayDeque<Integer> lld1 = new ArrayDeque<Integer>();
        lld1.addFirst(3);

        lld1.removeLast();
        lld1.removeFirst();
        lld1.removeLast();
        lld1.removeFirst();

        int size = lld1.size();
        String errorMsg = "  Bad size returned when removing from empty deque.\n";
        errorMsg += "  student size() returned " + size + "\n";
        errorMsg += "  actual size() returned 0\n";

        assertEquals(errorMsg, 0, size);
    }

    @Test
    /* Check if you can create ArrayDeques with different parameterized types*/
    public void multipleParamTest() {

        ArrayDeque<String>  lld1 = new ArrayDeque<String>();
        ArrayDeque<Double>  lld2 = new ArrayDeque<Double>();
        ArrayDeque<Boolean> lld3 = new ArrayDeque<Boolean>();

        lld1.addFirst("string");
        lld2.addFirst(3.14159);
        lld3.addFirst(true);

        String s = lld1.removeFirst();
        double d = lld2.removeFirst();
        boolean b = lld3.removeFirst();
    }
    
    @Test
    /* check if null is return when removing from an empty ArrayDeque. */
    public void emptyNullReturnTest() {

        System.out.println("Make sure to uncomment the lines below (and delete this print statement).");
        ArrayDeque<Integer> lld1 = new ArrayDeque<Integer>();

        boolean passed1 = false;
        boolean passed2 = false;
        assertEquals("Should return null when removeFirst is called on an empty Deque,", null, lld1.removeFirst());
        assertEquals("Should return null when removeLast is called on an empty Deque,", null, lld1.removeLast());

    }

    @Test
    public void get_test() {
        ArrayDeque<Integer> lld1 = new ArrayDeque<Integer>();

        for (int i = 0; i < 10000; i++) {
            lld1.addLast(i);
        }

        for (int i = 0; i < 10000; i += 1) {
            assertEquals("Should have the same value",lld1.get(i), i, 0);
        }
    }

    @Test
    public void IteratorTest() {
        ArrayDeque<Integer> lld1 = new ArrayDeque<Integer>();

        for (int i = 0; i < 100; i++) {
            lld1.addLast(i);
        }

        int i = 0;
        for(int e: lld1) {
            assertEquals("should be same value", e, i, 0.0);
            i += 1;
        }
    }

    @Test
    /* Add large number of elements to deque; check if order is correct. */
    public void bigLLDequeTest() {
        ArrayDeque<Integer> lld1 = new ArrayDeque<>();

        for (int i = 0; i < 1000000; i++) {
            lld1.addLast(i);
        }

        for (double i = 0; i < 500000; i++) {
            assertEquals("Should have the same value", (double) lld1.removeFirst(), i, 0.0);
        }

        for (double i = 999999; i > 500000; i--) {
            assertEquals("Should have the same value", (double) lld1.removeLast(), i, 0.0);
        }
    }

    @Test
    public void printDequeueTest() {
        ArrayDeque<Integer> lld1 = new ArrayDeque<>();

        for (int i = 0; i < 10; i++) {
            lld1.addLast(i);
        }

        lld1.printDeque();
    }

    @Test
    public void equalsTest() {
        LinkedListDeque<Integer> lld1 = new LinkedListDeque<>();
        ArrayDeque<Integer> lld2 = new ArrayDeque<>();

        for (int i = 0; i < 100; i += 1) {
            lld1.addLast(i);
        }
        assertFalse("Should return False", lld1.equals(lld2));

        for (int i = 0; i < 100; i += 1) {
            lld2.addLast(i);
        }
        assertTrue("Should return True", lld1.equals(lld2));


        lld1 = null;
        assertFalse("should be null", lld2.equals(lld1));
    }



}