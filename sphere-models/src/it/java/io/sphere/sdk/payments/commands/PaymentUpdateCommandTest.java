package io.sphere.sdk.payments.commands;

import io.sphere.sdk.customers.CustomerFixtures;
import io.sphere.sdk.models.LocalizedString;
import io.sphere.sdk.payments.*;
import io.sphere.sdk.payments.commands.updateactions.*;
import io.sphere.sdk.states.State;
import io.sphere.sdk.test.IntegrationTest;
import io.sphere.sdk.utils.MoneyImpl;
import org.junit.Test;

import javax.money.MonetaryAmount;
import java.time.ZonedDateTime;
import java.util.Collections;

import static io.sphere.sdk.carts.CartFixtures.withCustomerAndFilledCart;
import static io.sphere.sdk.customers.CustomerFixtures.withCustomer;
import static io.sphere.sdk.payments.PaymentFixtures.withPayment;
import static io.sphere.sdk.states.StateFixtures.withStateByBuilder;
import static io.sphere.sdk.states.StateType.PAYMENT_STATE;
import static io.sphere.sdk.test.SphereTestUtils.*;
import static org.assertj.core.api.Assertions.assertThat;

public class PaymentUpdateCommandTest extends IntegrationTest {
    @Test
    public void setAuthorization() {
        withPayment(client(), payment -> {
            //set authorization
            final MonetaryAmount totalAmount = EURO_30;
            final ZonedDateTime until = ZonedDateTime.now().plusDays(7);
            final Payment updatedPayment = execute(PaymentUpdateCommand.of(payment, SetAuthorization.of(totalAmount, until)));

            assertThat(updatedPayment.getAmountAuthorized()).isEqualTo(totalAmount);
            assertThat(updatedPayment.getAuthorizedUntil()).isEqualTo(until);
            assertThat(updatedPayment.getAmountPaid()).isNull();


            final Payment updatedPayment2 = execute(PaymentUpdateCommand.of(updatedPayment, asList(SetAuthorization.ofRemove(), SetAmountPaid.of(totalAmount))));

            assertThat(updatedPayment2.getAmountAuthorized()).isNull();
            assertThat(updatedPayment2.getAuthorizedUntil()).isNull();
            assertThat(updatedPayment2.getAmountPaid()).isEqualTo(totalAmount);

            return updatedPayment2;
        });

    }

    @Test
    public void setExternalId() {
            withPayment(client(), payment -> {
                final String externalId = randomKey();

                final Payment updatedPayment = execute(PaymentUpdateCommand.of(payment, asList(SetExternalId.of(externalId))));

                assertThat(updatedPayment.getExternalId()).isEqualTo(externalId);

                return updatedPayment;
            });
    }

    @Test
    public void setCustomer() {
        withCustomer(client(), customer -> {
            withPayment(client(), payment -> {
                assertThat(payment.getCustomer()).isNotEqualTo(customer.toReference());

                final Payment updatedPayment = execute(PaymentUpdateCommand.of(payment, SetCustomer.of(customer)));

                assertThat(updatedPayment.getCustomer()).isEqualTo(customer.toReference());

                return updatedPayment;
            });
        });
    }

    @Test
    public void refunded() {
        withPayment(client(), payment -> {

            final MonetaryAmount refundedAmount = payment.getAmountPlanned().divide(2);
            final Payment updatedPayment = execute(PaymentUpdateCommand.of(payment, SetAmountRefunded.of(refundedAmount)));

            assertThat(updatedPayment.getAmountRefunded()).isEqualTo(refundedAmount);

            return updatedPayment;
        });
    }

    @Test
    public void multiRefund() {
        withPayment(client(), payBuilder -> payBuilder.amountPaid(payBuilder.getAmountPlanned()), payment -> {
            final MonetaryAmount totalAmount = payment.getAmountPlanned();
            assertThat(payment.getAmountPaid()).as("amount paid").isEqualTo(totalAmount);

            final MonetaryAmount firstRefoundedAmount = totalAmount.scaleByPowerOfTen(-1);
            final Payment firstRefoundPayment = execute(PaymentUpdateCommand.of(payment, asList(SetAmountRefunded.of(firstRefoundedAmount))));

            assertThat(firstRefoundPayment.getAmountRefunded()).as("first refunded").isEqualTo(firstRefoundedAmount);

            final MonetaryAmount secondRefundedAmount = firstRefoundedAmount.multiply(2);
            //important, because SetAmountRefunded sets the total value
            final MonetaryAmount totalRefundedAmount = firstRefoundPayment.getAmountRefunded().add(secondRefundedAmount);

            final Payment secondRefundPayment = execute(PaymentUpdateCommand.of(firstRefoundPayment, asList(SetAmountRefunded.of(totalRefundedAmount))));

            assertThat(secondRefundPayment.getAmountRefunded()).as("total refunded").isEqualTo(totalRefundedAmount);

            return secondRefundPayment;
        });
    }

    @Test
    public void transitionState() {
        withStateByBuilder(client(), stateBuilder -> stateBuilder.initial(true).type(PAYMENT_STATE), validNextStateForPaymentStatus -> {
            withPayment(client(), payment -> {
                final Payment updatedPayment = execute(PaymentUpdateCommand.of(payment, TransitionState.of(validNextStateForPaymentStatus)));

                assertThat(updatedPayment.getPaymentStatus().getState()).isEqualTo(validNextStateForPaymentStatus.toReference());

                return updatedPayment;
            });
        });
    }


    @Test
    public void setStatusInterfaceText() {
        withPayment(client(), payment -> {
            final String interfaceText = "Operation successful";
            final String interfaceCode = "20000";
            final Payment updatedPayment = execute(PaymentUpdateCommand.of(payment,
                    asList(
                            SetStatusInterfaceText.of(interfaceText),
                            SetStatusInterfaceCode.of(interfaceCode)
                    )));

            assertThat(updatedPayment.getPaymentStatus().getInterfaceText()).isEqualTo(interfaceText);
            assertThat(updatedPayment.getPaymentStatus().getInterfaceCode()).isEqualTo(interfaceCode);

            return updatedPayment;
        });
    }

    @Test
    public void setInterfaceId() {
        withPayment(client(), payment -> {
            final String interfaceId =randomKey();
            final Payment updatedPayment = execute(PaymentUpdateCommand.of(payment, SetInterfaceId.of(interfaceId)));

            assertThat(updatedPayment.getInterfaceId()).isEqualTo(interfaceId);

            return updatedPayment;
        });
    }

    @Test
    public void setMethodInfoName() {
        withPayment(client(), payment -> {
            final LocalizedString name = randomSlug();
            final Payment updatedPayment = execute(PaymentUpdateCommand.of(payment, SetMethodInfoName.of(name)));

            assertThat(updatedPayment.getPaymentMethodInfo().getName()).isEqualTo(name);

            return updatedPayment;
        });
    }

    @Test
    public void setMethodInfoMethod() {
        withPayment(client(), payment -> {
            final String method = "method";
            final Payment updatedPayment = execute(PaymentUpdateCommand.of(payment, SetMethodInfoMethod.of(method)));

            assertThat(updatedPayment.getPaymentMethodInfo().getMethod()).isEqualTo(method);

            return updatedPayment;
        });
    }

    @Test
    public void setMethodInfoInterface() {
        withPayment(client(), payment -> {
            final String stripe = "STRIPE";
            final Payment updatedPayment = execute(PaymentUpdateCommand.of(payment, SetMethodInfoInterface.of(stripe)));

            assertThat(updatedPayment.getPaymentMethodInfo().getPaymentInterface()).isEqualTo(stripe);

            return updatedPayment;
        });
    }

    @Test
    public void transActions() {
        withCustomerAndFilledCart(client(), (customer, cart) -> {
            final MonetaryAmount totalAmount = cart.getTotalPrice();
            final PaymentMethodInfo paymentMethodInfo = PaymentMethodInfoBuilder.of()
                    .paymentInterface("STRIPE")
                    .method("CREDIT_CARD")
                    .build();
            final Transaction chargeTransaction = TransactionBuilder
                    .of(TransactionType.CHARGE, totalAmount, ZonedDateTime.now())
                    .build();
            final PaymentDraftBuilder paymentDraftBuilder = PaymentDraftBuilder.of(totalAmount)
                    .customer(customer)
                    .paymentMethodInfo(paymentMethodInfo)
                    .amountPaid(totalAmount)
                    .transactions(Collections.singletonList(chargeTransaction));
            final Payment payment = execute(PaymentCreateCommand.of(paymentDraftBuilder.build()));

            assertThat(payment.getCustomer()).isEqualTo(payment.getCustomer());
            assertThat(payment.getPaymentMethodInfo()).isEqualTo(paymentMethodInfo);
            assertThat(payment.getAmountPlanned()).isEqualTo(totalAmount);

            final MonetaryAmount firstRefundAmount = EURO_10;
            final Transaction firstRefundTransaction = TransactionBuilder.of(TransactionType.REFUND, firstRefundAmount, ZonedDateTime.now()).build();
            final Payment paymentWithFirstRefund = execute(PaymentUpdateCommand.of(payment, asList(SetAmountRefunded.of(firstRefundAmount), AddTransaction.of(firstRefundTransaction))));

            assertThat(paymentWithFirstRefund.getTransactions()).contains(chargeTransaction, firstRefundTransaction);


            final MonetaryAmount secondRefundAmount = EURO_5;
            final Transaction secondRefundTransaction = TransactionBuilder.of(TransactionType.REFUND, secondRefundAmount, ZonedDateTime.now()).build();
            final MonetaryAmount totalRefundAmount = secondRefundAmount.add(paymentWithFirstRefund.getAmountRefunded());
            final Payment paymentWithSecondRefund = execute(PaymentUpdateCommand.of(paymentWithFirstRefund, asList(SetAmountRefunded.of(totalRefundAmount), AddTransaction.of(secondRefundTransaction))));

            assertThat(paymentWithSecondRefund.getTransactions()).contains(chargeTransaction, firstRefundTransaction, secondRefundTransaction);
            assertThat(paymentWithSecondRefund.getAmountRefunded()).isEqualTo(totalRefundAmount);
        });
    }
}