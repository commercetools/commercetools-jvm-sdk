package io.sphere.sdk.products;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import io.sphere.sdk.annotations.ResourceValue;
import io.sphere.sdk.models.Asset;
import io.sphere.sdk.products.search.PriceSelection;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;

@ResourceValue(abstractResourceClass = true, baseClass = AttributeContainerBase.class)
@JsonDeserialize(as = ProductVariantImpl.class)
public interface ProductVariant extends AttributeContainer {
    Integer getId();

    @Nullable
    String getSku();

    /**
     * Gets all prices for this product variant.
     *
     * For a price matching a currency/country/channel/customer group context consider the use of
     * {@link io.sphere.sdk.products.search.ProductProjectionSearch} with {@link io.sphere.sdk.products.search.ProductProjectionSearch#withPriceSelection(PriceSelection)}.
     *
     * @return prices
     * @see #getPrice()
     * @see #getScopedPrice()
     */
    List<Price> getPrices();

    /**
     * Optional price from the price selection in the search endpoint.
     * By default this price is null but it can be filled by using {@link io.sphere.sdk.products.search.ProductProjectionSearch#withPriceSelection(PriceSelection)}.
     *
     * @return selected price or null
     * @see #getPrices() 
     * @see #getScopedPrice()
     */
    @Nullable
    Price getPrice();

    /**
     * Optional price from the price scoped filtering in the search endpoint.
     * By default this price is null but it can be filled by using {@link io.sphere.sdk.products.search.ProductVariantFilterSearchModel#scopedPrice()}.
     *
     * @return scoped price or null
     * @see #getPrices()
     * @see #getPrice()
     */
    @Nullable
    ScopedPrice getScopedPrice();

    /**
     * Flag if scoped price is discounted.
     *
     * By default this flag is null but it can be filled by using {@link io.sphere.sdk.products.search.ProductVariantFilterSearchModel#scopedPrice()}.
     *
     * @return
     */
    @JsonProperty("scopedPriceDiscounted")
    @Nullable
    Boolean isScopedPriceDiscounted();

    /**
     *
     * Product images
     *
     * @return Images
     * @see io.sphere.sdk.products.commands.updateactions.MoveImageToPosition
     * @see io.sphere.sdk.products.commands.updateactions.AddExternalImage
     * @see io.sphere.sdk.products.commands.updateactions.RemoveImage
     */
    List<Image> getImages();

    /**
     * The availability is set if the variant is tracked by the inventory. The field might not contain the latest
     * inventory state (it is eventually consistent) and can be used as an optimization to reduce calls to the inventory service.
     *
     * @return availability
     */
    @Nullable
    ProductVariantAvailability getAvailability();

    /**
     * A flag that indicates whether the variant matches the search criteria used when requesting the list of products or not.
     * This flag is needed since all products that have at least one matching variant will be returned, along with all its
     * variants, even those that do not match the search request themselves.
     * Notice that this flag is only set when using the ProductProjection Search endpoint.
     *
     * @return whether the variant matches the search request parameters
     */
    @JsonProperty("isMatchingVariant")
    @Nullable
    Boolean isMatchingVariant();

    /**
     * Gets the id of the product and the variant. This operation may not be available.
     * It will be available if this {@link ProductVariant} has been created
     * by loading a {@link Product} or a {@link ProductProjection} from JSON.
     * @return identifier for this variant
     * @throws UnsupportedOperationException if the operation is not available
     */
    @JsonIgnore
    @Nullable
    ByIdVariantIdentifier getIdentifier();

    /**
     * Key of the variant.
     * @return key
     *
     * @see io.sphere.sdk.products.commands.updateactions.SetProductVariantKey
     */
    @Nullable
    String getKey();

    @Nonnull
    List<Asset> getAssets();
}
