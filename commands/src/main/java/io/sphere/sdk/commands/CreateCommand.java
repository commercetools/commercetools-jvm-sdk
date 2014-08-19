package io.sphere.sdk.commands;

/**
 * Command to create an entity in SPHERE.IO.
 *
 * @param <T> the type of the result of the command, most likely the updated entity without expanded references
 *
 * <p>Example:</p>
 *
 * {@include.example example.CategoryLifecycleExample#createCategory()}
 */
public interface CreateCommand<T> extends Command<T> {
}
