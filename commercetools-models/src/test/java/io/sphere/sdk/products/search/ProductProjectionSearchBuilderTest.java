package io.sphere.sdk.products.search;

import org.junit.Ignore;
import org.junit.Test;

import static org.assertj.core.api.Assertions.*;

@Ignore
public class ProductProjectionSearchBuilderTest {
    @Ignore
    @Test
    public void searchRequestIsAsExpected() {
        final ProductProjectionSearch actual = ProductProjectionSearchBuilder.ofCurrent()
                .facets(m -> m.allVariants().price().centAmount().allRanges())
                .queryFilters(m -> m.allVariants().price().centAmount().isGreaterThanOrEqualTo(4L))
                .sort(m -> m.createdAt().asc())
                .expansionPaths(m -> m.categories())
                .build();

        final ProductProjectionSearch expected = ProductProjectionSearch.ofCurrent()
                .withFacets(m -> m.allVariants().price().centAmount().allRanges())
                .withQueryFilters(m -> m.allVariants().price().centAmount().isGreaterThanOrEqualTo(4L))
                .withSort(m -> m.createdAt().asc())
                .withExpansionPaths(m -> m.categories());

        assertThat(actual).isEqualTo(expected);
    }
}