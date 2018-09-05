package io.sphere.sdk.products.messages;


import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import io.sphere.sdk.messages.MessageDerivateHint;
import io.sphere.sdk.messages.UserProvidedIdentifiers;
import io.sphere.sdk.products.Product;

import java.time.ZonedDateTime;

@JsonDeserialize(as = ProductRevertedStagedChangesMessage.class)//important to override annotation in Message class
public final class ProductRevertedStagedChangesMessage extends AbstractImageDeletionMessage {

    public static final String MESSAGE_TYPE = "ProductRevertedStagedChanges";
    public static final MessageDerivateHint<ProductRevertedStagedChangesMessage> MESSAGE_HINT =
            MessageDerivateHint.ofSingleMessageType(MESSAGE_TYPE, ProductRevertedStagedChangesMessage.class, Product.referenceTypeId());

    @JsonCreator
    private ProductRevertedStagedChangesMessage(final String id, final Long version, final ZonedDateTime createdAt, final ZonedDateTime lastModifiedAt, final JsonNode resource, final Long sequenceNumber, final Long resourceVersion, final String type, final UserProvidedIdentifiers resourceUserProvidedIdentifiers, final String[] removedImageUrls) {
        super(id, version, createdAt, lastModifiedAt, resource, sequenceNumber, resourceVersion, type,resourceUserProvidedIdentifiers, removedImageUrls);
    }

}
