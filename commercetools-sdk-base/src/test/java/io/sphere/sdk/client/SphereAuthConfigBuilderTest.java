package io.sphere.sdk.client;

import org.junit.Test;

import static java.util.Arrays.asList;
import static org.assertj.core.api.Assertions.*;

public class SphereAuthConfigBuilderTest {
    @Test
    public void scopes() {
        final SphereAuthConfig config = SphereAuthConfigBuilder
                .ofKeyIdSecret("projectKey", "clientId", "clientSecret")
                .scopes(asList(SphereProjectScope.MANAGE_CUSTOMERS, SphereProjectScope.VIEW_ORDERS))
                .build();
        assertThat(config.getScopes()).containsExactly("manage_customers", "view_orders");
    }

}