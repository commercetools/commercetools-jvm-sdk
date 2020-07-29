package io.sphere.sdk.states.commands;

import com.neovisionaries.i18n.CountryCode;
import io.sphere.sdk.models.LocalizedString;
import io.sphere.sdk.models.Reference;
import io.sphere.sdk.states.State;
import io.sphere.sdk.states.StateFixtures;
import io.sphere.sdk.states.StateRole;
import io.sphere.sdk.states.StateType;
import io.sphere.sdk.states.commands.updateactions.*;
import io.sphere.sdk.states.queries.StateByIdGet;
import io.sphere.sdk.test.IntegrationTest;
import io.sphere.sdk.zones.Zone;
import io.sphere.sdk.zones.ZoneFixtures;
import io.sphere.sdk.zones.commands.ZoneUpdateCommand;
import io.sphere.sdk.zones.commands.updateactions.SetKey;
import org.junit.Test;

import java.util.LinkedList;
import java.util.Set;

import static io.sphere.sdk.states.StateFixtures.withUpdatableStateOfRole;
import static io.sphere.sdk.states.StateFixtures.withUpdateableState;
import static io.sphere.sdk.states.StateRole.REVIEW_INCLUDED_IN_STATISTICS;
import static io.sphere.sdk.test.SphereTestUtils.randomKey;
import static io.sphere.sdk.test.SphereTestUtils.randomSlug;
import static io.sphere.sdk.utils.SphereInternalUtils.asSet;
import static org.assertj.core.api.Assertions.assertThat;

public class StateUpdateCommandIntegrationTest extends IntegrationTest {
    @Test
    public void changeInitial() throws Exception {
        withUpdateableState(client(), state -> {
            final boolean oldIsInitial = state.isInitial();
            final boolean newIsInitial = !oldIsInitial;

            final StateUpdateCommand command = StateUpdateCommand.of(state, ChangeInitial.of(newIsInitial));
            final State updatedState = client().executeBlocking(command);
            assertThat(updatedState.isInitial()).isEqualTo(newIsInitial);
            return updatedState;
        });
    }

    @Test
    public void changeKey() throws Exception {
        withUpdateableState(client(), state -> {
            final String newKey = randomKey();

            final StateUpdateCommand command = StateUpdateCommand.of(state, ChangeKey.of(newKey));
            final State updatedState = client().executeBlocking(command);
            assertThat(updatedState.getKey()).isEqualTo(newKey);
            return updatedState;
        });
    }

    @Test
    public void setName() throws Exception {
        withUpdateableState(client(), state -> {
            final LocalizedString newName = randomSlug();

            final StateUpdateCommand command = StateUpdateCommand.of(state, SetName.of(newName));
            final State updatedState = client().executeBlocking(command);
            assertThat(updatedState.getName()).isEqualTo(newName);
            return updatedState;
        });
    }

    @Test
    public void setTransitions() throws Exception {
        withUpdateableState(client(), stateA -> {
            withUpdateableState(client(), stateB -> {
                final Set<Reference<State>> transitions = asSet(stateA.toReference());
                final StateUpdateCommand command = StateUpdateCommand.of(stateB, SetTransitions.of(transitions));
                final State updatedStateB = client().executeBlocking(command);
                assertThat(updatedStateB.getTransitions()).isEqualTo(transitions);

                //check reference expansion
                final StateByIdGet stateByIdGet = StateByIdGet.of(stateB).withExpansionPaths(m -> m.transitions());
                final State loadedStateB = client().executeBlocking(stateByIdGet);
                final Reference<State> stateReference = new LinkedList<>(loadedStateB.getTransitions()).getFirst();
                assertThat(stateReference.getObj()).isNotNull();

                final State updatedStateBWithoutTransitions = client().executeBlocking(StateUpdateCommand.of(updatedStateB, SetTransitions.of(null)));
                assertThat(updatedStateBWithoutTransitions.getTransitions()).isNull();

                return updatedStateBWithoutTransitions;
            });
            return stateA;
        });
    }

    @Test
    public void changeType() {
        withUpdateableState(client(), builder -> builder.type(StateType.REVIEW_STATE), state -> {
            final StateType newStateType = StateType.ORDER_STATE;

            final State updatedState = client().executeBlocking(StateUpdateCommand.of(state, ChangeType.of(newStateType)));

            assertThat(updatedState.getType()).isEqualTo(newStateType);

            return updatedState;
        });
    }

    @Test
    public void setRoles() throws Exception {
        withUpdateableState(client(), builder -> builder.type(StateType.REVIEW_STATE), state -> {
            final Set<StateRole> roles = asSet(REVIEW_INCLUDED_IN_STATISTICS);
            final StateUpdateCommand command = StateUpdateCommand.of(state, SetRoles.of(roles));
            final State updatedState = client().executeBlocking(command);
            assertThat(updatedState.getRoles()).isEqualTo(roles);
            return updatedState;
        });
    }

    @Test
    public void addRoles() throws Exception {
        withUpdateableState(client(), builder -> builder.type(StateType.REVIEW_STATE), state -> {
            final Set<StateRole> roles = asSet(REVIEW_INCLUDED_IN_STATISTICS);
            final StateUpdateCommand command = StateUpdateCommand.of(state, AddRoles.of(roles));
            final State updatedState = client().executeBlocking(command);
            assertThat(updatedState.getRoles()).isEqualTo(roles);
            return updatedState;
        });
    }

    @Test
    public void removeRoles() throws Exception {
        final Set<StateRole> roles = asSet(REVIEW_INCLUDED_IN_STATISTICS);
        withUpdatableStateOfRole(client(), roles, state -> {
            assertThat(state.getRoles()).isEqualTo(roles);
            final StateUpdateCommand cmdRemove = StateUpdateCommand.of(state, RemoveRoles.of(roles));
            final State updatedState = client().executeBlocking(cmdRemove);
            assertThat(updatedState.getRoles()).doesNotContainAnyElementsOf(roles);
            return updatedState;
        });
    }

    @Test
    public void updateByKey() throws Exception {
        StateFixtures.withUpdateableState(client(), state -> {
            final String newKey = randomKey();
            assertThat(state.getKey()).isNotEqualTo(newKey);
            final StateUpdateCommand command = StateUpdateCommand.of(state, ChangeKey.of(newKey));
            final State updatedState = client().executeBlocking(command);
            assertThat(updatedState.getKey()).isEqualTo(newKey);

            final String newKey2 = randomKey();
            final StateUpdateCommand commandByKey = StateUpdateCommand.ofKey(updatedState.getKey(), updatedState.getVersion(), ChangeKey.of(newKey2));
            final State updatedState2 = client().executeBlocking(commandByKey);
            assertThat(updatedState2.getKey()).isEqualTo(newKey2);

            return updatedState2;
        });
    }
}
