package io.sphere.internal.util;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

public final class Concurrent {
    /** Creates a thread factory.
     *  @param poolName Name of created threads (an auto-incrementing suffix is appended). */
    public static ThreadFactory namedThreadFactory(final String poolName) {
        return new ThreadFactory() {
            final AtomicInteger count = new AtomicInteger(0);
            @Override public Thread newThread(Runnable r) {
                Thread newThread = new Thread(r, poolName + "-" + count.incrementAndGet());
                return newThread;
            }
        };
    }

    /** Creates an executor that only allows execution of a single task at a time. */
    public static ThreadPoolExecutor singleTaskExecutor(String poolName) {
        return new ThreadPoolExecutor(
                1, 1, 30, TimeUnit.SECONDS, new SynchronousQueue<Runnable>(), namedThreadFactory(poolName));
    }
}
