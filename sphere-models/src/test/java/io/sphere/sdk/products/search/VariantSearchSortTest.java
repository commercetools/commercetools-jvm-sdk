package io.sphere.sdk.products.search;

import io.sphere.sdk.products.ProductProjection;
import io.sphere.sdk.search.DirectionlessMultiValueSearchSortModel;
import io.sphere.sdk.search.StringSearchModel;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class VariantSearchSortTest {

    @Test
    public void buildsAscendingSortExpression() throws Exception {
        assertThat(attributeModel().sorted().byAsc().expression()).isEqualTo("variants.attributes.size asc");
    }

    @Test
    public void buildsDescendingSortExpression() throws Exception {
        assertThat(attributeModel().sorted().byDesc().expression()).isEqualTo("variants.attributes.size desc");
    }

    @Test
    public void buildsAscendingSortExpressionWithAppendedParameter() throws Exception {
        assertThat(attributeModel().sorted().byAscWithMax().expression()).isEqualTo("variants.attributes.size asc.max");
    }

    @Test
    public void buildsDescendingSortExpressionWithAppendedParameter() throws Exception {
        assertThat(attributeModel().sorted().byDescWithMin().expression()).isEqualTo("variants.attributes.size desc.min");
    }

    private StringSearchModel<ProductProjection, DirectionlessMultiValueSearchSortModel<ProductProjection>> attributeModel() {
        return searchModel().allVariants().attribute().ofString("size");
    }

    private ProductProjectionSearchModel searchModel() {
        return ProductProjectionSearchModel.of();
    }
}