package io.sphere.sdk.search;

import org.junit.Test;

import java.math.BigDecimal;

import static java.math.BigDecimal.valueOf;
import static java.util.Arrays.asList;
import static org.assertj.core.api.Assertions.assertThat;

public class FacetExpressionTest {

    private static final FacetRange<BigDecimal> RANGE_3_TO_7 = FacetRange.of(valueOf(3), valueOf(7));
    private static final FacetRange<BigDecimal> RANGE_5_TO_9 = FacetRange.of(valueOf(5), valueOf(9));

    @Test
    public void facetsByAllTerms() throws Exception {
        final FacetExpression<Object> facet = stringModel().faceted().byAllTerms();
        assertThat(facet.toSearchExpression()).isEqualTo("path.to.attribute");
    }

    @Test
    public void facetsByTerm() throws Exception {
        final FacetExpression<Object> filter = stringModel().faceted().byTerm("foo");
        assertThat(filter.toSearchExpression()).isEqualTo("path.to.attribute:\"foo\"");
    }

    @Test
    public void facetsByTerms() throws Exception {
        final FacetExpression<Object> facet = stringModel().faceted().byTerm(asList("foo", "bar"));
        assertThat(facet.toSearchExpression()).isEqualTo("path.to.attribute:\"foo\",\"bar\"");
    }

    @Test
    public void facetsByRange() throws Exception {
        final FacetExpression<Object> facet = numberModel().faceted().byRange(RANGE_3_TO_7);
        assertThat(facet.toSearchExpression()).isEqualTo("path.to.attribute:range(3 to 7)");
    }

    @Test
    public void filtersByAnyRange() throws Exception {
        final FacetExpression<Object> facet = numberModel().faceted().byRange(asList(RANGE_3_TO_7, RANGE_5_TO_9));
        assertThat(facet.toSearchExpression()).isEqualTo("path.to.attribute:range(3 to 7),(5 to 9)");
    }

    @Test
    public void facetsByRangeBounds() throws Exception {
        final FacetExpression<Object> facet = numberModel().faceted().byRange(valueOf(3), valueOf(7));
        assertThat(facet.toSearchExpression()).isEqualTo("path.to.attribute:range(3 to 7)");
    }

    @Test
    public void facetsByGreaterThanOrEqualToRange() throws Exception {
        final FacetExpression<Object> facet = numberModel().faceted().byGreaterThanOrEqualTo(valueOf(3));
        assertThat(facet.toSearchExpression()).isEqualTo("path.to.attribute:range(3 to *)");
    }

    @Test
    public void facetsByLessThanRange() throws Exception {
        final FacetExpression<Object> facet = numberModel().faceted().byLessThan(valueOf(3));
        assertThat(facet.toSearchExpression()).isEqualTo("path.to.attribute:range(* to 3)");
    }

    private StringSearchModel<Object, DirectionlessSearchSortModel<Object>> stringModel() {
        return new StringSearchModel<>(null, "path.to.attribute", new DirectionlessSearchSortBuilder<>());
    }

    private NumberSearchModel<Object, DirectionlessSearchSortModel<Object>> numberModel() {
        return new NumberSearchModel<>(null, "path.to.attribute", new DirectionlessSearchSortBuilder<>());
    }
}