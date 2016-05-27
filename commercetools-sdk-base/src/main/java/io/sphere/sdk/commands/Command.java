package io.sphere.sdk.commands;

import io.sphere.sdk.client.SphereRequest;

/**
 * A command represents a request to update the state of platform entities.
 *
 * @param <T> the type of the result of the command
 *
 */
public interface Command<T> extends SphereRequest<T> {

}
