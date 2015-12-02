package io.sphere.sdk.commands;

import java.util.List;

final class UpdateCommandBody<T> {
    private final Long version;
    private final List<? extends UpdateAction<T>> actions;

    UpdateCommandBody(final Long version, final List<? extends UpdateAction<T>> actions) {
        this.version = version;
        this.actions = actions;
    }

    public Long getVersion() {
        return version;
    }

    public List<? extends UpdateAction<T>> getActions() {
        return actions;
    }
}
