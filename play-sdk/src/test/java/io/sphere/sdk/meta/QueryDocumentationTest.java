package io.sphere.sdk.meta;


import io.sphere.sdk.products.Product;
import io.sphere.sdk.products.ProductProjectionType;
import io.sphere.sdk.products.queries.ProductQuery;
import io.sphere.sdk.queries.Predicate;
import io.sphere.sdk.queries.QueryDsl;
import org.junit.Test;

import java.util.Locale;

import static org.fest.assertions.Assertions.assertThat;

public class QueryDocumentationTest {

    public void queryForAllDemo() {
        final ProductQuery query = new ProductQuery();
    }

    public void queryBySlug() {
        final QueryDsl<Product> queryBySlug = new ProductQuery().bySlug(ProductProjectionType.CURRENT, Locale.ENGLISH, "blue-t-shirt");
    }

    public void queryByNames() {
        final Predicate<Product> predicate = ProductQuery.model().masterData().current().name().lang(Locale.ENGLISH).isOneOf("blue t-shirt", "blue jeans");
        final QueryDsl<Product> query = new ProductQuery().withPredicate(predicate);
    }

    public void queryByNamesDesugared() {
        final Predicate<Product> predicate = ProductQuery.model().masterData().current().name().lang(Locale.ENGLISH).isOneOf("blue t-shirt", "blue jeans");
        final QueryDsl<Product> query = new ProductQuery().withPredicate(predicate);
    }

    @Test
    public void testX() throws Exception {
        final Predicate<Product> safePredicate = ProductQuery.model().masterData().current().name().lang(Locale.ENGLISH).isOneOf("blue t-shirt", "blue jeans");
        final Predicate<Product> unsafePredicate = Predicate.of("masterData(current(name(en in (\"blue t-shirt\", \"blue jeans\"))))");
        assertThat(unsafePredicate).isEqualTo(safePredicate);
    }
}