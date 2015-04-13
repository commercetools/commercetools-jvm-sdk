package io.sphere.sdk.states.commands;

import io.sphere.sdk.states.State;
import io.sphere.sdk.states.commands.updateactions.ChangeInitial;
import io.sphere.sdk.states.commands.updateactions.ChangeKey;
import io.sphere.sdk.test.IntegrationTest;
import org.junit.Test;

import static io.sphere.sdk.states.StateFixtures.withUpdateableState;
import static io.sphere.sdk.test.SphereTestUtils.*;
import static org.fest.assertions.Assertions.assertThat;

public class StateUpdateCommandTest extends IntegrationTest {
    @Test
    public void changeInitial() throws Exception {
        withUpdateableState(client(), state -> {
            final boolean oldIsInitial = state.isInitial();
            final boolean newIsInitial = !oldIsInitial;

            final StateUpdateCommand command = StateUpdateCommand.of(state, ChangeInitial.of(newIsInitial));
            final State updatedState = execute(command);
            assertThat(updatedState.isInitial()).isEqualTo(newIsInitial);
            return state;
        });
    }

    @Test
    public void changeKey() throws Exception {
        withUpdateableState(client(), state -> {
            final String newKey = randomKey();

            final StateUpdateCommand command = StateUpdateCommand.of(state, ChangeKey.of(newKey));
            final State updatedState = execute(command);
            assertThat(updatedState.getKey()).isEqualTo(newKey);
            return state;
        });
    }
}