package io.sphere.sdk.products.queries;

import io.sphere.sdk.products.expansion.ProductExpansionModel;
import org.junit.Test;

import static org.assertj.core.api.Assertions.*;

public class ProductExpansionModelTest {
    @Test
    public void categories() throws Exception {
        assertThat(ProductExpansionModel.of().masterData().current().categories().expansionPaths().get(0).toSphereExpand())
                .isEqualTo("masterData.current.categories[*]");
        assertThat(ProductExpansionModel.of().masterData().staged().categories().expansionPaths().get(0).toSphereExpand())
                .isEqualTo("masterData.staged.categories[*]");
    }
}