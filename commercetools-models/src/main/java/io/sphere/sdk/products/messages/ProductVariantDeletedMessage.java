package io.sphere.sdk.products.messages;


import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import io.sphere.sdk.messages.MessageDerivateHint;
import io.sphere.sdk.products.Product;
import io.sphere.sdk.products.ProductVariant;

import javax.annotation.Nullable;
import java.time.ZonedDateTime;

@JsonDeserialize(as = ProductVariantDeletedMessage.class)//important to override annotation in Message class
public final class ProductVariantDeletedMessage extends AbstractImageDeletionMessage {

    public static final String MESSAGE_TYPE = "ProductVariantDeleted";
    public static final MessageDerivateHint<ProductVariantDeletedMessage> MESSAGE_HINT =
            MessageDerivateHint.ofSingleMessageType(MESSAGE_TYPE, ProductVariantDeletedMessage.class, Product.referenceTypeId());

    @Nullable
    private final ProductVariant variant;

    @JsonCreator
    private ProductVariantDeletedMessage(String id, Long version, ZonedDateTime createdAt, ZonedDateTime lastModifiedAt, JsonNode resource, Long sequenceNumber, Long resourceVersion, String type, String[] removedImageUrls,ProductVariant variant) {
        super(id, version, createdAt, lastModifiedAt, resource, sequenceNumber, resourceVersion, type, removedImageUrls);
        this.variant = variant;
    }

    public ProductVariant getVariant(){
        return variant;
    }

}
