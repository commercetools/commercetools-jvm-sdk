package io.sphere.sdk.states.queries;

import io.sphere.sdk.test.IntegrationTest;
import org.junit.Test;

import static io.sphere.sdk.states.StateFixtures.withState;
import static org.assertj.core.api.Assertions.*;

public class StateByIdGetIntegrationTest extends IntegrationTest {
    @Test
    public void execution() throws Exception {
        withState(client(), state -> {
            assertThat(client().executeBlocking(StateByIdGet.of(state)).getId()).isEqualTo(state.getId());
        });
    }
}