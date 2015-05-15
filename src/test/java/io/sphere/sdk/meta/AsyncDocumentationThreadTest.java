package io.sphere.sdk.meta;

import org.assertj.core.api.AbstractAssert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ForkJoinPool;
import java.util.function.Function;

import static java.lang.String.format;

public class AsyncDocumentationThreadTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(AsyncDocumentationThreadTest.class);

    private static Long currentThreadId() {
        return Thread.currentThread().getId();
    }

    @Test
    public void threads() throws Exception {
        //step 1, the future is not yet completed
        final CompletableFuture<String> future = new CompletableFuture<>();
        //it is a function declaration, it is not yet executed
        final Function<String, Long> f = s -> {
            final Long id = currentThreadId();
            LOGGER.debug("f thread id=" + id);
            return id;
        };
        final long mainThreadId = currentThreadId();//actually it is a test thread
        LOGGER.debug("main thread id=" + mainThreadId);

        //step 2, thenApply for the future with the 3 different kinds of methods
        final CompletableFuture<Long> thenApplyFuture = future.thenApply(f);
        final CompletableFuture<Long> thenApplyAsyncFuture = future.thenApplyAsync(f);
        final ForkJoinPool customExecutor = new ForkJoinPool(1);//for test just one, so we can identify
        final long threadIdFromCustomPool = customExecutor.submit(() -> currentThreadId()).get();
        LOGGER.debug("threadIdFromCustomPool=" + threadIdFromCustomPool);
        final CompletableFuture<Long> thenApplyAsyncExecutorFuture =
                future.thenApplyAsync(f, customExecutor);

        //step 3 we complete the future and then the function will be called
        //we could call future.complete in this thread, but I want to be able to show the difference
        //between the thread where you call thenApply and where you complete the future
        final Thread completionThread = new Thread(() -> {
            LOGGER.debug("completionThread id=" + currentThreadId());
            future.complete("result");
        });
        completionThread.start();
        final long completionThreadId = completionThread.getId();

        //step 4 assertions for where what happened
        assertThat(thenApplyFuture).completedIn(completionThreadId, "completionThread");
        assertThat(thenApplyAsyncFuture)
                .overridingErrorMessage("this is performed in the JVMs common pool," +
                        "so it blocks not the threads you know")
                .notCompletedIn(completionThreadId).notCompletedIn(mainThreadId);
        assertThat(thenApplyAsyncExecutorFuture).completedIn(threadIdFromCustomPool, "threadIdFromCustomPool");

        //step 5, we called thenApply before the future completed
        //now we call it AFTER the future completed
        assertThat(future.thenApply(f))
                .overridingErrorMessage("If thenApply was called after completion," +
                        "the current thread will be used instead of the completion thread.")
                .notCompletedIn(completionThreadId).completedIn(mainThreadId, "mainThread");
        assertThat(future.thenApplyAsync(f))
                .notCompletedIn(completionThreadId)
                .notCompletedIn(mainThreadId);
        assertThat(future.thenApplyAsync(f, customExecutor)).completedIn(threadIdFromCustomPool, "customPool");

        //cleanup
        customExecutor.shutdown();
    }

    private static FutureAssert assertThat(final CompletableFuture<Long> actual) {
        return new FutureAssert(actual);
    }

    private static class FutureAssert extends AbstractAssert<FutureAssert, CompletableFuture<Long>> {
        protected FutureAssert(final CompletableFuture<Long> actual) {
            super(actual, FutureAssert.class);
        }


        public FutureAssert completedIn(final long completionThreadId, final String furtherDescription) {
            final Long threadId = actual.join();
            if (threadId != completionThreadId) {
                failWithMessage(format("%s did not complete in %d, but in %d. " + furtherDescription,
                        actual, completionThreadId, threadId));
            }
            return this;
        }

        public FutureAssert notCompletedIn(final long completionThreadId) {
            final Long threadId = actual.join();
            if (threadId == completionThreadId) {
                failWithMessage(format("%s did complete in %d, but not in %d.",
                        actual, completionThreadId, threadId));
            }
            return this;
        }
    }
}
