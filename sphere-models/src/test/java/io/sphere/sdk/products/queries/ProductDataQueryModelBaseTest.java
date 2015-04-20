package io.sphere.sdk.products.queries;

import io.sphere.sdk.products.Product;
import io.sphere.sdk.products.ProductProjection;
import io.sphere.sdk.queries.Predicate;
import org.junit.Test;

import static org.fest.assertions.Assertions.assertThat;

public class ProductDataQueryModelBaseTest {
    private static final String embeddedProductProjectionPredicate = "masterVariant(sku=\"bar\") or variants(sku=\"bar\")";
    private static final String embeddedProductPredicate = "masterData(current(" + embeddedProductProjectionPredicate + "))";


    @Test
    public void variants() throws Exception {
        assertThat(ProductQuery.model().masterData().current().variants().sku().is("bar").toSphereQuery())
                .isEqualTo("masterData(current(variants(sku=\"bar\")))");
    }

    @Test
    public void allVariantsInProduct() throws Exception {
        assertThat(ProductQuery.model().masterData().current().allVariants().where(m -> m.sku().is("bar")))
                .isEqualTo(ProductQuery.model().masterData().current().where(m -> m.masterVariant().sku().is("bar").or(m.variants().sku().is("bar"))))
                .isEqualTo(Predicate.<Product>of(embeddedProductPredicate));
    }

    @Test
    public void allVariantsInProductProjection() throws Exception {
        assertThat(ProductProjectionQuery.model().allVariants().where(m -> m.sku().is("bar")))
                .isEqualTo(ProductProjectionQuery.model().where(m -> m.masterVariant().sku().is("bar").or(m.variants().sku().is("bar"))))
                .isEqualTo(Predicate.<ProductProjection>of(embeddedProductProjectionPredicate));
    }
}