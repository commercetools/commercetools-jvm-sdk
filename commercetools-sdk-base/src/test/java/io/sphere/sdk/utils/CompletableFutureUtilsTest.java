package io.sphere.sdk.utils;

import org.junit.Test;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import java.util.function.Function;

import static java.util.Arrays.asList;
import static org.assertj.core.api.Assertions.assertThat;
import static io.sphere.sdk.utils.CompletableFutureUtils.*;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.Assertions.catchThrowable;

public class CompletableFutureUtilsTest {

    @Test
    public void testSuccessful() throws Exception {
        final CompletableFuture<String> future = successful("hello");
        assertThat(future.join()).isEqualTo("hello");
    }

    @Test
    public void testFailed() throws Exception {
        final RuntimeException e = new RuntimeException();
        final CompletableFuture<String> failed = failed(e);
        assertThatThrownBy(() -> failed.join()).hasCause(e);
    }

    @Test
    public void testTransferResultSuccessful() throws Exception {
        final CompletableFuture<String> future = new CompletableFuture<>();
        transferResult(successful("hello"), future);
        assertThat(future.join()).isEqualTo("hello");
    }

    @Test
    public void testTransferResultError() throws Exception {
        final CompletableFuture<String> future = new CompletableFuture<>();
        final RuntimeException e = new RuntimeException();
        transferResult(failed(e), future);
        catchThrowable(() -> future.join());
        assertThat(future.isCompletedExceptionally()).isTrue();
        assertThat(blockForFailure(future)).isEqualTo(e);
    }

    @Test
    public void testMap() throws Exception {
        final CompletionStage<String> future = successful("hello");
        final CompletionStage<Integer> result = map(future, s -> s.length());
        assertThat(result.toCompletableFuture().join()).isEqualTo(5);
    }

    @Test
    public void testFlatMap() throws Exception {
        final CompletionStage<String> future = successful("hello");
        final Function<String, CompletionStage<Integer>> f = s -> successful(s.length());
        final CompletionStage<Integer> result = flatMap(future, f);
        assertThat(result.toCompletableFuture().join()).isEqualTo(5);
    }

    @Test
    public void recoverSuccess() throws Exception {
        final String actual = recover(successful("hello"), e -> "hi").toCompletableFuture().join();
        assertThat(actual).isEqualTo("hello");
    }

    @Test
    public void recoverFailure() throws Exception {
        final String actual = recover(failed(new RuntimeException()), e -> "hi").toCompletableFuture().join();
        assertThat(actual).isEqualTo("hi");
    }

    @Test
    public void recoverWithSuccessInFirst() throws Exception {
        final String actual = recoverWithTestWithSuccessfulResult(successful("hello"), successful("hi"));
        assertThat(actual).isEqualTo("hello");
    }

    @Test
    public void recoverWithSuccessInSecond() throws Exception {
        final CompletableFuture<String> future = failed(new RuntimeException());
        final Function<Throwable, CompletionStage<String>> recoverFunction = e -> delayedResult("hi");
        final CompletableFuture<String> recoveredFuture = recoverWith(future, recoverFunction);
        final String actual = recoveredFuture.join();
        assertThat(actual).isEqualTo("hi");
    }

    private String recoverWithTestWithSuccessfulResult(final CompletableFuture<String> source, final CompletableFuture<String> alternative) {
        return recoverWith(source, e -> alternative).toCompletableFuture().join();
    }

    @Test
    public void recoverWithFailure() throws Exception {
        final RuntimeException e1 = new RuntimeException();
        final RuntimeException e2 = new RuntimeException();
        final CompletionStage<String> future = recoverWith(failed(e1), e -> failed(e2));
        assertThat(blockForFailure(future)).isEqualTo(e2);
    }

    @Test
    public void sequence() {
        final CompletableFuture<Integer> two = delayedResult(2);
        final List<CompletableFuture<Integer>> completableFutures = asList(successful(1), two, successful(3));
        final CompletionStage<List<Integer>> sequence = CompletableFutureUtils.sequence(completableFutures);
        final List<Integer> actual = sequence.toCompletableFuture().join();
        assertThat(actual).isEqualTo(asList(1, 2, 3));
    }

    @Test
    public void sequenceErrorCase() {
        final RuntimeException exception = new RuntimeException("failed");
        final List<CompletableFuture<Integer>> completableFutures = asList(delayedResult(1), failed(exception), successful(3));
        final CompletableFuture<List<Integer>> sequence = CompletableFutureUtils.sequence(completableFutures);
        assertThatThrownBy(() -> sequence.join()).hasCause(exception);
    }

    @Test
    public void onFailure() {
        final RuntimeException e = new RuntimeException("foo");
        final CompletableFuture<String> future = failed(e);
        final Throwable[] state = {null};
        catchThrowable(() -> CompletableFutureUtils.onFailure(future, exception -> state[0] = exception).toCompletableFuture().join());
        assertThat(state[0]).isEqualTo(e);
    }

    @Test
    public void onSuccess() {
        final CompletableFuture<String> future = successful("hello");
        final String[] state = {""};
        CompletableFutureUtils.onSuccess(future, s -> state[0] = s).toCompletableFuture().join();
        assertThat(state[0]).isEqualTo("hello");
    }

    @Test
    public void orElseThrow() {
        final CompletableFuture<String> incompleteFuture = new CompletableFuture<>();

        final Throwable throwable = catchThrowable(() -> {
            CompletableFutureUtils.orElseThrow(incompleteFuture,
                    () -> new RuntimeException("I don't want to wait anymore and throw an exception"));
        });

        assertThat(throwable)
                .isInstanceOf(RuntimeException.class)
                .hasMessage("I don't want to wait anymore and throw an exception");
    }

    @Test
    public void orElseGet() {
        final CompletableFuture<Integer> incompleteFuture = new CompletableFuture<>();
        final Integer result = CompletableFutureUtils.orElseGet(incompleteFuture, () -> 1 + 1);
        assertThat(result).isEqualTo(2);
    }

    private <T> CompletableFuture<T> delayedResult(final T t) {
        return CompletableFuture.supplyAsync(() -> {
            delay();
            return t;
        });
    }

    private void delay()  {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}