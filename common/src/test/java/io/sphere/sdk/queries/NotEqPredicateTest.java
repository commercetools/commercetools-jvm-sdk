package io.sphere.sdk.queries;

import org.junit.Test;
import static io.sphere.sdk.queries.Fixtures.emptyQueryModel;
import static org.fest.assertions.Assertions.assertThat;

public class NotEqPredicateTest {
    @Test
    public void render() throws Exception {
        assertThat(new NotEqPredicate<>(emptyQueryModel, "xyz").render()).isEqualTo(" <> \"xyz\"");
    }
}
