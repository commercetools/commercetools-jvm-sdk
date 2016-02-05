package io.sphere.sdk.products;

import io.sphere.sdk.products.queries.*;
import io.sphere.sdk.queries.QueryPredicate;
import org.junit.Test;

import static java.util.Locale.ENGLISH;
import static java.util.Locale.GERMAN;
import static org.assertj.core.api.Assertions.assertThat;

public class ProductQueryCombinationTest {
    public static final EmbeddedProductDataQueryModel DATA_QUERY_MODEL = EmbeddedProductDataQueryModel.of();

    @Test
    public void pure() throws Exception {
        final QueryPredicate<Product> purePredicate = ProductQueryModel.of().masterData().current().name().lang(ENGLISH).is("Yes");
        assertThat(purePredicate.toSphereQuery()).isEqualTo("masterData(current(name(en=\"Yes\")))");
    }

    @Test
    public void combinedEmbeddedQueries() throws Exception {
        final QueryPredicate<EmbeddedProductDataQueryModel> predicate =
                DATA_QUERY_MODEL.name().lang(ENGLISH).is("Yes").or(DATA_QUERY_MODEL.name().lang(GERMAN).is("Ja"));
        final QueryPredicate<Product> resultPredicate = ProductQueryModel.of().masterData().where(m -> m.current().where(predicate).and(m.staged().where(predicate)));
        assertThat(resultPredicate.toSphereQuery()).isEqualTo("masterData(current(name(en=\"Yes\") or name(de=\"Ja\")) and staged(name(en=\"Yes\") or name(de=\"Ja\")))");
    }
}
