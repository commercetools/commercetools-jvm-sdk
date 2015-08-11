package io.sphere.sdk.search;

import org.junit.Test;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static io.sphere.sdk.search.TypeSerializer.ofNumber;
import static io.sphere.sdk.search.TypeSerializer.ofText;
import static java.math.BigDecimal.valueOf;
import static java.util.Arrays.asList;
import static org.assertj.core.api.Assertions.*;

public class SearchExpressionTest {

    @Test
    public void buildsTermFilterExpression() throws Exception {
        List<String> terms = asList("foo", "bar");
        final TermFilterExpression<Object, String> expression = new TermFilterExpression<>(model(), ofText(), terms);
        assertThat(expression.toSphereFilter()).isEqualTo("variants.attributes.size:\"foo\",\"bar\"");
    }

    @Test
    public void buildsRangeFilterExpression() throws Exception {
        List<FilterRange<BigDecimal>> ranges = asList(FilterRange.atMost(valueOf(5)), FilterRange.atLeast(valueOf(3)), FilterRange.of(valueOf(5), valueOf(10)));
        final RangeFilterExpression<Object, BigDecimal> expression = new RangeFilterExpression<>(model(), ofNumber(), ranges);
        assertThat(expression.toSphereFilter()).isEqualTo("variants.attributes.size:range(* to 5),(3 to *),(5 to 10)");
    }

    @Test
    public void buildsTermFacetExpression() throws Exception {
        final TermFacetExpression<Object, String> expression = new TermFacetExpression<>(model(), ofText(), null);
        assertThat(expression.toSphereFacet()).isEqualTo("variants.attributes.size");
        assertThat(expression.resultPath()).isEqualTo("variants.attributes.size");
        assertThat(expression.path()).isEqualTo("variants.attributes.size");
    }

    @Test
    public void buildsTermFacetExpressionWithAlias() throws Exception {
        final TermFacetExpression<Object, String> expression = new TermFacetExpression<>(model(), ofText(), "my-facet");
        assertThat(expression.toSphereFacet()).isEqualTo("variants.attributes.size as my-facet");
        assertThat(expression.resultPath()).isEqualTo("my-facet");
        assertThat(expression.path()).isEqualTo("variants.attributes.size");
    }

    @Test
    public void buildsFilteredFacetExpression() throws Exception {
        List<String> terms = asList("foo", "bar");
        final FilteredFacetExpression<Object, String> expression = new FilteredFacetExpression<>(model(), ofText(), terms, null);
        assertThat(expression.toSphereFacet()).isEqualTo("variants.attributes.size:\"foo\",\"bar\"");
        assertThat(expression.resultPath()).isEqualTo("variants.attributes.size");
        assertThat(expression.path()).isEqualTo("variants.attributes.size");
    }

    @Test
    public void buildsFilteredFacetExpressionWithAlias() throws Exception {
        List<String> terms = asList("foo", "bar");
        final FilteredFacetExpression<Object, String> expression = new FilteredFacetExpression<>(model(), ofText(), terms, "my-facet");
        assertThat(expression.toSphereFacet()).isEqualTo("variants.attributes.size:\"foo\",\"bar\" as my-facet");
        assertThat(expression.resultPath()).isEqualTo("my-facet");
        assertThat(expression.path()).isEqualTo("variants.attributes.size");
    }

    @Test
    public void buildsRangeFacetExpression() throws Exception {
        List<FacetRange<BigDecimal>> ranges = asList(FacetRange.lessThan(valueOf(5)), FacetRange.atLeast(valueOf(3)), FacetRange.of(valueOf(5), valueOf(10)));
        final RangeFacetExpression<Object, BigDecimal> expression = new RangeFacetExpression<>(model(), ofNumber(), ranges, null);
        assertThat(expression.toSphereFacet()).isEqualTo("variants.attributes.size:range(* to 5),(3 to *),(5 to 10)");
        assertThat(expression.resultPath()).isEqualTo("variants.attributes.size");
        assertThat(expression.path()).isEqualTo("variants.attributes.size");
    }

    @Test
    public void buildsRangeFacetExpressionWithAlias() throws Exception {
        List<FacetRange<BigDecimal>> ranges = asList(FacetRange.lessThan(valueOf(5)), FacetRange.atLeast(valueOf(3)), FacetRange.of(valueOf(5), valueOf(10)));
        final RangeFacetExpression<Object, BigDecimal> expression = new RangeFacetExpression<>(model(), ofNumber(), ranges, "my-facet");
        assertThat(expression.toSphereFacet()).isEqualTo("variants.attributes.size:range(* to 5),(3 to *),(5 to 10) as my-facet");
        assertThat(expression.resultPath()).isEqualTo("my-facet");
        assertThat(expression.path()).isEqualTo("variants.attributes.size");
    }


    private SearchModel<Object> model() {
        return new SearchModelImpl<>(null, "variants").appended("attributes").appended("size");
    }
}