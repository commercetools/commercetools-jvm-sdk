package io.sphere.sdk.models.errors;

import io.sphere.sdk.client.ErrorResponseException;
import io.sphere.sdk.customers.CustomerDraftBuilder;
import io.sphere.sdk.customers.CustomerDraftDsl;
import io.sphere.sdk.customers.commands.CustomerCreateCommand;
import io.sphere.sdk.test.IntegrationTest;
import org.junit.Test;

import static io.sphere.sdk.customers.CustomerFixtures.withCustomer;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

public class DuplicateFieldErrorIntegrationTest extends IntegrationTest {
    @Test
    public void customerAlreadyRegistered() {
        withCustomer(client(), customer -> {
            final String email = customer.getEmail();
            final CustomerDraftDsl draft = CustomerDraftBuilder.of(email, "secret")
                    .build();
            final CustomerCreateCommand cmd = CustomerCreateCommand.of(draft);

            final Throwable throwable = catchThrowable(() -> client().executeBlocking(cmd));

            assertThat(throwable).isInstanceOf(ErrorResponseException.class);
            final ErrorResponseException e = (ErrorResponseException) throwable;
            assertThat(e.getErrors().get(0).getCode()).isEqualTo(DuplicateFieldError.CODE);
            final DuplicateFieldError error = e.getErrors().get(0).as(DuplicateFieldError.class);
            assertThat(error.getDuplicateValue()).isEqualToIgnoringCase(email);
            assertThat(error.getField()).isEqualTo("email");
        });
    }
}