package io.sphere.sdk.queries;

import org.junit.Test;
import static io.sphere.sdk.queries.Fixtures.emptyQueryModel;
import static io.sphere.sdk.queries.StringQuerySortingModel.*;
import static org.assertj.core.api.Assertions.assertThat;

public class NotEqQueryPredicateTest {
    @Test
    public void render() throws Exception {
        assertThat(ComparisonQueryPredicate.ofIsNotEqualTo(emptyQueryModel, normalize("xyz")).render()).isEqualTo("<>\"xyz\"");
    }
}
