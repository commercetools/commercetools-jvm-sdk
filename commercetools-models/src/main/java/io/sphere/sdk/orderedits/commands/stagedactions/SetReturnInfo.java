package io.sphere.sdk.orderedits.commands.stagedactions;

import io.sphere.sdk.orders.ReturnInfoDraft;

import java.util.List;

public final class SetReturnInfo extends OrderEditStagedUpdateActionBase {
    private final List<? extends ReturnInfoDraft> items;

    private SetReturnInfo(final List<? extends ReturnInfoDraft> items) {
        super("setReturnInfo");
        this.items = items;
    }

    public static SetReturnInfo of(final List<? extends ReturnInfoDraft> items) {
        return new SetReturnInfo(items);
    }

    public List<? extends ReturnInfoDraft> getItems() {
        return items;
    }
}
