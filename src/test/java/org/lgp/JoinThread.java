package org.lgp;

public class JoinThread extends Thread {
    static volatile int count = 0;

    @Override
    public void run() {
        for (int i = 0; i < 10; i++) {
            try {
                count = count + 1;
                Thread.sleep(3);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            System.out.println("Thread name:" + Thread.currentThread().getName() + " count:" + count);
        }
    }

    public static void main(String[] args) throws InterruptedException {
        Thread[] threads = new Thread[100];
        for (int i = 0; i < threads.length; i++) {
            threads[i] = new JoinThread();
        }
        for (int i = 0; i < threads.length; i++) {
            threads[i].start();
        }
        for (int i = 0; i < threads.length; i++) {
            threads[i].join();
        }
        System.out.println("count=" + JoinThread.count);
        ResourceFile file = new PptFile();
        file.extract();

        PptFile pptFile = (PptFile) file;
        pptFile.extract();
    }
    private static class ResourceFile {
        public static void extract() {
            System.out.println("ResourceFile extract");
        }
    }
    private static class PptFile extends ResourceFile {
        public static void extract() {
            System.out.println("PptFile extract");
        }
    }
}
