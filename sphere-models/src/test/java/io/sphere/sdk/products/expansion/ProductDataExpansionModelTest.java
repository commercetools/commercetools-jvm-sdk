package io.sphere.sdk.products.expansion;

import io.sphere.sdk.expansion.ExpansionPath;
import io.sphere.sdk.products.Product;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class ProductDataExpansionModelTest {

    @Test
    public void variants() throws Exception {
        final ExpansionPath<Product> expansionPath =
                ProductExpansionModel.of().masterData().current().variants().prices().customerGroup().expansionPaths().get(0);
        assertThat(expansionPath.toSphereExpand()).isEqualTo("masterData.current.variants[*].prices[*].customerGroup");
    }
}