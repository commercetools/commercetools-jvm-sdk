package io.sphere.sdk.states;

import io.sphere.sdk.client.SphereRequest;
import io.sphere.sdk.client.TestClient;
import io.sphere.sdk.queries.PagedQueryResult;
import io.sphere.sdk.queries.QueryIntegrationTest;
import io.sphere.sdk.states.commands.StateCreateCommand;
import io.sphere.sdk.states.commands.StateDeleteCommand;
import io.sphere.sdk.states.queries.StateByKeyFetch;
import io.sphere.sdk.states.queries.StateQuery;
import org.junit.Test;

import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Supplier;

import static io.sphere.sdk.test.OptionalAssert.assertThat;

public class StateIntegrationTest extends QueryIntegrationTest<State> {

    @Override
    protected SphereRequest<State> deleteCommand(State item) {
        return StateDeleteCommand.of(item);
    }

    @Override
    protected SphereRequest<State> newCreateCommandForName(final String name) {
        return StateCreateCommand.of(StateDraft.of(name, StateType.LINE_ITEM_STATE));
    }

    @Override
    protected String extractName(final State instance) {
        return instance.getKey();
    }

    @Override
    protected SphereRequest<PagedQueryResult<State>> queryRequestForQueryAll() {
        return StateQuery.of();
    }

    @Override
    protected SphereRequest<PagedQueryResult<State>> queryObjectForName(final String name) {
        return StateQuery.of().byKey(name);
    }

    @Override
    protected SphereRequest<PagedQueryResult<State>> queryObjectForNames(final List<String> names) {
        return StateQuery.of().withPredicate(StateQuery.model().key().isOneOf(names));
    }

    @Test
    public void FetchStateByKey() throws Exception {
        withState(client(), StateDraftBuilder.of("fubar", StateType.LINE_ITEM_STATE), state -> {
                    final Optional<State> stateOption = execute(StateByKeyFetch.of(state.getKey()));
                    assertThat(stateOption).isPresentAs(state);
                }
        );
    }

    private void withState(final TestClient client, final Supplier<StateDraft> creator, final Consumer<State> user) {
        final StateDraft stateDraft = creator.get();
        cleanUpByName(stateDraft.getKey());
        final State state = client.execute(StateCreateCommand.of(stateDraft));
        user.accept(state);
        cleanUpByName(stateDraft.getKey());
    }
}
