package io.sphere.sdk.products.messages;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import io.sphere.sdk.messages.MessageDerivateHint;
import io.sphere.sdk.messages.UserProvidedIdentifiers;
import io.sphere.sdk.productdiscounts.DiscountedPrice;
import io.sphere.sdk.products.Product;

import java.time.ZonedDateTime;
import java.util.List;

@JsonDeserialize(as = ProductPriceDiscountsSetMessage.class)
public final class ProductPriceDiscountsSetMessage extends AbstractImageDeletionMessage {

    public static final String MESSAGE_TYPE = "ProductPriceDiscountsSet";
    public static final MessageDerivateHint<ProductPriceDiscountsSetMessage> MESSAGE_HINT =
            MessageDerivateHint.ofSingleMessageType(MESSAGE_TYPE, ProductPriceDiscountsSetMessage.class, Product.referenceTypeId());


    private final List<ProductPriceDiscountUpdate> updatedPrices;
    private final long variantId;
    private final String variantKey;
    private final String sku;
    private final String priceId;
    private final DiscountedPrice discounted;
    private final Boolean staged;


    @JsonCreator
    ProductPriceDiscountsSetMessage(final String id, final  Long version, final  ZonedDateTime createdAt, final  ZonedDateTime lastModifiedAt, final  JsonNode resource, final  Long sequenceNumber, final  Long resourceVersion, final  String type, final  UserProvidedIdentifiers resourceUserProvidedIdentifiers, final  String[] removedImageUrls, final  List<ProductPriceDiscountUpdate> updatedPrices, final  long variantId, final  String variantKey, final  String sku, final  String priceId, final  DiscountedPrice discounted, final  Boolean staged) {
        super(id, version, createdAt, lastModifiedAt, resource, sequenceNumber, resourceVersion, type, resourceUserProvidedIdentifiers, removedImageUrls);
        this.updatedPrices = updatedPrices;
        this.variantId = variantId;
        this.variantKey = variantKey;
        this.sku = sku;
        this.priceId = priceId;
        this.discounted = discounted;
        this.staged = staged;
    }

    public List<ProductPriceDiscountUpdate> getUpdatedPrices() {
        return updatedPrices;
    }

    public long getVariantId() {
        return variantId;
    }

    public String getVariantKey() {
        return variantKey;
    }

    public String getSku() {
        return sku;
    }

    public String getPriceId() {
        return priceId;
    }

    public DiscountedPrice getDiscounted() {
        return discounted;
    }

    public Boolean getStaged() {
        return staged;
    }

}


