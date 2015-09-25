package io.sphere.sdk.search;

import org.junit.Test;

import static io.sphere.sdk.search.SearchSortDirection.*;
import static org.assertj.core.api.Assertions.assertThat;

public class SortExpressionTest {

    @Test
    public void buildsAscendingSortExpression() throws Exception {
        SearchModelImpl<Object> model = new SearchModelImpl<>(null, "foo").appended("bar").appended("size");
        String expression = new SortExpressionImpl<>(model, ASC).expression();
        assertThat(expression).isEqualTo("foo.bar.size asc");
    }

    @Test
    public void buildsDescendingSortExpression() throws Exception {
        SearchModelImpl<Object> model = new SearchModelImpl<>(null, "foo").appended("bar").appended("size");
        String expression = new SortExpressionImpl<>(model, DESC).expression();
        assertThat(expression).isEqualTo("foo.bar.size desc");
    }

    @Test
    public void buildsAscendingWithMaxSortExpression() throws Exception {
        SearchModelImpl<Object> model = new SearchModelImpl<>(null, "foo").appended("bar").appended("size");
        String expression = new SortExpressionImpl<>(model, ASC_MAX).expression();
        assertThat(expression).isEqualTo("foo.bar.size asc.max");
    }

    @Test
    public void buildsDescendingWithMinSortExpression() throws Exception {
        SearchModelImpl<Object> model = new SearchModelImpl<>(null, "foo").appended("bar").appended("size");
        String expression = new SortExpressionImpl<>(model, DESC_MIN).expression();
        assertThat(expression).isEqualTo("foo.bar.size desc.min");
    }
}