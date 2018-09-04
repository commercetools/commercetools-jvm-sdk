package io.sphere.sdk.products.messages;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import io.sphere.sdk.messages.GenericMessageImpl;
import io.sphere.sdk.messages.MessageDerivateHint;
import io.sphere.sdk.messages.UserProvidedIdentifiers;
import io.sphere.sdk.products.Image;
import io.sphere.sdk.products.Product;
import io.sphere.sdk.products.ProductVariant;

import java.time.ZonedDateTime;

/**
 * This message is the result of the {@link io.sphere.sdk.products.commands.updateactions.AddExternalImage} update action.
 */
@JsonDeserialize(as = ProductImageAddedMessage.class)
public final class ProductImageAddedMessage extends GenericMessageImpl<Product> {
    public static final String MESSAGE_TYPE = "ProductImageAdded";
    public static final MessageDerivateHint<ProductImageAddedMessage> MESSAGE_HINT =
            MessageDerivateHint.ofSingleMessageType(MESSAGE_TYPE, ProductImageAddedMessage.class, Product.referenceTypeId());

    private final Long variantId;

    private final Image image;

    private final Boolean staged;

    @JsonCreator
    private ProductImageAddedMessage(final String id, final Long version, final ZonedDateTime createdAt, final ZonedDateTime lastModifiedAt,
                                    final JsonNode resource, final Long sequenceNumber, final Long resourceVersion, final String type, final UserProvidedIdentifiers resourceUserProvidedIdentifiers,
                                    final Long variantId, final Image image, final Boolean staged) {
        super(id, version, createdAt, lastModifiedAt, resource, sequenceNumber, resourceVersion, type,resourceUserProvidedIdentifiers, Product.class);
        this.variantId = variantId;
        this.image = image;
        this.staged = staged;
    }

    /**
     * The variant id to identify the variant for which the image was added.
     *
     * @see ProductVariant#getId()
     *
     * @return the added variant id
     */
    public Long getVariantId() {
        return variantId;
    }

    /**
     * The image that was added.
     *
     * @see ProductVariant#getImages()
     *
     * @return the added image
     */
    public Image getImage() {
        return image;
    }

    /**
     * {@code true} if it was applied only to staged.
     *
     * @return the staged flag
     */
    public Boolean getStaged() {
        return staged;
    }
}
