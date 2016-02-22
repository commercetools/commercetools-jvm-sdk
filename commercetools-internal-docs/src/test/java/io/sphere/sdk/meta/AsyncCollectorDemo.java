package io.sphere.sdk.meta;

import io.sphere.sdk.client.SphereClient;
import io.sphere.sdk.products.Product;
import io.sphere.sdk.products.commands.ProductDeleteCommand;

import java.time.Duration;
import java.util.List;

import static io.sphere.sdk.client.SphereClientUtils.blockingWaitForEachCollector;

public class AsyncCollectorDemo {
    public static void demo(final SphereClient client, final List<Product> products) {
        final List<Product> deletedProducts = products.stream()
                .map(product -> client.execute(ProductDeleteCommand.of(product)))
                .collect(blockingWaitForEachCollector(Duration.ofSeconds(10)));
    }
}
