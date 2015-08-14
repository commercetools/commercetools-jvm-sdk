package io.sphere.sdk.commands;

import java.util.List;

public interface UpdateCommand<T> extends Command<T> {
    List<? extends UpdateAction<T>> getUpdateActions();
}
