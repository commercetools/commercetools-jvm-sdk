package io.sphere.sdk.products.messages;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.databind.JsonNode;
import io.sphere.sdk.messages.GenericMessageImpl;
import io.sphere.sdk.messages.MessageDerivateHint;
import io.sphere.sdk.messages.UserProvidedIdentifiers;
import io.sphere.sdk.models.LocalizedString;
import io.sphere.sdk.products.Product;
import io.sphere.sdk.products.ProductVariant;

import java.time.ZonedDateTime;

public final class ProductVariantAddedMessage extends GenericMessageImpl<Product> {
    public static final String MESSAGE_TYPE = "ProductVariantAdded";
    public static final MessageDerivateHint<ProductVariantAddedMessage> MESSAGE_HINT =
            MessageDerivateHint.ofSingleMessageType(MESSAGE_TYPE, ProductVariantAddedMessage.class, Product.referenceTypeId());

    private final ProductVariant variant;
    private final Boolean staged;

    @JsonCreator
    private ProductVariantAddedMessage(final String id, final Long version, final ZonedDateTime createdAt, final ZonedDateTime lastModifiedAt, final JsonNode resource, final Long sequenceNumber, final Long resourceVersion, final String type, final UserProvidedIdentifiers resourceUserProvidedIdentifiers, final LocalizedString slug, ProductVariant variant, Boolean staged) {
        super(id, version, createdAt, lastModifiedAt, resource, sequenceNumber, resourceVersion, type,resourceUserProvidedIdentifiers, Product.class);
        this.variant = variant;
        this.staged = staged;
    }
}
