package io.sphere.sdk.client;

import io.sphere.sdk.categories.queries.CategoryQuery;
import io.sphere.sdk.test.IntegrationTest;
import org.junit.Test;


import static io.sphere.sdk.categories.CategoryFixtures.withCategory;
import static org.fest.assertions.Assertions.assertThat;

public class SphereFixedAccessTokenSupplierImplTest extends IntegrationTest {
    @Test
    public void requestsArePossible() throws Exception {
        withCategory(client(), category -> {
            final CategoryQuery categoryQuery = CategoryQuery.of();
            final int expected = client().execute(categoryQuery).getTotal();
            final SphereApiConfig apiConfig = SphereApiConfig.of(projectKey(), apiUrl());
            final SphereAccessTokenSupplier refreshSupplier = SphereAccessTokenSupplier.ofAutoRefresh(SphereAuthConfig.of(projectKey(), clientId(), clientSecret(), authUrl()));
            final String token = refreshSupplier.get();
            refreshSupplier.close();
            final SphereAccessTokenSupplier fixedTokenSupplier = SphereAccessTokenSupplier.ofFixedToken(token);
            final SphereClient oneTokenClient = SphereClientFactory.of().createClient(apiConfig, fixedTokenSupplier);
            final int actual;
            try {
                actual = oneTokenClient.execute(categoryQuery).get().getTotal();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            assertThat(actual).isEqualTo(expected);
        });
    }
}