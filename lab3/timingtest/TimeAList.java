package timingtest;
import edu.princeton.cs.algs4.Stopwatch;

/**
 * Created by hug.
 */
public class TimeAList {

    /** take Ns, times. opCounts as input, print their element as output of each coloumn of each line */
    private static void printTimingTable(AList<Integer> Ns, AList<Double> times, AList<Integer> opCounts) {
        System.out.println("AList :");
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
        timeAListConstruction();
    }
    private final static int thousand = 1000;

    public static void timeAListConstruction() {
        // TODO: YOUR CODE HERE

        AList Ns = new AList<Integer>();
        AList times = new AList<Double>();
        AList opCounts = new AList<Integer>();

        for (int i = 1 * thousand; i <= 1000 * thousand; i += 100 * thousand) {
            addItem(i, Ns, times, opCounts);
        }

        printTimingTable(Ns, times, opCounts);
    }


    /**
     *  增加n个元素, Ns收集元素个数, times收集时间, opCount收集操作次数.
     * */
    public static void addItem(int n, AList<Integer> Ns, AList<Double> times, AList<Integer> opCounts){
        AList test = new AList();

        Stopwatch sw = new Stopwatch();

        for (int i = 0; i < n; i += 1) {
            test.addLast(i);
        }

        Double elapsed_time = sw.elapsedTime();
        Ns.addLast(n);
        opCounts.addLast(n);
        times.addLast(elapsed_time);
    }
}
