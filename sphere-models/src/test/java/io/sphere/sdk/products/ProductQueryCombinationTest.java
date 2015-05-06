package io.sphere.sdk.products;

import io.sphere.sdk.products.queries.*;
import io.sphere.sdk.queries.Predicate;
import org.junit.Test;

import static java.util.Locale.ENGLISH;
import static java.util.Locale.GERMAN;
import static org.assertj.core.api.Assertions.assertThat;

public class ProductQueryCombinationTest {
    public static final PartialProductDataQueryModel DATA_QUERY_MODEL = ProductDataQueryModel.getPartialProductDataQueryModel();
    public static final PartialProductCatalogDataQueryModel MASTER_DATA_QUERY_MODEL = ProductCatalogDataQueryModel.get();

    @Test
    public void pure() throws Exception {
        final Predicate<Product> purePredicate = ProductQuery.model().masterData().current().name().lang(ENGLISH).is("Yes");
        assertThat(purePredicate.toSphereQuery()).isEqualTo("masterData(current(name(en=\"Yes\")))");
    }

    @Test
    public void combinedEmbeddedQueries() throws Exception {
        final Predicate<PartialProductDataQueryModel> predicate =
                DATA_QUERY_MODEL.name().lang(ENGLISH).is("Yes").or(DATA_QUERY_MODEL.name().lang(GERMAN).is("Ja"));
        final Predicate<PartialProductCatalogDataQueryModel> x1 = MASTER_DATA_QUERY_MODEL.current().where(predicate).and(MASTER_DATA_QUERY_MODEL.staged().where(predicate));
        final Predicate<Product> resultPredicate = ProductQuery.model().masterData().where(x1);
        assertThat(resultPredicate.toSphereQuery()).isEqualTo("masterData(current(name(en=\"Yes\") or name(de=\"Ja\")) and staged(name(en=\"Yes\") or name(de=\"Ja\")))");
    }
}
