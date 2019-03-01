import javafx.scene.paint.Stop;
import org.apache.commons.lang.time.StopWatch;
import java.util.Random;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;

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
        System.out.println("\n---------------- 4000 DATA -------------------\n");
        runParallelMergeTest(testArray1);

        // 10000 data
        System.out.println("\n---------------- 10000 DATA -------------------\n");
        runParallelMergeTest(testArray2);

        // 100000 data
        System.out.println("\n---------------- 100000 DATA -------------------\n");
        runParallelMergeTest(testArray3);

        // 1000000 data
        System.out.println("\n---------------- 1000000 DATA -------------------\n");
        runParallelMergeTest(testArray4);


        System.out.println();
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
        ForkJoinPool pool = new ForkJoinPool(Runtime.getRuntime().availableProcessors());
        StopWatch stopWatch = new StopWatch();

        stopWatch.start();
        mergeSort(testArray);
        stopWatch.stop();
        System.out.printf("*** Normal Merge Sort with %d data duration was: %d milliseconds\n", testArray.length, stopWatch.getTime());
        stopWatch.reset();
        generateIntegerArray(testArray);
        System.out.println();

        stopWatch.start();
        pool.invoke(new Sorting<>(testArray, 1));
        stopWatch.stop();
        System.out.printf("Parallel Merge with %d data and %d threshold duration was: %d milliseconds\n", testArray.length, 1, stopWatch.getTime());
        stopWatch.reset();
        checkIfSorted(testArray);
        generateIntegerArray(testArray);
        System.out.println();

        stopWatch.start();
        pool.invoke(new Sorting<>(testArray, 16));
        stopWatch.stop();
        System.out.printf("Parallel Merge with %d data and %d thresholds duration was: %d milliseconds\n", testArray.length, 16, stopWatch.getTime());
        stopWatch.reset();
        checkIfSorted(testArray);
        generateIntegerArray(testArray);
        System.out.println();

        stopWatch.start();
        pool.invoke(new Sorting<>(testArray, 100));
        stopWatch.stop();
        System.out.printf("Parallel Merge with %d data and %d thresholds duration was: %d milliseconds \n", testArray.length, 100, stopWatch.getTime());
        stopWatch.reset();
        checkIfSorted(testArray);
        generateIntegerArray(testArray);
        System.out.println();

        stopWatch.start();
        pool.invoke(new Sorting<>(testArray, 500));
        stopWatch.stop();
        System.out.printf("Parallel Merge with %d data and %d thresholds duration was: %d milliseconds\n", testArray.length, 500, stopWatch.getTime());
        stopWatch.reset();
        checkIfSorted(testArray);
        System.out.println();
    }


    private static Integer[] generateIntegerArray(int size) {
        if (size <= 0) {
            throw new IllegalArgumentException("Don't do this please");
        }

        Random random = new Random();
        Integer[] array = new Integer[size];

        for (int i = 0; i < size; i++) {
            array[i] = random.nextInt();
        }

        System.out.printf("Random array of size %d has been generated.\n", size);
        return array;
    }

    private static void generateIntegerArray(Integer[] testArray) {
        Random random = new Random();

        for (int i = 0; i < testArray.length; i++) {
            testArray[i] = random.nextInt();
        }

        System.out.printf("Random array of size %d has been regenerated.\n", testArray.length);
    }



    private static void printIntegerArray(Integer[] array) {
        for (int i = 0; i < array.length; i++) {
            System.out.print(array[i] + " --> \n");
        }

        System.out.println(array[array.length-1]);
    }

    private static void mergeSort(Integer[] array) {
        mergeSort(array, 0, array.length-1);
    }

    private static void mergeSort(Integer[] array, int leftPointer, int rightPointer) {
        if (leftPointer < rightPointer) {
            int middle = (rightPointer + leftPointer) / 2;
            mergeSort(array, leftPointer, middle);
            mergeSort(array, middle+1, rightPointer);
            // Do all the merge
            merge(array, middle, leftPointer, rightPointer);
        }
    }

    private static void merge(Integer[] array, int middle, int leftPointer, int rightPointer) {
        // Establish the sizes because they are going to be used a lot
        int sizeLeft = (middle - leftPointer) + 1;
        int sizeRight = rightPointer - middle;

        Integer[] leftArray = new Integer[sizeLeft];
        Integer[] rightArray = new Integer[sizeRight];

        // Copy the elements to compare them later
        for (int i = 0; i < sizeLeft; i++) {
            leftArray[i] = array[leftPointer + i];
        }

        for (int i = 0; i < sizeRight; i++) {
            rightArray[i] = array[middle + 1 + i];
        }

        // Make the pointers for the arrays
        int i = 0, j = 0;
        // Make a pointer to the original array
        int originalIndex = leftPointer;

        // Copy the elements to the original array
        while (i < sizeLeft && j < sizeRight) {

            if (leftArray[i].compareTo(rightArray[j]) <= 0) {
                array[originalIndex++] = leftArray[i++];
            } else {
                array[originalIndex++] = rightArray[j++];
            }
        }

        // Copy the rest of the elements
        while (i < sizeLeft) {
            array[originalIndex++] = leftArray[i++];
        }

        while (j < sizeRight) {
            array[originalIndex++] = rightArray[j++];
        }
    }
}
