package io.sphere.sdk.states;

import io.sphere.sdk.client.TestClient;
import io.sphere.sdk.models.LocalizedStrings;
import io.sphere.sdk.models.Reference;
import io.sphere.sdk.states.commands.StateCreateCommand;
import io.sphere.sdk.states.commands.StateDeleteCommand;
import io.sphere.sdk.states.commands.StateUpdateCommand;
import io.sphere.sdk.states.commands.updateactions.ChangeInitial;
import io.sphere.sdk.states.commands.updateactions.SetTransitions;
import io.sphere.sdk.states.queries.StateByKeyFetch;
import io.sphere.sdk.utils.SetUtils;

import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;

import static io.sphere.sdk.test.SphereTestUtils.*;
import static io.sphere.sdk.utils.SetUtils.asSet;
import static java.util.Locale.ENGLISH;

public class StateFixtures {
    private StateFixtures() {
    }

    public static State createStateByKey(final TestClient client, final String key) {
        final StateDraft stateDraft = createStateDraft(key);
        return client.execute(StateCreateCommand.of(stateDraft));
    }

    private static StateDraft createStateDraft(final String key) {
        return StateDraft.of(key, StateType.LINE_ITEM_STATE)
                .withDescription(LocalizedStrings.of(ENGLISH, "description"))
                .withName(LocalizedStrings.of(ENGLISH, "name"))
                .withInitial(Boolean.TRUE);
    }

    public static void cleanUpByKey(final TestClient client, final String key) {
        client.execute(StateByKeyFetch.of(key)).ifPresent(state -> client.execute(StateDeleteCommand.of(state)));
    }

    public static void withState(final TestClient client, final Consumer<State> consumer) {
        withUpdateableState(client, consumerToFunction(consumer));
    }

    /**
     * Provides states where the first one is an initial state and has a transition to the second one.
     * The states may reused and won't be deleted.
     * @param client sphere test client
     * @param consumer consumer which uses the two states
     */
    public static void withStandardStates(final TestClient client, final BiConsumer<State, State> consumer) {
        final String keyA = "Initial";//given from SPHERE.IO backend
        final String keyB = StateFixtures.class + "_B";
        final State stateB = client.execute(StateByKeyFetch.of(keyB)).orElseGet(() -> createStateByKey(client, keyB));
        final State stateA = client.execute(StateByKeyFetch.of(keyA))
                .map(initialState -> {
                    final Boolean initialCanTransistToStateB = initialState.getTransitions().map(transitions -> transitions.contains(stateB.toReference())).orElse(false);
                    final Set<Reference<State>> transitions = initialState.getTransitions().map(trans -> SetUtils.setOf(stateB.toReference(), trans)).orElse(asSet(stateB.toReference()));
                    final SetTransitions action = SetTransitions.of(transitions);
                    final StateUpdateCommand updateCommand = StateUpdateCommand.of(initialState, action);
                    return initialCanTransistToStateB ? initialState : client.execute(updateCommand);
                })
                .get();
        consumer.accept(stateA, stateB);
    }

    public static void withUpdateableState(final TestClient client, final Function<State, State> f) {
        final State state = createStateByKey(client, randomKey());
        final State updatedState = f.apply(state);
        client.execute(StateDeleteCommand.of(updatedState));
    }
}
