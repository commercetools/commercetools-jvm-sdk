package io.sphere.sdk.products.queries;

import io.sphere.sdk.queries.Predicate;
import io.sphere.sdk.queries.QueryModel;
import org.junit.Test;

import java.util.Optional;

import static org.fest.assertions.Assertions.assertThat;

public class ProductVariantQueryModelTest {

    @Test
    public void whereClosure() throws Exception {
        final ProductVariantQueryModel<String> model = new ProductVariantQueryModel<>(Optional.<QueryModel<String>>empty(), "foo");
        final Predicate<String> normalWay = model.where(ProductVariantQueryModel.get().sku().is("x"));
        final Predicate<String> closureWay = model.where(m -> m.sku().is("x"));
        assertThat(normalWay.toSphereQuery()).isEqualTo(closureWay.toSphereQuery()).isEqualTo("foo(sku=\"x\")");
    }
}