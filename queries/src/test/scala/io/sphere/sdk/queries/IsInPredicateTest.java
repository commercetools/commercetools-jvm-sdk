package io.sphere.sdk.queries;

import org.junit.Test;

import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;
import static org.fest.assertions.Assertions.assertThat;

public class IsInPredicateTest {

    private final QueryModel<String> emptyQueryModel = new QueryModel<String>() {
        @Override
        public Optional<String> getPathSegment() {
            return Optional.empty();
        }

        @Override
        public Optional<? extends QueryModel<String>> getParent() {
            return Optional.empty();
        }
    };

    private Predicate<String> model = create("foo", "bar\"evil", "baz");

    private Predicate<String> create(String ... values) {
        return new IsInPredicate<String, String, String>(emptyQueryModel, Stream.of(values).map(v -> StringQuerySortingModel.escape(v)).collect(toList()));
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
