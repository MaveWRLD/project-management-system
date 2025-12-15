package utils;

import java.util.Arrays;

/**
 * The type Resize object size utils.
 */
public class ResizeObjectSizeUtils {

    /**
     * Resize objects size if full t [ ].
     *
     * @param <T>   the type parameter
     * @param array the array
     * @return the t [ ]
     */
    public static <T> T[] resizeObjectsSizeIfFull(T[] array) {
        int size = countElements(array);
        if (array.length == size) {
            return Arrays.copyOf(array, size * 2);
        }
        return array;
    }

    /**
     * Count elements int.
     *
     * @param <T>   the type parameter
     * @param items the items
     * @return the int
     */
    public static <T> int countElements(T[] items) {
        int elementsSize = 0;
        for (T item: items) {
            if (item != null) {
                elementsSize++;
            }
        }
        return elementsSize;
    }
}

