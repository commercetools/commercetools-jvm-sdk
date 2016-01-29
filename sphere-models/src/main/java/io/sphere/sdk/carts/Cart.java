package io.sphere.sdk.carts;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.neovisionaries.i18n.CountryCode;
import io.sphere.sdk.customergroups.CustomerGroup;
import io.sphere.sdk.discountcodes.DiscountCodeInfo;
import io.sphere.sdk.models.Address;
import io.sphere.sdk.models.Reference;
import io.sphere.sdk.orders.Order;
import io.sphere.sdk.types.CustomFields;
import io.sphere.sdk.types.TypeDraft;

import javax.annotation.Nullable;
import javax.money.MonetaryAmount;
import java.util.List;

/**
 <p>A shopping cart holds product variants and can be ordered.</p> Each cart either belongs to a registered customer or is an anonymous cart ({@link #getCustomerId()} yields null).

 <p>A cart can have {@link io.sphere.sdk.types.Custom custom fields}.</p>

 @see io.sphere.sdk.carts.commands.CartCreateCommand
 @see io.sphere.sdk.carts.commands.CartUpdateCommand
 @see io.sphere.sdk.carts.commands.CartDeleteCommand
 @see io.sphere.sdk.orders.commands.OrderFromCartCreateCommand
 @see io.sphere.sdk.carts.queries.CartQuery
 @see io.sphere.sdk.carts.queries.CartByCustomerIdGet
 @see io.sphere.sdk.carts.queries.CartByIdGet
 @see io.sphere.sdk.orders.Order
 @see Order#getCart()
 */
@JsonDeserialize(as=CartImpl.class)
public interface Cart extends CartLike<Cart> {

    /**
     * An identifier for this resource which supports {@link CustomFields}.
     * @see TypeDraft#getResourceTypeIds()
     * @see io.sphere.sdk.types.Custom
     * @return ID of this resource type
     */
    static String resourceTypeId() {
        return "order";//sic http://dev.sphere.io/http-api-projects-custom-fields.html#customizable-resource
    }

    /**
     * A type hint for references which resource type is linked in a reference.
     * @see Reference#getTypeId()
     * @return type hint
     */
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

    /**
     * The custom line items of this cart.
     * @return custom line items
     *
     * @see io.sphere.sdk.carts.commands.updateactions.AddCustomLineItem
     * @see io.sphere.sdk.carts.commands.updateactions.RemoveCustomLineItem
     * @see Order#getCustomLineItems()
     */
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

    static Reference<Cart> referenceOfId(final String id) {
        return Reference.of(referenceTypeId(), id);
    }
}
