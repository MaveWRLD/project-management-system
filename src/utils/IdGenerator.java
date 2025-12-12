package utils;

public class IdGenerator {

    public String idGenerator(char prefix, int number){
        return String.format("%c%03d", prefix, number);
    }
}
