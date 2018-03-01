package io.sphere.sdk.shoppinglists;

import io.sphere.sdk.products.ByIdVariantIdentifier;
import io.sphere.sdk.products.BySkuVariantIdentifier;
import io.sphere.sdk.types.CustomFields;
import io.sphere.sdk.types.CustomFieldsDraft;
import io.sphere.sdk.types.CustomFieldsDraftBuilder;

import javax.annotation.Nullable;
import java.time.ZonedDateTime;

public final class LineItemDraftBuilder extends LineItemDraftBuilderBase<LineItemDraftBuilder> {
    LineItemDraftBuilder(@Nullable ZonedDateTime addedAt, @Nullable CustomFieldsDraft custom, String productId, @Nullable Long quantity, String sku, @Nullable Integer variantId) {
        super(addedAt, custom, productId, quantity, sku, variantId);
    }

    /**
     * Sets the {@code custom} property of this builder.
     *
     * @param custom the value for {@link LineItemDraft#getCustom()}
     * @return this builder
     *
     * @deprecated This method will be removed be removed with the next major SDK update.
     * Please use the {@link #custom(CustomFieldsDraft)} method instead.
     */
    @Deprecated
    public LineItemDraftBuilder custom(@Nullable final CustomFields custom) {
        return super.custom(CustomFieldsDraftBuilder.of(custom).build());
    }

    /**
     * Creates a new object initialized with the given values.
     *
     * @param variantIdentifier initial value for the {@link io.sphere.sdk.carts.LineItemDraft#getProductId()} and {@link io.sphere.sdk.carts.LineItemDraft#getVariantId()} properties
     * @param quantity          initial value for the {@link io.sphere.sdk.carts.LineItemDraft#getQuantity()} property
     * @return new object initialized with the given values
     */
    public static LineItemDraftBuilder ofVariantIdentifier(final ByIdVariantIdentifier variantIdentifier, @Nullable final Long quantity) {
        return of(variantIdentifier.getProductId()).variantId(variantIdentifier.getVariantId()).quantity(quantity);
    }

    /**
     * Creates a new object initialized with the given values.
     *
     * @param skuVariantIdentifier initial value for the {@link io.sphere.sdk.carts.LineItemDraft#getSku()} property
     * @param quantity             initial value for the {@link io.sphere.sdk.carts.LineItemDraft#getQuantity()} property
     * @return new object initialized with the given values
     */
    public static LineItemDraftBuilder ofSkuVariantIdentifier(final BySkuVariantIdentifier skuVariantIdentifier, @Nullable final Long quantity) {
        return ofSku(skuVariantIdentifier.getSku(), quantity);
    }
}
