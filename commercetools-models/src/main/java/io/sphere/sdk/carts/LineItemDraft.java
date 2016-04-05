package io.sphere.sdk.carts;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import io.sphere.sdk.channels.Channel;
import io.sphere.sdk.models.Reference;
import io.sphere.sdk.products.ProductIdentifiable;
import io.sphere.sdk.taxcategories.ExternalTaxRateDraft;
import io.sphere.sdk.types.CustomFieldsDraft;

import javax.annotation.Nullable;

/**
 * Draft for a new line item.
 *
 * @see io.sphere.sdk.carts.commands.CartCreateCommand
 */
@JsonDeserialize(as = LineItemDraftDsl.class)
public interface LineItemDraft {
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
     * Possible custom tax rate.
     * If set, this tax rate will override the tax rate selected by the platform.
     *
     * @return external tax rate or null
     */
    @Nullable
    ExternalTaxRateDraft getExternalTaxRate();

    static LineItemDraftDsl of(final ProductIdentifiable product, final Integer variantId, final long quantity) {
        return of(product.getId(), variantId, quantity);
    }

    static LineItemDraftDsl of(final String productId, final Integer variantId, final long quantity) {
        return new LineItemDraftDsl(productId, variantId, quantity, null, null, null, null);
    }
}
