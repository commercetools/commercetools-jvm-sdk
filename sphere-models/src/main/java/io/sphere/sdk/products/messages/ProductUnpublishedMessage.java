package io.sphere.sdk.products.messages;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import io.sphere.sdk.messages.GenericMessageImpl;
import io.sphere.sdk.messages.MessageDerivatHint;
import io.sphere.sdk.products.Product;
import io.sphere.sdk.queries.PagedQueryResult;

import java.time.ZonedDateTime;

import static io.sphere.sdk.products.messages.MessagesPackage.PRODUCT_REFERENCE_TYPE_REFERENCE;

@JsonDeserialize(as = ProductUnpublishedMessage.class)//important to override annotation in Message class
public class ProductUnpublishedMessage extends GenericMessageImpl<Product> {
    public static final String MESSAGE_TYPE = "ProductUnpublished";
    public static final MessageDerivatHint<ProductUnpublishedMessage> MESSAGE_HINT =
            MessageDerivatHint.ofSingleMessageType(MESSAGE_TYPE,
                    new TypeReference<PagedQueryResult<ProductUnpublishedMessage>>() {
                    },
                    new TypeReference<ProductUnpublishedMessage>() {
                    }
            );

    @JsonCreator
    private ProductUnpublishedMessage(final String id, final Long version, final ZonedDateTime createdAt, final ZonedDateTime lastModifiedAt, final JsonNode resource, final Long sequenceNumber, final Long resourceVersion, final String type) {
        super(id, version, createdAt, lastModifiedAt, resource, sequenceNumber, resourceVersion, type, PRODUCT_REFERENCE_TYPE_REFERENCE);
    }
}
