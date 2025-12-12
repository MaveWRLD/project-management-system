package utils;

import java.util.Arrays;

public class ResizeObjectSizeUtils {

    public static <T> T[] resizeObjectsSizeIfFull(T[] array) {
        int size = countElements(array);
        if (array.length == size) {
            return Arrays.copyOf(array, size * 2);
        }
        return array;
    }

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

