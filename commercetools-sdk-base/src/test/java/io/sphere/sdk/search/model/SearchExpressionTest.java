package io.sphere.sdk.search.model;

import io.sphere.sdk.search.*;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.List;

import static io.sphere.sdk.search.SearchSortDirection.ASC;
import static io.sphere.sdk.search.SearchSortDirection.ASC_MAX;
import static io.sphere.sdk.search.model.TypeSerializer.ofNumber;
import static io.sphere.sdk.search.model.TypeSerializer.ofString;
import static java.math.BigDecimal.valueOf;
import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;
import static org.assertj.core.api.Assertions.*;

public class SearchExpressionTest {
    private static final List<String> TERMS = asList("foo", "bar");
    private static final List<FilterRange<BigDecimal>> FILTER_RANGES = asList(FilterRange.atMost(valueOf(5)), FilterRange.atLeast(valueOf(3)), FilterRange.of(valueOf(5), valueOf(10)));
    private static final List<FacetRange<BigDecimal>> FACET_RANGES = asList(FacetRange.lessThan(valueOf(5)), FacetRange.atLeast(valueOf(3)), FacetRange.of(valueOf(5), valueOf(10)));

    private static final String ATTRIBUTE_PATH = "variants.attributes.size";
    private static final String TERM_VALUE = ":\"foo\",\"bar\"";
    private static final String RANGE_VALUE = ":range(* to 5),(3 to *),(5 to 10)";
    private static final String ALIAS = "my-facet";
    private static final String AS_ALIAS = " as " + ALIAS;

    @Test
    public void buildsTermFilterExpression() throws Exception {
        final List<FilterExpression<Object>> filters = TermFilterSearchModel.of(ATTRIBUTE_PATH, ofString()).isIn(TERMS);
        assertThat(filters).hasSize(1);
        final FilterExpression<Object> filter = filters.get(0);
        assertThat(filter.expression()).isEqualTo(ATTRIBUTE_PATH + TERM_VALUE);
        assertThat(filter.attributePath()).isEqualTo(ATTRIBUTE_PATH);
        assertThat(filter.value()).isEqualTo(TERM_VALUE);
    }

    @Test
    public void buildsRangeFilterExpression() throws Exception {
        final List<FilterExpression<Object>> filters = RangeTermFilterSearchModel.of(ATTRIBUTE_PATH, ofNumber()).isBetweenAny(FILTER_RANGES);
        assertThat(filters).hasSize(1);
        final FilterExpression<Object> filter = filters.get(0);
        assertThat(filter.expression()).isEqualTo(ATTRIBUTE_PATH + RANGE_VALUE);
        assertThat(filter.attributePath()).isEqualTo(ATTRIBUTE_PATH);
        assertThat(filter.value()).isEqualTo(RANGE_VALUE);
    }

    @Test
    public void buildsTermFacetExpression() throws Exception {
        final TermFacetExpression<Object> facet = TermFacetSearchModel.of(ATTRIBUTE_PATH, ofString()).allTerms();
        assertThat(facet.expression()).isEqualTo(ATTRIBUTE_PATH);
        assertThat(facet.attributePath()).isEqualTo(ATTRIBUTE_PATH);
        assertThat(facet.value()).isNull();
        assertThat(facet.alias()).isNull();
        assertThat(facet.resultPath()).isEqualTo(ATTRIBUTE_PATH);
    }

    @Test
    public void buildsRangeFacetExpression() throws Exception {
        final RangeFacetExpression<Object> facet = RangeTermFacetSearchModel.of(ATTRIBUTE_PATH, ofNumber()).onlyRange(FACET_RANGES);
        assertThat(facet.expression()).isEqualTo(ATTRIBUTE_PATH + RANGE_VALUE);
        assertThat(facet.attributePath()).isEqualTo(ATTRIBUTE_PATH);
        assertThat(facet.value()).isEqualTo(RANGE_VALUE);
        assertThat(facet.alias()).isNull();
        assertThat(facet.resultPath()).isEqualTo(ATTRIBUTE_PATH);
    }

    @Test
    public void buildsFilteredFacetExpression() throws Exception {
        final FilteredFacetExpression<Object> facet = TermFacetSearchModel.of(ATTRIBUTE_PATH, ofString()).onlyTerm(TERMS);
        assertThat(facet.expression()).isEqualTo(ATTRIBUTE_PATH + TERM_VALUE);
        assertThat(facet.attributePath()).isEqualTo(ATTRIBUTE_PATH);
        assertThat(facet.value()).isEqualTo(TERM_VALUE);
        assertThat(facet.alias()).isNull();
        assertThat(facet.resultPath()).isEqualTo(ATTRIBUTE_PATH);
    }

    @Test
    public void buildsTermFacetExpressionWithAlias() throws Exception {
        final TermFacetExpression<Object> facet = TermFacetSearchModel.of(ATTRIBUTE_PATH, ofString()).withAlias(ALIAS).allTerms();
        assertThat(facet.expression()).isEqualTo(ATTRIBUTE_PATH + AS_ALIAS);
        assertThat(facet.attributePath()).isEqualTo(ATTRIBUTE_PATH);
        assertThat(facet.value()).isNull();
        assertThat(facet.alias()).isEqualTo(ALIAS);
        assertThat(facet.resultPath()).isEqualTo(ALIAS);
    }

    @Test
    public void buildsRangeFacetExpressionWithAlias() throws Exception {
        final RangeFacetExpression<Object> facet = RangeTermFacetSearchModel.of(ATTRIBUTE_PATH, ofNumber()).withAlias(ALIAS).onlyRange(FACET_RANGES);
        assertThat(facet.expression()).isEqualTo(ATTRIBUTE_PATH + RANGE_VALUE + AS_ALIAS);
        assertThat(facet.attributePath()).isEqualTo(ATTRIBUTE_PATH);
        assertThat(facet.value()).isEqualTo(RANGE_VALUE);
        assertThat(facet.alias()).isEqualTo(ALIAS);
        assertThat(facet.resultPath()).isEqualTo(ALIAS);
    }

    @Test
    public void buildsFilteredFacetExpressionWithAlias() throws Exception {
        final FilteredFacetExpression<Object> facet = TermFacetSearchModel.of(ATTRIBUTE_PATH, ofString()).withAlias(ALIAS).onlyTerm(TERMS);
        assertThat(facet.expression()).isEqualTo(ATTRIBUTE_PATH + TERM_VALUE + AS_ALIAS);
        assertThat(facet.attributePath()).isEqualTo(ATTRIBUTE_PATH);
        assertThat(facet.value()).isEqualTo(TERM_VALUE);
        assertThat(facet.alias()).isEqualTo(ALIAS);
        assertThat(facet.resultPath()).isEqualTo(ALIAS);
    }

    @Test
    public void buildsSingleValuedSortExpression() throws Exception {
        final SortExpression<Object> sort = SingleValueSortSearchModel.of(ATTRIBUTE_PATH).asc();
        assertThat(sort.expression()).isEqualTo(ATTRIBUTE_PATH + " " + ASC);
        assertThat(sort.attributePath()).isEqualTo(ATTRIBUTE_PATH);
        assertThat(sort.value()).isEqualTo(ASC.toString());
    }

    @Test
    public void buildsMultiValuedSortExpression() throws Exception {
        final SortExpression<Object> sort = MultiValueSortSearchModel.of(ATTRIBUTE_PATH).ascWithMaxValue();
        assertThat(sort.expression()).isEqualTo(ATTRIBUTE_PATH + " " + ASC_MAX);
        assertThat(sort.attributePath()).isEqualTo(ATTRIBUTE_PATH);
        assertThat(sort.value()).isEqualTo(ASC_MAX.toString());
    }

    @Test
    public void failsOnEmptyTermFilterExpression() throws Exception {
        assertThatThrownBy(() -> TermFilterSearchModel.of(ATTRIBUTE_PATH, ofString()).isIn(emptyList()))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("iterable must not be empty");
    }

    @Test
    public void failsOnEmptyRangeFilterExpression() throws Exception {
        assertThatThrownBy(() -> RangeTermFilterSearchModel.of(ATTRIBUTE_PATH, ofNumber()).isBetweenAny(emptyList()))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("iterable must not be empty");
    }

    @Test
    public void failsOnEmptyFilteredFacetExpression() throws Exception {
        assertThatThrownBy(() -> TermFacetSearchModel.of(ATTRIBUTE_PATH, ofString()).onlyTerm(emptyList()))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("iterable must not be empty");
    }

    @Test
    public void failsOnEmptyRangeFacetExpression() throws Exception {
        assertThatThrownBy(() -> RangeTermFacetSearchModel.of(ATTRIBUTE_PATH, ofNumber()).onlyRange(emptyList()))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("iterable must not be empty");
    }
}