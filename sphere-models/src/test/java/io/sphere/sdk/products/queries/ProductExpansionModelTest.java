package io.sphere.sdk.products.queries;

import org.junit.Test;

import static org.assertj.core.api.Assertions.*;

public class ProductExpansionModelTest {
    @Test
    public void categories() throws Exception {
        assertThat(ProductQuery.expansionPath().masterData().current().categories().toSphereExpand())
                .isEqualTo("masterData.current.categories[*]");
        assertThat(ProductQuery.expansionPath().masterData().staged().categories().toSphereExpand())
                .isEqualTo("masterData.staged.categories[*]");
    }
}