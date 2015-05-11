package io.sphere.sdk.queries;

import org.junit.Test;
import static io.sphere.sdk.queries.Fixtures.emptyQueryModel;
import static org.assertj.core.api.Assertions.assertThat;

public class NotEqPredicateTest {
    @Test
    public void render() throws Exception {
        assertThat(NotEqPredicate.of(emptyQueryModel, "xyz").render()).isEqualTo(" <> \"xyz\"");
    }
}
