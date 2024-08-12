package org.lgp;

import java.util.Arrays;

public class TestStream {
    public static void test1() {
        int[] array = {1, 2, 3, 4};
        Arrays.stream(array).filter(item -> {
            if (item % 2 == 0) {
                System.out.println("filter:" + item);
                return true;
            }
            return false;
        });
    }

    public static void test2() {
        int[] array = {1, 2, 3, 4};
        Arrays.stream(array).filter(item -> {
            if (item % 2 == 0) {
                System.out.println("filter:" + item);
                return true;
            }
            return false;
        }).forEach(System.out::println);
    }

    public static void test3() {
        int[] array = {1, 2, 3, 4};
        Arrays.stream(array).filter(item -> {
            if (item % 2 == 0) {
                System.out.println("filter:" + item);
                return true;
            }
            return false;
        }).anyMatch(item -> {
            System.out.println(item);
            return item / 2 == 2;
        });
    }

    public static void main(String[] args) {
        test1();
        System.out.println("----------------------");
        test2();
        System.out.println("----------------------");
        test3();
    }
}
