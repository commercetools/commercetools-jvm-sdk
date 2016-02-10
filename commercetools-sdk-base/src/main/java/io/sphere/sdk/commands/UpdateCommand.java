package io.sphere.sdk.commands;

import java.util.List;

/**
 * Command which updates a commercetools resource based on a list of update actions.
 * @param <T> type of the updated object
 */
public interface UpdateCommand<T> extends Command<T> {
    /**
     * Gets the list of updates which should be applied to the object
     * @return update actions
     */
    List<? extends UpdateAction<T>> getUpdateActions();
}
