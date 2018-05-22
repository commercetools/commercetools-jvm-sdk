package io.sphere.sdk.payments.commands;

import io.sphere.sdk.payments.Payment;
import io.sphere.sdk.payments.PaymentDraft;
import io.sphere.sdk.payments.PaymentDraftBuilder;
import io.sphere.sdk.payments.queries.PaymentByIdGet;
import io.sphere.sdk.payments.queries.PaymentByKeyGet;
import io.sphere.sdk.test.IntegrationTest;
import org.junit.Test;

import static io.sphere.sdk.test.SphereTestUtils.EURO_20;
import static io.sphere.sdk.test.SphereTestUtils.randomKey;
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

    @Test
    public void deleteByKey() {
        final String key = randomKey();
        final PaymentDraft paymentDraft = PaymentDraftBuilder.of(EURO_20).key(key).build();
        final Payment payment = client().executeBlocking(PaymentCreateCommand.of(paymentDraft));
        client().executeBlocking(PaymentDeleteCommand.ofKey(key, payment.getVersion()));

        final Payment loadedPayment = client().executeBlocking(PaymentByKeyGet.of(key));

        assertThat(loadedPayment).isNull();
    }

    @Test
    public void executionWithDataErasure() {
        final PaymentDraft paymentDraft = PaymentDraftBuilder.of(EURO_20).build();
        final Payment payment = client().executeBlocking(PaymentCreateCommand.of(paymentDraft));
        client().executeBlocking(PaymentDeleteCommand.of(payment,true));

        final Payment loadedPayment = client().executeBlocking(PaymentByIdGet.of(payment));

        assertThat(loadedPayment).isNull();
    }

    @Test
    public void deleteByKeyWithDataErasure() {
        final String key = randomKey();
        final PaymentDraft paymentDraft = PaymentDraftBuilder.of(EURO_20).key(key).build();
        final Payment payment = client().executeBlocking(PaymentCreateCommand.of(paymentDraft));
        client().executeBlocking(PaymentDeleteCommand.ofKey(key, payment.getVersion(),true));

        final Payment loadedPayment = client().executeBlocking(PaymentByKeyGet.of(key));

        assertThat(loadedPayment).isNull();
    }
}