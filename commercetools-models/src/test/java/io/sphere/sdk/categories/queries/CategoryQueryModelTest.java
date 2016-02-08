package io.sphere.sdk.categories.queries;

import io.sphere.sdk.categories.Category;
import io.sphere.sdk.queries.QueryPredicate;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class CategoryQueryModelTest {
    @Test
    public void not() throws Exception {
        final QueryPredicate<Category> predicate = CategoryQuery.of().withPredicates(m -> m.not(m.id().is("foo"))).predicates().get(0);
        assertThat(predicate.toSphereQuery()).isEqualTo("not(id=\"foo\")");
    }
}