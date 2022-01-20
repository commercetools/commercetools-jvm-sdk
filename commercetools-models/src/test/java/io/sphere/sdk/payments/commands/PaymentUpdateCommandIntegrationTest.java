package io.sphere.sdk.payments.commands;

import com.neovisionaries.i18n.CountryCode;
import io.sphere.sdk.cartdiscounts.CartDiscount;
import io.sphere.sdk.cartdiscounts.commands.CartDiscountUpdateCommand;
import io.sphere.sdk.carts.Cart;
import io.sphere.sdk.carts.CartDraft;
import io.sphere.sdk.carts.LineItem;
import io.sphere.sdk.carts.commands.CartUpdateCommand;
import io.sphere.sdk.carts.commands.updateactions.SetBillingAddressCustomField;
import io.sphere.sdk.carts.commands.updateactions.SetBillingAddressCustomType;
import io.sphere.sdk.carts.commands.updateactions.SetShippingAddressCustomField;
import io.sphere.sdk.carts.commands.updateactions.SetShippingAddressCustomType;
import io.sphere.sdk.json.TypeReferences;
import io.sphere.sdk.messages.queries.MessageQuery;
import io.sphere.sdk.models.Address;
import io.sphere.sdk.models.Asset;
import io.sphere.sdk.models.LocalizedString;
import io.sphere.sdk.payments.*;
import io.sphere.sdk.payments.commands.updateactions.*;
import io.sphere.sdk.payments.messages.PaymentInteractionAddedMessage;
import io.sphere.sdk.payments.messages.PaymentStatusStateTransitionMessage;
import io.sphere.sdk.payments.messages.PaymentTransactionAddedMessage;
import io.sphere.sdk.payments.messages.PaymentTransactionStateChangedMessage;
import io.sphere.sdk.products.Product;
import io.sphere.sdk.products.ProductVariant;
import io.sphere.sdk.products.commands.ProductUpdateCommand;
import io.sphere.sdk.products.commands.updateactions.SetAssetCustomField;
import io.sphere.sdk.products.commands.updateactions.SetAssetCustomType;
import io.sphere.sdk.queries.PagedQueryResult;
import io.sphere.sdk.queries.Query;
import io.sphere.sdk.test.IntegrationTest;
import io.sphere.sdk.types.CustomFieldsDraft;
import io.sphere.sdk.types.CustomFieldsDraftBuilder;
import io.sphere.sdk.types.Type;
import io.sphere.sdk.types.TypeFixtures;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Test;

import javax.money.MonetaryAmount;
import java.time.ZonedDateTime;
import java.util.Collections;
import java.util.HashMap;
import java.util.Objects;

import static io.sphere.sdk.cartdiscounts.CartDiscountFixtures.withCartDiscount;
import static io.sphere.sdk.carts.CartFixtures.withCartDraft;
import static io.sphere.sdk.carts.CartFixtures.withCustomerAndFilledCart;
import static io.sphere.sdk.customers.CustomerFixtures.withCustomer;
import static io.sphere.sdk.payments.PaymentFixtures.withPayment;
import static io.sphere.sdk.payments.PaymentFixtures.withPaymentTransaction;
import static io.sphere.sdk.products.ProductFixtures.withProductHavingAssets;
import static io.sphere.sdk.states.StateFixtures.withStateByBuilder;
import static io.sphere.sdk.states.StateType.PAYMENT_STATE;
import static io.sphere.sdk.test.SphereTestUtils.*;
import static io.sphere.sdk.types.TypeFixtures.STRING_FIELD_NAME;
import static io.sphere.sdk.types.TypeFixtures.withUpdateableType;
import static java.util.Collections.singletonMap;
import static org.assertj.core.api.Assertions.assertThat;

public class PaymentUpdateCommandIntegrationTest extends IntegrationTest {
    @Test
    public void setAuthorization() {
        withPayment(client(), payment -> {
            //set authorization
            final MonetaryAmount totalAmount = EURO_30;
            final ZonedDateTime until = ZonedDateTime.now().plusDays(7);
            final Payment updatedPayment = client().executeBlocking(PaymentUpdateCommand.of(payment, SetAuthorization.of(totalAmount, until)));

            assertThat(updatedPayment.getAmountAuthorized()).isEqualTo(totalAmount);
            assertThat(updatedPayment.getAuthorizedUntil()).isEqualTo(until);
            assertThat(updatedPayment.getAmountPaid()).isNull();

            //remove authorization, set amount paid
            final Payment updatedPayment2 = client().executeBlocking(PaymentUpdateCommand.of(updatedPayment, asList(SetAuthorization.ofRemove(), SetAmountPaid.of(totalAmount))));

            assertThat(updatedPayment2.getAmountAuthorized()).isNull();
            assertThat(updatedPayment2.getAuthorizedUntil()).isNull();
            assertThat(updatedPayment2.getAmountPaid()).isEqualTo(totalAmount);

            return updatedPayment2;
        });

    }

    @Test
    public void setAnonymousId() {
        withPayment(client(), payment -> {
            final String anonymousId = randomString();
            final Payment updatedPayment = client().executeBlocking(PaymentUpdateCommand.of(payment, SetAnonymousId.of(anonymousId)));

            assertThat(updatedPayment.getAnonymousId()).isEqualTo(anonymousId);

            return updatedPayment;
        });
    }

    @Test
    public void setExternalId() {
            withPayment(client(), payment -> {
                final String externalId = randomKey();

                final Payment updatedPayment = client().executeBlocking(PaymentUpdateCommand.of(payment, asList(SetExternalId.of(externalId))));

                assertThat(updatedPayment.getExternalId()).isEqualTo(externalId);

                return updatedPayment;
            });
    }

    @Test
    public void setCustomer() {
        withCustomer(client(), customer -> {
            withPayment(client(), payment -> {
                assertThat(payment.getCustomer()).isNotEqualTo(customer.toReference());

                final Payment updatedPayment = client().executeBlocking(PaymentUpdateCommand.of(payment, SetCustomer.of(customer)));

                assertThat(updatedPayment.getCustomer()).isEqualTo(customer.toReference());

                return updatedPayment;
            });
        });
    }

    @Test
    public void setCustomerByKey() {
        withCustomer(client(), customer -> {
            withPayment(client(), paymentDraftBuilder -> paymentDraftBuilder.key(randomKey()) ,payment -> {
                final Payment updatedPayment =
                        client().executeBlocking(PaymentUpdateCommand.ofKey(payment.getKey(), payment.getVersion(), SetCustomer.of(customer)));
                assertThat(updatedPayment.getCustomer()).isEqualTo(customer.toReference());

                return updatedPayment;
            });
        });
    }

    @Test
    public void refunded() {
        withPayment(client(), payment -> {

            final MonetaryAmount refundedAmount = payment.getAmountPlanned().divide(2);
            final Payment updatedPayment = client().executeBlocking(PaymentUpdateCommand.of(payment, SetAmountRefunded.of(refundedAmount)));

            assertThat(updatedPayment.getAmountRefunded()).isEqualTo(refundedAmount);

            return updatedPayment;
        });
    }

    @Test
    public void multiRefund() {
        withPayment(client(), payBuilder -> payBuilder.amountPaid(payBuilder.getAmountPlanned()), payment -> {
            final MonetaryAmount totalAmount = payment.getAmountPlanned();
            assertThat(payment.getAmountPaid()).as("amount paid").isEqualTo(totalAmount);

            final MonetaryAmount firstRefundedAmount = totalAmount.scaleByPowerOfTen(-1);
            final Payment firstRefundPayment = client().executeBlocking(PaymentUpdateCommand.of(payment, asList(SetAmountRefunded.of(firstRefundedAmount))));

            assertThat(firstRefundPayment.getAmountRefunded()).as("first refunded").isEqualTo(firstRefundedAmount);

            final MonetaryAmount secondRefundedAmount = firstRefundedAmount.multiply(2);
            //important, because SetAmountRefunded sets the total value
            final MonetaryAmount totalRefundedAmount = firstRefundPayment.getAmountRefunded().add(secondRefundedAmount);

            final Payment secondRefundPayment = client().executeBlocking(PaymentUpdateCommand.of(firstRefundPayment, asList(SetAmountRefunded.of(totalRefundedAmount))));

            assertThat(secondRefundPayment.getAmountRefunded()).as("total refunded").isEqualTo(totalRefundedAmount);

            return secondRefundPayment;
        });
    }

    @Test
    public void transitionState() {
        withStateByBuilder(client(), stateBuilder -> stateBuilder.initial(true).type(PAYMENT_STATE), validNextStateForPaymentStatus -> {
            withPayment(client(), payment -> {
                final Payment updatedPayment = client().executeBlocking(PaymentUpdateCommand.of(payment, TransitionState.of(validNextStateForPaymentStatus)));

                assertThat(updatedPayment.getPaymentStatus().getState()).isEqualTo(validNextStateForPaymentStatus.toReference());

                assertEventually(() -> {
                    final PagedQueryResult<PaymentStatusStateTransitionMessage> messageQueryResult = client().executeBlocking(MessageQuery.of()
                            .withPredicates(m -> m.resource().is(payment))
                            .forMessageType(PaymentStatusStateTransitionMessage.MESSAGE_HINT));

                    assertThat(messageQueryResult.head()).isPresent();
                    assertThat(messageQueryResult.head().get().getState()).isEqualTo(validNextStateForPaymentStatus.toReference());
                });

                return updatedPayment;
            });
        });
    }


    @Test
    public void setStatusInterfaceText() {
        withPayment(client(), payment -> {
            final String interfaceText = "Operation successful";
            final String interfaceCode = "20000";
            final Payment updatedPayment = client().executeBlocking(PaymentUpdateCommand.of(payment,
                            SetStatusInterfaceText.of(interfaceText),
                            SetStatusInterfaceCode.of(interfaceCode)
                    ));

            assertThat(updatedPayment.getPaymentStatus().getInterfaceText()).isEqualTo(interfaceText);
            assertThat(updatedPayment.getPaymentStatus().getInterfaceCode()).isEqualTo(interfaceCode);

            return updatedPayment;
        });
    }

    @Test
    public void setInterfaceId() {
        withPayment(client(), payment -> {
            final String interfaceId =randomKey();
            final Payment updatedPayment = client().executeBlocking(PaymentUpdateCommand.of(payment, SetInterfaceId.of(interfaceId)));

            assertThat(updatedPayment.getInterfaceId()).isEqualTo(interfaceId);

            return updatedPayment;
        });
    }

    @Test
    public void setMethodInfoName() {
        withPayment(client(), payment -> {
            final LocalizedString name = randomSlug();
            final Payment updatedPayment = client().executeBlocking(PaymentUpdateCommand.of(payment, SetMethodInfoName.of(name)));

            assertThat(updatedPayment.getPaymentMethodInfo().getName()).isEqualTo(name);

            return updatedPayment;
        });
    }

    @Test
    public void setMethodInfoMethod() {
        withPayment(client(), payment -> {
            final String method = "method";
            final Payment updatedPayment = client().executeBlocking(PaymentUpdateCommand.of(payment, SetMethodInfoMethod.of(method)));

            assertThat(updatedPayment.getPaymentMethodInfo().getMethod()).isEqualTo(method);

            return updatedPayment;
        });
    }

    @Test
    public void setMethodInfoInterface() {
        withPayment(client(), payment -> {
            final String methodInfoInterface = randomKey();
            final Payment updatedPayment = client().executeBlocking(PaymentUpdateCommand.of(payment, SetMethodInfoInterface.of(methodInfoInterface)));

            assertThat(updatedPayment.getPaymentMethodInfo().getPaymentInterface()).isEqualTo(methodInfoInterface);

            return updatedPayment;
        });
    }

    @Test
    public void setCustomType() {
        withUpdateableType(client(), type -> {
            withPayment(client(), payment -> {
                final SetCustomType updateAction = SetCustomType
                        .ofTypeIdAndObjects(type.getId(), singletonMap(STRING_FIELD_NAME, "foo"));
                final Payment updatedPayment = client().executeBlocking(PaymentUpdateCommand.of(payment, updateAction));

                assertThat(updatedPayment.getCustom().getFieldAsString(STRING_FIELD_NAME)).isEqualTo("foo");

                final Payment updatedPayment2 = client().executeBlocking(PaymentUpdateCommand.of(updatedPayment,
                        SetCustomField.ofObject(STRING_FIELD_NAME, "bar")));

                assertThat(updatedPayment2.getCustom().getFieldAsString(STRING_FIELD_NAME)).isEqualTo("bar");

                return updatedPayment2;
            });
            return type;
        });
    }

    @Test
    public void transActions() {
        withCustomerAndFilledCart(client(), (customer, cart) -> {
            final MonetaryAmount totalAmount = cart.getTotalPrice();
            final PaymentMethodInfo paymentMethodInfo = PaymentMethodInfoBuilder.of()
                    .paymentInterface(randomKey())
                    .method("CREDIT_CARD")
                    .build();
            final TransactionDraft chargeTransaction = TransactionDraftBuilder
                    .of(TransactionType.CHARGE, totalAmount, ZonedDateTime.now())
                    .build();
            final PaymentDraftBuilder paymentDraftBuilder = PaymentDraftBuilder.of(totalAmount)
                    .customer(customer)
                    .paymentMethodInfo(paymentMethodInfo)
                    .amountPaid(totalAmount)
                    .transactions(Collections.singletonList(chargeTransaction));
            final Payment payment = client().executeBlocking(PaymentCreateCommand.of(paymentDraftBuilder.build()));

            assertThat(payment.getCustomer()).isEqualTo(payment.getCustomer());
            assertThat(payment.getPaymentMethodInfo()).isEqualTo(paymentMethodInfo);
            assertThat(payment.getAmountPlanned()).isEqualTo(totalAmount);

            final MonetaryAmount firstRefundAmount = EURO_10;
            final TransactionDraft firstRefundTransaction = TransactionDraftBuilder.of(TransactionType.REFUND, firstRefundAmount, ZonedDateTime.now()).build();
            final Payment paymentWithFirstRefund = client().executeBlocking(PaymentUpdateCommand.of(payment, asList(SetAmountRefunded.of(firstRefundAmount), AddTransaction.of(firstRefundTransaction))));

            assertThat(paymentWithFirstRefund.getTransactions()).hasSize(2);
            assertThat(paymentWithFirstRefund.getTransactions().get(0).getId()).isNotEmpty();

            final Query<PaymentTransactionAddedMessage> messageQuery = MessageQuery.of().withPredicates(m -> m.resource().is(payment))
                    .forMessageType(PaymentTransactionAddedMessage.MESSAGE_HINT);

            assertEventually(() -> {
                final PagedQueryResult<PaymentTransactionAddedMessage> messageQueryResult = client().executeBlocking(messageQuery);
                assertThat(messageQueryResult.getTotal()).isGreaterThanOrEqualTo(1L);
                final PaymentTransactionAddedMessage paymentTransactionAddedMessage = messageQueryResult.head().get();
                assertThat(paymentTransactionAddedMessage.getTransaction().getTimestamp()).isEqualTo(firstRefundTransaction.getTimestamp());
            });

            final MonetaryAmount secondRefundAmount = EURO_5;
            final TransactionDraft secondRefundTransaction = TransactionDraftBuilder.of(TransactionType.REFUND, secondRefundAmount, ZonedDateTime.now()).build();
            final MonetaryAmount totalRefundAmount = secondRefundAmount.add(paymentWithFirstRefund.getAmountRefunded());
            final Payment paymentWithSecondRefund = client().executeBlocking(PaymentUpdateCommand.of(paymentWithFirstRefund, asList(SetAmountRefunded.of(totalRefundAmount), AddTransaction.of(secondRefundTransaction))));

            assertThat(paymentWithSecondRefund.getTransactions()).hasSize(3);
            assertThat(paymentWithSecondRefund.getAmountRefunded()).isEqualTo(totalRefundAmount);
        });
    }

    @Test
    public void addInterfaceInteraction() {
        withUpdateableType(client(), type -> {
            withPayment(client(), payment -> {
                final AddInterfaceInteraction addInterfaceInteraction = AddInterfaceInteraction.ofTypeIdAndObjects(type.getId(), singletonMap(STRING_FIELD_NAME, "some id"));
                final Payment updatedPayment = client().executeBlocking(PaymentUpdateCommand.of(payment, addInterfaceInteraction));

                assertThat(updatedPayment.getInterfaceInteractions().get(0).getFieldAsString(STRING_FIELD_NAME)).isEqualTo("some id");

                assertEventually(() -> {
                    final PagedQueryResult<PaymentInteractionAddedMessage> pagedQueryResult = client().executeBlocking(MessageQuery.of()
                            .withPredicates(m -> m.resource().is(payment))
                            .forMessageType(PaymentInteractionAddedMessage.MESSAGE_HINT));
                    assertThat(pagedQueryResult.head()).isPresent();
                    assertThat(pagedQueryResult.head().get().getInteraction().getFieldAsString(STRING_FIELD_NAME)).isEqualTo("some id");
                });

                return updatedPayment;
            });
            return type;
        });
    }

    @Test
    public void changeTransactionTimestamp() {
        withPaymentTransaction(client(), (Payment payment, Transaction transaction) -> {
            final ZonedDateTime now = ZonedDateTime.now();

            final Payment updatedPayment = client().executeBlocking(PaymentUpdateCommand.of(payment, ChangeTransactionTimestamp.of(now, transaction.getId())));

            final Transaction updatedTransaction = updatedPayment.getTransactions().get(0);
            assertThat(updatedTransaction.getTimestamp()).isEqualTo(now);

            return updatedPayment;
        });
    }

    @Test
    public void changeTransactionInteractionId() {
        withPaymentTransaction(client(), (Payment payment, Transaction transaction) -> {
            final String newInteractionId = RandomStringUtils.randomAlphanumeric(32);

            final Payment updatedPayment = client().executeBlocking(PaymentUpdateCommand.of(payment, ChangeTransactionInteractionId.of(newInteractionId, transaction.getId())));

            final Transaction updatedTransaction = updatedPayment.getTransactions().get(0);
            assertThat(updatedTransaction.getInteractionId()).isEqualTo(newInteractionId);

            return updatedPayment;
        });
    }

    @Test
    public void changeTransactionState() {
        withPaymentTransaction(client(), (Payment payment, Transaction transaction) -> {
            assertThat(transaction.getState()).isEqualTo(TransactionState.INITIAL);
            final TransactionState transactionState = TransactionState.SUCCESS;

            final Payment updatedPayment = client().executeBlocking(PaymentUpdateCommand.of(payment, ChangeTransactionState.of(transactionState, transaction.getId())));

            final Transaction updatedTransaction = updatedPayment.getTransactions().get(0);
            assertThat(updatedTransaction.getState()).isEqualTo(transactionState);

            //check messages
            assertEventually(() -> {
                final PagedQueryResult<PaymentTransactionStateChangedMessage> messageQueryResult = client().executeBlocking(MessageQuery.of()
                        .withPredicates(m -> m.resource().is(payment))
                        .forMessageType(PaymentTransactionStateChangedMessage.MESSAGE_HINT));
                assertThat(messageQueryResult.head()).isPresent();
                final PaymentTransactionStateChangedMessage message = messageQueryResult.head().get();
                assertThat(message.getState()).isEqualTo(transactionState);
                assertThat(message.getTransactionId()).isEqualTo(transaction.getId());
            });

            return updatedPayment;
        });
    }

    @Test
    public void changeAmountPlanned() {
        withPayment(client(), (Payment payment) -> {
            assertThat(payment.getAmountPlanned()).isEqualTo(EURO_20);

            final PaymentUpdateCommand cmd = PaymentUpdateCommand.of(payment, ChangeAmountPlanned.of(EURO_10));
            final Payment updatedPayment = client().executeBlocking(cmd);

            assertThat(updatedPayment.getAmountPlanned()).isEqualTo(EURO_10);

            return updatedPayment;
        });
    }

    @Test
    public void setKey() {
        withPayment(client(), (Payment payment) -> {
            final String newKey = randomKey();
            final PaymentUpdateCommand cmd = PaymentUpdateCommand.of(payment, SetKey.of(newKey));
            final Payment updatedPayment = client().executeBlocking(cmd);

            assertThat(updatedPayment.getKey()).isEqualTo(newKey);

            return updatedPayment;
        });
    }

    @Test
    public void setTransactionCustomType() throws Exception {
        TypeFixtures.withUpdateableType(client(), type -> {
            withPaymentTransaction(client(), (Payment payment, Transaction transaction) -> {
                final HashMap<String, Object> fields = new HashMap<>();
                fields.put(STRING_FIELD_NAME, "test-transaction");

                final Payment updatedPayment = client().executeBlocking(PaymentUpdateCommand.of(payment, SetTransactionCustomType.ofTypeIdAndObjects(type.getId(), fields, transaction.getId())));
                assertThat(updatedPayment.getTransactions().get(0).getId()).isEqualTo(transaction.getId());
                assertThat(updatedPayment.getTransactions().get(0).getCustom().getFieldAsString(STRING_FIELD_NAME)).isEqualTo("test-transaction");

                final Payment updatedPayment2 = client().executeBlocking(PaymentUpdateCommand.of(updatedPayment, SetTransactionCustomField.ofObject(STRING_FIELD_NAME, "a new value", transaction.getId())));
                assertThat(updatedPayment2.getTransactions().get(0).getCustom().getField(STRING_FIELD_NAME, TypeReferences.stringTypeReference()))
                        .isEqualTo("a new value");

                return updatedPayment2;
            });
            return type;
        });
    }
}