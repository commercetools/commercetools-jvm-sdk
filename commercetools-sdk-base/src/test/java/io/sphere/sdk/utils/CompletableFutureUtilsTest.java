package io.sphere.sdk.utils;

import org.junit.Test;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

import static org.assertj.core.api.Assertions.assertThat;
import static io.sphere.sdk.utils.CompletableFutureUtils.*;

public class CompletableFutureUtilsTest {

    @Test
    public void testSuccessful() throws Exception {
        assertThat(successful("hello").join()).isEqualTo("hello");
    }

    @Test
    public void testFailed() throws Exception {
        final RuntimeException e = new RuntimeException();
        assertThat(blockForFailure(failed(e))).isEqualTo(e);
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
        assertThat(future.isCompletedExceptionally()).isTrue();
        assertThat(blockForFailure(future)).isEqualTo(e);
    }

    @Test
    public void testFlatMap() throws Exception {
        final CompletionStage<String> future = successful("hello");
        final CompletionStage<Integer> result = flatMap(future, s -> successful(s.length()));
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
        final String actual = recoverWithTestWithSuccessfulResult(failed(new RuntimeException()), successful("hi"));
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
}