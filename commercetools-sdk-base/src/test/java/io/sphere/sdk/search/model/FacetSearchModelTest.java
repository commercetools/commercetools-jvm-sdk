package io.sphere.sdk.search.model;

import org.junit.Test;

import static io.sphere.sdk.search.model.TypeSerializer.ofString;
import static org.assertj.core.api.Assertions.assertThat;

public class FacetSearchModelTest {

    @Test
    public void termFacetsAllowAlias() throws Exception {
        final TermFacetSearchModel<Object, String> facetModel = new TermFacetSearchModelImpl<>(null, ofString());
        assertThat(facetModel.getAlias()).isNull();
        assertThat(facetModel.withAlias("my-alias").getAlias()).isEqualTo("my-alias");
    }

    @Test
    public void rangeFacetsAllowAlias() throws Exception {
        final RangeTermFacetSearchModel<Object, String> facetModel = new RangeTermFacetSearchModel<>(null, ofString());
        assertThat(facetModel.getAlias()).isNull();
        assertThat(facetModel.withAlias("my-alias").getAlias()).isEqualTo("my-alias");
    }

    @Test
    public void termFacetsAllowCountingProducts() throws Exception {
        final TermFacetSearchModel<Object, String> facetModel = new TermFacetSearchModelImpl<>(null, ofString());
        assertThat(facetModel.isCountingProducts()).isFalse();
        assertThat(facetModel.withCountingProducts(true).isCountingProducts()).isTrue();
    }

    @Test
    public void rangeFacetsAllowCountingProducts() throws Exception {
        final RangeTermFacetSearchModel<Object, String> facetModel = new RangeTermFacetSearchModel<>(null, ofString());
        assertThat(facetModel.isCountingProducts()).isFalse();
        assertThat(facetModel.withCountingProducts(true).isCountingProducts()).isTrue();
    }
}