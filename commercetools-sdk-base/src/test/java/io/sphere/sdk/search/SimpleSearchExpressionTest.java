package io.sphere.sdk.search;

import org.junit.Test;

import static io.sphere.sdk.search.SearchSortDirection.ASC_MAX;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class SimpleSearchExpressionTest {
    private static final String ATTRIBUTE_PATH = "variants.attributes.size";
    private static final String TERM_VALUE = ":\"foo\",\"bar\"";
    private static final String RANGE_VALUE = ":range(* to 5),(3 to *),(5 to 10)";
    private static final String ALIAS = "my-facet";
    private static final String AS_ALIAS = " as " + ALIAS;

    @Test
    public void buildsTermFilterExpression() throws Exception {
        final FilterExpression<Object> filter = FilterExpression.of(ATTRIBUTE_PATH + TERM_VALUE);
        assertThat(filter.expression()).isEqualTo(ATTRIBUTE_PATH + TERM_VALUE);
        assertThat(filter.attributePath()).isEqualTo(ATTRIBUTE_PATH);
        assertThat(filter.value()).isEqualTo(TERM_VALUE);
    }

    @Test
    public void buildsRangeFilterExpression() throws Exception {
        final FilterExpression<Object> filter = FilterExpression.of(ATTRIBUTE_PATH + RANGE_VALUE);
        assertThat(filter.expression()).isEqualTo(ATTRIBUTE_PATH + RANGE_VALUE);
        assertThat(filter.attributePath()).isEqualTo(ATTRIBUTE_PATH);
        assertThat(filter.value()).isEqualTo(RANGE_VALUE);
    }

    @Test
    public void buildsTermFacetExpression() throws Exception {
        final TermFacetExpression<Object> facet = TermFacetExpression.of(ATTRIBUTE_PATH);
        assertThat(facet.expression()).isEqualTo(ATTRIBUTE_PATH);
        assertThat(facet.attributePath()).isEqualTo(ATTRIBUTE_PATH);
        assertThat(facet.value()).isNull();
        assertThat(facet.alias()).isNull();
        assertThat(facet.resultPath()).isEqualTo(ATTRIBUTE_PATH);
    }

    @Test
    public void buildsRangeFacetExpression() throws Exception {
        final RangeFacetExpression<Object> facet = RangeFacetExpression.of(ATTRIBUTE_PATH + RANGE_VALUE);
        assertThat(facet.expression()).isEqualTo(ATTRIBUTE_PATH + RANGE_VALUE);
        assertThat(facet.attributePath()).isEqualTo(ATTRIBUTE_PATH);
        assertThat(facet.value()).isEqualTo(RANGE_VALUE);
        assertThat(facet.alias()).isNull();
        assertThat(facet.resultPath()).isEqualTo(ATTRIBUTE_PATH);
    }

    @Test
    public void buildsFilteredFacetExpression() throws Exception {
        final FilteredFacetExpression<Object> facet = FilteredFacetExpression.of(ATTRIBUTE_PATH + TERM_VALUE);
        assertThat(facet.expression()).isEqualTo(ATTRIBUTE_PATH + TERM_VALUE);
        assertThat(facet.attributePath()).isEqualTo(ATTRIBUTE_PATH);
        assertThat(facet.value()).isEqualTo(TERM_VALUE);
        assertThat(facet.alias()).isNull();
        assertThat(facet.resultPath()).isEqualTo(ATTRIBUTE_PATH);
    }

    @Test
    public void buildsTermFacetExpressionWithAlias() throws Exception {
        final TermFacetExpression<Object> facet = TermFacetExpression.of(ATTRIBUTE_PATH + AS_ALIAS);
        assertThat(facet.expression()).isEqualTo(ATTRIBUTE_PATH + AS_ALIAS);
        assertThat(facet.attributePath()).isEqualTo(ATTRIBUTE_PATH);
        assertThat(facet.value()).isNull();
        assertThat(facet.alias()).isEqualTo(ALIAS);
        assertThat(facet.resultPath()).isEqualTo(ALIAS);
    }

    @Test
    public void buildsRangeFacetExpressionWithAlias() throws Exception {
        final RangeFacetExpression<Object> facet = RangeFacetExpression.of(ATTRIBUTE_PATH + RANGE_VALUE + AS_ALIAS);
        assertThat(facet.expression()).isEqualTo(ATTRIBUTE_PATH + RANGE_VALUE + AS_ALIAS);
        assertThat(facet.attributePath()).isEqualTo(ATTRIBUTE_PATH);
        assertThat(facet.value()).isEqualTo(RANGE_VALUE);
        assertThat(facet.alias()).isEqualTo(ALIAS);
        assertThat(facet.resultPath()).isEqualTo(ALIAS);
    }

    @Test
    public void buildsFilteredFacetExpressionWithAlias() throws Exception {
        final FilteredFacetExpression<Object> facet = FilteredFacetExpression.of(ATTRIBUTE_PATH + TERM_VALUE + AS_ALIAS);
        assertThat(facet.expression()).isEqualTo(ATTRIBUTE_PATH + TERM_VALUE + AS_ALIAS);
        assertThat(facet.attributePath()).isEqualTo(ATTRIBUTE_PATH);
        assertThat(facet.value()).isEqualTo(TERM_VALUE);
        assertThat(facet.alias()).isEqualTo(ALIAS);
        assertThat(facet.resultPath()).isEqualTo(ALIAS);
    }

    @Test
    public void buildsSortExpression() throws Exception {
        final SortExpression<Object> sort = SortExpression.of(ATTRIBUTE_PATH + " " + ASC_MAX);
        assertThat(sort.expression()).isEqualTo(ATTRIBUTE_PATH + " " + ASC_MAX);
        assertThat(sort.attributePath()).isEqualTo(ATTRIBUTE_PATH);
        assertThat(sort.value()).isEqualTo(ASC_MAX.toString());
    }

    @Test
    public void throwsExceptionOnAccessingWrongExpression() throws Exception {
        final FilteredFacetExpression<Object> facet = FilteredFacetExpression.of("some wrong facet");
        assertThat(facet.expression()).isEqualTo("some wrong facet");
        assertThatThrownBy(() -> facet.attributePath()).isInstanceOf(IllegalArgumentException.class);
        assertThatThrownBy(() -> facet.value()).isInstanceOf(IllegalArgumentException.class);
        assertThatThrownBy(() -> facet.alias()).isInstanceOf(IllegalArgumentException.class);
        assertThatThrownBy(() -> facet.resultPath()).isInstanceOf(IllegalArgumentException.class);
    }

}