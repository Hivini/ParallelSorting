/**
 * @author Jorge Vinicio Quintero Santos
 * @className Sorting
 * @date Feb/23/2019
 * @comments None
 */
public class Sorting<T extends Comparable<T>> {

    private int threshold;

    public Sorting(int threshold) {
        this.threshold = threshold;
    }

    public void sort(T[] array) {
        insertionSort(array);
    }

    private void insertionSort(T[] array) {
        for (int i = 0; i < array.length-1; i++) {
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
