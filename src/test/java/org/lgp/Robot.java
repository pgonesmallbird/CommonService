package org.lgp;

public class Robot {
    public String ask(String message) {
        Messages.check(message);
        if ("".equals(message)) {
            return "repeat again";
        }
        return "Hi " + message;
    }
}
