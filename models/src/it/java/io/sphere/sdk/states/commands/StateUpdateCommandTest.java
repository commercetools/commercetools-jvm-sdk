package io.sphere.sdk.states.commands;

import io.sphere.sdk.states.State;
import io.sphere.sdk.states.StateFixtures;
import io.sphere.sdk.states.commands.updateactions.ChangeInitial;
import io.sphere.sdk.test.IntegrationTest;
import org.junit.Test;

import static org.fest.assertions.Assertions.assertThat;

public class StateUpdateCommandTest extends IntegrationTest {
    @Test
    public void changeInitial() throws Exception {
        StateFixtures.withUpdateableState(client(), state -> {
            final boolean oldIsInitial = state.isInitial();
            final boolean newIsInitial = !oldIsInitial;

            final StateUpdateCommand command = StateUpdateCommand.of(state, ChangeInitial.of(newIsInitial));
            final State updatedState = execute(command);
            assertThat(updatedState.isInitial()).isEqualTo(newIsInitial);
            return state;
        });
    }
}