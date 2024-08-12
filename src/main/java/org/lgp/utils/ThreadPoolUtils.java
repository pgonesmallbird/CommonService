package org.lgp.utils;

import org.lgp.config.ThreadPoolExecutorMdcWrapper;

import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

public class ThreadPoolUtils {
    private static ThreadPoolExecutorMdcWrapper threadPoolExecutorMdcWrapper = new ThreadPoolExecutorMdcWrapper(10,10,10, TimeUnit.MINUTES, new LinkedBlockingQueue(100));

    public static  <T> Future<T> execute(Runnable runnable) {
        return threadPoolExecutorMdcWrapper.submit(runnable, null);
    }

}
