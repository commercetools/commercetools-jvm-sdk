package io.sphere.sdk.products.messages;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import io.sphere.sdk.categories.Category;
import io.sphere.sdk.messages.GenericMessageImpl;
import io.sphere.sdk.messages.MessageDerivateHint;
import io.sphere.sdk.messages.UserProvidedIdentifiers;
import io.sphere.sdk.models.Reference;
import io.sphere.sdk.products.Product;

import java.time.ZonedDateTime;

@JsonDeserialize(as = ProductRemovedFromCategoryMessage.class)//important to override annotation in Message class
public final class ProductRemovedFromCategoryMessage extends GenericMessageImpl<Product> {

    public static final String MESSAGE_TYPE = "ProductRemovedFromCategory";
    public static final MessageDerivateHint<ProductRemovedFromCategoryMessage> MESSAGE_HINT =
            MessageDerivateHint.ofSingleMessageType(MESSAGE_TYPE, ProductRemovedFromCategoryMessage.class, Product.referenceTypeId());

    private final Reference<Category> category;

    private final Boolean staged;

    @JsonCreator
    private ProductRemovedFromCategoryMessage(final String id, final Long version, final ZonedDateTime createdAt, final ZonedDateTime lastModifiedAt, final JsonNode resource, final Long sequenceNumber, final Long resourceVersion, final String type, final UserProvidedIdentifiers resourceUserProvidedIdentifiers, final Reference<Category> category, final Boolean staged) {
        super(id, version, createdAt, lastModifiedAt, resource, sequenceNumber, resourceVersion, type,resourceUserProvidedIdentifiers, Product.class);
        this.category = category;
        this.staged = staged;
    }

    public Reference<Category> getCategory() {
        return category;
    }

    public Boolean getStaged() {
        return staged;
    }
    
}
