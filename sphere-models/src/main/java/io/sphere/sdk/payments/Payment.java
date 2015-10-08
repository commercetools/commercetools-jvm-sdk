package io.sphere.sdk.payments;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import io.sphere.sdk.customers.Customer;
import io.sphere.sdk.models.Reference;
import io.sphere.sdk.models.Resource;
import io.sphere.sdk.types.Custom;
import io.sphere.sdk.types.CustomFields;

import javax.annotation.Nullable;
import javax.money.MonetaryAmount;
import java.time.ZonedDateTime;
import java.util.List;

@JsonDeserialize(as = PaymentImpl.class)
public interface Payment extends Resource<Payment>, Custom {
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

    @Override
    @Nullable
    CustomFields getCustom();

    PaymentStatus getPaymentStatus();

    List<Transaction> getTransactions();

    List<CustomFields> getInterfaceInteractions();

    @Override
    default Reference<Payment> toReference() {
        return Reference.of(referenceTypeId(), getId(), this);
    }

    static String referenceTypeId() {
        return "payment";
    }

    static TypeReference<Payment> typeReference() {
        return new TypeReference<Payment>() {
            @Override
            public String toString() {
                return "TypeReference<Payment>";
            }
        };
    }
}
