package io.sphere.sdk.commands;

/**
 * Commands which deletes an entity by ID in SPHERE.IO.
 *
 * @param <T> the type of the result of the command, most likely the updated entity without expanded references
 *
 */
public interface DeleteByIdCommand<T> extends Command<T> {
}
