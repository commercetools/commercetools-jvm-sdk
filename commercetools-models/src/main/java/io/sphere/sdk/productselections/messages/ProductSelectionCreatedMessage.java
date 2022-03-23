package io.sphere.sdk.productselections.messages;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import io.sphere.sdk.messages.GenericMessageImpl;
import io.sphere.sdk.messages.MessageDerivateHint;
import io.sphere.sdk.messages.UserProvidedIdentifiers;
import io.sphere.sdk.productselections.ProductSelection;
import io.sphere.sdk.productselections.ProductSelectionType;

import java.time.ZonedDateTime;

@JsonDeserialize(as = ProductSelectionCreatedMessage.class)//important to override annotation in Message class
public final class ProductSelectionCreatedMessage extends GenericMessageImpl<ProductSelection> {
    public static final String MESSAGE_TYPE = "ProductSelectionCreated";
    public static final MessageDerivateHint<ProductSelectionCreatedMessage> MESSAGE_HINT =
            MessageDerivateHint.ofSingleMessageType(MESSAGE_TYPE, ProductSelectionCreatedMessage.class, ProductSelection.referenceTypeId());

    private final ProductSelectionType productSelectionType;

    @JsonCreator
    private ProductSelectionCreatedMessage(final String id, final Long version, final ZonedDateTime createdAt, final ZonedDateTime lastModifiedAt, final JsonNode resource, final Long sequenceNumber, final Long resourceVersion, final String type, final UserProvidedIdentifiers resourceUserProvidedIdentifiers, final ProductSelectionType productSelectionType) {
        super(id, version, createdAt, lastModifiedAt, resource, sequenceNumber, resourceVersion, type, resourceUserProvidedIdentifiers, ProductSelection.class);
        this.productSelectionType = productSelectionType;
    }
    
    public ProductSelectionType getProductSelectionType() {
        return productSelectionType;
    }

}
