import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveAction;

/**
 * @author Jorge Vinicio Quintero Santos
 * @className Sorting
 * @date Feb/23/2019
 * @comments None
 */
public class Sorting<T extends Comparable<? super T>> extends RecursiveAction {

    private final T[] array;
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
        if (leftPointer < rightPointer) {
            if (rightPointer-leftPointer <= threshold) {
                // To be more efficient it is better to use insertion sort based on a threshold
                insertionSort(this.array, this.leftPointer, this.rightPointer);
            } else {
                int middle = (rightPointer + leftPointer) / 2;
                Sorting<T> leftPart = new Sorting<>(array, threshold, leftPointer, middle);
                Sorting<T> rightPart = new Sorting<>(array, threshold, middle+1, rightPointer);
                // Do all the splitting
                ForkJoinTask.invokeAll(leftPart, rightPart);
                // Do all the merge
                merge(this.array, middle, this.leftPointer, this.rightPointer);
            }
        }

    }

    private void merge(T[] array, int middle, int leftPointer, int rightPointer) {
        // Establish the sizes because they are going to be used a lot
        int sizeLeft = (middle - leftPointer) + 1;
        int sizeRight = rightPointer - middle;

        T[] leftArray = (T[]) new Comparable[sizeLeft];
        T[] rightArray = (T[]) new Comparable[sizeRight];

        // Copy the elements to compare them later
        for (int i = 0; i < sizeLeft; i++) {
            leftArray[i] = array[leftPointer + i];
        }

        for (int i = 0; i < sizeRight; i++) {
            rightArray[i] = array[middle + 1 + i];
        }

        // Make the pointers for the generic arrays
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


    /**
     * A basic implementation of the Insertion Sort, this will be used when the split of the merge sort reaches the
     * given threshold.
     */
    private void insertionSort(T[] array, int leftPointer, int rightPointer) {
        for (int i = leftPointer; i <= rightPointer; i++) {
            // We always check the left side of the array
            for (int y = i - 1; y >= leftPointer; y--) {
                // Check if the element to right is greater to the one in the left
                if (array[y + 1].compareTo(array[y]) < 0) {
                    // Do a simple swap
                    T temp = array[y + 1];
                    array[y + 1] = array[y];
                    array[y] = temp;
                } else {
                    // If the element on the left is less than the number on the right, It means is already sorted
                    break;
                }
            }
        }
    }
}
