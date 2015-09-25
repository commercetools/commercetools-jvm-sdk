package io.sphere.sdk.search;

import org.junit.Test;

import java.math.BigDecimal;
import java.util.List;

import static io.sphere.sdk.search.TypeSerializer.ofNumber;
import static io.sphere.sdk.search.TypeSerializer.ofString;
import static java.math.BigDecimal.valueOf;
import static java.util.Arrays.asList;
import static org.assertj.core.api.Assertions.*;

public class SearchExpressionTest {

    @Test
    public void buildsTermFilterExpression() throws Exception {
        List<String> terms = asList("foo", "bar");
        final TermFilterExpression<Object, String> filter = new TermFilterExpression<>(model(), ofString(), terms);
        assertThat(filter.expression()).isEqualTo("variants.attributes.size:\"foo\",\"bar\"");
        assertThat(filter.attributePath()).isEqualTo("variants.attributes.size");
    }

    @Test
    public void buildsRangeFilterExpression() throws Exception {
        List<FilterRange<BigDecimal>> ranges = asList(FilterRange.atMost(valueOf(5)), FilterRange.atLeast(valueOf(3)), FilterRange.of(valueOf(5), valueOf(10)));
        final RangeFilterExpression<Object, BigDecimal> filter = new RangeFilterExpression<>(model(), ofNumber(), ranges);
        assertThat(filter.expression()).isEqualTo("variants.attributes.size:range(* to 5),(3 to *),(5 to 10)");
        assertThat(filter.attributePath()).isEqualTo("variants.attributes.size");
    }

    @Test
    public void buildsTermFacetExpression() throws Exception {
        final TermFacetExpression<Object, String> facet = new TermFacetExpression<>(model(), ofString(), null);
        assertThat(facet.expression()).isEqualTo("variants.attributes.size");
        assertThat(facet.resultPath()).isEqualTo("variants.attributes.size");
        assertThat(facet.attributePath()).isEqualTo("variants.attributes.size");
    }

    @Test
    public void buildsTermFacetExpressionWithAlias() throws Exception {
        final TermFacetExpression<Object, String> facet = new TermFacetExpression<>(model(), ofString(), "my-facet");
        assertThat(facet.expression()).isEqualTo("variants.attributes.size as my-facet");
        assertThat(facet.resultPath()).isEqualTo("my-facet");
        assertThat(facet.attributePath()).isEqualTo("variants.attributes.size");
    }

    @Test
    public void buildsFilteredFacetExpression() throws Exception {
        List<String> terms = asList("foo", "bar");
        final FilteredFacetExpression<Object, String> facet = new FilteredFacetExpression<>(model(), ofString(), terms, null);
        assertThat(facet.expression()).isEqualTo("variants.attributes.size:\"foo\",\"bar\"");
        assertThat(facet.resultPath()).isEqualTo("variants.attributes.size");
        assertThat(facet.attributePath()).isEqualTo("variants.attributes.size");
    }

    @Test
    public void buildsFilteredFacetExpressionWithAlias() throws Exception {
        List<String> terms = asList("foo", "bar");
        final FilteredFacetExpression<Object, String> facet = new FilteredFacetExpression<>(model(), ofString(), terms, "my-facet");
        assertThat(facet.expression()).isEqualTo("variants.attributes.size:\"foo\",\"bar\" as my-facet");
        assertThat(facet.resultPath()).isEqualTo("my-facet");
        assertThat(facet.attributePath()).isEqualTo("variants.attributes.size");
    }

    @Test
    public void buildsRangeFacetExpression() throws Exception {
        List<FacetRange<BigDecimal>> ranges = asList(FacetRange.lessThan(valueOf(5)), FacetRange.atLeast(valueOf(3)), FacetRange.of(valueOf(5), valueOf(10)));
        final RangeFacetExpression<Object, BigDecimal> facet = new RangeFacetExpression<>(model(), ofNumber(), ranges, null);
        assertThat(facet.expression()).isEqualTo("variants.attributes.size:range(* to 5),(3 to *),(5 to 10)");
        assertThat(facet.resultPath()).isEqualTo("variants.attributes.size");
        assertThat(facet.attributePath()).isEqualTo("variants.attributes.size");
    }

    @Test
    public void buildsRangeFacetExpressionWithAlias() throws Exception {
        List<FacetRange<BigDecimal>> ranges = asList(FacetRange.lessThan(valueOf(5)), FacetRange.atLeast(valueOf(3)), FacetRange.of(valueOf(5), valueOf(10)));
        final RangeFacetExpression<Object, BigDecimal> facet = new RangeFacetExpression<>(model(), ofNumber(), ranges, "my-facet");
        assertThat(facet.expression()).isEqualTo("variants.attributes.size:range(* to 5),(3 to *),(5 to 10) as my-facet");
        assertThat(facet.resultPath()).isEqualTo("my-facet");
        assertThat(facet.attributePath()).isEqualTo("variants.attributes.size");
    }

    @Test
    public void buildsSortExpression() throws Exception {
        final SortExpression<Object> sort = new SortExpressionImpl<>(model(), SearchSortDirection.ASC);
        assertThat(sort.expression()).isEqualTo("variants.attributes.size asc");
        assertThat(sort.attributePath()).isEqualTo("variants.attributes.size");
    }

    private SearchModel<Object> model() {
        return new SearchModelImpl<>(null, "variants").appended("attributes").appended("size");
    }
}