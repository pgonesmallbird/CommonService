package org.lgp;

public class NullTest {
    static void register() {
        System.out.println("register");
    }

    static void test(NullTest obj) {
        obj.register();
    }

    public static void main(String[] args) {
        try {
            test(null);
        } catch (NullPointerException e) {
            System.out.println("NPE error");
        } catch (Exception e) {
            System.out.println("error");
        }

    }
}
