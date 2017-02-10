package io.sphere.sdk.shoppinglists;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import io.sphere.sdk.annotations.ResourceValue;
import io.sphere.sdk.models.LocalizedString;
import io.sphere.sdk.types.CustomFields;
import io.sphere.sdk.types.TypeDraft;

import javax.annotation.Nullable;
import java.time.ZonedDateTime;

@JsonDeserialize(as = TextLineItemImpl.class)
@ResourceValue
public interface TextLineItem
{
    String getId();

    LocalizedString getName();

    @Nullable
    LocalizedString getDescription();

    Long getQuantity();

    @Nullable
    CustomFields getCustom();

    ZonedDateTime getAddedAt();

    /**
     * An identifier for this resource which supports {@link CustomFields}.
     * @see TypeDraft#getResourceTypeIds()
     * @see io.sphere.sdk.types.Custom
     * @return ID of this resource type
     */
    static String resourceTypeId() {
        return "shopping-list-text-line-item";
    }
}
