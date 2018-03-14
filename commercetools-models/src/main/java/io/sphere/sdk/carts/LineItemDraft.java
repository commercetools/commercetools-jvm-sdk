package io.sphere.sdk.carts;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import io.sphere.sdk.annotations.FactoryMethod;
import io.sphere.sdk.annotations.ResourceDraftValue;
import io.sphere.sdk.channels.Channel;
import io.sphere.sdk.models.Reference;
import io.sphere.sdk.products.ProductIdentifiable;
import io.sphere.sdk.taxcategories.ExternalTaxRateDraft;
import io.sphere.sdk.types.CustomFieldsDraft;

import javax.annotation.Nullable;
import javax.money.MonetaryAmount;

/**
 * Draft for a new line item.
 *
 * @see io.sphere.sdk.carts.commands.CartCreateCommand
 */
@JsonDeserialize(as = LineItemDraftDsl.class)
@ResourceDraftValue(
        abstractBuilderClass = true,
        factoryMethods = {
                @FactoryMethod(methodName = "ofSku", parameterNames = {"sku", "quantity"}),
                @FactoryMethod(parameterNames = {"productId", "variantId", "quantity", "supplyChannel", "distributionChannel", "custom", "externalTaxRate", "externalPrice", "externalTotalPrice"})
        }
)
public interface LineItemDraft {

    String getSku();

    @Nullable
    CustomFieldsDraft getCustom();

    @Nullable
    Reference<Channel> getDistributionChannel();

    String getProductId();

    @Nullable
    Long getQuantity();

    @Nullable
    Reference<Channel> getSupplyChannel();

    Integer getVariantId();

    /**
     * The optional external tax rate if the cart has the tax mode {@link TaxMode#EXTERNAL}.
     *
     * @return external tax rate or null
     * @see TaxMode
     */
    @Nullable
    ExternalTaxRateDraft getExternalTaxRate();

    @Nullable
    MonetaryAmount getExternalPrice();

    @Nullable
    ExternalLineItemTotalPrice getExternalTotalPrice();

    /**
     * Container for the sub-quantity of the line item quantity for the specific address
     * when multiple shipping addresses are required.
     * @return the itemShippingDetailsDraft
     */
    @Nullable
    ItemShippingDetailsDraft getShippingDetails();

    static LineItemDraftDsl of(final ProductIdentifiable product, final Integer variantId, final long quantity) {
        return of(product.getId(), variantId, quantity);
    }

    static LineItemDraftDsl of(final String productId, final Integer variantId, final long quantity) {
        return LineItemDraftDsl.of(productId, variantId, quantity, null, null, null, null, null, null);
    }
}
