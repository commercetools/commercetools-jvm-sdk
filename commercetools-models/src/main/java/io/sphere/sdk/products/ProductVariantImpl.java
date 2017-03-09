package io.sphere.sdk.products;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.sphere.sdk.json.JsonException;
import io.sphere.sdk.models.Asset;
import io.sphere.sdk.products.attributes.Attribute;
import io.sphere.sdk.products.attributes.AttributeMapper;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Optional;

import static java.lang.String.format;

class ProductVariantImpl extends ProductVariantImplBase {
    @Nullable
    // hack for VariantIdentifier getter
    // https://github.com/commercetools/commercetools-jvm-sdk/issues/239
    private String productId;

    @JsonCreator
    ProductVariantImpl(final List<Asset> assets, final List<Attribute> attributes, @Nullable final ProductVariantAvailability availability,
                       final Integer id, final List<Image> images, @Nullable final String key,
                       @Nullable final Boolean matchingVariant, @Nullable final Price price, final List<Price> prices, @Nullable final ScopedPrice scopedPrice,
                       @Nullable final Boolean scopedPriceDiscounted, @Nullable final String sku) {
        super(assets, attributes, availability, id, images, key, matchingVariant, price, prices, scopedPrice, scopedPriceDiscounted, sku);
    }

    @JsonIgnore
    @Nullable
    @Override
    public ByIdVariantIdentifier getIdentifier() {
        return Optional.ofNullable(productId).map(pId -> ByIdVariantIdentifier.of(pId, getId())).orElseThrow(UnsupportedOperationException::new);
    }

    @Override
    protected JsonException transformError(JsonException e, String attributeName, AttributeMapper<?> mapper) {
        return enrich(format("ProductVariant(id=%s)", getId()), attributeName, mapper, e.getCause());
    }

    // required trick for variant identifier
    // https://github.com/commercetools/commercetools-jvm-sdk/issues/239
    void setProductId(@Nullable final String productId) {
        this.productId = productId;
    }

    private JsonException enrich(final Object objectWithAttributes, final String attributeName, final AttributeMapper<?> mapper, final Throwable cause) {
        return new JsonException(format("%s does not contain an attribute '%s' which can be mapped with %s.", objectWithAttributes, attributeName, mapper), cause);
    }
}
