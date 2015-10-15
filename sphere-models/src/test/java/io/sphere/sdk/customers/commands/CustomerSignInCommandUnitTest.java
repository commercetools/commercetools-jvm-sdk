package io.sphere.sdk.customers.commands;

import org.junit.Test;

import static org.assertj.core.api.Assertions.*;

public class CustomerSignInCommandUnitTest {
    @Test
    public void doesNotLogPassword() {
        final CustomerSignInCommand cmd = CustomerSignInCommand.of("email", "secret");
        assertThat(cmd.toString()).doesNotContain("secret");
    }
}