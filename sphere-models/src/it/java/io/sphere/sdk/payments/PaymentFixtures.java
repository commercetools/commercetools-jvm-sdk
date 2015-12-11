package io.sphere.sdk.payments;

import io.sphere.sdk.client.BlockingSphereClient;
import io.sphere.sdk.payments.commands.PaymentCreateCommand;
import io.sphere.sdk.payments.commands.PaymentDeleteCommand;

import java.util.function.BiFunction;
import java.util.function.UnaryOperator;

import static io.sphere.sdk.test.SphereTestUtils.EURO_1;
import static io.sphere.sdk.test.SphereTestUtils.EURO_20;
import static java.util.Collections.singletonList;

public class PaymentFixtures {
    public static void withPayment(final BlockingSphereClient client, final UnaryOperator<PaymentDraftBuilder> builderMapping, final UnaryOperator<Payment> op) {
        final PaymentDraft paymentDraft = builderMapping.apply(PaymentDraftBuilder.of(EURO_20)).build();
        final Payment payment = client.executeBlocking(PaymentCreateCommand.of(paymentDraft));
        final Payment paymentToDelete = op.apply(payment);
        client.executeBlocking(PaymentDeleteCommand.of(paymentToDelete));
    }

    public static void withPayment(final BlockingSphereClient client, final UnaryOperator<Payment> op) {
        withPayment(client, a -> a, op);
    }

    public static void withPaymentTransaction(final BlockingSphereClient client, final BiFunction<Payment, Transaction, Payment> operation) {
        final TransactionDraft transactionDraft = TransactionDraftBuilder.of(TransactionType.CHARGE, EURO_1).build();
        final PaymentDraft paymentDraft = PaymentDraftBuilder.of(EURO_1)
                .transactions(singletonList(transactionDraft))
                .build();
        final Payment payment = client.executeBlocking(PaymentCreateCommand.of(paymentDraft));
        final Transaction transaction = payment.getTransactions().get(0);
        final Payment paymentToDelete = operation.apply(payment, transaction);
        client.executeBlocking(PaymentDeleteCommand.of(paymentToDelete));
    }
}
