package io.sphere.sdk.client;

import io.sphere.sdk.categories.queries.CategoryQuery;
import io.sphere.sdk.test.IntegrationTest;
import org.junit.Test;


import static io.sphere.sdk.categories.CategoryFixtures.withCategory;
import static org.fest.assertions.Assertions.assertThat;

public class SphereConstantAccessTokenSupplierImplTest extends IntegrationTest {
    @Test
    public void requestsArePossible() throws Exception {
        withCategory(client(), category -> {
            final CategoryQuery categoryQuery = CategoryQuery.of();
            final int expected = client().execute(categoryQuery).getTotal();
            final SphereApiConfig apiConfig = SphereApiConfig.of(projectKey(), apiUrl());

            final SphereAuthConfig authConfig = SphereAuthConfig.of(projectKey(), clientId(), clientSecret(), authUrl());

            final SphereAccessTokenSupplier fixedTokenSupplier = SphereAccessTokenSupplier.ofOneTimeFetchingToken(authConfig, NingAsyncHttpClientAdapter.of(), true);
            final SphereClient oneTokenClient = SphereClientFactory.of().createClient(apiConfig, fixedTokenSupplier);
            final int actual = oneTokenClient.execute(categoryQuery).join().getTotal();
            assertThat(actual).isEqualTo(expected);
            oneTokenClient.close();
        });
    }
}