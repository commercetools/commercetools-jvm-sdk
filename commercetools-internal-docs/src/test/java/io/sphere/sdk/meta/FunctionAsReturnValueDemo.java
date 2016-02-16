package io.sphere.sdk.meta;

import io.sphere.sdk.customers.Customer;
import io.sphere.sdk.customers.queries.CustomerByIdGet;

import java.util.concurrent.CompletionStage;
import java.util.function.Function;

public class FunctionAsReturnValueDemo extends AsyncDocumentationTest {
    public static final Function<Customer, String> renderCustomerPage(final String title) {
        return customerOption -> title + " " + customerOption;
    }

    public static void showUsage() {
        final String customerId = "customer-id";
        final CompletionStage<Customer> customerStage =
                execute(CustomerByIdGet.of(customerId));
        final CompletionStage<String> pageStage =
                customerStage.thenApply(renderCustomerPage("customer page"));
    }
}
