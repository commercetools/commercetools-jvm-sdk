package io.sphere.sdk.products.commands.updateactions;

import io.sphere.sdk.commands.UpdateActionImpl;

import javax.annotation.Nullable;

public abstract class StagedBase<T> extends UpdateActionImpl<T> {

    @Nullable
    private final boolean staged;

    protected StagedBase(final String action, final boolean staged) {
        super(action);
        this.staged = staged;
    }

    @Nullable
    public boolean isStaged() {
        return staged;
    }
}
