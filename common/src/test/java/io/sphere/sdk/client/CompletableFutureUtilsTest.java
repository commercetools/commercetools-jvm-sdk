package io.sphere.sdk.client;

import org.junit.Test;

import java.util.concurrent.CompletableFuture;

import static org.fest.assertions.Assertions.assertThat;
import static io.sphere.sdk.client.CompletableFutureUtils.*;

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
        final CompletableFuture<String> future = successful("hello");
        final CompletableFuture<Integer> result = flatMap(future, s -> successful(s.length()));
        assertThat(result.join()).isEqualTo(5);
    }

    @Test
    public void recoverSuccess() throws Exception {
        final String actual = recover(successful("hello"), e -> "hi").join();
        assertThat(actual).isEqualTo("hello");
    }

    @Test
    public void recoverFailure() throws Exception {
        final String actual = recover(failed(new RuntimeException()), e -> "hi").join();
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
        return recoverWith(source, e -> alternative).join();
    }

    @Test
    public void recoverWithFailure() throws Exception {
        final RuntimeException e1 = new RuntimeException();
        final RuntimeException e2 = new RuntimeException();
        final CompletableFuture<String> future = recoverWith(failed(e1), e -> failed(e2));
        assertThat(blockForFailure(future)).isEqualTo(e2);
    }
}