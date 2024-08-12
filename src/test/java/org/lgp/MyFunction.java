package org.lgp;

@FunctionalInterface
public interface MyFunction{
    static void test() {

    }
    void test1();

    public static void main(String[] args) {
        MyFunction f = ()-> {
            System.out.println("a:");
        } ;
        f.test1();
    }
}
