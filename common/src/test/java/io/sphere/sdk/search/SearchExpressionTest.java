package io.sphere.sdk.search;

import org.junit.Test;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static io.sphere.sdk.search.TypeSerializer.ofNumber;
import static io.sphere.sdk.search.TypeSerializer.ofText;
import static java.util.Arrays.asList;
import static org.fest.assertions.Assertions.*;

public class SearchExpressionTest {

    @Test
    public void buildsRangeFilterExpression() throws Exception {
        List<FilterRange<BigDecimal>> ranges = asList(FilterRange.atMost(number(5)), FilterRange.atLeast(number(3)), FilterRange.of(number(5), number(10)));
        String expression = new RangeFilterExpression<>(model(), ranges, ofNumber()).toSphereSearchExpression();
        assertThat(expression).isEqualTo("variants.attributes.size:range(* to 5),(3 to *),(5 to 10)");
    }

    @Test
    public void buildsRangeFacetExpression() throws Exception {
        List<FacetRange<BigDecimal>> ranges = asList(FacetRange.lessThan(number(5)), FacetRange.atLeast(number(3)), FacetRange.of(number(5), number(10)));
        String expression = new RangeFacetExpression<>(model(), ranges, ofNumber()).toSphereSearchExpression();
        assertThat(expression).isEqualTo("variants.attributes.size:range(* to 5),(3 to *),(5 to 10)");
    }

    @Test
    public void buildsTermFilterExpression() throws Exception {
        List<String> terms = asList("foo", "bar");
        String expression = new TermFilterExpression<>(model(), terms, ofText()).toSphereSearchExpression();
        assertThat(expression).isEqualTo("variants.attributes.size:\"foo\",\"bar\"");
    }

    @Test
    public void buildsTermFacetExpression() throws Exception {
        List<String> terms = asList("foo", "bar");
        String expression = new TermFacetExpression<>(model(), terms, ofText()).toSphereSearchExpression();
        assertThat(expression).isEqualTo("variants.attributes.size:\"foo\",\"bar\"");
    }

    private SearchModel<Object> model() {
        return new SearchModelImpl<>(Optional.empty(), "variants").appended("attributes").appended("size");
    }

    private BigDecimal number(final double value) {
        return new BigDecimal(value);
    }
}