package utils;

import java.util.Arrays;

public class ResizeUtils {

    public static <T> T[] resizeProjectsSizeIfFull(T[] array) {
        int size = countElements(array);
        if (array.length == size) {
            return Arrays.copyOf(array, size * 2);
        }
        return array;
    }

    public static <T> int countElements(T[] array) {
        int elementsSize = 0;
        for (Object obj : array) {
            if (obj != null) {
                elementsSize++;
            }
        }
        return elementsSize;
    }
}

