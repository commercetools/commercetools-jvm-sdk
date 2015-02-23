package io.sphere.sdk.states;

import io.sphere.sdk.client.TestClient;
import io.sphere.sdk.models.LocalizedStrings;
import io.sphere.sdk.states.commands.StateCreateCommand;
import io.sphere.sdk.states.commands.StateDeleteCommand;
import io.sphere.sdk.states.queries.StateFetchByKey;

import static java.util.Locale.ENGLISH;

public class StateFixtures {
    private StateFixtures() {
    }

    public static State createStateByKey(final TestClient client, final String key) {
        final StateDraft stateDraft = StateDraft.of(key, StateType.LINE_ITEM_STATE)
                .withDescription(LocalizedStrings.of(ENGLISH, "description"))
                .withName(LocalizedStrings.of(ENGLISH, "name"))
                .withInitial(Boolean.TRUE);
        return client.execute(StateCreateCommand.of(stateDraft));
    }

    public static void cleanUpByKey(final TestClient client, final String key) {
        client.execute(StateFetchByKey.of(key)).ifPresent(state -> client.execute(StateDeleteCommand.of(state)));
    }
}
