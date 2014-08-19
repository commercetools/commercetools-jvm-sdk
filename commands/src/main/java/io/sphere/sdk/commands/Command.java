package io.sphere.sdk.commands;

import io.sphere.sdk.http.ClientRequest;

/**
 * A command represents a request to update the state of SPHERE.io entities.
 *
 * @param <T> the type of the result of the command, most likely the updated entity without expanded references
 *
 * {@include.example example.CategoryLifecycleExample#createCategory()}
 */
public interface Command<T> extends ClientRequest<T> {

}
