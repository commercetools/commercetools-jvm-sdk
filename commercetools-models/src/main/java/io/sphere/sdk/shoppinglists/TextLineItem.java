package io.sphere.sdk.shoppinglists;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import io.sphere.sdk.annotations.ResourceValue;
import io.sphere.sdk.models.LocalizedString;
import io.sphere.sdk.types.CustomFields;
import io.sphere.sdk.types.TypeDraft;

import javax.annotation.Nullable;
import java.time.ZonedDateTime;

/**
 * A TextLineItem has a name, a description and a quantity.
 * A TextLineItem can have {@link io.sphere.sdk.types.Custom custom fields}.
 *
 * @see io.sphere.sdk.shoppinglists.commands.updateactions.AddTextLineItem
 * @see io.sphere.sdk.shoppinglists.commands.updateactions.RemoveTextLineItem
 * @see io.sphere.sdk.shoppinglists.commands.updateactions.ChangeTextLineItemQuantity
 * @see io.sphere.sdk.shoppinglists.commands.updateactions.ChangeTextLineItemName
 * @see io.sphere.sdk.shoppinglists.commands.updateactions.SetTextLineItemDescription
 * @see io.sphere.sdk.shoppinglists.commands.updateactions.SetTextLineItemCustomField
 * @see io.sphere.sdk.shoppinglists.commands.updateactions.SetTextLineItemCustomType
 * * @see ShoppingList#getTextLineItems()
 */

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
