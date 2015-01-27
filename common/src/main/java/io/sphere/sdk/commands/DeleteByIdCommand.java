package io.sphere.sdk.commands;

/**
 * Command which deletes an entity by ID in SPHERE.IO.
 *
 * @param <T> the type of the result of the command, most likely the deleted entity without expanded references
 *
 */
public interface DeleteByIdCommand<T> extends DeleteCommand<T> {
}
