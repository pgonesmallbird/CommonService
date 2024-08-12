package org.lgp;

import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.security.SecureRandom;
import java.util.*;

import static org.mockito.ArgumentMatchers.anyString;

public class TestMain {
    public static void invoke(Integer i) {
        System.out.println("Integer" + i);
    }

    public static void invoke(int i) {
        System.out.println("int" + i);
    }

    public void invoke1(Integer i) {
        System.out.println("Integer" + i);
    }

    public void invoke1(int i) {
        System.out.println("int" + i);
    }

    public static void invoke(Integer i, Object object) {
        System.out.println(i);
    }

    public static void invoke(int i, Object object) {
        System.out.println(i);
    }

    public static void main(String[] args) {
//        invoke(1);
//        invoke(Integer.valueOf("1"));
//        TestMain testMain = new TestMain();
//        testMain.invoke1(1);
//        testMain.invoke1(Integer.valueOf("1"));
        ArrayList<String> arrayList = new ArrayList<>();
        LinkedList<String> linkedList = new LinkedList<>();
        TestMain testMain = new TestMain();
        long tm = System.currentTimeMillis();
        testMain.construct(arrayList);
        testMain.removeElements(arrayList, 100000);

        System.out.println(System.currentTimeMillis() - tm);
        testMain.construct(linkedList);
        testMain.removeElements(linkedList, 100000);
        System.out.println(System.currentTimeMillis() - tm);
    }

    private static final int LEN = 100000;
    private Random random = new SecureRandom();
    private ArrayList<String> arrayList = new ArrayList<>();
    private LinkedList<String> linkedList = new LinkedList<>();

    public void construct(List<String> list) {
        list.clear();
        for (int i = 0; i < LEN; i++) {
            list.add(String.valueOf(i));
        }
        Collections.shuffle(list);
    }

    public void removeElements(List<String> list, int nums) {
        for (int i = nums; i > 0; i--) {
            int index = random.nextInt(i);
            list.remove(index);
        }
    }

    @Test
    public void test1() {
        Thread t1 = new Thread(() -> {
            System.out.println("come in" + Thread.currentThread().getName());
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }, "t1");
        Thread t2 = new Thread(() -> {
            System.out.println("come in" + Thread.currentThread().getName());
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }, "t2");
        t1.start();
        t2.start();
//        System.out.println("a..b..c".replaceAll("..", "|"));
    }

    public void test2() {
        List<String> list = new ArrayList<>();
        list.add("1");
        list.add(1,"adf");
        System.out.println(list);
    }

    @Test
    public void test3() throws InterruptedException {
       Sys sys = new Sys();
       sys.start();
       Thread.sleep(500);
       sys.interrupt();
       Thread.sleep(3000);
        System.out.println("Stop app");
    }
    @Test
    public void test4() {
        Mockito.mock(Messages.class).check(anyString());
        Robot robot = new Robot();
        String result = robot.ask(null);
        Assert.assertEquals("Hi null", result);
    }
    @org.junit.Test(expected = RuntimeException.class)
    public void test5() {
        Robot robot = new Robot();
        String result = robot.ask(null);
    }
}
