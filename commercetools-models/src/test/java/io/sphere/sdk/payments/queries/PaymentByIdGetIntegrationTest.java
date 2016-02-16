package io.sphere.sdk.payments.queries;

import io.sphere.sdk.payments.Payment;
import io.sphere.sdk.test.IntegrationTest;
import org.junit.Test;

import static io.sphere.sdk.payments.PaymentFixtures.withPayment;
import static org.assertj.core.api.Assertions.assertThat;

public class PaymentByIdGetIntegrationTest extends IntegrationTest {
    @Test
    public void execution() {
        withPayment(client(), payment -> {
            final String paymentId = payment.getId();

            final Payment loadedPayment = client().executeBlocking(PaymentByIdGet.of(paymentId));

            assertThat(loadedPayment).isEqualTo(payment);
            return payment;
        });

    }
}