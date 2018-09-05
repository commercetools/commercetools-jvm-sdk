package io.sphere.sdk.products.messages;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import io.sphere.sdk.messages.GenericMessageImpl;
import io.sphere.sdk.messages.MessageDerivateHint;
import io.sphere.sdk.messages.UserProvidedIdentifiers;
import io.sphere.sdk.products.Product;
import io.sphere.sdk.products.ProductProjection;

import java.time.ZonedDateTime;

@JsonDeserialize(as = ProductCreatedMessage.class)//important to override annotation in Message class
public final class ProductCreatedMessage extends GenericMessageImpl<Product> {
    public static final String MESSAGE_TYPE = "ProductCreated";
    public static final MessageDerivateHint<ProductCreatedMessage> MESSAGE_HINT =
            MessageDerivateHint.ofSingleMessageType(MESSAGE_TYPE, ProductCreatedMessage.class, Product.referenceTypeId());

    private final ProductProjection productProjection;

    @JsonCreator
    private ProductCreatedMessage(final String id, final Long version, final ZonedDateTime createdAt, final ZonedDateTime lastModifiedAt, final JsonNode resource, final Long sequenceNumber, final Long resourceVersion, final String type, final UserProvidedIdentifiers resourceUserProvidedIdentifiers, final ProductProjection productProjection) {
        super(id, version, createdAt, lastModifiedAt, resource, sequenceNumber, resourceVersion, type,resourceUserProvidedIdentifiers, Product.class);
        this.productProjection = productProjection;
    }

    /**
     * The staged projection of the product at the time of creation.
     * @return staged product
     */
    public ProductProjection getProductProjection() {
        return productProjection;
    }
}
