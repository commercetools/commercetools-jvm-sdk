package io.sphere.sdk.states.commands;

import io.sphere.sdk.models.LocalizedStrings;
import io.sphere.sdk.states.State;
import io.sphere.sdk.states.StateDraft;
import io.sphere.sdk.states.StateType;
import io.sphere.sdk.states.queries.StateFetchByKey;
import io.sphere.sdk.test.IntegrationTest;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static java.util.Locale.ENGLISH;
import static org.fest.assertions.Assertions.assertThat;
import static io.sphere.sdk.test.OptionalAssert.assertThat;

public class StateCreateCommandTest extends IntegrationTest {

    public static final String STATE_KEY = "bar";

    @Before
    public void setUp() throws Exception {
        cleanUp();
    }

    @After
    public void tearDown() throws Exception {
        cleanUp();
    }

    private void cleanUp() {
        execute(StateFetchByKey.of(STATE_KEY)).ifPresent(state -> execute(StateDeleteCommand.of(state)));
    }

    @Test
    public void execution() throws Exception {
        final LocalizedStrings description = LocalizedStrings.of(ENGLISH, "description");
        final LocalizedStrings name = LocalizedStrings.of(ENGLISH, "name");
        final StateDraft stateDraft = StateDraft.of(STATE_KEY, StateType.LINE_ITEM_STATE)
                .withDescription(description)
                .withName(name)
                .withInitial(true);

        final State state = execute(StateCreateCommand.of(stateDraft));

        assertThat(state.getKey()).isEqualTo("bar");
        assertThat(state.getType()).isEqualTo(StateType.LINE_ITEM_STATE);
        assertThat(state.getDescription()).isPresentAs(description);
        assertThat(state.getName()).isPresentAs(name);
    }
}