package io.sphere.sdk.states.queries;

import io.sphere.sdk.states.State;
import io.sphere.sdk.test.IntegrationTest;
import org.junit.Test;

import java.util.Optional;

import static io.sphere.sdk.states.StateFixtures.*;
import static io.sphere.sdk.test.OptionalAssert.assertThat;

public class StateByKeyFetchTest extends IntegrationTest {
    @Test
    public void execution() throws Exception {
        withState(client(), state -> {
            final String key = state.getKey();
            final Optional<State> stateOption = execute(StateByKeyFetch.of(key));
            assertThat(stateOption).isPresentAs(state);
        });
    }
}