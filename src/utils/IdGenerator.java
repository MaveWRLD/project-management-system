package utils;

/**
 * The type Id generator.
 */
public class IdGenerator {

    /**
     * Id generator string.
     *
     * @param prefix the prefix
     * @param number the number
     * @return the string
     */
    public String idGenerator(char prefix, int number){
        return String.format("%c%03d", prefix, number);
    }
}
