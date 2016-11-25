package io.sphere.sdk.carts;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.neovisionaries.i18n.CountryCode;
import io.sphere.sdk.annotations.*;
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
@ResourceValue
@HasQueryEndpoint()
@ResourceInfo(pluralName = "carts", pathElement = "carts")
@HasByIdGetEndpoint(javadocSummary = "Gets a cart by ID.", includeExamples = "io.sphere.sdk.carts.queries.CartByIdGetIntegrationTest#fetchById()")
@HasCreateCommand(javadocSummary = "Creates a cart.", includeExamples = {"io.sphere.sdk.carts.commands.CartCreateCommandIntegrationTest#execution()", "io.sphere.sdk.carts.commands.CartCreateCommandIntegrationTest#fullExample()"})
@HasUpdateCommand(javadocSummary = "Updates a cart.")
@HasDeleteCommand(javadocSummary = "Deletes a cart.")
public interface Cart extends CartLike<Cart> {

    /**
     * An identifier for this resource which supports {@link CustomFields}.
     * @see TypeDraft#getResourceTypeIds()
     * @see io.sphere.sdk.types.Custom
     * @return ID of this resource type
     */
    static String resourceTypeId() {
        return "order";//sic http://dev.commercetools.io/http-api-projects-custom-fields.html#customizable-resource
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
     * Creates a container which contains the full Java type information to deserialize this class from JSON.
     *
     * @see io.sphere.sdk.json.SphereJsonUtils#readObject(byte[], TypeReference)
     * @see io.sphere.sdk.json.SphereJsonUtils#readObject(String, TypeReference)
     * @see io.sphere.sdk.json.SphereJsonUtils#readObject(com.fasterxml.jackson.databind.JsonNode, TypeReference)
     * @see io.sphere.sdk.json.SphereJsonUtils#readObjectFromResource(String, TypeReference)
     *
     * @return type reference
     */
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

    /**
     * State in the perspective if the cart is active, merged with another cart or ordered.
     *
     * @see io.sphere.sdk.customers.commands.CustomerSignInCommand
     * @see io.sphere.sdk.orders.commands.OrderFromCartCreateCommand
     *
     * @return state
     */
    CartState getCartState();

    InventoryMode getInventoryMode();

    @Override
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

    @Nullable
    @Override
    PaymentInfo getPaymentInfo();

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
    static Reference<Cart> referenceOfId(final String id) {
        return Reference.of(referenceTypeId(), id);
    }
}
