package io.sphere.sdk.products.messages;


import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import io.sphere.sdk.messages.MessageDerivateHint;
import io.sphere.sdk.products.Product;

import java.time.ZonedDateTime;

@JsonDeserialize(as = ProductRevertedStagedChangesMessage.class)//important to override annotation in Message class
public class ProductRevertedStagedChangesMessage extends AbstractImageDeletionMessage {

    public static final String MESSAGE_TYPE = "ProductRevertedStagedChanges";
    public static final MessageDerivateHint<ProductRevertedStagedChangesMessage> MESSAGE_HINT =
            MessageDerivateHint.ofSingleMessageType(MESSAGE_TYPE, ProductRevertedStagedChangesMessage.class, Product.referenceTypeId());

    @JsonCreator
    private ProductRevertedStagedChangesMessage(String id, Long version, ZonedDateTime createdAt, ZonedDateTime lastModifiedAt, JsonNode resource, Long sequenceNumber, Long resourceVersion, String type, String[] removedImageUrls) {
        super(id, version, createdAt, lastModifiedAt, resource, sequenceNumber, resourceVersion, type, removedImageUrls);
    }

}
