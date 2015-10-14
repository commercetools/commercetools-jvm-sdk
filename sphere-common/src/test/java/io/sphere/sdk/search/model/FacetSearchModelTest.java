package io.sphere.sdk.search.model;

import org.junit.Test;

import java.util.function.Function;

import static java.util.Arrays.asList;
import static org.assertj.core.api.Assertions.assertThat;

public class FacetSearchModelTest {

    public static final Function<String, String> SERIALIZER = TypeSerializer.ofString().getSerializer();

    @Test
    public void termFacetsAllowAlias() throws Exception {
        final TermFacetSearchModel<Object, String> facetModel = new TermFacetSearchModel<>(null, SERIALIZER);
        assertThat(facetModel.getAlias()).isNull();
        assertThat(facetModel.withAlias("my-alias").getAlias()).isEqualTo("my-alias");
    }

    @Test
    public void rangeFacetsAllowAlias() throws Exception {
        final RangeFacetSearchModel<Object, String> facetModel = new RangeFacetSearchModel<>(null, SERIALIZER);
        assertThat(facetModel.getAlias()).isNull();
        assertThat(facetModel.withAlias("my-alias").getAlias()).isEqualTo("my-alias");
    }
}