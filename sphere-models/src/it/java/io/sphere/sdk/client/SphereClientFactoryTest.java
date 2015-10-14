package io.sphere.sdk.client;

import io.sphere.sdk.categories.Category;
import io.sphere.sdk.categories.queries.CategoryQuery;
import io.sphere.sdk.queries.PagedQueryResult;
import io.sphere.sdk.test.IntegrationTest;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class SphereClientFactoryTest extends IntegrationTest {
    @Test
    public void autoLoadHttpClientWithoutExplicitSpecification() {
        if (!"false".equals(System.getenv("JVM_SDK_IT_SSL_VALIDATION"))) {
            final SphereClientFactory sphereClientFactory = SphereClientFactory.of();
            final SphereClient client = sphereClientFactory.createClient(getSphereClientConfig());
            final PagedQueryResult<Category> queryResult = client.execute(CategoryQuery.of()).toCompletableFuture().join();
            assertThat(queryResult).isNotNull();
            client.close();
        }
    }
}
