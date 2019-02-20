package io.sphere.sdk.products.queries;

import io.sphere.sdk.queries.QueryPredicate;
import org.junit.Ignore;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

//TODO FIX
@Ignore
public class ProductVariantQueryModelTest {

    @Test
    public void whereClosure() throws Exception {
        final ProductVariantQueryModel<String> model = ProductVariantQueryModel.of(null, "foo");
        final QueryPredicate<String> normalWay = model.where(EmbeddedProductVariantQueryModel.of().sku().is("x"));
        final QueryPredicate<String> closureWay = model.where(m -> m.sku().is("x"));
        assertThat(normalWay.toSphereQuery()).isEqualTo(closureWay.toSphereQuery()).isEqualTo("foo(sku=\"x\")");
    }
}