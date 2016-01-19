package io.sphere.sdk.reviews.approvaldemo;

import io.sphere.sdk.client.BlockingSphereClient;
import io.sphere.sdk.states.State;
import io.sphere.sdk.states.StateDraft;
import io.sphere.sdk.states.StateRole;
import io.sphere.sdk.states.StateType;
import io.sphere.sdk.states.commands.StateCreateCommand;

import java.util.List;

import static java.util.Arrays.asList;
import static java.util.Collections.singleton;

public class CreateReviewStates {
    public static List<State> createStates(final BlockingSphereClient client) {
        final StateDraft approvedStateDraft =
                StateDraft.of("approved", StateType.REVIEW_STATE).withRoles(StateRole.REVIEW_INCLUDED_IN_STATISTICS);
        final State approvedState = client.executeBlocking(StateCreateCommand.of(approvedStateDraft));

        final StateDraft initialStateDraft =
                StateDraft.of("to-approve", StateType.REVIEW_STATE)
                        .withInitial(true)
                        .withTransitions(singleton(approvedState.toReference()));
        final State initialState = client.executeBlocking(StateCreateCommand.of(initialStateDraft));
        return asList(initialState, approvedState);
    }
}
