package io.sphere.sdk.productselections.messages;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import io.sphere.sdk.messages.GenericMessageImpl;
import io.sphere.sdk.messages.MessageDerivateHint;
import io.sphere.sdk.messages.UserProvidedIdentifiers;
import io.sphere.sdk.models.LocalizedString;
import io.sphere.sdk.productselections.ProductSelection;

import java.time.ZonedDateTime;

@JsonDeserialize(as = ProductSelectionDeletedMessage.class)//important to override annotation in Message class
public final class ProductSelectionDeletedMessage extends GenericMessageImpl<ProductSelection> {
    public static final String MESSAGE_TYPE = "ProductSelectionDeleted";
    public static final MessageDerivateHint<ProductSelectionDeletedMessage> MESSAGE_HINT =
            MessageDerivateHint.ofSingleMessageType(MESSAGE_TYPE, ProductSelectionDeletedMessage.class, ProductSelection.referenceTypeId());

    private final LocalizedString name;

    @JsonCreator
    private ProductSelectionDeletedMessage(final String id, final Long version, final ZonedDateTime createdAt, final ZonedDateTime lastModifiedAt, final JsonNode resource, final Long sequenceNumber, final Long resourceVersion, final String type, final UserProvidedIdentifiers resourceUserProvidedIdentifiers, final LocalizedString name) {
        super(id, version, createdAt, lastModifiedAt, resource, sequenceNumber, resourceVersion, type, resourceUserProvidedIdentifiers, ProductSelection.class);
        this.name = name;
    }
    
    public LocalizedString getName() {
        return name;
    }

}
