package io.sphere.sdk.client;

import io.sphere.sdk.products.ProductProjection;
import io.sphere.sdk.products.queries.ProductProjectionQuery;
import io.sphere.sdk.queries.PagedQueryResult;

import java.util.concurrent.CompletionStage;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class MainMethodThreadLeakTest {

    /**
     * This is a test if no threads are blocking the the termination of the application. Needs to be executed on the ci server.
     * In case it has a leak, it just hangs.
     * This is necessary, since a unit test cannot cover this termination problem.
     * @param args unused command line parameters
     */
    public static void main(String[] args) throws InterruptedException, ExecutionException, TimeoutException {
        final SphereClient client = SphereClientFactory.of()
                .createClient(SphereClientConfig.ofEnvironmentVariables("JVM_SDK_IT"));
        final CompletionStage<PagedQueryResult<ProductProjection>> stage = client.execute(ProductProjectionQuery.ofStaged());
        stage.toCompletableFuture().get(10, TimeUnit.SECONDS);
        client.close();
    }
}
