package io.sphere.sdk.queries;

import org.junit.Test;
import static io.sphere.sdk.queries.Fixtures.*;
import static io.sphere.sdk.queries.SortDirection.*;
import static org.fest.assertions.Assertions.assertThat;

public class SphereSortTest {
    @Test
    public void singleElement() throws Exception {
        final String actual = new SphereSort<>(fooQueryModel(), ASC).toSphereSort();
        assertThat(actual).isEqualTo("foo asc");
    }

    @Test
    public void twoElements() throws Exception {
        final String actual = new SphereSort<>(barQueryModel(), ASC).toSphereSort();
        assertThat(actual).isEqualTo("foo.bar asc");
    }

    @Test
    public void threeElementsDesc() throws Exception {
        final String actual = new SphereSort<>(bazQueryModel(), DESC).toSphereSort();
        assertThat(actual).isEqualTo("foo.bar.baz desc");
    }
}
