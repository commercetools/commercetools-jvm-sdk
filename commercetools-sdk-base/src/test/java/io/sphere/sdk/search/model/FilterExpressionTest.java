package io.sphere.sdk.search.model;

import io.sphere.sdk.search.FilterExpression;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.List;
import java.util.function.Consumer;

import static java.math.BigDecimal.valueOf;
import static java.util.Arrays.asList;
import static java.util.stream.Collectors.toList;
import static org.assertj.core.api.Assertions.assertThat;

public class FilterExpressionTest {
    private static final FilterRange<BigDecimal> RANGE_3_TO_7 = FilterRange.of(valueOf(3), valueOf(7));
    private static final FilterRange<BigDecimal> RANGE_5_TO_9 = FilterRange.of(valueOf(5), valueOf(9));
    private static final String PATH_TO_ATTRIBUTE = "path.to.attribute";

    @Test
    public void filtersByTerm() throws Exception {
        testExpression(stringModel().is("foo"), (expr) ->
                assertThat(expr).containsExactly("path.to.attribute:\"foo\""));
    }

    @Test
    public void filtersByAnyTerm() throws Exception {
        testExpression(stringModel().containsAny(asList("foo", "bar")), (expr) ->
                assertThat(expr).containsExactly("path.to.attribute:\"foo\",\"bar\""));
    }

    @Test
    public void filtersByAllTerms() throws Exception {
        testExpression(stringModel().containsAll(asList("foo", "bar")), (expr) ->
                assertThat(expr).containsExactly("path.to.attribute:\"foo\"", "path.to.attribute:\"bar\""));
    }

    @Test
    public void filtersByRange() throws Exception {
        testExpression(numberModel().isBetween(RANGE_3_TO_7), (expr) ->
                assertThat(expr).containsExactly("path.to.attribute:range(3 to 7)"));
    }

    @Test
    public void filtersByAnyRange() throws Exception {
        testExpression(numberModel().isBetweenAny(asList(RANGE_3_TO_7, RANGE_5_TO_9)), (expr) ->
                assertThat(expr).containsExactly("path.to.attribute:range(3 to 7),(5 to 9)"));
    }

    @Test
    public void filtersByAllRanges() throws Exception {
        testExpression(numberModel().isBetweenAll(asList(RANGE_3_TO_7, RANGE_5_TO_9)), (expr) ->
                assertThat(expr).containsExactly("path.to.attribute:range(3 to 7)", "path.to.attribute:range(5 to 9)"));
    }

    @Test
    public void filtersByRangeBounds() throws Exception {
        testExpression(numberModel().isBetween(valueOf(3), valueOf(7)), (expr) ->
                assertThat(expr).containsExactly("path.to.attribute:range(3 to 7)"));
    }

    @Test
    public void filtersByGreaterThanOrEqualToRange() throws Exception {
        testExpression(numberModel().isGreaterThanOrEqualTo(valueOf(3)), (expr) ->
                assertThat(expr).containsExactly("path.to.attribute:range(3 to *)"));
    }

    @Test
    public void filtersByLessThanOrEqualToRange() throws Exception {
        testExpression(numberModel().isLessThanOrEqualTo(valueOf(3)), (expr) ->
                assertThat(expr).containsExactly("path.to.attribute:range(* to 3)"));
    }

    private TermFilterSearchModel<Object, String> stringModel() {
        return new StringSearchModel<>(null, PATH_TO_ATTRIBUTE).filtered();
    }

    private RangeTermFilterSearchModel<Object, BigDecimal> numberModel() {
        return new NumberSearchModel<>(null, PATH_TO_ATTRIBUTE).filtered();
    }

    private <T> void testExpression(List<FilterExpression<T>> filterExpr, Consumer<List<String>> test) {
        final List<String> expressions = filterExpr.stream()
                .map(expr -> expr.expression())
                .collect(toList());
        test.accept(expressions);
    }
}