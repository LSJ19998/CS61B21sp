package timingtest;
import edu.princeton.cs.algs4.Stopwatch;

import java.util.List;

/**
 * Created by hug.
 */
public class TimeSLList {
    private static void printTimingTable(AList<Integer> Ns, AList<Double> times, AList<Integer> opCounts) {
        System.out.println("Timing table for getLast ");
        System.out.printf("%12s %12s %12s %12s\n", "N", "time (s)", "# ops", "microsec/op");
        System.out.printf("------------------------------------------------------------\n");
        for (int i = 0; i < Ns.size(); i += 1) {
            int N = Ns.get(i);
            double time = times.get(i);
            int opCount = opCounts.get(i);
            double timePerOp = time / opCount * 1e6;
            System.out.printf("%12d %12.2f %12d %12.2f\n", N, time, opCount, timePerOp);
        }
    }

    public static void main(String[] args) {
        timeGetLast();
    }

    private final static int THOUSAND = 1000;

    public static void timeGetLast() {
        // TODO: YOUR CODE HERE

        AList Ns = new AList<Integer>();
        AList times = new AList<Double>();
        AList opCounts = new AList<Integer>();


        for (int i = 10000; i <= 100 * THOUSAND; i += 10 * THOUSAND) {
            getItem(i, Ns, times, opCounts);
        }


        printTimingTable(Ns, times,opCounts);
    }
    /**
     *  增加n个元素 == 对nth元素进行getLast
     *  Ns收集元素个数. opCount收集增加的元素的个数.
     *  times收集花费的时间.
     * */
    public static void getItem(int n, AList<Integer> Ns, AList<Double> times, AList<Integer> opCounts ){
        /*create an SLList*/
        SLList list = new SLList<Integer>();

        for(int i = 0; i < n; i++){
            list.addLast(1);
        }

        Stopwatch sw = new Stopwatch();
        for(int i = 0; i < 10 * THOUSAND; i++){
            list.getLast();
        }
        double timeInSeconds = sw.elapsedTime();


        Ns.addLast(n);
        times.addLast(timeInSeconds);
        opCounts.addLast(n);
    }

}
