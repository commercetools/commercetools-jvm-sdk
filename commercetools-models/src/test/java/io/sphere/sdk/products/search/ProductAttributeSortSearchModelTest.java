package io.sphere.sdk.products.search;

import io.sphere.sdk.products.ProductProjection;
import io.sphere.sdk.search.model.MultiValueSortSearchModel;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class ProductAttributeSortSearchModelTest {

    @Test
    public void buildsAscendingSortExpression() throws Exception {
        assertThat(sortModel().asc().expression()).isEqualTo("variants.attributes.size asc");
    }
    
    @Test
    public void buildsDescendingSortExpression() throws Exception {
        assertThat(sortModel().desc().expression()).isEqualTo("variants.attributes.size desc");
    }

    @Test
    public void buildsAscendingSortExpressionWithAppendedParameter() throws Exception {
        assertThat(sortModel().ascWithMaxValue().expression()).isEqualTo("variants.attributes.size asc.max");
    }

    @Test
    public void buildsDescendingSortExpressionWithAppendedParameter() throws Exception {
        assertThat(sortModel().descWithMinValue().expression()).isEqualTo("variants.attributes.size desc.min");
    }

    private MultiValueSortSearchModel<ProductProjection> sortModel() {
        return ProductProjectionSearchModel.of().sort().allVariants().attribute().ofString("size");
    }
}