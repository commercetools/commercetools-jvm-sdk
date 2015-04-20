package io.sphere.sdk.customers;

import org.junit.Test;

import static io.sphere.sdk.test.SphereTestUtils.randomString;
import static org.fest.assertions.Assertions.assertThat;

public class CustomerDraftBuilderTest {
    @Test
    public void toStringDoesNotIncludeThePassword() throws Exception {
        final String secret = "123456";
        final CustomerDraft draft = CustomerDraft.of(CustomerName.ofFirstAndLastName("hello", "world"), randomString(), secret);
        assertThat(CustomerDraftBuilder.of(draft).toString()).doesNotContain(secret);
    }

}