package io.sphere.sdk.states.commands;

import io.sphere.sdk.models.LocalizedString;
import io.sphere.sdk.states.*;
import io.sphere.sdk.test.IntegrationTest;
import io.sphere.sdk.test.JsonNodeReferenceResolver;
import net.jcip.annotations.NotThreadSafe;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static io.sphere.sdk.states.StateFixtures.cleanUpByKey;
import static io.sphere.sdk.states.StateFixtures.withState;
import static io.sphere.sdk.states.StateFixtures.withStateByBuilder;
import static io.sphere.sdk.test.SphereTestUtils.draftFromJsonResource;
import static java.util.Locale.ENGLISH;
import static org.assertj.core.api.Assertions.assertThat;

@NotThreadSafe
public class StateCreateCommandIntegrationTest extends IntegrationTest {

    public static final String KEY = StateCreateCommandIntegrationTest.class.getSimpleName();

    @Before
    @After
    public void clear() throws Exception {
        cleanUpByKey(client(), KEY);
    }

    @Test
    public void execution() throws Exception {
        final LocalizedString description = LocalizedString.of(ENGLISH, "description");
        final LocalizedString name = LocalizedString.of(ENGLISH, "name");
        final StateDraft stateDraft = StateDraft.of(KEY, StateType.LINE_ITEM_STATE)
                .withDescription(description)
                .withName(name)
                .withInitial(true);

        final State state = client().executeBlocking(StateCreateCommand.of(stateDraft));

        assertThat(state.getKey()).isEqualTo(KEY);
        assertThat(state.getType()).isEqualTo(StateType.LINE_ITEM_STATE);
        assertThat(state.getDescription()).isEqualTo(description);
        assertThat(state.getName()).isEqualTo(name);
    }

    @Test
    @SuppressWarnings({"varargs", "unchecked"})
    public void createByJson() {
        withStateByBuilder(client(), builder -> builder.type(StateType.PRODUCT_STATE), otherState -> {
            final JsonNodeReferenceResolver referenceResolver = new JsonNodeReferenceResolver();
            referenceResolver.addResourceByKey("other-state", otherState);
            final StateDraft draft = draftFromJsonResource("drafts-tests/state.json", StateDraft.class, referenceResolver);
            withState(client(), draft, state -> {
                assertThat(state.getKey()).isEqualTo(KEY);
                assertThat(state.getType()).isEqualTo(StateType.PRODUCT_STATE);
                assertThat(state.isInitial()).isEqualTo(true);
                assertThat(state.getTransitions()).hasSize(1);
            });
        });

    }
}