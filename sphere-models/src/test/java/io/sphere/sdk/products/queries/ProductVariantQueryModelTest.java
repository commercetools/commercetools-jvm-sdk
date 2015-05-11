package io.sphere.sdk.products.queries;

import io.sphere.sdk.queries.QueryPredicate;
import io.sphere.sdk.queries.QueryModel;
import org.junit.Test;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

public class ProductVariantQueryModelTest {

    @Test
    public void whereClosure() throws Exception {
        final ProductVariantQueryModel<String> model = new ProductVariantQueryModel<>(Optional.<QueryModel<String>>empty(), "foo");
        final QueryPredicate<String> normalWay = model.where(ProductVariantQueryModel.get().sku().is("x"));
        final QueryPredicate<String> closureWay = model.where(m -> m.sku().is("x"));
        assertThat(normalWay.toSphereQuery()).isEqualTo(closureWay.toSphereQuery()).isEqualTo("foo(sku=\"x\")");
    }
}