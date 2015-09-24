package io.sphere.sdk.customers;

import org.junit.Test;

import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;

public class CustomerImplTest {
    @Test
    public void toStringDoesNotIncludeThePassword() throws Exception {
        final String secret = "123456";
        final CustomerImpl customer = new CustomerImpl(null, 1L, null, null, null, null, null, null, secret, null, null, Collections.emptyList(), null, null, false, null, null, null, null, null, null);
        assertThat(customer.toString()).doesNotContain(secret);
    }
}