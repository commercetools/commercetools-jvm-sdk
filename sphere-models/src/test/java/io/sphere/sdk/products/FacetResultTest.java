package io.sphere.sdk.products;

import com.fasterxml.jackson.core.type.TypeReference;
import io.sphere.sdk.search.*;
import io.sphere.sdk.json.JsonUtils;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;

import static java.math.BigDecimal.valueOf;
import static org.assertj.core.api.Assertions.assertThat;

public class FacetResultTest {
    private static final String TERM_FACET_KEY = "variants.attributes.filterColor.key";
    private static final String RANGE_FACET_KEY = "variants.attributes.priceb2c.centAmount";
    private PagedSearchResult<ProductProjection> pagedSearchResult;

    @Before
    public void setUp() throws Exception {
        pagedSearchResult = JsonUtils.readObjectFromResource("facetResult.json", new TypeReference<PagedSearchResult<ProductProjection>>() { });
    }

    @Test
    public void parsesTermFacetResults() throws Exception {
        final TermFacetResult<String> termFacet = termFacet();
        assertThat(termFacet.getMissing()).isEqualTo(44);
        assertThat(termFacet.getTotal()).isEqualTo(6937);
        assertThat(termFacet.getOther()).isEqualTo(0);
        assertThat(termFacet.getTerms()).hasSize(21);
        assertThat(termFacet.getTerms().get(2)).isEqualTo(TermStats.of("4B432E_1", 585));
    }

    @Test
    public void parsesRangeFacetResults() throws Exception {
        final RangeFacetResult<BigDecimal> rangeFacet = rangeFacet();
        assertThat(rangeFacet.getRanges()).hasSize(2);
        final RangeStats<BigDecimal> rangeStats = rangeFacet.getRanges().get(1);
        assertThat(rangeStats.getLowerEndpoint().get()).isEqualByComparingTo(valueOf(5001));
        assertThat(rangeStats.getUpperEndpoint()).isEmpty();
        assertThat(rangeStats.getCount()).isEqualTo(1799L);
        assertThat(rangeStats.getMin()).isEqualByComparingTo(valueOf(5100));
        assertThat(rangeStats.getMax()).isEqualByComparingTo(valueOf(590000));
        assertThat(rangeStats.getSum()).isEqualByComparingTo(valueOf(92868378));
        assertThat(rangeStats.getMean()).isEqualTo(51622.222345747636);
    }

    @SuppressWarnings("unchecked")
    private TermFacetResult<String> termFacet() {
        return (TermFacetResult) pagedSearchResult.getFacetsResults().get(TERM_FACET_KEY);
    }

    @SuppressWarnings("unchecked")
    private RangeFacetResult<BigDecimal> rangeFacet() {
        return (RangeFacetResult) pagedSearchResult.getFacetsResults().get(RANGE_FACET_KEY);
    }
}
