package io.sphere.sdk.meta;

import io.sphere.sdk.carts.Cart;
import io.sphere.sdk.carts.LineItem;
import io.sphere.sdk.carts.queries.CartByCustomerIdFetch;
import io.sphere.sdk.carts.queries.CartByIdFetch;
import io.sphere.sdk.client.SphereRequest;
import io.sphere.sdk.customers.Customer;
import io.sphere.sdk.customers.queries.CustomerByIdFetch;
import io.sphere.sdk.products.ProductProjection;
import io.sphere.sdk.products.ProductProjectionType;
import io.sphere.sdk.products.queries.ProductProjectionByIdFetch;
import io.sphere.sdk.utils.CompletableFutureUtils;
import org.junit.Test;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import java.util.function.Function;
import java.util.stream.Stream;

import static io.sphere.sdk.products.ProductProjectionType.CURRENT;
import static io.sphere.sdk.test.SphereTestUtils.*;
import static java.util.stream.Collectors.toList;
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

    public void serialWayToFetchCustomerAndCart() throws Exception {
        final String customerId = "customer-id";//time t = 0
        final Optional<Customer> customerOption = executeSerial(CustomerByIdFetch.of(customerId));//t = 100ms
        final Optional<Cart> cartOption = executeSerial(CartByCustomerIdFetch.of(customerId));//t = 200ms
        println("cart: " + cartOption + " customer: " + customerOption);
    }

    private void println(final String s) {

    }

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

    public void thenApplyFirstDemo() throws Exception {
        final String customerId = "customer-id";
        final CompletionStage<Optional<Customer>> customerStage = execute(CustomerByIdFetch.of(customerId));
        final CompletionStage<String> pageStage = customerStage.thenApply(customerOption -> "customer page " + customerOption);
    }

    public void thenApplyFirstDemoVerbose() throws Exception {
        final String customerId = "customer-id";
        final CompletionStage<Optional<Customer>> customerStage = execute(CustomerByIdFetch.of(customerId));
        final Function<Optional<Customer>, String> f = customerOption -> "customer page " + customerOption;//stored in a value
        final CompletionStage<String> pageStage = customerStage.thenApply(f);
    }

    public void thenApplyUtil() throws Exception {
        final String customerId = "customer-id";
        final CompletionStage<Optional<Customer>> customerStage = execute(CustomerByIdFetch.of(customerId));
        final CompletionStage<String> pageStage = CompletableFutureUtils.map(customerStage, customerOption -> "customer page " + customerOption);
    }

    public void shouldUseFlatMap() throws Exception {
        final String cartIt = "cart-id";
        final CompletionStage<Optional<Cart>> cartStage = execute(CartByIdFetch.of(cartIt));
        final Function<Optional<Cart>, CompletionStage<Optional<ProductProjection>>> f = cartOption -> {
            final LineItem lineItem = cartOption.get().getLineItems().get(0);
            final String productId = lineItem.getProductId();
            final CompletionStage<Optional<ProductProjection>> product = execute(ProductProjectionByIdFetch.of(productId, CURRENT));
            return product;
        };
        // CompletionStage of CompletionStage, urgs!
        final CompletionStage<CompletionStage<Optional<ProductProjection>>> productStageStage = cartStage.thenApply(f);
    }

    public void flatMapFirstDemo() throws Exception {
        final String cartIt = "cart-id";
        final CompletionStage<Optional<Cart>> cartStage = execute(CartByIdFetch.of(cartIt));
        final Function<Optional<Cart>, CompletionStage<Optional<ProductProjection>>> f = cartOption -> {
            final LineItem lineItem = cartOption.get().getLineItems().get(0);
            final String productId = lineItem.getProductId();
            final CompletionStage<Optional<ProductProjection>> product = execute(ProductProjectionByIdFetch.of(productId, CURRENT));
            return product;
        };
        //no nested CompletionStage, by using thenCompose instead of thenApply
        final CompletionStage<Optional<ProductProjection>> productStageStage = cartStage.thenCompose(f);
    }

    @Test
    public void functionalCompositionMapStreamExample() throws Exception {
        final List<Person> persons = asList(new Person("John", "Smith"), new Person("Michael", "Müller"));
        final List<String> lastNames = persons.stream().map(person -> person.getLastName()).distinct().collect(toList());
        assertThat(lastNames).containsExactly("Smith", "Müller");
    }

    @Test
    public void functionalCompositionFlatMapStreamExample() throws Exception {
        final List<Person> persons = asList(new Person("John", "Smith"), new Person("Michael", "Müller"));
        //map causes Stream of Stream
        final Stream<Stream<Integer>> streamStream = persons.stream().map(person -> person.getLastName().chars().boxed());
        //flatMap
        final Stream<Integer> simpleStream = persons.stream().flatMap(person -> person.getLastName().chars().boxed());
        assertThat(simpleStream.collect(toList())).containsExactly(83, 109, 105, 116, 104, 77, 252, 108, 108, 101, 114);

    }

    private static class Person {
        private final String firstName;
        private final String lastName;

        public Person(final String firstName, final String lastName) {
            this.firstName = firstName;
            this.lastName = lastName;
        }

        public String getFirstName() {
            return firstName;
        }

        public String getLastName() {
            return lastName;
        }
    }
}