package io.sphere.sdk.states.commands;

import io.sphere.sdk.states.State;
import io.sphere.sdk.states.StateFixtures;
import io.sphere.sdk.test.IntegrationTest;
import org.junit.Test;

public class StateDeleteCommandIntegrationTest extends IntegrationTest {

    @Test
    public void execution() throws Exception {
        StateFixtures.withState(client(), state -> {
            final State deletedState = client().executeBlocking(StateDeleteCommand.of(state));
        });

    }

    @Test
    public void deleteByKey() throws Exception {
        StateFixtures.withState(client(), state -> {
            final State deletedState = client().executeBlocking(StateDeleteCommand.ofKey(state.getKey(), state.getVersion()));
        });
    }
}
