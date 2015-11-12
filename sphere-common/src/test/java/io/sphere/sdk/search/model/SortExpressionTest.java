package io.sphere.sdk.search.model;

import org.junit.Test;

import static io.sphere.sdk.search.SearchSortDirection.*;
import static org.assertj.core.api.Assertions.assertThat;

public class SortExpressionTest {

    @Test
    public void buildsAscendingSortExpression() throws Exception {
        String expression = new SortExpressionImpl<>(sortModel(), ASC).expression();
        assertThat(expression).isEqualTo("foo.bar.size asc");
    }

    @Test
    public void buildsDescendingSortExpression() throws Exception {
        String expression = new SortExpressionImpl<>(sortModel(), DESC).expression();
        assertThat(expression).isEqualTo("foo.bar.size desc");
    }

    @Test
    public void buildsAscendingWithMaxSortExpression() throws Exception {
        String expression = new SortExpressionImpl<>(sortModel(), ASC_MAX).expression();
        assertThat(expression).isEqualTo("foo.bar.size asc.max");
    }

    @Test
    public void buildsDescendingWithMinSortExpression() throws Exception {
        String expression = new SortExpressionImpl<>(sortModel(), DESC_MIN).expression();
        assertThat(expression).isEqualTo("foo.bar.size desc.min");
    }

    private SearchModelImpl<Object> sortModel() {
        return new SearchModelImpl<>("foo").appended("bar").appended("size");
    }
}