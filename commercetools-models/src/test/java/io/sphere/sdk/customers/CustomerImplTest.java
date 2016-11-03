package io.sphere.sdk.customers;

import io.sphere.sdk.json.SphereJsonUtils;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class CustomerImplTest {
    @Test
    public void toStringDoesNotIncludeThePassword() throws Exception {
        final String secret = "123456";
        final String json = String.format("{\"password\":\"%s\"}", secret);
        final Customer customer = SphereJsonUtils.readObject(json, Customer.class);
        assertThat(customer.toString()).doesNotContain(secret);
    }
}