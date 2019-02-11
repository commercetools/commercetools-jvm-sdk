package io.sphere.sdk.orderedits.commands.stagedactions;

import io.sphere.sdk.commands.StagedUpdateActionBase;
import io.sphere.sdk.orderedits.OrderEdit;

abstract class OrderEditStagedUpdateActionBase extends StagedUpdateActionBase<OrderEdit> implements OrderEditStagedUpdateAction {

    OrderEditStagedUpdateActionBase(final String action) {
        super(action);
    }

}