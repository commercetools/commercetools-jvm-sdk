package io.sphere.sdk.search;

import org.junit.Test;

import static io.sphere.sdk.search.SimpleSearchSortDirection.ASC;
import static io.sphere.sdk.search.SimpleSearchSortDirection.DESC;
import static org.assertj.core.api.Assertions.assertThat;

public class SearchSortTest {

    @Test
    public void buildsAscendingSortExpression() throws Exception {
        SearchModelImpl<Object> model = new SearchModelImpl<>(null, "foo").appended("bar").appended("size");
        String expression = new SphereSearchSort<>(model, ASC).toSphereSort();
        assertThat(expression).isEqualTo("foo.bar.size asc");
    }

    @Test
    public void buildsDescendingSortExpression() throws Exception {
        SearchModelImpl<Object> model = new SearchModelImpl<>(null, "foo").appended("bar").appended("size");
        String expression = new SphereSearchSort<>(model, DESC).toSphereSort();
        assertThat(expression).isEqualTo("foo.bar.size desc");
    }
}