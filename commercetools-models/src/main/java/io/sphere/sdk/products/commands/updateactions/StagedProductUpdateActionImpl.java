package io.sphere.sdk.products.commands.updateactions;

import io.sphere.sdk.commands.UpdateActionImpl;

import javax.annotation.Nullable;

abstract class StagedProductUpdateActionImpl<T> extends UpdateActionImpl<T> {

    @Nullable
    protected final Boolean staged;

    protected StagedProductUpdateActionImpl(final String action, @Nullable final Boolean staged) {
        super(action);
        this.staged = staged;
    }

    @Nullable
    public Boolean isStaged() {
        return staged;
    }
}
