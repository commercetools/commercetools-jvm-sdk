package io.sphere.sdk.states.queries;

import io.sphere.sdk.states.State;
import io.sphere.sdk.test.IntegrationTest;
import org.junit.Test;

import static io.sphere.sdk.states.StateFixtures.withState;
import static org.assertj.core.api.Assertions.assertThat;

public class StateByKeyFetchTest extends IntegrationTest {
    @Test
    public void execution() throws Exception {
        withState(client(), state -> {
            final String key = state.getKey();
            final State fetchedState = execute(StateQuery.of().byKey(key)).head().orElse(null);
            assertThat(fetchedState).isEqualTo(state);
        });
    }
}