package io.sphere.sdk.orderedits.commands.updateactions;

import io.sphere.sdk.commands.UpdateActionImpl;
import io.sphere.sdk.orderedits.OrderEdit;
import io.sphere.sdk.orderedits.commands.stagedactions.OrderEditStagedUpdateAction;

public final class AddStagedAction extends UpdateActionImpl<OrderEdit> {

    private final OrderEditStagedUpdateAction stagedAction;

    private AddStagedAction(final OrderEditStagedUpdateAction stagedAction) {
        super("addStagedAction");
        this.stagedAction = stagedAction;
    }

    public static AddStagedAction of(final OrderEditStagedUpdateAction stagedAction) {
        return new AddStagedAction(stagedAction);
    }

    public OrderEditStagedUpdateAction getStagedAction() {
        return stagedAction;
    }
}
