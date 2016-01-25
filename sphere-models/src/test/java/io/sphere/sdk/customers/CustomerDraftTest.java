package io.sphere.sdk.customers;

import io.sphere.sdk.models.Address;
import org.junit.Test;

import java.util.List;

import static io.sphere.sdk.test.SphereTestUtils.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class CustomerDraftTest {

    private static final CustomerDraftDsl CUSTOMER_DRAFT = CustomerDraftDsl.of(CustomerName.ofFirstAndLastName("John", "Smith"), randomString(), "secret");
    private static final List<Address> ADDRESSES = asList(Address.of(DE), Address.of(GB));

    @Test
    public void toStringDoesNotIncludeThePassword() throws Exception {
        final String secret = "123456";
        final CustomerDraft draft = CustomerDraftDsl.of(CustomerName.ofFirstAndLastName("hello", "world"), randomString(), secret);
        assertThat(draft.toString()).doesNotContain(secret);
    }

    @Test
    public void checksAddressesForBillingAddress() throws Exception {
        final CustomerDraftDsl draft = CUSTOMER_DRAFT.withAddresses(ADDRESSES).withDefaultBillingAddress(1);
        assertThatThrownBy(() -> draft.withDefaultBillingAddress(10))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void checksAddressesForShippingAddress() throws Exception {
        final CustomerDraftDsl draft = CUSTOMER_DRAFT.withAddresses(ADDRESSES).withDefaultShippingAddress(0);
        assertThatThrownBy(() -> draft.withDefaultShippingAddress(10))
                .isInstanceOf(IllegalArgumentException.class);
    }
}