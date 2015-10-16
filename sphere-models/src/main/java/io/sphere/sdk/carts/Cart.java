package io.sphere.sdk.carts;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.neovisionaries.i18n.CountryCode;
import io.sphere.sdk.customergroups.CustomerGroup;
import io.sphere.sdk.discountcodes.DiscountCodeInfo;
import io.sphere.sdk.models.Address;
import io.sphere.sdk.models.Reference;
import io.sphere.sdk.types.CustomFields;

import javax.annotation.Nullable;
import javax.money.MonetaryAmount;
import java.util.List;

/**
 <p>A shopping cart holds product variants and can be ordered.</p> Each cart either belongs to a registered customer or is an anonymous cart ({@link #getCustomerId()} yields null).

 <p>A cart can have {@link io.sphere.sdk.types.Custom custom fields}.</p>
 */
@JsonDeserialize(as=CartImpl.class)
public interface Cart extends CartLike<Cart> {

    static String resourceTypeId() {
        return "order";//sic http://dev.sphere.io/http-api-projects-custom-fields.html#customizable-resource
    }

    static String referenceTypeId() {
        return "cart";
    }

    /**
     *
     * @deprecated use {@link #referenceTypeId()} instead
     * @return referenceTypeId
     */
    @Deprecated
    static String typeId() {
        return "cart";
    }

    static TypeReference<Cart> typeReference() {
        return new TypeReference<Cart>() {
            @Override
            public String toString() {
                return "TypeReference<Cart>";
            }
        };
    }

    @Override
    default Reference<Cart> toReference() {
        return Reference.of(referenceTypeId(), getId(), this);
    }

    CartState getCartState();

    InventoryMode getInventoryMode();

    @Nullable
    CartShippingInfo getShippingInfo();

    @Nullable
    @Override
    Address getBillingAddress();

    @Override
    @Nullable
    CountryCode getCountry();

    @Override
    @Nullable
    String getCustomerEmail();

    @Override
    @Nullable
    Reference<CustomerGroup> getCustomerGroup();

    /**
     * The id of the customer possessing the cart. Can be null so it is then an anonymous cart.
     * @return the id of the corresponding customer or null
     */
    @Override
    @Nullable
    String getCustomerId();

    @Override
    List<CustomLineItem> getCustomLineItems();

    @Override
    List<LineItem> getLineItems();

    @Override
    @Nullable
    Address getShippingAddress();

    @Override
    @Nullable
    TaxedPrice getTaxedPrice();

    @Override
    MonetaryAmount getTotalPrice();

    @Override
    List<DiscountCodeInfo> getDiscountCodes();

    @Nullable
    @Override
    CustomFields getCustom();

    /**
     * Get associated payments.
     *
     * @return payments
     * @see io.sphere.sdk.carts.commands.updateactions.AddPayment
     * @see io.sphere.sdk.carts.commands.updateactions.RemovePayment
     */
    @Nullable
    @Override
    PaymentInfo getPaymentInfo();
}
