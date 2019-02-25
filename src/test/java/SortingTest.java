import org.junit.jupiter.api.Test;

import java.util.Random;
import java.util.concurrent.ForkJoinPool;

/**
 * @author Jorge Vinicio Quintero Santos
 * @className SortingTest
 * @date Feb/24/2019
 * @comments None
 */
public class SortingTest {

    @Test
    public static void checkIntegerArray(Integer[] array) {
        Integer[] testArray1 = generateIntegerArray(100);

        //Integer[] testArray1 = {87, 35, 67, 60, 2};
        printIntegerArray(testArray1);

        ForkJoinPool pool = new ForkJoinPool(Runtime.getRuntime().availableProcessors() - 1);
        pool.invoke(new Sorting<>(testArray1, 16));

        printIntegerArray(testArray1);

        for (int i = 0; i < array.length-1; i++) {
            if (array[i + 1].compareTo(array[i]) < 0) {
                System.out.println("No sirve");
                break;
            }
        }
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
