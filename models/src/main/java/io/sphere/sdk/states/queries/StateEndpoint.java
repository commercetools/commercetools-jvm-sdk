package io.sphere.sdk.states.queries;

import io.sphere.sdk.client.JsonEndpoint;
import io.sphere.sdk.states.State;

final class StateEndpoint {
    static final JsonEndpoint<State> ENDPOINT = JsonEndpoint.of(State.typeReference(), "/states");
}
