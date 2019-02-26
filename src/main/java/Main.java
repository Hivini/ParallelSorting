import javafx.scene.paint.Stop;
import org.apache.commons.lang.time.StopWatch;
import java.util.Random;
import java.util.concurrent.ForkJoinPool;

/**
 * @author Jorge Vinicio Quintero Santos
 * @className Main
 * @date Feb/23/2019
 * @comments None
 */
public class Main {

    public static void main(String[] args) {

        Integer[] testArray1 = generateIntegerArray(4000);
        Integer[] testArray2 = generateIntegerArray(10000);
        Integer[] testArray3 = generateIntegerArray(100000);
        Integer[] testArray4 = generateIntegerArray(1000000);


        // 4000 data
        runParallelMergeTest(testArray1);

        // 10000 data
        runParallelMergeTest(testArray2);

        // 100000 data
        runParallelMergeTest(testArray3);

        // 1000000 data
        runParallelMergeTest(testArray4);

        checkIfSorted(testArray1);
        checkIfSorted(testArray2);
        checkIfSorted(testArray3);
        checkIfSorted(testArray4);
    }

    private static void checkIfSorted(Integer[] testArray) {
        boolean worked = true;
        for (int i = 0; i < testArray.length-1; i++) {
            if (testArray[i+1].compareTo(testArray[i]) < 0) {
                worked = false;
                break;
            }
        }

        if (worked) {
            System.out.println("This thing works");
        } else {
            System.out.println("This thing doesn't work");
        }
    }

    private static void runParallelMergeTest(Integer[] testArray) {
        ForkJoinPool pool = new ForkJoinPool(Runtime.getRuntime().availableProcessors() - 1);
        StopWatch stopWatch = new StopWatch();

        stopWatch.start();
        pool.invoke(new Sorting<>(testArray, 1));
        stopWatch.stop();
        System.out.printf("Parallel Merge with %d data and %d threshold was duration was: %d miliseconds\n", testArray.length, 1, stopWatch.getTime());
        stopWatch.reset();

        stopWatch.start();
        pool.invoke(new Sorting<>(testArray, 16));
        stopWatch.stop();
        System.out.printf("Parallel Merge with %d data and %d threshold was duration was: %d miliseconds\n", testArray.length, 16, stopWatch.getTime());
        stopWatch.reset();

        stopWatch.start();
        pool.invoke(new Sorting<>(testArray, 100));
        stopWatch.stop();
        System.out.printf("Parallel Merge with %d data and %d threshold was duration was: %d miliseconds \n", testArray.length, 100, stopWatch.getTime());
        stopWatch.reset();

        stopWatch.start();
        pool.invoke(new Sorting<>(testArray, 500));
        stopWatch.stop();
        System.out.printf("Parallel Merge with %d data and %d threshold was duration was: %d miliseconds\n", testArray.length, 500, stopWatch.getTime());
        stopWatch.reset();
    }


    private static Integer[] generateIntegerArray(int size) {
        if (size <= 0) {
            throw new IllegalArgumentException("Don't do this please");
        }

        Random random = new Random();
        Integer[] array = new Integer[size];

        for (int i = 0; i < size; i++) {
            array[i] = random.nextInt(100);
        }

        System.out.printf("Random array of size %d has been generated.\n", size);
        return array;
    }

    private static void printIntegerArray(Integer[] array) {
        for (int i = 0; i < array.length-1; i++) {
            System.out.print(array[i] + " --> ");
        }

        System.out.println(array[array.length-1]);
    }
}
