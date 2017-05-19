package io.sphere.sdk.products.messages;


import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import io.sphere.sdk.messages.MessageDerivateHint;
import io.sphere.sdk.products.Product;
import io.sphere.sdk.products.ProductProjection;
import io.sphere.sdk.products.ProductVariant;

import javax.annotation.Nullable;
import java.time.ZonedDateTime;

@JsonDeserialize(as = ProductDeletedMessage.class)//important to override annotation in Message class
public final class ProductDeletedMessage extends AbstractImageDeletionMessage {

    public static final String MESSAGE_TYPE = "ProductDeleted";
    public static final MessageDerivateHint<ProductDeletedMessage> MESSAGE_HINT =
            MessageDerivateHint.ofSingleMessageType(MESSAGE_TYPE, ProductDeletedMessage.class, Product.referenceTypeId());

    @Nullable
    private final ProductProjection currentProjection;

    @JsonCreator
    private ProductDeletedMessage(String id, Long version, ZonedDateTime createdAt, ZonedDateTime lastModifiedAt, JsonNode resource, Long sequenceNumber, Long resourceVersion, String type, String[] removedImageUrls,ProductProjection currentProjection) {
        super(id, version, createdAt, lastModifiedAt, resource, sequenceNumber, resourceVersion, type, removedImageUrls);
        this.currentProjection = currentProjection;
    }

    public ProductProjection getCurrentProjection(){
        return currentProjection;
    }

}
