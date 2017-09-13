package io.sphere.sdk.payments.queries;

import io.sphere.sdk.payments.Payment;
import io.sphere.sdk.test.IntegrationTest;
import org.junit.Test;

import static io.sphere.sdk.payments.PaymentFixtures.withPayment;
import static io.sphere.sdk.test.SphereTestUtils.randomKey;
import static org.assertj.core.api.Assertions.assertThat;

public class PaymentByKeyGetIntegrationTest extends IntegrationTest {
    @Test
    public void execution() {
        withPayment(client(), paymentDraftBuilder -> paymentDraftBuilder.key(randomKey()), payment -> {
            final String key = payment.getKey();

            final Payment loadedPayment = client().executeBlocking(PaymentByKeyGet.of(key));

            assertThat(loadedPayment).isEqualTo(payment);
            return payment;
        });

    }
}