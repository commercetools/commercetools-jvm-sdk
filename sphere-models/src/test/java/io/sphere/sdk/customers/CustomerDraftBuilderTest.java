package io.sphere.sdk.customers;

import org.junit.Test;

import static io.sphere.sdk.test.SphereTestUtils.randomString;
import static org.assertj.core.api.Assertions.assertThat;

public class CustomerDraftBuilderTest {
    @Test
    public void toStringDoesNotIncludeThePassword() throws Exception {
        final String secret = "123456";
        final CustomerDraft draft = CustomerDraftDsl.of(CustomerName.ofFirstAndLastName("hello", "world"), randomString(), secret);
        assertThat(CustomerDraftBuilder.of(draft).toString()).doesNotContain(secret);
    }

    @Test
    public void constructionWithoutName() {
        final CustomerDraft customerDraft = CustomerDraftDsl.of(randomString(), "password");
        assertThat(customerDraft.getLastName()).isNull();
        assertThat(customerDraft.getFirstName()).isNull();
        assertThat(customerDraft.getName().getLastName()).isNull();
    }
}