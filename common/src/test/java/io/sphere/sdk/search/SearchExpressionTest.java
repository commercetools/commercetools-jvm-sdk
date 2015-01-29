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
    public void buildsRangeExpression() throws Exception {
        SearchModelImpl<Object> model = new SearchModelImpl<>(Optional.empty(), "variants").appended("attributes").appended("size");
        List<Range<BigDecimal>> ranges = asList(Range.lessThan(number(5)), Range.greaterThan(number(3)));
        String expression = new RangeFacetExpression<>(model, ranges, ofNumber()).toSphereSearchExpression();
        assertThat(expression).isEqualTo("variants.attributes.size:range(* to 5),(3 to *)");
    }

    @Test
    public void buildsTermExpression() throws Exception {
        SearchModelImpl<Object> model = new SearchModelImpl<>(Optional.empty(), "variants").appended("attributes").appended("size");
        List<String> terms = asList("foo", "bar");
        String expression = new TermFilterExpression<>(model, terms, ofText()).toSphereSearchExpression();
        assertThat(expression).isEqualTo("variants.attributes.size:\"foo\",\"bar\"");
    }

    private BigDecimal number(final double value) {
        return new BigDecimal(value);
    }
}