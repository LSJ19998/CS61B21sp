package randomizedtest;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdRandom;
import org.junit.Assert;
import org.junit.Test;

import javax.sound.midi.SysexMessage;

import static org.junit.Assert.*;

/**
 * Created by hug.
 */
public class TestBuggyAList {

    private BuggyAList test;
    private AListNoResizing correct;

    @Test
    public void add() {
        test = new BuggyAList();
        correct = new AListNoResizing();

        for (int i = 0; i < 3; i += 1) {
            test.addLast(4 + i);
            correct.addLast(4 + i);
        }

        assertEquals(correct.removeLast(), test.removeLast());
    }


    @Test
    // i can't think of position where error happens.
    public void randomizedTest() {
        AListNoResizing<Integer> L = new AListNoResizing<>();
        BuggyAList<Integer> B = new BuggyAList<>();

        int N = 5000;
        for (int i = 0; i < N; i += 1) {
            int operationNumber = StdRandom.uniform(0, 4);
                // addLast
            if (operationNumber == 0) {
                int randVal = StdRandom.uniform(0, 100);
                L.addLast(randVal);
                B.addLast(randVal);
                System.out.println("addLast(" + randVal + ")");
                // 1: size
            } else if (operationNumber == 1) {
                int size = L.size();
                int size1 = B.size();
                System.out.println("size: " + size);
                assertEquals(size, size1);
                // 2: getLast
            } else if (L.size() > 0 && operationNumber == 2) {
                int res = L.getLast();
                int res1 = B.getLast();
                System.out.println("getLast: " + res);
                assertEquals(res, res1);
                // 3: removeLast
            } else if (L.size() > 0 && operationNumber == 3) {
                int res = L.removeLast();
                int res1 = B.removeLast();
                System.out.println("removeLast: " + res);
                assertEquals(res, res1);
            }
        }
    }
}
