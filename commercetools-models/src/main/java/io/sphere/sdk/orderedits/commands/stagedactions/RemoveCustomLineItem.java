package io.sphere.sdk.orderedits.commands.stagedactions;

import com.fasterxml.jackson.annotation.JsonCreator;
import io.sphere.sdk.carts.CustomLineItem;

public final class RemoveCustomLineItem extends OrderEditStagedUpdateActionBase {

    private final String customLineItemId;

    @JsonCreator
    private RemoveCustomLineItem(final String customLineItemId) {
        super("removeCustomLineItem");
        this.customLineItemId = customLineItemId;
    }

    public static RemoveCustomLineItem of(final String customLineItemId) {
        return new RemoveCustomLineItem(customLineItemId);
    }

    public static RemoveCustomLineItem of(final CustomLineItem customLineItem) {
        return of(customLineItem.getId());
    }

    public String getCustomLineItemId() {
        return customLineItemId;
    }
}