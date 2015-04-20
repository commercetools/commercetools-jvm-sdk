package io.sphere.sdk.queries;

import org.junit.Test;

import static org.fest.assertions.Assertions.assertThat;

public class PredicateBaseTest {
    final Predicate<String> p1 = Predicate.<String>of("masterData(current(slug(en=\"xyz-42\")");
    final Predicate<String> p2 = Predicate.<String>of("tags contains all (\"a\", \"b\", \"c\")");

    @Test
    public void correctClass() throws Exception {
        assertThat(p1).isInstanceOf(PredicateBase.class);
        assertThat(p2).isInstanceOf(PredicateBase.class);
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
}
