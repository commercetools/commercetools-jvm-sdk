package io.sphere.sdk.products.search;

import com.fasterxml.jackson.core.type.TypeReference;
import io.sphere.sdk.products.ProductProjection;
import io.sphere.sdk.search.*;
import io.sphere.sdk.json.SphereJsonUtils;
import io.sphere.sdk.search.model.RangeStats;
import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class FacetResultTest {
    private static final String TERM_FACET_KEY = "variants.attributes.filterColor.key";
    private static final String RANGE_FACET_KEY = "variants.attributes.priceb2c.centAmount";
    private static final String FILTERED_FACET_KEY = "variants.attributes.size";
    private PagedSearchResult<ProductProjection> searchResult;

    @Before
    public void setUp() throws Exception {
        searchResult = SphereJsonUtils.readObjectFromResource("facetResult.json", new TypeReference<PagedSearchResult<ProductProjection>>() {});
    }

    @Test
    public void parsesTermFacetResults() throws Exception {
        final TermFacetResult termFacet = searchResult.getTermFacetResult(TERM_FACET_KEY);
        assertThat(termFacet.getMissing()).isEqualTo(44);
        assertThat(termFacet.getTotal()).isEqualTo(6937);
        assertThat(termFacet.getOther()).isEqualTo(0);
        assertThat(termFacet.getTerms()).hasSize(21);
        assertThat(termFacet.getTerms().get(2)).isEqualTo(TermStats.of("4B432E_1", 585L));
    }

    @Test
    public void parsesRangeFacetResults() throws Exception {
        final RangeFacetResult rangeFacet = searchResult.getRangeFacetResult(RANGE_FACET_KEY);
        assertThat(rangeFacet.getRanges()).hasSize(2);
        final RangeStats rangeStats = rangeFacet.getRanges().get(1);
        assertThat(rangeStats.getLowerEndpoint()).isEqualTo("5001");
        assertThat(rangeStats.getUpperEndpoint()).isNull();
        assertThat(rangeStats.getCount()).isEqualTo(1799L);
        assertThat(rangeStats.getMin()).isEqualTo("5100");
        assertThat(rangeStats.getMax()).isEqualTo("590000");
        assertThat(rangeStats.getSum()).isEqualTo("92868378");
        assertThat(rangeStats.getMean()).isEqualTo(51622.222345747636);
    }

    @Test
    public void parsesFilteredFacetResults() throws Exception {
        final FilteredFacetResult filteredFacet = searchResult.getFilteredFacetResult(FILTERED_FACET_KEY);
        assertThat(filteredFacet.getCount()).isEqualTo(2);
    }
}