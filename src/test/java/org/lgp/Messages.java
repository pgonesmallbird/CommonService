package org.lgp;

public class Messages {
    public static void check(String message) {
        if (message == null) {
            throw new RuntimeException("Error message");
        }
    }
}
