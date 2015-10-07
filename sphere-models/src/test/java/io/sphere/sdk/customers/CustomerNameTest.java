package io.sphere.sdk.customers;

import org.junit.Test;

import static org.assertj.core.api.Assertions.*;

public class CustomerNameTest {

    @Test
    public void withTitle() {
        final CustomerName customerName = CustomerName.ofFirstAndLastName("first", "last").withTitle("title");
        assertThat(customerName.getFirstName()).isEqualTo("first");
        assertThat(customerName.getLastName()).isEqualTo("last");
        assertThat(customerName.getTitle()).isEqualTo("title");
        assertThat(customerName.getMiddleName()).isNull();
    }

    @Test
    public void withMiddleName() {
        final CustomerName customerName = CustomerName.ofFirstAndLastName("first", "last").withMiddleName("Jay");
        assertThat(customerName.getFirstName()).isEqualTo("first");
        assertThat(customerName.getLastName()).isEqualTo("last");
        assertThat(customerName.getTitle()).isNull();
        assertThat(customerName.getMiddleName()).isEqualTo("Jay");
    }
}