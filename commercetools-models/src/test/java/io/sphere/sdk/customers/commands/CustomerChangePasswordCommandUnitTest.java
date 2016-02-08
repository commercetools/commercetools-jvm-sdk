package io.sphere.sdk.customers.commands;

import io.sphere.sdk.models.Versioned;
import org.junit.Test;

import static org.assertj.core.api.Assertions.*;

public class CustomerChangePasswordCommandUnitTest {
    @Test
    public void doesNotShowPassword() {
        final CustomerChangePasswordCommand cmd = CustomerChangePasswordCommand.of(Versioned.of("id", 1L), "secret1", "secret2");
        assertThat(cmd.toString()).doesNotContain("secret");
    }
}