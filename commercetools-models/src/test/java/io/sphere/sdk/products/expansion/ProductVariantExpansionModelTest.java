package io.sphere.sdk.products.expansion;

import io.sphere.sdk.products.queries.ProductProjectionQuery;
import org.junit.Test;

import static org.assertj.core.api.Assertions.*;

public class ProductVariantExpansionModelTest {
    @Test
    public void productAttributeReferenceExpansion() throws Exception {
        final ProductProjectionQuery query = ProductProjectionQuery.ofCurrent()
                .withExpansionPaths(m -> m.masterVariant().attributes().value());
        assertThat(query.expansionPaths().get(0).toSphereExpand())
                .isEqualTo("masterVariant.attributes[*].value");
    }
}