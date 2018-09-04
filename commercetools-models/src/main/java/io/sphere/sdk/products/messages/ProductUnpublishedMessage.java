package io.sphere.sdk.products.messages;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import io.sphere.sdk.messages.GenericMessageImpl;
import io.sphere.sdk.messages.MessageDerivateHint;
import io.sphere.sdk.messages.UserProvidedIdentifiers;
import io.sphere.sdk.products.Product;

import java.time.ZonedDateTime;

@JsonDeserialize(as = ProductUnpublishedMessage.class)//important to override annotation in Message class
public final class ProductUnpublishedMessage extends GenericMessageImpl<Product> {
    public static final String MESSAGE_TYPE = "ProductUnpublished";
    public static final MessageDerivateHint<ProductUnpublishedMessage> MESSAGE_HINT =
            MessageDerivateHint.ofSingleMessageType(MESSAGE_TYPE, ProductUnpublishedMessage.class, Product.referenceTypeId());

    @JsonCreator
    private ProductUnpublishedMessage(final String id, final Long version, final ZonedDateTime createdAt, final ZonedDateTime lastModifiedAt, final JsonNode resource, final Long sequenceNumber, final Long resourceVersion, final String type, final UserProvidedIdentifiers resourceUserProvidedIdentifiers) {
        super(id, version, createdAt, lastModifiedAt, resource, sequenceNumber, resourceVersion, type,resourceUserProvidedIdentifiers, Product.class);
    }
}
