package io.sphere.sdk.products.queries;

import io.sphere.sdk.products.expansion.ProductExpansionModel;
import org.junit.Test;

import static org.assertj.core.api.Assertions.*;

public class ProductExpansionModelTest {
    @Test
    public void categories() throws Exception {
        assertThat(ProductExpansionModel.of().masterData().current().categories().toSphereExpand())
                .isEqualTo("masterData.current.categories[*]");
        assertThat(ProductExpansionModel.of().masterData().staged().categories().toSphereExpand())
                .isEqualTo("masterData.staged.categories[*]");
    }
}