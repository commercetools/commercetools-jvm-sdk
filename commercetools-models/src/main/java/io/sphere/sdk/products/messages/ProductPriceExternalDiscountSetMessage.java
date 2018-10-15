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

@JsonDeserialize(as = ProductPriceExternalDiscountSetMessage.class)
public final class ProductPriceExternalDiscountSetMessage extends AbstractImageDeletionMessage {

    public static final String MESSAGE_TYPE = "ProductPriceExternalDiscountSet";
    public static final MessageDerivateHint<ProductPriceExternalDiscountSetMessage> MESSAGE_HINT =
            MessageDerivateHint.ofSingleMessageType(MESSAGE_TYPE, ProductPriceExternalDiscountSetMessage.class, Product.referenceTypeId());

    private final long variantId;
    private final String variantKey;
    private final String sku;
    private final String priceId;
    private final DiscountedPrice discounted;
    private final Boolean staged;


    @JsonCreator
    ProductPriceExternalDiscountSetMessage(final String id, final Long version, final ZonedDateTime createdAt, final ZonedDateTime lastModifiedAt, final JsonNode resource, final Long sequenceNumber, final Long resourceVersion, final String type, final UserProvidedIdentifiers resourceUserProvidedIdentifiers, final String[] removedImageUrls, final long variantId, final String variantKey, final String sku, final String priceId, final DiscountedPrice discounted, final Boolean staged) {
        super(id, version, createdAt, lastModifiedAt, resource, sequenceNumber, resourceVersion, type, resourceUserProvidedIdentifiers, removedImageUrls);
        this.variantId = variantId;
        this.variantKey = variantKey;
        this.sku = sku;
        this.priceId = priceId;
        this.discounted = discounted;
        this.staged = staged;
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
