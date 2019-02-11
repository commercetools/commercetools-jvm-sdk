package io.sphere.sdk.orderedits.commands.stagedactions;

import com.fasterxml.jackson.annotation.JsonCreator;
import io.sphere.sdk.carts.LineItemDraft;

public final class AddLineItem extends OrderEditStagedUpdateActionBase {

    private final LineItemDraft lineItem;

    @JsonCreator
    private AddLineItem(final LineItemDraft lineItemDraft) {
        super("addLineItem");
        this.lineItem = lineItemDraft;
    }

    public static AddLineItem of(final LineItemDraft lineItemDraft) {
        return new AddLineItem(lineItemDraft);
    }

    public LineItemDraft getLineItem() {
        return lineItem;
    }
}