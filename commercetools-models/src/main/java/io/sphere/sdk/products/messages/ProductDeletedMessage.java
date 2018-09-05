package io.sphere.sdk.products.messages;


import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import io.sphere.sdk.messages.MessageDerivateHint;
import io.sphere.sdk.messages.UserProvidedIdentifiers;
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
    private ProductDeletedMessage(final String id, final Long version, final ZonedDateTime createdAt, final ZonedDateTime lastModifiedAt, final JsonNode resource, final Long sequenceNumber, final Long resourceVersion, final String type, final UserProvidedIdentifiers resourceUserProvidedIdentifiers, final String[] removedImageUrls, final ProductProjection currentProjection) {
        super(id, version, createdAt, lastModifiedAt, resource, sequenceNumber, resourceVersion, type,resourceUserProvidedIdentifiers, removedImageUrls);
        this.currentProjection = currentProjection;
    }

    public ProductProjection getCurrentProjection(){
        return currentProjection;
    }

}
