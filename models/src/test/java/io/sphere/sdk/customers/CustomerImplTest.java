package io.sphere.sdk.customers;

import org.junit.Test;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.fest.assertions.Assertions.assertThat;

public class CustomerImplTest {
    @Test
    public void toStringDoesNotIncludeThePassword() throws Exception {
        final String secret = "123456";
        final CustomerImpl customer = new CustomerImpl(null, 1, null, null, Optional.empty(), null, null, null, secret, Optional.empty(), Optional.empty(), Collections.emptyList(), Optional.empty(), Optional.empty(), false, Optional.empty(), Optional.empty(), Optional.empty(), Optional.empty(), Optional.empty());
        assertThat(customer.toString()).doesNotContain(secret);
    }
}