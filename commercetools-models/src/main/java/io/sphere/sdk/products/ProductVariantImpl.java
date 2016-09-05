package io.sphere.sdk.products;

import com.fasterxml.jackson.annotation.JsonCreator;
import io.sphere.sdk.json.JsonException;
import io.sphere.sdk.products.attributes.Attribute;
import io.sphere.sdk.products.attributes.AttributeMapper;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Optional;

import static java.lang.String.format;

class ProductVariantImpl extends AttributeContainerImpl implements ProductVariant {
    @Nullable
    private String productId;//hack for VariantIdentifier getter
    private final Integer id;
    @Nullable
    private final String sku;
    private final List<Price> prices;
    private final List<Image> images;
    @Nullable
    private final ProductVariantAvailability availability;
    @Nullable
    private final Boolean isMatchingVariant;
    @Nullable
    private final Price price;
    @Nullable
    private final ScopedPrice scopedPrice;
    @Nullable
    private final Boolean scopedPriceDiscounted;
    @Nullable
    private final String key;

    @JsonCreator
    ProductVariantImpl(final Integer id, @Nullable final String sku, final List<Price> prices, final List<Attribute> attributes,
                       final List<Image> images, @Nullable final ProductVariantAvailability availability,
                       @Nullable final Boolean isMatchingVariant, @Nullable final String productId,
                       @Nullable final Price price, @Nullable final ScopedPrice scopedPrice,
                       @Nullable final Boolean scopedPriceDiscounted, @Nullable final String key) {
        super(attributes);
        this.id = id;
        this.sku = sku;
        this.prices = prices;
        this.images = images;
        this.availability = availability;
        this.isMatchingVariant = isMatchingVariant;
        this.productId = productId;
        this.price = price;
        this.scopedPrice = scopedPrice;
        this.scopedPriceDiscounted = scopedPriceDiscounted;
        this.key = key;
    }

    @Override
    public Integer getId() {
        return id;
    }

    @Override
    @Nullable
    public String getSku() {
        return sku;
    }

    @Override
    public List<Price> getPrices() {
        return prices;
    }

    @Override
    public List<Image> getImages() {
        return images;
    }

    @Override
    @Nullable
    public ProductVariantAvailability getAvailability() {
        return availability;
    }

    @Override
    @Nullable
    public Boolean isMatchingVariant() {
        return isMatchingVariant;
    }

    @Override
    @Nullable
    public Price getPrice() {
        return price;
    }

    @Override
    @Nullable
    public ScopedPrice getScopedPrice() {
        return scopedPrice;
    }

    @Nullable
    @Override
    public Boolean isScopedPriceDiscounted() {
        return scopedPriceDiscounted;
    }

    @Override
    public ByIdVariantIdentifier getIdentifier() {
        return Optional.ofNullable(productId).map(pId -> ByIdVariantIdentifier.of(pId, getId())).orElseThrow(UnsupportedOperationException::new);
    }

    @Override
    @Nullable
    public String getKey() {
        return key;
    }

    @Override
    protected JsonException transformError(JsonException e, String attributeName, AttributeMapper<?> mapper) {
        return enrich(format("ProductVariant(id=%s)", id), attributeName, mapper, e.getCause());
    }

    //required trick for variant identifier
    void setProductId(@Nullable final String productId) {
        this.productId = productId;
    }

    private JsonException enrich(final Object objectWithAttributes, final String attributeName, final AttributeMapper<?> mapper, final Throwable cause) {
        return new JsonException(format("%s does not contain an attribute '%s' which can be mapped with %s.", objectWithAttributes, attributeName, mapper), cause);
    }
}
