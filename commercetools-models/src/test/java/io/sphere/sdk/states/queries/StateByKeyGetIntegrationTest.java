package io.sphere.sdk.states.queries;

import io.sphere.sdk.test.IntegrationTest;
import org.junit.Test;

import static io.sphere.sdk.states.StateFixtures.withState;
import static org.assertj.core.api.Assertions.assertThat;

public class StateByKeyGetIntegrationTest extends IntegrationTest {
    @Test
    public void execution() throws Exception {
        withState(client(), state -> {
            assertThat(client().executeBlocking(StateByKeyGet.of(state.getKey())).getKey()).isEqualTo(state.getKey());
        });
    }
}
