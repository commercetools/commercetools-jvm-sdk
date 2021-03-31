package io.sphere.sdk.payments;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import io.sphere.sdk.annotations.FactoryMethod;
import io.sphere.sdk.annotations.ResourceDraftValue;
import io.sphere.sdk.customers.Customer;
import io.sphere.sdk.models.Reference;
import io.sphere.sdk.models.WithKey;
import io.sphere.sdk.types.CustomDraft;
import io.sphere.sdk.types.CustomFieldsDraft;

import javax.annotation.Nullable;
import javax.money.MonetaryAmount;
import java.time.ZonedDateTime;
import java.util.List;

/**
 * Draft for a new Payment resource.
 *
 * @see PaymentDraftBuilder
 * @see PaymentDraftDsl
 */
@JsonDeserialize(as = PaymentDraftDsl.class)
@ResourceDraftValue(
        factoryMethods = {@FactoryMethod(parameterNames = {"amountPlanned"})},
        additionalBuilderInterfaces = "io.sphere.sdk.types.CustomDraft")
public interface PaymentDraft extends CustomDraft, WithKey {
    @Nullable
    Reference<Customer> getCustomer();

    /**
     * @deprecated This field will be removed with the next major SDK update.3
     * @return the external id
     */
    @Deprecated
    @Nullable
    String getExternalId();

    @Nullable
    String getInterfaceId();

    MonetaryAmount getAmountPlanned();

    /**
     * @deprecated This field will be removed with the next major SDK update.
     * @return the authorized amount
     */
    @Deprecated
    @Nullable
    MonetaryAmount getAmountAuthorized();

    /**
     * @deprecated This field will be removed with the next major SDK update.
     * @return the
     */
    @Deprecated
    @Nullable
    ZonedDateTime getAuthorizedUntil();

    /**
     * @deprecated This field will be removed with the next major SDK update.
     */
    @Deprecated
    @Nullable
    MonetaryAmount getAmountPaid();

    /**
     * @deprecated This field will be removed with the next major SDK update.
     */
    @Deprecated
    @Nullable
    MonetaryAmount getAmountRefunded();

    @Nullable
    PaymentMethodInfo getPaymentMethodInfo();

    @Nullable
    CustomFieldsDraft getCustom();

    @Nullable
    PaymentStatus getPaymentStatus();

    @Nullable
    List<TransactionDraft> getTransactions();

    @Nullable
    List<CustomFieldsDraft> getInterfaceInteractions();


    /**
     * User-specific unique identifier for the payment.
     *
     * @return the user defined key
     */
    @Nullable
    String getKey();

    /**
     * Identifies payments belonging to an anonymous session (the customer has not signed up/in yet).
     *
     * @return the anonymous id of this payment
     */
    @Nullable
    String getAnonymousId();
}
