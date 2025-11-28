package models.utils;

public class IDGenerator {
    private int idCounter = 1;

    public String setID(Character prefix) {
        return String.format(prefix+"%03d", idCounter++);
    }
}
