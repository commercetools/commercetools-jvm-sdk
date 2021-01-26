package io.sphere.sdk.carts;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.neovisionaries.i18n.CountryCode;
import io.sphere.sdk.annotations.*;
import io.sphere.sdk.cartdiscounts.CartDiscount;
import io.sphere.sdk.customergroups.CustomerGroup;
import io.sphere.sdk.discountcodes.DiscountCodeInfo;
import io.sphere.sdk.models.Address;
import io.sphere.sdk.models.KeyReference;
import io.sphere.sdk.models.Reference;
import io.sphere.sdk.orders.Order;
import io.sphere.sdk.stores.Store;
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
@HasByKeyGetEndpoint (javadocSummary = "Gets a cart by Key.", includeExamples = "io.sphere.sdk.carts.queries.CartByKeyGetIntegrationTest#fetchByKeyWithUpdateAction()")
@HasCreateCommand(javadocSummary = "Creates a cart.", includeExamples = {"io.sphere.sdk.carts.commands.CartCreateCommandIntegrationTest#execution()", "io.sphere.sdk.carts.commands.CartCreateCommandIntegrationTest#fullExample()"})
@HasUpdateCommand(javadocSummary = "Updates a cart.")
@HasDeleteCommand(javadocSummary = "Deletes a cart", canEraseUsersData = true, deleteWith = {"key","id"}, includeExamples = "io.sphere.sdk.carts.commands.CartDeleteCommandIntegrationTest#deleteCartByKey()")
@HasQueryModel(implBaseClass = "CartLikeQueryModelImpl<Cart>", baseInterfaces = {"CartLikeQueryModel<Cart>"})
public interface Cart extends CartLike<Cart> {

    /**
     * An identifier for this resource which supports {@link CustomFields}.
     * @see TypeDraft#getResourceTypeIds()
     * @see io.sphere.sdk.types.Custom
     * @return ID of this resource type
     */
    static String resourceTypeId() {
        return "order";//sic https://docs.commercetools.com/http-api-projects-custom-fields.html#customizable-resource
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

    @Nullable
    String getKey();

    @HasUpdateAction
    @Nullable
    String getAnonymousId();

    /**
     * State in the perspective if the cart is active, merged with another cart or ordered.
     *
     * @see io.sphere.sdk.customers.commands.CustomerSignInCommand
     * @see io.sphere.sdk.orders.commands.OrderFromCartCreateCommand
     *
     * @return state
     */
    CartState getCartState();

    @IgnoreInQueryModel
    InventoryMode getInventoryMode();

    @Override
    @Nullable
    @QueryModelHint(type = "CartShippingInfoQueryModel<Cart>")
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
    @HasUpdateAction(value = "applyDeltaToCustomLineItemShippingDetailsTargets", fields = {@PropertySpec(name = "customLineItemId",type = String.class),@PropertySpec(name = "targetsDelta",type = ItemShippingTarget[].class)})
    @HasUpdateAction(value = "setCustomLineItemShippingDetails", fields = {@PropertySpec(name = "customLineItemId",type = String.class),@PropertySpec(name = "shippingDetails",type = ItemShippingDetailsDraft.class)})
    @QueryModelHint(type = "CustomLineItemCollectionQueryModel<Cart>")
    List<CustomLineItem> getCustomLineItems();

    @Override
    @HasUpdateAction(value = "applyDeltaToLineItemShippingDetailsTargets", fields = {@PropertySpec(name = "lineItemId",type = String.class),@PropertySpec(name = "targetsDelta",type = ItemShippingTarget[].class)})
    @HasUpdateAction(value = "setLineItemShippingDetails", fields = {@PropertySpec(name = "lineItemId",type = String.class),@PropertySpec(name = "shippingDetails",type = ItemShippingDetailsDraft.class)})
    @HasUpdateAction(value = "removeLineItem", makeAbstract = true,
            fields = {
                    @PropertySpec(name = "lineItemId", type = String.class),
                    @PropertySpec(name = "quantity", type = Long.class,isOptional = true),
                    @PropertySpec(name = "externalPrice", type = MonetaryAmount.class,isOptional = true),
                    @PropertySpec(name = "externalTotalPrice", type = ExternalLineItemTotalPrice.class,isOptional = true),
                    @PropertySpec(name = "shippingDetailsToRemove", type = ItemShippingDetailsDraft .class,isOptional = true)
            }
    )
    @QueryModelHint(type = "LineItemCollectionQueryModel<Cart>")
    List<LineItem> getLineItems();

    @Override
    @Nullable
    Address getShippingAddress();

    @Override
    @Nullable
    @QueryModelHint(type = "TaxedPriceOptionalQueryModel<Cart>")
    TaxedPrice getTaxedPrice();

    @Override
    @HasUpdateAction
    TaxCalculationMode getTaxCalculationMode();

    @Override
    MonetaryAmount getTotalPrice();

    @Override
    @QueryModelHint(type = "DiscountCodeInfoCollectionQueryModel<Cart>")
    List<DiscountCodeInfo> getDiscountCodes();

    @Nullable
    @Override
    CustomFields getCustom();

    @Nullable
    @Override
    @QueryModelHint(type = "PaymentInfoQueryModel<Cart>")
    PaymentInfo getPaymentInfo();

    @Nullable
    Integer getDeleteDaysAfterLastModification();

    /**
     * The refused gifts of this cart. Automatically filled when a line item with {@link LineItemMode#GIFT_LINE_ITEM}
     * is removed from this cart.
     *
     * @return the refused gifts of this cart
     */
    @Nullable
    @IgnoreInQueryModel
    List<Reference<CartDiscount>> getRefusedGifts();

    /**
     *  The shippingRateInput is used as an input to select a shipping rate price tier
     * @return shippingRateInput
     */
    @Override
    @Nullable
    @QueryModelHint(type = "ShippingRateInputQueryModel<Cart>")
    ShippingRateInput getShippingRateInput();


    @IgnoreInQueryModel
    CartOrigin getOrigin();

    /**
     * @return  addresses for orders with multiple shipping addresses.
     */
    @Override
    @QueryModelHint(type = "AddressCollectionQueryModel<Cart>")
    @HasUpdateAction(value = "addItemShippingAddress", fields = {@PropertySpec(name = "address",type = Address.class)})
    @HasUpdateAction(value = "updateItemShippingAddress", fields = {@PropertySpec(name = "address",type = Address.class)})
    @HasUpdateAction(value = "removeItemShippingAddress", fields = {@PropertySpec(name = "addressKey",type = String.class)})
    List<Address> getItemShippingAddresses();

    @Nullable
    @QueryModelHint(type = "KeyReferenceQueryModel<Cart>", impl = "return keyReferenceQueryModel(fieldName);")
    KeyReference<Store> getStore();

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
