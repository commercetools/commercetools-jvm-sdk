package io.sphere.sdk.commands;

/**
 * Command to create a resource in commercetools based on a draft object.
 *
 * @param <T> the type of the result of the command
 * @param <D> the type of the draft (body of the command)
 *
 */
public interface DraftBasedCreateCommand<T, D> extends CreateCommand<T> {
    D getDraft();
}
