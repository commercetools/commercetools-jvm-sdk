package io.sphere.sdk.orders.messages;

import io.sphere.sdk.messages.queries.MessageQuery;
import io.sphere.sdk.payments.Payment;
import io.sphere.sdk.payments.commands.PaymentUpdateCommand;
import io.sphere.sdk.payments.commands.updateactions.SetStatusInterfaceCode;
import io.sphere.sdk.payments.commands.updateactions.SetStatusInterfaceText;
import io.sphere.sdk.payments.messages.PaymentStatusInterfaceCodeSetMessage;
import io.sphere.sdk.queries.PagedQueryResult;
import io.sphere.sdk.queries.Query;
import io.sphere.sdk.test.IntegrationTest;
import org.junit.Test;

import java.util.Optional;

import static io.sphere.sdk.payments.PaymentFixtures.withPayment;
import static io.sphere.sdk.test.SphereTestUtils.asList;
import static io.sphere.sdk.test.SphereTestUtils.assertEventually;
import static org.assertj.core.api.Assertions.assertThat;


public class PaymentStatusInterfaceCodeSetMessageIntegrationTest extends IntegrationTest {


    @Test
    public void setStatusInterfaceText() {
        withPayment(client(), payment -> {
            final String interfaceText = "Operation successful";
            final String interfaceCode = "20000";
            final Payment updatedPayment = client().executeBlocking(PaymentUpdateCommand.of(payment,
                    asList(
                            SetStatusInterfaceText.of(interfaceText),
                            SetStatusInterfaceCode.of(interfaceCode)
                    )));

            assertThat(updatedPayment.getPaymentStatus().getInterfaceText()).isEqualTo(interfaceText);
            assertThat(updatedPayment.getPaymentStatus().getInterfaceCode()).isEqualTo(interfaceCode);

            final Query<PaymentStatusInterfaceCodeSetMessage> query =
                    MessageQuery.of()
                            .withPredicates(m -> m.resource().id().is(updatedPayment.getId()))
                            .withSort(m -> m.createdAt().sort().desc())
                            .withExpansionPaths(m -> m.resource())
                            .withLimit(1L)
                            .forMessageType(PaymentStatusInterfaceCodeSetMessage.MESSAGE_HINT);

            assertEventually(() -> {
                final PagedQueryResult<PaymentStatusInterfaceCodeSetMessage> pagedQueryResult = client().executeBlocking(query);
                final Optional<PaymentStatusInterfaceCodeSetMessage> optMessage = pagedQueryResult.head();
                assertThat(optMessage).isPresent();
                PaymentStatusInterfaceCodeSetMessage message = optMessage.get();
                assertThat(message.getInterfaceCode()).isEqualTo(interfaceCode);
                assertThat(message.getResource().getId()).isEqualTo(updatedPayment.getId());
            });

            return updatedPayment;
        });
    }
}
