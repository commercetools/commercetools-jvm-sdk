package io.sphere.sdk.states.commands;

import io.sphere.sdk.states.State;
import io.sphere.sdk.test.IntegrationTest;
import org.junit.Before;
import org.junit.Test;

import static io.sphere.sdk.states.StateFixtures.cleanUpByKey;
import static io.sphere.sdk.states.StateFixtures.createStateByKey;
import static io.sphere.sdk.test.SphereTestUtils.*;

public class StateDeleteCommandIntegrationTest extends IntegrationTest {

    public static final String KEY = StateDeleteCommandIntegrationTest.class.getSimpleName();

    @Before
    public void setUp() throws Exception {
        cleanUpByKey(client(), KEY);
    }

    @Test
    public void execution() throws Exception {
        final State state = getState();
        assertEventually(() -> {
            final State deletedState = client().executeBlocking(StateDeleteCommand.of(state));
        });

    }

    @Test
    public void deleteByKey() throws Exception {
        final State state = getState();

        assertEventually(() -> {
            final State deletedState = client().executeBlocking(StateDeleteCommand.ofKey(state.getKey(), state.getVersion()));
        });
    }

    private State getState() {
        return createStateByKey(client(), KEY);
    }
}
