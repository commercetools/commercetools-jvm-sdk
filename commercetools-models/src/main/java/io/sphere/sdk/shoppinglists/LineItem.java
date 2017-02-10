package io.sphere.sdk.shoppinglists;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import io.sphere.sdk.annotations.ResourceValue;
import io.sphere.sdk.models.LocalizedString;
import io.sphere.sdk.models.Reference;
import io.sphere.sdk.products.ProductVariant;
import io.sphere.sdk.producttypes.ProductType;
import io.sphere.sdk.types.CustomFields;
import io.sphere.sdk.types.TypeDraft;

import javax.annotation.Nullable;
import java.time.ZonedDateTime;

@JsonDeserialize(as = LineItemImpl.class)
@ResourceValue
public interface LineItem {
    String getId();

    String getProductId();

    @Nullable
    Integer getVariantId();

    @Nullable
    Reference<ProductType> getProductType();

    Long getQuantity();

    CustomFields getCustom();

    LocalizedString getName();

    ZonedDateTime getAddedAt();

    @Nullable
    ZonedDateTime getDeactivatedAt();

    @Nullable
    LocalizedString getProductSlug();

    @Nullable
    Reference<ProductVariant> getProductVariant();

    /**
     * An identifier for this resource which supports {@link CustomFields}.
     * @see TypeDraft#getResourceTypeIds()
     * @see io.sphere.sdk.types.Custom
     * @return ID of this resource type
     */
    static String resourceTypeId() {
        return "line-item";
    }
}
