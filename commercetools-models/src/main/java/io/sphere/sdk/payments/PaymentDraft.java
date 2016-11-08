package io.sphere.sdk.payments;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import io.sphere.sdk.annotations.FactoryMethod;
import io.sphere.sdk.annotations.ResourceDraftValue;
import io.sphere.sdk.customers.Customer;
import io.sphere.sdk.models.Reference;
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
@ResourceDraftValue(gettersForBuilder = true, factoryMethods = {
        @FactoryMethod(parameterNames = {"amountPlanned"})
})
public interface PaymentDraft extends CustomDraft {
    @Nullable
    Reference<Customer> getCustomer();

    @Nullable
    String getExternalId();

    @Nullable
    String getInterfaceId();

    MonetaryAmount getAmountPlanned();

    @Nullable
    MonetaryAmount getAmountAuthorized();

    @Nullable
    ZonedDateTime getAuthorizedUntil();

    @Nullable
    MonetaryAmount getAmountPaid();

    @Nullable
    MonetaryAmount getAmountRefunded();

    PaymentMethodInfo getPaymentMethodInfo();

    @Nullable
    CustomFieldsDraft getCustom();

    PaymentStatus getPaymentStatus();

    List<TransactionDraft> getTransactions();

    List<CustomFieldsDraft> getInterfaceInteractions();
}
