package io.sphere.sdk.products.messages;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import io.sphere.sdk.messages.GenericMessageImpl;
import io.sphere.sdk.messages.MessageDerivateHint;
import io.sphere.sdk.products.Product;
import io.sphere.sdk.products.ProductProjection;

import java.time.ZonedDateTime;

@JsonDeserialize(as = ProductPublishedMessage.class)//important to override annotation in Message class
public final class ProductPublishedMessage extends GenericMessageImpl<Product> {
    public static final String MESSAGE_TYPE = "ProductPublished";
    public static final MessageDerivateHint<ProductPublishedMessage> MESSAGE_HINT =
            MessageDerivateHint.ofSingleMessageType(MESSAGE_TYPE, ProductPublishedMessage.class);

    private final ProductProjection productProjection;

    @JsonCreator
    private ProductPublishedMessage(final String id, final Long version, final ZonedDateTime createdAt, final ZonedDateTime lastModifiedAt, final JsonNode resource, final Long sequenceNumber, final Long resourceVersion, final String type, final ProductProjection productProjection) {
        super(id, version, createdAt, lastModifiedAt, resource, sequenceNumber, resourceVersion, type, Product.class);
        this.productProjection = productProjection;
    }

    public ProductProjection getProductProjection() {
        return productProjection;
    }
}
