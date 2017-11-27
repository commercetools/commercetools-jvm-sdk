package io.sphere.sdk.products.messages;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import io.sphere.sdk.messages.GenericMessageImpl;
import io.sphere.sdk.messages.MessageDerivateHint;
import io.sphere.sdk.models.LocalizedString;
import io.sphere.sdk.products.Product;

import java.time.ZonedDateTime;

/**
 * Message emitted by the {@link io.sphere.sdk.products.commands.updateactions.RevertStagedVariantChanges} update action.
 *
 * {@include.example io.sphere.sdk.products.commands.ProductUpdateCommandIntegrationTest#revertStagedVariantChanges()}
 */
@JsonDeserialize(as = ProductStagedVariantChangesRevertedMessage.class)//important to override annotation in Message class
public final class ProductStagedVariantChangesRevertedMessage extends GenericMessageImpl<Product> {
    public static final String MESSAGE_TYPE = "ProductStagedVariantChangesReverted";
    public static final MessageDerivateHint<ProductStagedVariantChangesRevertedMessage> MESSAGE_HINT =
            MessageDerivateHint.ofSingleMessageType(MESSAGE_TYPE, ProductStagedVariantChangesRevertedMessage.class, Product.referenceTypeId());

    public Integer getVariantId() {
        return variantId;
    }

    private final Integer variantId;

    @JsonCreator
    private ProductStagedVariantChangesRevertedMessage(final String id, final Long version, final ZonedDateTime createdAt, final ZonedDateTime lastModifiedAt, final JsonNode resource, final Long sequenceNumber, final Long resourceVersion, final String type, final Integer variantId) {
        super(id, version, createdAt, lastModifiedAt, resource, sequenceNumber, resourceVersion, type, Product.class);
        this.variantId = variantId;
    }

}
