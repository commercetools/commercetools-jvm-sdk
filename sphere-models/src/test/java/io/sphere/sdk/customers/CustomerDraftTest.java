package io.sphere.sdk.customers;

import org.junit.Test;

import static io.sphere.sdk.test.SphereTestUtils.*;
import static org.assertj.core.api.Assertions.assertThat;

public class CustomerDraftTest {
    @Test
    public void toStringDoesNotIncludeThePassword() throws Exception {
        final String secret = "123456";
        final CustomerDraft draft = CustomerDraft.of(CustomerName.ofFirstAndLastName("hello", "world"), randomString(), secret);
        assertThat(draft.toString()).doesNotContain(secret);
    }
}