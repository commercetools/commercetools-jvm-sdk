package io.sphere.sdk.payments;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import io.sphere.sdk.annotations.*;
import io.sphere.sdk.customers.Customer;
import io.sphere.sdk.models.Reference;
import io.sphere.sdk.models.Resource;
import io.sphere.sdk.orders.Order;
import io.sphere.sdk.types.Custom;
import io.sphere.sdk.types.CustomFields;
import io.sphere.sdk.types.TypeDraft;

import javax.annotation.Nullable;
import javax.money.MonetaryAmount;
import java.time.ZonedDateTime;
import java.util.List;

/**
 * Payments hold information about the current state of receiving and/or refunding money, but the process itself is handled by a PSP. 
 * They are usually referenced by an {@link Order#getPaymentInfo() Order} or a {@link io.sphere.sdk.carts.Cart#getPaymentInfo() Cart}.
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
@ResourceValue
@HasQueryEndpoint()
@ResourceInfo(pluralName = "payments", pathElement = "payments")
@HasByIdGetEndpoint(javadocSummary = "Retrieves a payment by a known ID.", includeExamples = "io.sphere.sdk.payments.queries.PaymentByIdGetIntegrationTest#execution()")
@HasCreateCommand(includeExamples = "io.sphere.sdk.payments.commands.PaymentCreateCommandIntegrationTest#payingPerCreditCart()")
@HasUpdateCommand(javadocSummary = "Updates a payment.")
@HasDeleteCommand
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

    /**
     * A type hint for references which resource type is linked in a reference.
     * @see Reference#getTypeId()
     * @return type hint
     */
    static String referenceTypeId() {
        return "payment";
    }

    /**
     * Creates a container which contains the full Java type information to deserialize this class from JSON.
     *
     * @see io.sphere.sdk.json.SphereJsonUtils#readObject(byte[], TypeReference)
     * @see io.sphere.sdk.json.SphereJsonUtils#readObject(String, TypeReference)
     * @see io.sphere.sdk.json.SphereJsonUtils#readObject(com.fasterxml.jackson.databind.JsonNode, TypeReference)
     * @see io.sphere.sdk.json.SphereJsonUtils#readObjectFromResource(String, TypeReference)
     *
     * @return type reference
     */
    static TypeReference<Payment> typeReference() {
        return new TypeReference<Payment>() {
            @Override
            public String toString() {
                return "TypeReference<Payment>";
            }
        };
    }

    /**
     * An identifier for this resource which supports {@link CustomFields}.
     * @see TypeDraft#getResourceTypeIds()
     * @see io.sphere.sdk.types.Custom
     * @return ID of this resource type
     */
    static String resourceTypeId() {
        return "payment";
    }

    /**
     * Creates a reference for one item of this class by a known ID.
     *
     * <p>An example for categories but this applies for other resources, too:</p>
     * {@include.example io.sphere.sdk.categories.CategoryTest#referenceOfId()}
     *
     * <p>If you already have a resource object, then use {@link #toReference()} instead:</p>
     *
     * {@include.example io.sphere.sdk.categories.CategoryTest#toReference()}
     *
     * @param id the ID of the resource which should be referenced.
     * @return reference
     */
    static Reference<Payment> referenceOfId(final String id) {
        return Reference.of(referenceTypeId(), id);
    }
}
