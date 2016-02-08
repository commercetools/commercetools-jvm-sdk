package io.sphere.sdk.commands;

/**
 * An update action updates a resource.
 * @param <T> the type of the resource this update action can affect
 */
public interface UpdateAction<T> {
    String getAction();
}
