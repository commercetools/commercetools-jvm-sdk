package io.sphere.sdk.states.queries;

import io.sphere.sdk.queries.PagedQueryResult;
import io.sphere.sdk.states.State;
import io.sphere.sdk.test.IntegrationTest;
import org.junit.Test;


import static io.sphere.sdk.states.StateFixtures.withState;
import static org.assertj.core.api.Assertions.assertThat;

public class StateQueryTest extends IntegrationTest {

    @Test
    public void byKey() throws Exception {
        withState(client(), state -> {
                final String key = state.getKey();
                final PagedQueryResult<State> stateOption = execute(StateQuery.of().byKey(key));
                assertThat(stateOption.head()).contains(state);
            }
        );
    }
}