package io.sphere.sdk.states.queries;

import io.sphere.sdk.client.SphereRequest;
import io.sphere.sdk.queries.PagedQueryResult;
import io.sphere.sdk.queries.QueryPredicate;
import io.sphere.sdk.queries.SphereEnumerationQueryModel;
import io.sphere.sdk.states.State;
import io.sphere.sdk.states.StateType;
import io.sphere.sdk.test.IntegrationTest;
import org.junit.Test;


import java.util.function.Function;

import static io.sphere.sdk.states.StateFixtures.withState;
import static java.util.Collections.singletonList;
import static org.assertj.core.api.Assertions.assertThat;

public class StateQueryIntegrationTest extends IntegrationTest {

    @Test
    public void byKey() throws Exception {
        withState(client(), state -> {
                final String key = state.getKey();
            final PagedQueryResult<State> stateOption = client().executeBlocking(StateQuery.of().byKey(key));
                assertThat(stateOption.head()).contains(state);
            }
        );
    }

    @Test
    public void byTypeIs() throws Exception {
        typeQueryTest(m -> m.is(StateType.LINE_ITEM_STATE));
    }

    @Test
    public void byTypeIsNot() throws Exception {
        typeQueryTest(m -> m.isNot(StateType.ORDER_STATE));
    }

    @Test
    public void byTypeIsIn() throws Exception {
        typeQueryTest(m -> m.isIn(singletonList(StateType.LINE_ITEM_STATE)));
    }

    @Test
    public void byTypeIsNotIn() throws Exception {
        typeQueryTest(m -> m.isNotIn(singletonList(StateType.ORDER_STATE)));
    }

    private void typeQueryTest(final Function<SphereEnumerationQueryModel<State, StateType>, QueryPredicate<State>> f) {
        withState(client(), state -> {
            final StateQuery stateQuery = StateQuery.of()
                        .withPredicates(m -> f.apply(m.type()).and(m.id().is(state.getId())));
            final PagedQueryResult<State> stateOption = client().executeBlocking(stateQuery);
                assertThat(stateOption.head()).contains(state);
            }
        );
    }
}