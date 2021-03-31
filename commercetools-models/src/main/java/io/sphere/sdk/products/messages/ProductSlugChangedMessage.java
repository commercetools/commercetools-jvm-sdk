package io.sphere.sdk.products.messages;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import io.sphere.sdk.messages.GenericMessageImpl;
import io.sphere.sdk.messages.MessageDerivateHint;
import io.sphere.sdk.messages.UserProvidedIdentifiers;
import io.sphere.sdk.models.LocalizedString;
import io.sphere.sdk.products.Product;

import java.time.ZonedDateTime;

/**
 * Message emitted by the {@link io.sphere.sdk.products.commands.updateactions.ChangeSlug} update action.
 *
 * {@include.example io.sphere.sdk.products.commands.ProductUpdateCommandIntegrationTest#changeSlug()}
 */
@JsonDeserialize(as = ProductSlugChangedMessage.class)//important to override annotation in Message class
public final class ProductSlugChangedMessage extends GenericMessageImpl<Product> {
    public static final String MESSAGE_TYPE = "ProductSlugChanged";
    public static final MessageDerivateHint<ProductSlugChangedMessage> MESSAGE_HINT =
            MessageDerivateHint.ofSingleMessageType(MESSAGE_TYPE, ProductSlugChangedMessage.class, Product.referenceTypeId());

    private final LocalizedString slug;
    private final LocalizedString oldSlug;

    @JsonCreator
    private ProductSlugChangedMessage(final String id, final Long version, final ZonedDateTime createdAt, final ZonedDateTime lastModifiedAt, final JsonNode resource, final Long sequenceNumber, final Long resourceVersion, final String type, final UserProvidedIdentifiers resourceUserProvidedIdentifiers, final LocalizedString slug, final LocalizedString oldSlug) {
        super(id, version, createdAt, lastModifiedAt, resource, sequenceNumber, resourceVersion, type,resourceUserProvidedIdentifiers, Product.class);
        this.slug = slug;
        this.oldSlug = oldSlug;
    }

    public LocalizedString getSlug() {
        return slug;
    }

    public LocalizedString getOldSlug() {
        return oldSlug;
    }
}
