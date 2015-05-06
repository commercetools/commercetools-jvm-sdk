package io.sphere.sdk.states.commands;

import io.sphere.sdk.models.LocalizedStrings;
import io.sphere.sdk.states.State;
import io.sphere.sdk.states.StateDraft;
import io.sphere.sdk.states.StateType;
import io.sphere.sdk.test.IntegrationTest;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static io.sphere.sdk.states.StateFixtures.cleanUpByKey;
import static java.util.Locale.ENGLISH;
import static org.assertj.core.api.Assertions.assertThat;

public class StateCreateCommandTest extends IntegrationTest {

    public static final String KEY = StateCreateCommandTest.class.getSimpleName();

    @Before
    @After
    public void clear() throws Exception {
        cleanUpByKey(client(), KEY);
    }

    @Test
    public void execution() throws Exception {
        final LocalizedStrings description = LocalizedStrings.of(ENGLISH, "description");
        final LocalizedStrings name = LocalizedStrings.of(ENGLISH, "name");
        final StateDraft stateDraft = StateDraft.of(KEY, StateType.LINE_ITEM_STATE)
                .withDescription(description)
                .withName(name)
                .withInitial(true);

        final State state = execute(StateCreateCommand.of(stateDraft));

        assertThat(state.getKey()).isEqualTo(KEY);
        assertThat(state.getType()).isEqualTo(StateType.LINE_ITEM_STATE);
        assertThat(state.getDescription()).contains(description);
        assertThat(state.getName()).contains(name);
    }
}