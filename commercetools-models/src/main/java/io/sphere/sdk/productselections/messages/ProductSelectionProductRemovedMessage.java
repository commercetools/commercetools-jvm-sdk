package io.sphere.sdk.productselections.messages;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import io.sphere.sdk.messages.GenericMessageImpl;
import io.sphere.sdk.messages.MessageDerivateHint;
import io.sphere.sdk.messages.UserProvidedIdentifiers;
import io.sphere.sdk.models.Reference;
import io.sphere.sdk.products.Product;
import io.sphere.sdk.productselections.ProductSelection;


import java.time.ZonedDateTime;

@JsonDeserialize(as = ProductSelectionProductRemovedMessage.class)//important to override annotation in Message class
public final class ProductSelectionProductRemovedMessage extends GenericMessageImpl<ProductSelection> {
    public static final String MESSAGE_TYPE = "ProductSelectionProductRemoved";
    public static final MessageDerivateHint<ProductSelectionProductRemovedMessage> MESSAGE_HINT =
            MessageDerivateHint.ofSingleMessageType(MESSAGE_TYPE, ProductSelectionProductRemovedMessage.class, ProductSelection.referenceTypeId());

    private final Reference<Product> productReference;

    @JsonCreator
    private ProductSelectionProductRemovedMessage(final String id, final Long version, final ZonedDateTime createdAt, final ZonedDateTime lastModifiedAt, final JsonNode resource, final Long sequenceNumber, final Long resourceVersion, final String type, final UserProvidedIdentifiers resourceUserProvidedIdentifiers, final Reference<Product> productReference) {
        super(id, version, createdAt, lastModifiedAt, resource, sequenceNumber, resourceVersion, type, resourceUserProvidedIdentifiers, ProductSelection.class);
        this.productReference = productReference;
    }
    
    public Reference<Product> getProductReference() {
        return productReference;
    }

}
