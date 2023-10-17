package randomizedtest;
import org.junit.Test;
import static org.junit.Assert.*;
import edu.princeton.cs.algs4.StdRandom;
/** Array based list.
 *  @author Josh Hug
 */

//         0 1  2 3 4 5 6 7
// items: [6 9 -1 2 0 0 0 0 ...]
// size: 5

/* Invariants:
 addLast: The next item we want to add, will go into position size
 getLast: The item we want to return is in position size - 1
 size: The number of items in the list should be size.
*/

public class AListNoResizing<Item> {
    private Item[] items;
    private int size;

    /** Creates an empty list. */
    public AListNoResizing() {
        items = (Item[]) new Object[1000];
        size = 0;
    }

    /** Inserts X into the back of the list. */
    public void addLast(Item x) {
        items[size] = x;
        size = size + 1;
    }

    /** Returns the item from the back of the list. */
    public Item getLast() {
        return items[size - 1];
    }
    /** Gets the ith item in the list (0 is the front). */
    public Item get(int i) {
        return items[i];
    }

    /** Returns the number of items in the list. */
    public int size() {
        return size;
    }

    /** Deletes item from back of the list and
      * returns deleted item. */
    public Item removeLast() {
        Item x = getLast();
        items[size - 1] = null;
        size = size - 1;
        return x;
    }




    @Test
    public void testThreeAddThreeRemove(){
        /*add the same class into two class */
        AListNoResizing<Integer> a = new AListNoResizing<>();
        BuggyAList<Integer> b = new BuggyAList<>();

        /*remove the last item , test if it is equal*/
        a.addLast(1);
        b.addLast(1);
        a.addLast(2);
        b.addLast(2);
        a.addLast(3);
        b.addLast(3);

        assertEquals(a.size(),b.size());
        assertEquals(a.removeLast(),b.removeLast());
        assertEquals(a.removeLast(),b.removeLast());
        assertEquals(a.removeLast(),b.removeLast());

    }



    @Test
    public void randomizedTest(){
        AListNoResizing<Integer> L = new AListNoResizing<>();
        BuggyAList<Integer> B = new BuggyAList<>();

        /*how to delete */
        int N = 5000;
        for (int i = 0; i < N; i += 1) {
            int operationNumber = StdRandom.uniform(0, 4);
            if (operationNumber == 0 ) {
                // addLast
                    int randVal = StdRandom.uniform(0, 100);
                    L.addLast(randVal);
                    B.addLast(randVal);
                    System.out.println("addLast ----------");
                    System.out.println("addLast(" + randVal + ")");
            } else if (operationNumber == 1) {
                // size
                int size = L.size();
                int Bsize = B.size();
                System.out.println("size ----------");
                System.out.println("AList size: " + size);
                System.out.println("BuggyAList size: " + size);
            } else if(operationNumber == 2 && L.size() > 0 && B.size() > 0) {
                    int last = L.removeLast();
                    int Blast = B.removeLast();
                    System.out.println("remove ----------");
                    System.out.println("AList removeLast : " + last);
                    System.out.println("BuggyAList removeLast : " + Blast);
            } else if(operationNumber == 3 && L.size() > 0 && B.size() > 0) {
                int last = L.getLast();
                int Blast = B.getLast();
                System.out.println("getLast ----------");
                System.out.println("getLast :" + last);
                System.out.println("Buggy getLast :" + Blast);
            }
        }
    }

}
