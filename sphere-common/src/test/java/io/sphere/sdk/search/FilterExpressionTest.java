package io.sphere.sdk.search;

import org.junit.Test;

import java.math.BigDecimal;
import java.util.List;

import static java.math.BigDecimal.valueOf;
import static java.util.Arrays.asList;
import static org.assertj.core.api.Assertions.assertThat;

public class FilterExpressionTest {

    private static final FilterRange<BigDecimal> RANGE_3_TO_7 = FilterRange.of(valueOf(3), valueOf(7));
    private static final FilterRange<BigDecimal> RANGE_5_TO_9 = FilterRange.of(valueOf(5), valueOf(9));
    private static final String PATH_TO_ATTRIBUTE = "path.to.attribute";

    @Test
    public void filtersByTerm() throws Exception {
        final FilterExpression<Object> filter = stringModel().filtered().by("foo");
        assertThat(filter.toSearchExpression()).isEqualTo("path.to.attribute:\"foo\"");
    }

    @Test
    public void filtersByAnyTerm() throws Exception {
        final FilterExpression<Object> filter = stringModel().filtered().byAny(asList("foo", "bar"));
        assertThat(filter.toSearchExpression()).isEqualTo("path.to.attribute:\"foo\",\"bar\"");
    }

    @Test
    public void filtersByAllTerms() throws Exception {
        final List<FilterExpression<Object>> filters = stringModel().filtered().byAll(asList("foo", "bar"));
        assertThat(filters).extracting(filter -> filter.toSearchExpression()).containsExactly("path.to.attribute:\"foo\"", "path:\"bar\"");
    }

    @Test
    public void filtersByRange() throws Exception {
        final FilterExpression<Object> filter = numberModel().filtered().byRange(RANGE_3_TO_7);
        assertThat(filter.toSearchExpression()).isEqualTo("path.to.attribute:range(3 to 7)");
    }

    @Test
    public void filtersByAnyRange() throws Exception {
        final FilterExpression<Object> filter = numberModel().filtered().byAnyRange(asList(RANGE_3_TO_7, RANGE_5_TO_9));
        assertThat(filter.toSearchExpression()).isEqualTo("path.to.attribute:range(3 to 7),(5 to 9)");
    }

    @Test
    public void filtersByAllRanges() throws Exception {
        final List<FilterExpression<Object>> filters = numberModel().filtered().byAllRanges(asList(RANGE_3_TO_7, RANGE_5_TO_9));
        assertThat(filters).extracting(filter -> filter.toSearchExpression()).containsExactly("path.to.attribute:range(3 to 7)", "path:range(5 to 9)");
    }

    @Test
    public void filtersByRangeBounds() throws Exception {
        final FilterExpression<Object> filter = numberModel().filtered().byRange(valueOf(3), valueOf(7));
        assertThat(filter.toSearchExpression()).isEqualTo("path.to.attribute:range(3 to 7)");
    }

    @Test
    public void filtersByGreaterThanOrEqualToRange() throws Exception {
        final FilterExpression<Object> filter = numberModel().filtered().byGreaterThanOrEqualTo(valueOf(3));
        assertThat(filter.toSearchExpression()).isEqualTo("path.to.attribute:range(3 to *)");
    }

    @Test
    public void filtersByLessThanOrEqualToRange() throws Exception {
        final FilterExpression<Object> filter = numberModel().filtered().byLessThanOrEqualTo(valueOf(3));
        assertThat(filter.toSearchExpression()).isEqualTo("path.to.attribute:range(* to 3)");
    }

    private StringSearchModel<Object, DirectionlessSearchSortModel<Object>> stringModel() {
        return new StringSearchModel<>(null, PATH_TO_ATTRIBUTE, new DirectionlessSearchSortBuilder<>());
    }

    private NumberSearchModel<Object, DirectionlessSearchSortModel<Object>> numberModel() {
        return new NumberSearchModel<>(null, PATH_TO_ATTRIBUTE, new DirectionlessSearchSortBuilder<>());
    }
}