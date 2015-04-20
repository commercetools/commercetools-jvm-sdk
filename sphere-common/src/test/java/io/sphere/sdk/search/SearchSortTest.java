package io.sphere.sdk.search;

import org.junit.Test;
import java.util.Optional;

import static io.sphere.sdk.search.SimpleSearchSortDirection.*;
import static org.fest.assertions.Assertions.*;

public class SearchSortTest {

    @Test
    public void buildsAscendingSortExpression() throws Exception {
        SearchModelImpl<Object> model = new SearchModelImpl<>(Optional.empty(), "foo").appended("bar").appended("size");
        String expression = new SphereSearchSort<>(model, ASC).toSphereSort();
        assertThat(expression).isEqualTo("foo.bar.size asc");
    }

    @Test
    public void buildsDescendingSortExpression() throws Exception {
        SearchModelImpl<Object> model = new SearchModelImpl<>(Optional.empty(), "foo").appended("bar").appended("size");
        String expression = new SphereSearchSort<>(model, DESC).toSphereSort();
        assertThat(expression).isEqualTo("foo.bar.size desc");
    }
}