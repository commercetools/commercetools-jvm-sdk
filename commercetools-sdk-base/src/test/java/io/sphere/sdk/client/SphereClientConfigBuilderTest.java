package io.sphere.sdk.client;

import org.junit.Test;

import static java.util.Arrays.asList;
import static org.assertj.core.api.Assertions.*;

public class SphereClientConfigBuilderTest {
    @Test
    public void scopes() {
        final SphereClientConfig config = SphereClientConfigBuilder
                .ofKeyIdSecret("projectKey", "clientId", "clientSecret")
                .scopes(asList(SphereProjectScope.MANAGE_CUSTOMERS, SphereProjectScope.VIEW_ORDERS))
                .build();
        assertThat(config.getScopes()).containsExactly("manage_customers", "view_orders");
    }

}