package io.sphere.sdk.states;

import io.sphere.sdk.client.BlockingSphereClient;
import io.sphere.sdk.client.NotFoundException;
import io.sphere.sdk.models.LocalizedString;
import io.sphere.sdk.models.Reference;
import io.sphere.sdk.states.commands.StateCreateCommand;
import io.sphere.sdk.states.commands.StateDeleteCommand;
import io.sphere.sdk.states.commands.StateUpdateCommand;
import io.sphere.sdk.states.commands.updateactions.SetTransitions;
import io.sphere.sdk.states.queries.StateQuery;

import java.util.Optional;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.UnaryOperator;

import static io.sphere.sdk.test.SphereTestUtils.consumerToFunction;
import static io.sphere.sdk.test.SphereTestUtils.randomKey;
import static io.sphere.sdk.utils.SphereInternalUtils.asSet;
import static io.sphere.sdk.utils.SphereInternalUtils.setOf;
import static java.util.Locale.ENGLISH;
import static java.util.Locale.PRC;

public class StateFixtures {
    private StateFixtures() {
    }

    public static State createStateByKey(final BlockingSphereClient client, final String key) {
        final StateDraft stateDraft = createStateDraft(key);
        return client.executeBlocking(StateCreateCommand.of(stateDraft));
    }

    private static StateDraft createStateDraft(final String key) {
        return StateDraftDsl.of(key, StateType.LINE_ITEM_STATE)
                .withDescription(LocalizedString.of(ENGLISH, "description"))
                .withName(LocalizedString.of(ENGLISH, "name"))
                .withInitial(Boolean.TRUE);
    }

    public static void cleanUpByKey(final BlockingSphereClient client, final String key) {
        client.executeBlocking(StateQuery.of().byKey(key)).head().ifPresent(state -> client.executeBlocking(StateDeleteCommand.of(state)));
    }

    public static void withStateByBuilder(final BlockingSphereClient client, final UnaryOperator<StateDraftBuilder> drafting, final Consumer<State> consumer) {
        final StateDraftBuilder stateDraftBuilder = StateDraftBuilder.of(createStateDraft(randomKey()));
        final StateDraft stateDraft = drafting.apply(stateDraftBuilder).build();
        withState(client, stateDraft, consumer);
    }

    public static void withState(final BlockingSphereClient client, final UnaryOperator<StateDraftBuilder> builderUnaryOperator, final Consumer<State> consumer) {
        withUpdateableState(client, builderUnaryOperator, state -> {
            consumer.accept(state);
            return state;
        });
    }

    public static void withState(final BlockingSphereClient client, final StateDraft stateDraft, final Consumer<State> consumer) {
        final State state = client.executeBlocking(StateCreateCommand.of(stateDraft));
        consumer.accept(state);
        try {
            client.executeBlocking(StateDeleteCommand.of(state));
        } catch (NotFoundException ignored) {}
    }

    public static void withState(final BlockingSphereClient client, final Consumer<State> consumer) {
        withUpdateableState(client, consumerToFunction(consumer));
    }

    /**
     * Provides states where the first one is an initial state and has a transition to the second one.
     * The states may reused and won't be deleted.
     *
     * @param client   sphere test client
     * @param consumer consumer which uses the two states
     */
    public static void withStandardStates(final BlockingSphereClient client, final BiConsumer<State, State> consumer) {
        final String keyA = "Initial";//given from the platform
        final String keyB = StateFixtures.class + "_B";
        final State stateB = client.executeBlocking(StateQuery.of().byKey(keyB)).head().orElseGet(() -> createStateByKey(client, keyB));
        final State stateA = client.executeBlocking(StateQuery.of().byKey(keyA)).head()
                .map(initialState -> {
                    final Optional<Set<Reference<State>>> transitionOptional = Optional.ofNullable(initialState.getTransitions());
                    final Boolean initialCanTransistToStateB = transitionOptional.map(transitions -> transitions.contains(stateB.toReference())).orElse(false);
                    final Set<Reference<State>> transitions = transitionOptional.map(trans -> setOf(stateB.toReference(), trans)).orElse(asSet(stateB.toReference()));
                    final SetTransitions action = SetTransitions.of(transitions);
                    final StateUpdateCommand updateCommand = StateUpdateCommand.of(initialState, action);
                    return initialCanTransistToStateB ? initialState : client.executeBlocking(updateCommand);
                })
                .get();
        consumer.accept(stateA, stateB);
    }

    public static void withUpdateableState(final BlockingSphereClient client, final UnaryOperator<StateDraftBuilder> b, final UnaryOperator<State> f) {
        final StateDraftBuilder builder = StateDraftBuilder.of(randomKey(), StateType.LINE_ITEM_STATE);
        final StateDraft draft = b.apply(builder).build();
        final State state = client.executeBlocking(StateCreateCommand.of(draft));
        final State updatedState = f.apply(state);
        client.executeBlocking(StateDeleteCommand.of(updatedState));
    }

    public static void withUpdateableState(final BlockingSphereClient client, final Function<State, State> f) {
        final State state = createStateByKey(client, randomKey());
        final State updatedState = f.apply(state);
        try {
            client.executeBlocking(StateDeleteCommand.of(updatedState));
        } catch (NotFoundException ignored) {}
    }

    public static void withUpdatableStateOfRole(final BlockingSphereClient client, final Set<StateRole> roles, final UnaryOperator<State> f) {
        final StateDraft draft = StateDraftBuilder.of(randomKey(), StateType.REVIEW_STATE)
                .roles(roles)
                .build();
        final State state = client.executeBlocking(StateCreateCommand.of(draft));
        final State updatedState = f.apply(state);
        try {
            client.executeBlocking(StateDeleteCommand.of(updatedState));
        } catch (NotFoundException ignored) {}
    }
}
