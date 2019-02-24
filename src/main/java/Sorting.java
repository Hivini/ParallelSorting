import java.util.Arrays;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveAction;
import java.util.concurrent.RecursiveTask;

/**
 * @author Jorge Vinicio Quintero Santos
 * @className Sorting
 * @date Feb/23/2019
 * @comments None
 */
public class Sorting<T extends Comparable<T>> extends RecursiveAction {

    private T[] array;
    private int threshold;
    private int leftPointer;
    private int rightPointer;

    public Sorting(T[] array, int threshold, int leftPointer, int rightPointer) {
        this.threshold = threshold;
        this.array = array;
        this.leftPointer = leftPointer;
        this.rightPointer = rightPointer;
    }

    public Sorting(T[] array, int threshold) {
        this(array, threshold, 0, array.length-1);
    }

    protected void compute() {
        if ((rightPointer-leftPointer) + 1 <= threshold) {
            // To be more efficient it is better to use insertion sort based on a threshold
            // The best threshold its said to be 16, but for the sake of this activity we will receive it as an input
            insertionSort(array, leftPointer, rightPointer);
        } else {
            Sorting<T> leftPart = new Sorting<>(array, threshold, leftPointer, rightPointer / 2);
            Sorting<T> rightPart = new Sorting<>(array, threshold, rightPointer / 2 + 1, rightPointer);

            ForkJoinTask.invokeAll(leftPart, rightPart);
            merge(leftPart.leftPointer, rightPart.leftPointer);
        }
    }

    private void merge(int pointer1, int pointer2) {
        int originalLeft = pointer1;
        int originalRight = pointer2;

        System.out.println("This run");
    }


    private void insertionSort(T[] array, int leftPointer, int rightPointer) {
        for (int i = 0; i < rightPointer; i++) {
            // We always check the left side of the array
            for (int y = i; y >= 0; y--) {
                // Check if the element to right is greater to the one in the left
                if (array[y + 1].compareTo(array[y]) < 0) {
                    // Do a simple swap
                    T temp = array[y + 1];
                    array[y + 1] = array[y];
                    array[y] = temp;
                } else {
                    // If the element on the left is less than the number on the irght, It means is already sorted
                    break;
                }
            }
        }
    }
}
