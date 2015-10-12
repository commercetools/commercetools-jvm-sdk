package io.sphere.sdk.payments;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import io.sphere.sdk.customers.Customer;
import io.sphere.sdk.models.Reference;
import io.sphere.sdk.models.Resource;
import io.sphere.sdk.orders.Order;
import io.sphere.sdk.types.Custom;
import io.sphere.sdk.types.CustomFields;

import javax.annotation.Nullable;
import javax.money.MonetaryAmount;
import java.time.ZonedDateTime;
import java.util.List;

/**
 * Payments hold information about the current state of receiving and/or refunding money, but the process itself is handled by a PSP. They should always reference a Customer. They are usually referenced by an Order or a Cart.
 *
 * @see io.sphere.sdk.payments.commands.PaymentCreateCommand
 * @see io.sphere.sdk.payments.commands.PaymentUpdateCommand
 * @see io.sphere.sdk.payments.commands.PaymentDeleteCommand
 * @see io.sphere.sdk.payments.queries.PaymentByIdGet
 * @see io.sphere.sdk.payments.queries.PaymentQuery
 * @see Order#getPaymentInfo()
 * @see io.sphere.sdk.carts.Cart#getPaymentInfo()
 */
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

    static String resourceTypeId() {
        return "payment";
    }
}
