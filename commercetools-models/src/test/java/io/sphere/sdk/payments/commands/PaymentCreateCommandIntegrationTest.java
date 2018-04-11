package io.sphere.sdk.payments.commands;

import io.sphere.sdk.customers.CustomerFixtures;
import io.sphere.sdk.messages.queries.MessageQuery;
import io.sphere.sdk.payments.*;
import io.sphere.sdk.payments.messages.PaymentCreatedMessage;
import io.sphere.sdk.queries.PagedQueryResult;
import io.sphere.sdk.test.IntegrationTest;
import io.sphere.sdk.types.CustomFieldsDraft;
import io.sphere.sdk.types.TypeFixtures;
import org.assertj.core.api.Assertions;
import org.junit.Test;

import javax.money.MonetaryAmount;
import java.time.ZonedDateTime;
import java.util.Collections;
import java.util.List;

import static io.sphere.sdk.customers.CustomerFixtures.withUpdateableCustomer;
import static io.sphere.sdk.states.StateFixtures.withStateByBuilder;
import static io.sphere.sdk.states.StateType.PAYMENT_STATE;
import static io.sphere.sdk.test.SphereTestUtils.*;
import static java.util.Collections.singletonMap;
import static java.util.stream.Collectors.toList;
import static org.assertj.core.api.Assertions.assertThat;

public class PaymentCreateCommandIntegrationTest extends IntegrationTest {

    @Test
    public void withAnonymousId() {
        CustomerFixtures.withCustomerAndCart(client(), ((customer, cart) -> {
            final MonetaryAmount totalAmount = cart.getTotalPrice();
            final PaymentMethodInfo paymentMethodInfo = PaymentMethodInfoBuilder.of()
                    .paymentInterface("payment interface X")
                    .method("CREDIT_CARD")
                    .build();

            final String anonymousId = randomString();
            final PaymentDraftBuilder paymentDraftBuilder = PaymentDraftBuilder.of(totalAmount)
                    .paymentMethodInfo(paymentMethodInfo)
                    .anonymousId(anonymousId);
            final Payment payment = client().executeBlocking(PaymentCreateCommand.of(paymentDraftBuilder.build()));

            assertThat(payment.getAnonymousId()).isEqualTo(anonymousId);
            assertThat(payment.getPaymentMethodInfo()).isEqualTo(paymentMethodInfo);
            assertThat(payment.getAmountPlanned()).isEqualTo(totalAmount);

            client().executeBlocking(PaymentDeleteCommand.of(payment));
        }));
    }

    @Test
    public void payingPerCreditCart() {
        CustomerFixtures.withCustomerAndCart(client(), ((customer, cart) -> {
            final MonetaryAmount totalAmount = cart.getTotalPrice();
            final PaymentMethodInfo paymentMethodInfo = PaymentMethodInfoBuilder.of()
                    .paymentInterface("payment interface X")
                    .method("CREDIT_CARD")
                    .build();
            final PaymentDraftBuilder paymentDraftBuilder = PaymentDraftBuilder.of(totalAmount)
                    .customer(customer)
                    .paymentMethodInfo(paymentMethodInfo)
                    ;
            final Payment payment = client().executeBlocking(PaymentCreateCommand.of(paymentDraftBuilder.build()));

            assertThat(payment.getCustomer()).isEqualTo(payment.getCustomer());
            assertThat(payment.getPaymentMethodInfo()).isEqualTo(paymentMethodInfo);
            Assertions.assertThat(payment.getAmountPlanned()).isEqualTo(totalAmount);

            assertEventually(() -> {
                final PagedQueryResult<PaymentCreatedMessage> pagedQueryResult = client().executeBlocking(MessageQuery.of()
                        .withPredicates(m -> m.resource().is(payment))
                        .forMessageType(PaymentCreatedMessage.MESSAGE_HINT));

                assertThat(pagedQueryResult.head()).isPresent();
                final PaymentCreatedMessage paymentCreatedMessage = pagedQueryResult.head().get();
                assertThat(paymentCreatedMessage.getPayment().getId()).isEqualTo(payment.getId());
                assertThat(paymentCreatedMessage.getResource().getId()).isEqualTo(payment.getId());
            });

            client().executeBlocking(PaymentDeleteCommand.of(payment));
        }));
    }

    @Test
    public void invoice() {
        withUpdateableCustomer(client(), customer -> {
            withStateByBuilder(client(), stateBuilder -> stateBuilder.initial(true).type(PAYMENT_STATE), paidState -> {
                final MonetaryAmount totalAmount = EURO_30;
                final PaymentMethodInfo paymentMethodInfo = PaymentMethodInfoBuilder.of()
                        .method("INVOICE")
                        .name(en("invoice payment"))
                        .build();
                final PaymentStatus paymentStatus = PaymentStatusBuilder.of()
                        .interfaceCode("300")
                        .interfaceText("received invoice")
                        .state(paidState).build();
                final String interfaceId = randomKey();
                final String interactionId = randomKey();
                final List<TransactionDraft> transactions = Collections.singletonList(TransactionDraftBuilder
                        .of(TransactionType.CHARGE, totalAmount, ZonedDateTime.now())
                        .timestamp(ZonedDateTime.now())
                        .interactionId(interactionId)
                        .build());
                final PaymentDraftBuilder paymentDraftBuilder = PaymentDraftBuilder.of(totalAmount)
                        .interfaceId(interfaceId)
                        .amountPaid(totalAmount)
                        .customer(customer)
                        .paymentMethodInfo(paymentMethodInfo)
                        .paymentStatus(paymentStatus)
                        .transactions(transactions);
                final Payment payment = client().executeBlocking(PaymentCreateCommand.of(paymentDraftBuilder.build()));

                assertThat(payment.getCustomer()).isEqualTo(payment.getCustomer());
                assertThat(payment.getPaymentMethodInfo()).isEqualTo(paymentMethodInfo);
                Assertions.assertThat(payment.getAmountPlanned()).isEqualTo(totalAmount);
                Assertions.assertThat(payment.getAmountPaid()).isEqualTo(totalAmount);
                assertThat(payment.getPaymentStatus()).isEqualTo(paymentStatus);
                assertThat(payment.getInterfaceId()).isEqualTo(interfaceId);
                assertThat(payment.getTransactions().get(0).getTimestamp()).isEqualTo(transactions.get(0).getTimestamp());

                client().executeBlocking(PaymentDeleteCommand.of(payment));
            });
            return customer;
        });
    }

    @Test
    public void fullTest() {
        withStateByBuilder(client(), stateBuilder -> stateBuilder.initial(true).type(PAYMENT_STATE), paidState -> {
            TypeFixtures.withUpdateableType(client(), type -> {
                CustomerFixtures.withCustomerAndCart(client(), ((customer, cart) -> {
                    final MonetaryAmount totalAmount = cart.getTotalPrice();
                    final PaymentStatus paymentStatus = PaymentStatusBuilder.of().interfaceCode(randomKey()).interfaceText(randomString()).state(paidState).build();
                    final PaymentMethodInfo paymentMethodInfo = PaymentMethodInfoBuilder.of()
                            .paymentInterface("payment interface X")
                            .method("CREDIT_CARD")
                            .name(randomSlug())
                            .build();
                    final TransactionDraft transactionDraft = TransactionDraftBuilder
                            .of(TransactionType.CHARGE, totalAmount, ZonedDateTime.now())
                            .timestamp(ZonedDateTime.now())
                            .interactionId(randomKey())
                            .state(TransactionState.PENDING)
                            .build();
                    final List<TransactionDraft> transactions = Collections.singletonList(transactionDraft);
                    final String externalId = randomKey();
                    final String interfaceId = randomKey();
                    final ZonedDateTime authorizedUntil = ZonedDateTime.now().plusMonths(1);
                    final PaymentDraftBuilder paymentDraftBuilder = PaymentDraftBuilder.of(totalAmount)
                            .customer(customer)
                            .externalId(externalId)
                            .interfaceId(interfaceId)
                            .amountAuthorized(totalAmount)
                            .amountPaid(totalAmount)
                            .authorizedUntil(authorizedUntil)
                            .amountRefunded(EURO_1)
                            .paymentMethodInfo(paymentMethodInfo)
                            .custom(CustomFieldsDraft.ofTypeKeyAndObjects(type.getKey(), singletonMap(TypeFixtures.STRING_FIELD_NAME, "foo")))
                            .paymentStatus(paymentStatus)
                            .transactions(transactions)
                            .interfaceInteractions(asList("foo1", "foo2").stream()
                                    .map(s -> CustomFieldsDraft.ofTypeKeyAndObjects(type.getKey(), singletonMap(TypeFixtures.STRING_FIELD_NAME, s)))
                                    .collect(toList()));
                    final Payment payment = client().executeBlocking(PaymentCreateCommand.of(paymentDraftBuilder.build()));

                    softAssert(s -> {
                        s.assertThat(payment.getCustomer()).isEqualTo(payment.getCustomer());
                        s.assertThat(payment.getExternalId()).isEqualTo(externalId);
                        s.assertThat(payment.getInterfaceId()).isEqualTo(interfaceId);
                        s.assertThat(payment.getAmountPlanned()).as("planned").isEqualTo(totalAmount);
                        s.assertThat(payment.getAmountAuthorized()).as("authorized").isEqualTo(totalAmount);
                        s.assertThat(payment.getAuthorizedUntil()).isEqualTo(authorizedUntil);
                        s.assertThat(payment.getAmountPaid()).as("paid").isEqualTo(totalAmount);
                        s.assertThat(payment.getAmountRefunded()).as("refunded").isEqualTo(EURO_1);
                        s.assertThat(payment.getPaymentMethodInfo()).isEqualTo(paymentMethodInfo);
                        s.assertThat(payment.getCustom().getFieldAsString(TypeFixtures.STRING_FIELD_NAME)).isEqualTo("foo");
                        s.assertThat(payment.getPaymentStatus()).isEqualTo(paymentStatus);
                        final Transaction transaction = payment.getTransactions().get(0);
                        s.assertThat(transaction.getTimestamp()).isEqualTo(transactionDraft.getTimestamp());
                        s.assertThat(transaction.getAmount()).isEqualTo(transactionDraft.getAmount());
                        s.assertThat(transaction.getInteractionId()).isEqualTo(transactionDraft.getInteractionId());
                        s.assertThat(transaction.getType()).isEqualTo(transactionDraft.getType());
                        s.assertThat(transaction.getState()).isEqualTo(transactionDraft.getState());
                        s.assertThat(payment.getInterfaceInteractions().get(0).getFieldAsString(TypeFixtures.STRING_FIELD_NAME)).isEqualTo("foo1");
                        s.assertThat(payment.getInterfaceInteractions().get(1).getFieldAsString(TypeFixtures.STRING_FIELD_NAME)).isEqualTo("foo2");
                    });
                    client().executeBlocking(PaymentDeleteCommand.of(payment));
                }));
                return type;
            });
        });
    }
}