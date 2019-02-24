import java.util.Random;

/**
 * @author Jorge Vinicio Quintero Santos
 * @className Main
 * @date Feb/23/2019
 * @comments None
 */
public class Main {

    public static void main(String[] args) {

        Sorting<Integer> integerSorting = new Sorting<Integer>(16);

        Integer[] testArray1 = generateIntegerArray(10);
        printIntegerArray(testArray1);
        integerSorting.sort(testArray1);
        printIntegerArray(testArray1);
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

    private static void printIntegerArray(Integer[] array) {
        for (int i = 0; i < array.length-1; i++) {
            System.out.print(array[i] + " --> ");
        }

        System.out.println(array[array.length-1]);
    }
}
