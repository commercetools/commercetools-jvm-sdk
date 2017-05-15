package io.sphere.sdk.shoppinglists;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.databind.node.ObjectNode;
import io.sphere.sdk.json.SphereJsonUtils;
import io.sphere.sdk.models.LocalizedString;
import io.sphere.sdk.models.Reference;
import io.sphere.sdk.products.ProductVariant;
import io.sphere.sdk.producttypes.ProductType;
import io.sphere.sdk.types.CustomFields;

import javax.annotation.Nullable;
import java.time.ZonedDateTime;
import java.util.Objects;

/**
 * Created by abeniasaad on 11.05.17.
 */
final class LineItemImpl extends LineItemImplBase {


    @JsonCreator
    public LineItemImpl(@Nullable  final ObjectNode variant, final ZonedDateTime addedAt, @Nullable final CustomFields custom,
                        @Nullable final ZonedDateTime deactivatedAt, final String id, final LocalizedString name,
                        final String productId, @Nullable final LocalizedString productSlug,
                        final Reference<ProductType> productType, final Long quantity, @Nullable final Integer variantId) {

        super(addedAt, custom, deactivatedAt, id, name, productId, productSlug, productType, quantity, asVariant(variant, productId), variantId);


    }

    private static ProductVariant asVariant(final ObjectNode variant, final String productId) {
        if (variant == null) {
            return null;
        }
        Objects.requireNonNull(productId);
        variant.put("productId", productId);
        return SphereJsonUtils.readObject(variant, ProductVariant.class);
    }


}
