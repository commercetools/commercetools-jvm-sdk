package io.sphere.sdk.commands;

import java.util.List;

final class UpdateCommandBody<T> {
    private final long version;
    private final List<? extends UpdateAction<T>> actions;

    UpdateCommandBody(final long version, final List<? extends UpdateAction<T>> actions) {
        this.version = version;
        this.actions = actions;
    }

    public long getVersion() {
        return version;
    }

    public List<? extends UpdateAction<T>> getActions() {
        return actions;
    }
}
