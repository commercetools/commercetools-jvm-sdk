package io.sphere.sdk.categories.messages;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import io.sphere.sdk.categories.Category;
import io.sphere.sdk.messages.GenericMessageImpl;
import io.sphere.sdk.messages.MessageDerivateHint;
import io.sphere.sdk.messages.UserProvidedIdentifiers;
import io.sphere.sdk.models.LocalizedString;

import java.time.ZonedDateTime;

@JsonDeserialize(as = CategorySlugChangedMessage.class)//important to override annotation in Message class
public final class CategorySlugChangedMessage extends GenericMessageImpl<Category> {
    public static final String MESSAGE_TYPE = "CategorySlugChanged";
    public static final MessageDerivateHint<CategorySlugChangedMessage> MESSAGE_HINT =
            MessageDerivateHint.ofSingleMessageType(MESSAGE_TYPE, CategorySlugChangedMessage.class, Category.referenceTypeId());

    private final LocalizedString slug;
    private final LocalizedString oldSlug;

    @JsonCreator
    private CategorySlugChangedMessage(final String id, final Long version, final ZonedDateTime createdAt, final ZonedDateTime lastModifiedAt, final JsonNode resource, final Long sequenceNumber, final Long resourceVersion, final String type, final UserProvidedIdentifiers resourceUserProvidedIdentifiers, final LocalizedString slug, final LocalizedString oldSlug) {
        super(id, version, createdAt, lastModifiedAt, resource, sequenceNumber, resourceVersion, type,resourceUserProvidedIdentifiers, Category.class);
        this.slug = slug;
        this.oldSlug = oldSlug;
    }

    /**
     * Gets the new Slug
     *
     * @return the new slug
     */
    public LocalizedString getSlug(){
        return slug;
    }

    public LocalizedString getOldSlug() {
        return oldSlug;
    }
}
