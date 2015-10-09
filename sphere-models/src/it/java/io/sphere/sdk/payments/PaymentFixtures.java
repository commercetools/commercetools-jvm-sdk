package io.sphere.sdk.payments;

import io.sphere.sdk.client.TestClient;
import io.sphere.sdk.payments.commands.PaymentCreateCommand;
import io.sphere.sdk.payments.commands.PaymentDeleteCommand;

import java.util.function.Function;
import java.util.function.UnaryOperator;

import static io.sphere.sdk.test.SphereTestUtils.*;

public class PaymentFixtures {
    public static void withPayment(final TestClient client, final UnaryOperator<PaymentDraftBuilder> builderMapping, final UnaryOperator<Payment> op) {
        final PaymentDraft paymentDraft = builderMapping.apply(PaymentDraftBuilder.of(EURO_20)).build();
        final Payment payment = client.execute(PaymentCreateCommand.of(paymentDraft));
        final Payment paymentToDelete = op.apply(payment);
        client.execute(PaymentDeleteCommand.of(paymentToDelete));
    }

    public static void withPayment(final TestClient client, final UnaryOperator<Payment> op) {
        withPayment(client, a -> a, op);
    }
}
