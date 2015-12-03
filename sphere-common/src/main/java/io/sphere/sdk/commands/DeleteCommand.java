package io.sphere.sdk.commands;

/**
 * Command which deletes one or more resources in SPHERE.IO.
 *
 * @param <T> the type of the result of the command, most likely the deleted resource without expanded references
 *
 */
public interface DeleteCommand<T> extends Command<T> {
}
