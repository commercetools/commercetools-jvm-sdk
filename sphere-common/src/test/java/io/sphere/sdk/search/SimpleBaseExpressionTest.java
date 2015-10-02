package io.sphere.sdk.search;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class SimpleBaseExpressionTest {

    @Test
    public void pathTerm() throws Exception {
        final FacetExpression<Object> expr = FacetExpression.of("variants.attributes.color");
        assertThat(expr.attributePath()).isEqualTo("variants.attributes.color");
        assertThat(expr.resultPath()).isEqualTo("variants.attributes.color");
    }

    @Test
    public void pathRange() throws Exception {
        final FacetExpression<Object> expr = FacetExpression.of("variants.attributes.color:range(* to 43),range(44 to *)");
        assertThat(expr.attributePath()).isEqualTo("variants.attributes.color");
        assertThat(expr.resultPath()).isEqualTo("variants.attributes.color");
    }

    @Test
    public void pathFiltered() throws Exception {
        final FacetExpression<Object> expr = FacetExpression.of("variants.attributes.color:\"red\",\"blue\"");
        assertThat(expr.attributePath()).isEqualTo("variants.attributes.color");
        assertThat(expr.resultPath()).isEqualTo("variants.attributes.color");
    }

    @Test
    public void pathTermWithAlias() throws Exception {
        final FacetExpression<Object> expr = FacetExpression.of("variants.attributes.color as myColor");
        assertThat(expr.attributePath()).isEqualTo("variants.attributes.color");
        assertThat(expr.resultPath()).isEqualTo("myColor");
    }

    @Test
    public void pathRangeWithAlias() throws Exception {
        final FacetExpression<Object> expr = FacetExpression.of("variants.attributes.color:range(* to 43),range(44 to *) as myColor");
        assertThat(expr.attributePath()).isEqualTo("variants.attributes.color");
        assertThat(expr.resultPath()).isEqualTo("myColor");
    }

    @Test
    public void pathFilteredWithAlias() throws Exception {
        final FacetExpression<Object> expr = FacetExpression.of("variants.attributes.color:\"red\",\"blue\" as myColor");
        assertThat(expr.attributePath()).isEqualTo("variants.attributes.color");
        assertThat(expr.resultPath()).isEqualTo("myColor");
    }
}
