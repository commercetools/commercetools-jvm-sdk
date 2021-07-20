package io.sphere.sdk.meta;

import io.sphere.sdk.carts.Cart;
import io.sphere.sdk.carts.LineItem;
import io.sphere.sdk.carts.queries.CartByCustomerIdGet;
import io.sphere.sdk.carts.queries.CartByIdGet;
import io.sphere.sdk.client.SphereClientUtils;
import io.sphere.sdk.client.SphereRequest;
import io.sphere.sdk.customers.Customer;
import io.sphere.sdk.customers.queries.CustomerByIdGet;
import io.sphere.sdk.models.DefaultCurrencyUnits;
import io.sphere.sdk.products.ProductProjection;
import io.sphere.sdk.products.queries.ProductProjectionByIdGet;
import io.sphere.sdk.utils.CompletableFutureUtils;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import javax.money.CurrencyUnit;
import java.util.List;
import java.util.concurrent.*;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Stream;

import static io.sphere.sdk.products.ProductProjectionType.CURRENT;
import static io.sphere.sdk.test.SphereTestUtils.*;
import static java.util.stream.Collectors.toList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

public class AsyncDocumentationTest {
    @BeforeClass
    public static void warmUpJavaMoney() throws Exception {
        final CurrencyUnit eur = DefaultCurrencyUnits.EUR;//workaround for https://github.com/commercetools/commercetools-jvm-sdk/issues/779
    }

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
        final String customerId = "customer-id";//time t=0
        final Customer customer = executeSerial(CustomerByIdGet.of(customerId));//t=100ms
        final Cart cart = executeSerial(CartByCustomerIdGet.of(customerId));//t=200ms
        println("cart: " + cart + " customer: " + customer);
    }

    private void println(final String s) {

    }

    public void parallelWayToFetchCustomerAndCart() throws Exception {
        final String customerId = "customer-id";//time t=0
        final CompletionStage<Customer> customerStage = execute(CustomerByIdGet.of(customerId));//t=1ms
        //after creating the CompletionStage the Thread is freed to start further requests
        final CompletionStage<Cart> cartStage = execute(CartByCustomerIdGet.of(customerId));//t=2ms
        //collect the results
        customerStage.thenAcceptBoth(cartStage, (customer, cart) -> {
            //t=102ms
            println("cart: " + cart + " customer: " + customer);
        });
    }

    public void firstAsyncThenSync() throws Exception {
        final String customerId = "customer-id";//time t=0
        final CompletionStage<Customer> customerStage = execute(CustomerByIdGet.of(customerId));//t=1ms
        //after creating the CompletionStage the Thread is freed to start further requests
        final CompletionStage<Cart> cartStage = execute(CartByCustomerIdGet.of(customerId));//t=2ms
        //collect the results

        final CompletionStage<String> resultStage = customerStage.thenCompose(customer ->
            cartStage.thenApply(cart -> {
                //t=102ms
                return "cart: " + cart.getCustomerId() + " customer: " + customer.getId();//do some computation
            })
        );
        final String result = SphereClientUtils.blockingWait(resultStage, 500, TimeUnit.MILLISECONDS);

        assertThat(result).isEqualTo("cart: " + customerId + " customer: " + customerId);
    }

    protected static <T> CompletionStage<T> execute(final SphereRequest<T> sphereRequest) {
        return null;
    }

    private <T> T executeSerial(final SphereRequest<T> sphereRequest) {
        return null;
    }

    public void thenApplyFirstDemo() throws Exception {
        final String customerId = "customer-id";
        final CompletionStage<Customer> customerStage = execute(CustomerByIdGet.of(customerId));
        final CompletionStage<String> pageStage = customerStage.thenApply(customerOption ->
                "customer page " + customerOption);
    }

    public void thenApplyFirstDemoVerbose() throws Exception {
        final String customerId = "customer-id";
        final CompletionStage<Customer> customerStage = execute(CustomerByIdGet.of(customerId));
        //stored in a value
        final Function<Customer, String> f = customer -> "customer page " + customer;
        final CompletionStage<String> pageStage = customerStage.thenApply(f);
    }

    public void thenApplyUtil() throws Exception {
        final String customerId = "customer-id";
        final CompletionStage<Customer> customerStage = execute(CustomerByIdGet.of(customerId));
        final CompletionStage<String> pageStage = CompletableFutureUtils.map(customerStage, customer -> "customer page " + customer);
    }

    public void shouldUseFlatMap() throws Exception {
        final String cartIt = "cart-id";
        final CompletionStage<Cart> cartStage = execute(CartByIdGet.of(cartIt));
        final Function<Cart, CompletionStage<ProductProjection>> f = cart -> {
            final LineItem lineItem = cart.getLineItems().get(0);
            final String productId = lineItem.getProductId();
            final CompletionStage<ProductProjection> product =
                    execute(ProductProjectionByIdGet.of(productId, CURRENT));
            return product;
        };
        // CompletionStage of CompletionStage, urgs!
        final CompletionStage<CompletionStage<ProductProjection>> productStageStage =
                cartStage.thenApply(f);
    }

    public void flatMapFirstDemo() throws Exception {
        final String cartIt = "cart-id";
        final CompletionStage<Cart> cartStage = execute(CartByIdGet.of(cartIt));
        final Function<Cart, CompletionStage<ProductProjection>> f = cart -> {
            final LineItem lineItem = cart.getLineItems().get(0);
            final String productId = lineItem.getProductId();
            final CompletionStage<ProductProjection> product =
                    execute(ProductProjectionByIdGet.of(productId, CURRENT));
            return product;
        };
        //no nested CompletionStage, by using thenCompose instead of thenApply
        final CompletionStage<ProductProjection> productStageStage = cartStage.thenCompose(f);
    }

    @Test
    public void functionalCompositionMapStreamExample() throws Exception {
        final List<Person> persons = asList(new Person("John", "Smith"), new Person("Michael", "Müller"));
        final List<String> lastNames = persons.stream().map(Person::getLastName).distinct().collect(toList());
        assertThat(lastNames).isEqualTo(asList("Smith", "Müller"));
    }

    @Test
    public void functionalCompositionFlatMapStreamExample() throws Exception {
        final List<Person> persons = asList(new Person("John", "Smith"), new Person("Michael", "Müller"));
        //map causes Stream of Stream
        final Stream<Stream<Integer>> streamStream = persons.stream()
                .map(person -> person.getLastName().chars().boxed());
        //flatMap
        final Stream<Integer> simpleStream = persons.stream()
                .flatMap(person -> person.getLastName().chars().boxed());
        assertThat(simpleStream.collect(toList()))
                .isEqualTo(asList(83, 109, 105, 116, 104, 77, 252, 108, 108, 101, 114));
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

    protected CompletableFuture<String> getFuture() {
        return CompletableFuture.completedFuture("HI");
    }



    protected static class Logger {
        public void info(final String s) {

        }

        public void error(final String s, final Throwable nullableError) {

        }

        public void debug(final String s) {

        }
    }

    @Test
    public void immediateAccessCompletedFuture() throws Exception {
        final CompletableFuture<String> future = CompletableFuture.completedFuture("hi");
        assertThat(future.get()).isEqualTo("hi");
        assertThat(future.get(12, TimeUnit.MILLISECONDS)).isEqualTo("hi");
        assertThat(future.getNow("other")).isEqualTo("hi");
        assertThat(future.join()).isEqualTo("hi");
    }

    @Test
    public void immediateAccessUncompletedFuture() throws Exception {
        final CompletableFuture<String> future = new CompletableFuture<>();
//        assertThat(future.get())
        trying(() -> future.get(12, TimeUnit.MILLISECONDS), e -> assertThat(e).isInstanceOf(TimeoutException.class));
        assertThat(future.getNow("other")).isEqualTo("other");
//        assertThat(future.join())
    }

    private static void fail() {
        assertThat(true).isFalse();
    }

    @Test
    public void immediateAccessCompletedFailedFuture() {
        final CompletableFuture<String> future = CompletableFutureUtils.failed(new WhatEverException());
        //get has checked exception, join not
        trying(() -> future.get(), e -> assertExceptionAndCause(e, ExecutionException.class, WhatEverException.class));
        trying(() -> future.get(12, TimeUnit.MILLISECONDS), e -> assertExceptionAndCause(e, ExecutionException.class, WhatEverException.class));
        //get now has different exception
        trying(() -> future.getNow("other"), e -> assertExceptionAndCause(e, CompletionException.class, WhatEverException.class));
        trying(() -> future.join(), e -> assertExceptionAndCause(e, CompletionException.class, WhatEverException.class));
    }

    private void assertExceptionAndCause(final Throwable e, final Class<? extends Throwable> type, final Class<? extends Throwable> cause) {
        assertThat(e).isInstanceOf(type);
        assertThat(e.getCause()).isExactlyInstanceOf(cause);
    }

    private static class PriceChangedException extends RuntimeException {
        static final long serialVersionUID = 0L;

        public String getCurrentPrice() {
            return null;
        }
    }

    private static class OutOfStockException extends RuntimeException {
        static final long serialVersionUID = 0L;
    }

    private static class WhatEverException extends RuntimeException {
        static final long serialVersionUID = 0L;
    }

    private static void trying(final ExceptionRunnable runnable, final Consumer<Throwable> consumer) {
        try {
            runnable.run();
            fail();
        } catch (final Throwable e) {
            consumer.accept(e);
        }
    }

    @FunctionalInterface
    private static interface ExceptionRunnable {
        void run() throws Throwable;
    }

    @Test
    public void futureJoinDemo() throws Exception {
        final CompletableFuture<String> future = CompletableFuture.completedFuture("hi");
        final String actual = future.join();
        assertThat(actual).isEqualTo("hi");
    }

    @Test
    public void futureGetTimeoutDemo() throws Exception {
        final CompletableFuture<String> future = CompletableFuture.completedFuture("hi");
        final String actual = future.get(12, TimeUnit.MILLISECONDS);
        assertThat(actual).isEqualTo("hi");
    }

    @Test
    public void futureGetTimeoutDemoWithActualTimeout() throws Exception {
        final CompletableFuture<String> futureThatTakesTooLong = new CompletableFuture<>();
        Assert.assertThrows(TimeoutException.class, () -> futureThatTakesTooLong.get(12, TimeUnit.MILLISECONDS));

    }

    @Test
    public void demoGetNow() throws Exception {
        final CompletableFuture<String> incompleteFuture = new CompletableFuture<>();
        final String value = incompleteFuture.getNow("alternative");
        assertThat(value).isEqualTo("alternative");
    }

    @Test
    public void demoGetNowCompleted() throws Exception {
        final CompletableFuture<String> future =
                CompletableFuture.completedFuture("success in time");
        final String value = future.getNow("alternative");
        assertThat(value).isEqualTo("success in time");
    }

    @Test
    public void testOrElseGet() throws Exception {
        final CompletableFuture<String> incompleteFuture = new CompletableFuture<>();
        final String value = CompletableFutureUtils //SDK utils class
                .orElseGet(incompleteFuture, () -> "ALTERNATIVE".toLowerCase());
        assertThat(value).isEqualTo("alternative");

    }

    @Test
    public void testOrElseThrow() throws Exception {
        final CompletableFuture<String> incompleteFuture = new CompletableFuture<>();
        Assert.assertThrows(WhatEverException.class, () -> CompletableFutureUtils //SDK utils class
                    .orElseThrow(incompleteFuture, WhatEverException::new));
    }

    @Test
    public void testOrElseThrowHappyPath() throws Exception {
        final CompletableFuture<String> future =
                CompletableFuture.completedFuture("success in time");
        final String value = CompletableFutureUtils //SDK utils class
                .orElseThrow(future, () -> new WhatEverException());
        assertThat(value).isEqualTo("success in time");
    }

    @Test
    public void wrongWayOfErrorHandling() throws Exception {
        final Service service = new Service();
        try {
            final CompletionStage<String> result = service.execute();
        } catch (final WhatEverException e) {
            //catch block does not make sense
        }
    }

    @Test
    public void simpleRecover() throws Exception {
        final CompletableFuture<String> future = new CompletableFuture<>();
        future.completeExceptionally(new WhatEverException());
        final CompletableFuture<String> hardenedFuture = future.exceptionally(e -> "a default value");
        assertThat(hardenedFuture.join()).isEqualTo("a default value");
    }

    @Test
    public void recoverWith() throws Exception {
        final Service service = new Service();
        final OtherService fallbackService = new OtherService();
        final CompletionStage<String> firstAttempt = service.execute();
        final CompletionStage<String> hardenedFuture = CompletableFutureUtils
                .recoverWith(firstAttempt, error -> {
                    final CompletionStage<String> secondAttempt = fallbackService.execute();
                    return secondAttempt;
                });
    }

    @Test
    public void handleLikeExceptionallyAndThenApply() throws Exception {
        final CompletableFuture<String> future = CompletableFuture.completedFuture("hi");

        final CompletableFuture<String> viaHandle = future.handle((nullableValue, nullableError) ->
                        nullableValue != null ? nullableValue.toUpperCase() : "DEFAULT"
        );

        final CompletableFuture<String> viaThenApply = future
                .thenApply(value -> value.toUpperCase()).exceptionally(e -> "DEFAULT");

        assertThat(viaHandle.join()).isEqualTo(viaThenApply.join()).isEqualTo("HI");
    }

    @Test
    public void exceptionallyWithExceptionTypes() throws Exception {
        final CompletionStage<String> stage = new Service().execute();
        final CompletionStage<String> result = stage.exceptionally(e -> {
            if (e instanceof PriceChangedException) {
                return "price changed, is now " + ((PriceChangedException) e).getCurrentPrice();
            } else if (e instanceof OutOfStockException) {
                return "out of stock";
            } else {
                return "oops";
            }
        });
    }

    @Test
    public void exceptionallyWithExceptionTypesButUncoveredPart() throws Exception {
        final CompletableFuture<String> stage = CompletableFutureUtils.failed(new WhatEverException());
        final Function<Throwable, String> f = e -> {
            if (e instanceof PriceChangedException) {
                return "price changed, is now " + ((PriceChangedException) e).getCurrentPrice();
            } else if (e instanceof OutOfStockException) {
                return "out of stock";
            } else if (e instanceof RuntimeException) {
                throw (RuntimeException) e;
            } else {
                //wrap with CompletionException it checked exceptions
                //it works out with future.get and future.join
                throw new CompletionException(e);
            }
        };
        final CompletableFuture<String> hardened = stage.exceptionally(f);
        trying(() -> hardened.join(), e -> assertExceptionAndCause(e, CompletionException.class, WhatEverException.class));

        //now with a checked exception
        final CompletableFuture<String> withChecked = CompletableFutureUtils.failed(new EvilCheckedException());
        trying(() -> withChecked.join(), e -> assertExceptionAndCause(e, CompletionException.class, EvilCheckedException.class));
        trying(() -> withChecked.get(), e -> assertExceptionAndCause(e, ExecutionException.class, EvilCheckedException.class));
    }

    private static class Service {
        public CompletionStage<String> execute() {
            return CompletableFuture.completedFuture("HI");
        }
    }

    private static class OtherService extends Service {

    }

    private static class EvilCheckedException extends Exception {
        static final long serialVersionUID = 0L;
    }
}
