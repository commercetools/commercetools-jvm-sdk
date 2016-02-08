package io.sphere.sdk.queries;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class QueryPredicateTest {
    final QueryPredicate<String> p1 = QueryPredicate.<String>of("masterData(current(slug(en=\"xyz-42\")");
    final QueryPredicate<String> p2 = QueryPredicate.<String>of("tags contains all (\"a\", \"b\", \"c\")");

    @Test
    public void correctClass() throws Exception {
        assertThat(p1).isInstanceOf(QueryPredicateBase.class);
        assertThat(p2).isInstanceOf(QueryPredicateBase.class);
    }

    @Test
    public void or() throws Exception {
        assertThat((p1.or(p2)).toSphereQuery()).isEqualTo("masterData(current(slug(en=\"xyz-42\") or tags contains all (\"a\", \"b\", \"c\")");
    }

    @Test
    public void and() throws Exception {
        assertThat((p1.and(p2)).toSphereQuery()).isEqualTo("masterData(current(slug(en=\"xyz-42\") and tags contains all (\"a\", \"b\", \"c\")");
    }

    @Test
    public void testToString() throws Exception {
        assertThat(p1.toString()).isEqualTo("Predicate[masterData(current(slug(en=\"xyz-42\")]");
    }

    @Test
    public void negated() throws Exception {
        final QueryPredicate<String> negated = p1.negate();
        assertThat(negated.toSphereQuery()).isEqualTo("not(masterData(current(slug(en=\"xyz-42\"))");
        assertThat(negated.toString()).isEqualTo("Predicate[not(masterData(current(slug(en=\"xyz-42\"))]");
    }
}
