package io.sphere.sdk.categories.messages;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonTypeName;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import io.sphere.sdk.annotations.MessageTypeInfo;
import io.sphere.sdk.categories.Category;
import io.sphere.sdk.messages.GenericMessageImpl;
import io.sphere.sdk.messages.MessageDerivateHint;

import java.time.ZonedDateTime;

@JsonDeserialize(as = CategoryCreatedMessage.class)//important to override annotation in Message class
@MessageTypeInfo(type = CategoryCreatedMessage.MESSAGE_TYPE, resourceType = "category") // TODO improve this further
@JsonTypeName("message")
public final class CategoryCreatedMessage extends GenericMessageImpl<Category> {
    public static final String MESSAGE_TYPE = "CategoryCreated";
    public static final MessageDerivateHint<CategoryCreatedMessage> MESSAGE_HINT =
            MessageDerivateHint.ofSingleMessageType(MESSAGE_TYPE, CategoryCreatedMessage.class, Category.referenceTypeId());

    private final Category category;

    @JsonCreator
    private CategoryCreatedMessage(final String id, final Long version, final ZonedDateTime createdAt, final ZonedDateTime lastModifiedAt, final JsonNode resource, final Long sequenceNumber, final Long resourceVersion, final String type, final Category category) {
        super(id, version, createdAt, lastModifiedAt, resource, sequenceNumber, resourceVersion, type, Category.class);
        this.category = category;
    }

    /**
     * Gets the category object at creation time.
     *
     * @return the category at creation time
     */
    public Category getCategory(){
        return category;
    }

}
