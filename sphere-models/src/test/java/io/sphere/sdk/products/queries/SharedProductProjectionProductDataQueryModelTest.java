package io.sphere.sdk.products.queries;

import io.sphere.sdk.products.Product;
import io.sphere.sdk.products.ProductProjection;
import io.sphere.sdk.queries.QueryPredicate;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class SharedProductProjectionProductDataQueryModelTest {
    private static final String embeddedProductProjectionPredicate = "masterVariant(sku=\"bar\") or variants(sku=\"bar\")";
    private static final String embeddedProductPredicate = "masterData(current(" + embeddedProductProjectionPredicate + "))";


    @Test
    public void variants() throws Exception {
        assertThat(ProductQueryModel.of().masterData().current().variants().sku().is("bar").toSphereQuery())
                .isEqualTo("masterData(current(variants(sku=\"bar\")))");
    }

    @Test
    public void allVariantsInProduct() throws Exception {
        assertThat(ProductQueryModel.of().masterData().current().allVariants().where(m -> m.sku().is("bar")))
                .isEqualTo(ProductQueryModel.of().masterData().current().where(m -> m.masterVariant().sku().is("bar").or(m.variants().sku().is("bar"))))
                .isEqualTo(QueryPredicate.<Product>of(embeddedProductPredicate));
    }

    @Test
    public void allVariantsInProductProjection() throws Exception {
        assertThat(ProductProjectionQueryModel.of().allVariants().where(m -> m.sku().is("bar")))
                .isEqualTo(ProductProjectionQueryModel.of().where(m -> m.masterVariant().sku().is("bar").or(m.variants().sku().is("bar"))))
                .isEqualTo(QueryPredicate.<ProductProjection>of(embeddedProductProjectionPredicate));
    }
}