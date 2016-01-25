package io.sphere.sdk.queries;

import io.sphere.sdk.client.SphereClient;
import io.sphere.sdk.products.ProductProjection;
import io.sphere.sdk.products.queries.ProductProjectionQuery;
import org.junit.Test;
import org.reactivestreams.Publisher;

import static org.assertj.core.api.Assertions.*;

public class ExperimentalReactiveStreamUtilsDemoTest {
    @Test
    public void productProjectionDemo() {
        final SphereClient client = getClient();
        final ProductProjectionQuery seedQuery = ProductProjectionQuery.ofCurrent()
                .withPredicates(m -> m.categories().id().is("category-id"));
        final Publisher<ProductProjection> productProjectionPublisher =
                ExperimentalReactiveStreamUtils.publisherOf(seedQuery, ProductProjection::getId, client);
    }

    private SphereClient getClient() {
        return null;
    }
}