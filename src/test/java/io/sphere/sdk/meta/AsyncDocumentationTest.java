package io.sphere.sdk.meta;

import io.sphere.sdk.carts.Cart;
import io.sphere.sdk.carts.queries.CartByCustomerIdFetch;
import io.sphere.sdk.client.SphereRequest;
import io.sphere.sdk.customers.Customer;
import io.sphere.sdk.customers.queries.CustomerByIdFetch;
import io.sphere.sdk.utils.CompletableFutureUtils;
import org.junit.Test;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import java.util.function.Function;

import static org.fest.assertions.Assertions.assertThat;

public class AsyncDocumentationTest {
    @Test
    public void createFailedFuture() throws Exception {
        final CompletableFuture<String> future = new CompletableFuture<>();

        assertThat(future.isDone()).isFalse();
        assertThat(future.isCompletedExceptionally()).isFalse();

        final Throwable throwable = new Exception();
        future.completeExceptionally(throwable);

        assertThat(future.isDone()).isTrue();
        assertThat(future.isCompletedExceptionally()).isTrue();
    }

    @Test
    public void createFailedFutureShortcut() throws Exception {
        final CompletableFuture<String> future = CompletableFutureUtils.failed(new Exception());
    }

    @Test
    public void createFulfilledFuture() throws Exception {
        final CompletableFuture<String> future = new CompletableFuture<>();

        assertThat(future.isDone()).isFalse();
        assertThat(future.isCompletedExceptionally()).isFalse();

        final String resultOfAComputation = "result";
        future.complete(resultOfAComputation);

        assertThat(future.isDone()).isTrue();
        assertThat(future.isCompletedExceptionally()).isFalse();
    }

    @Test
    public void createImmediatelyFulfilledFuture() throws Exception {
        final CompletableFuture<String> future = CompletableFuture.completedFuture("result");
    }

    @Test
    public void createImmediatelyFulfilledFutureShortcut() throws Exception {
        final CompletableFuture<String> future = CompletableFutureUtils.successful("result");
    }

    @Test
    public void serialWayToFetchCustomerAndCart() throws Exception {
        final String customerId = "customer-id";//time t = 0
        final Optional<Customer> customerOption = executeSerial(CustomerByIdFetch.of(customerId));//t = 100ms
        final Optional<Cart> cartOption = executeSerial(CartByCustomerIdFetch.of(customerId));//t = 200ms
        println("cart: " + cartOption + " customer: " + customerOption);
    }

    private void println(final String s) {

    }

    @Test
    public void parallelWayToFetchCustomerAndCart() throws Exception {
        final String customerId = "customer-id";//time t = 0
        final CompletionStage<Optional<Customer>> customerStage = execute(CustomerByIdFetch.of(customerId));//t = 1ms
        //after creating the CompletionStage the Thread is freed to start further requests
        final CompletionStage<Optional<Cart>> cartStage = execute(CartByCustomerIdFetch.of(customerId));//t = 2ms
        //collect the results
        customerStage.thenAcceptBoth(cartStage, (customerOption, cartOption) -> {
            //t=102ms
            println("cart: " + cartOption + " customer: " + customerOption);
        });
    }

    protected static <T> CompletionStage<T> execute(final SphereRequest<T> sphereRequest) {
        return null;
    }

    private <T> T executeSerial(final SphereRequest<T> sphereRequest) {
        return null;
    }

    @Test
    public void thenApplyFirstDemo() throws Exception {
        final String customerId = "customer-id";
        final CompletionStage<Optional<Customer>> customerStage = execute(CustomerByIdFetch.of(customerId));
        final CompletionStage<String> pageStage = customerStage.thenApply(customerOption -> "customer page " + customerOption);
    }

    @Test
    public void thenApplyFirstDemoVerbose() throws Exception {
        final String customerId = "customer-id";
        final CompletionStage<Optional<Customer>> customerStage = execute(CustomerByIdFetch.of(customerId));
        final Function<Optional<Customer>, String> f = customerOption -> "customer page " + customerOption;//stored in a value
        final CompletionStage<String> pageStage = customerStage.thenApply(f);
    }
}