package io.sphere.sdk.search.model;

import io.sphere.sdk.search.FacetExpression;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.function.Consumer;

import static java.math.BigDecimal.valueOf;
import static java.util.Arrays.asList;
import static org.assertj.core.api.Assertions.assertThat;

public class FacetExpressionTest {
    private static final FacetRange<BigDecimal> RANGE_3_TO_7 = FacetRange.of(valueOf(3), valueOf(7));
    private static final FacetRange<BigDecimal> RANGE_5_TO_9 = FacetRange.of(valueOf(5), valueOf(9));

    @Test
    public void facetsByAllTerms() throws Exception {
        testExpression(stringModel().allTerms(), (expr) ->
                assertThat(expr).isEqualTo("path.to.attribute"));
    }

    @Test
    public void facetsByTerm() throws Exception {
        testExpression(stringModel().onlyTerm("foo"), (expr) ->
                assertThat(expr).isEqualTo("path.to.attribute:\"foo\""));
    }

    @Test
    public void facetsByTerms() throws Exception {
        testExpression(stringModel().onlyTerm(asList("foo", "bar")), (expr) ->
                assertThat(expr).isEqualTo("path.to.attribute:\"foo\",\"bar\""));
    }

    @Test
    public void facetsByRange() throws Exception {
        testExpression(numberModel().onlyRange(RANGE_3_TO_7), (expr) ->
                assertThat(expr).isEqualTo("path.to.attribute:range(3 to 7)"));
    }

    @Test
    public void facetsByRanges() throws Exception {
        testExpression(numberModel().onlyRange(asList(RANGE_3_TO_7, RANGE_5_TO_9)), (expr) ->
                assertThat(expr).isEqualTo("path.to.attribute:range(3 to 7),(5 to 9)"));
    }

    @Test
    public void facetsByRangeBounds() throws Exception {
        testExpression(numberModel().onlyRange(valueOf(3), valueOf(7)), (expr) ->
                assertThat(expr).isEqualTo("path.to.attribute:range(3 to 7)"));
    }

    @Test
    public void facetsByGreaterThanOrEqualToRange() throws Exception {
        testExpression(numberModel().onlyGreaterThanOrEqualTo(valueOf(3)), (expr) ->
                assertThat(expr).isEqualTo("path.to.attribute:range(3 to *)"));
    }

    @Test
    public void facetsByLessThanRange() throws Exception {
        testExpression(numberModel().onlyLessThan(valueOf(3)), (expr) ->
                assertThat(expr).isEqualTo("path.to.attribute:range(* to 3)"));
    }

    @Test
    public void facetsWithAlias() throws Exception {
        testExpression(stringModel().withAlias("testAlias").allTerms(), (expr) ->
                assertThat(expr).isEqualTo("path.to.attribute as testAlias"));
    }

    @Test
    public void facetsCountingProducts() throws Exception {
        testExpression(stringModel().withCountingProducts(true).allTerms(), (expr) ->
                assertThat(expr).isEqualTo("path.to.attribute counting products"));
    }

    @Test
    public void facetsWithAliasCountingProducts() throws Exception {
        testExpression(stringModel().withAlias("testAlias").withCountingProducts(true).allTerms(), (expr) ->
                assertThat(expr).isEqualTo("path.to.attribute as testAlias counting products"));
    }

    private TermFacetSearchModel<Object, String> stringModel() {
        return new StringSearchModel<>(null, "path.to.attribute").faceted();
    }

    private RangeTermFacetSearchModel<Object, BigDecimal> numberModel() {
        return new NumberSearchModel<>(null, "path.to.attribute").faceted();
    }

    private <T> void testExpression(FacetExpression<T> facetExpr, Consumer<String> test) {
        test.accept(facetExpr.expression());
    }
}