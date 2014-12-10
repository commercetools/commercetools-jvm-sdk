package io.sphere.sdk.queries;

import org.junit.Test;

import java.util.Arrays;

import static java.util.stream.Collectors.toList;
import static org.fest.assertions.Assertions.assertThat;
import static io.sphere.sdk.queries.Fixtures.emptyQueryModel;

public class IsInPredicateTest {

    private final Predicate<String> model = create("foo", "bar\"evil", "baz");

    private Predicate<String> create(String ... values) {
        return new IsInPredicate<String, String, String>(emptyQueryModel, Arrays.stream(values).map(v -> StringQuerySortingModel.escape(v)).collect(toList()));
    }

    @Test
    public void queryExpression() throws Exception {
        assertThat(model.toSphereQuery()).isEqualTo(" in (\"foo\", \"bar\\\"evil\", \"baz\")");
    }

    @Test
    public void testToString() throws Exception {
        assertThat(model.toString()).contains("foo");
    }

    @Test(expected = IllegalArgumentException.class)
    public void requireAtLeastOneArgument() throws Exception {
        create();
    }
}
