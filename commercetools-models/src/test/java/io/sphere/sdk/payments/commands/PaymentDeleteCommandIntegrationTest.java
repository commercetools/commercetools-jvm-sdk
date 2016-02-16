package io.sphere.sdk.payments.commands;

import io.sphere.sdk.payments.Payment;
import io.sphere.sdk.payments.PaymentDraft;
import io.sphere.sdk.payments.PaymentDraftBuilder;
import io.sphere.sdk.payments.queries.PaymentByIdGet;
import io.sphere.sdk.test.IntegrationTest;
import org.junit.Test;

import static io.sphere.sdk.test.SphereTestUtils.EURO_20;
import static org.assertj.core.api.Assertions.assertThat;

public class PaymentDeleteCommandIntegrationTest extends IntegrationTest {
    @Test
    public void execution() {
        final PaymentDraft paymentDraft = PaymentDraftBuilder.of(EURO_20).build();
        final Payment payment = client().executeBlocking(PaymentCreateCommand.of(paymentDraft));
        client().executeBlocking(PaymentDeleteCommand.of(payment));

        final Payment loadedPayment = client().executeBlocking(PaymentByIdGet.of(payment));

        assertThat(loadedPayment).isNull();
    }
}