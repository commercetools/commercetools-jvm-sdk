package io.sphere.sdk.categories;

import io.sphere.sdk.categories.queries.CategoryQuery;
import io.sphere.sdk.queries.PagedQueryResult;
import io.sphere.sdk.test.IntegrationTest;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class CategoriesPagingIntegrationTest extends IntegrationTest {
    @Test
    public void overPaging() throws Exception {
        final long offset = 5000;
        final PagedQueryResult<Category> result = client().executeBlocking(CategoryQuery.of().withOffset(offset));
        assertThat(result.getOffset()).isEqualTo(5000);
        assertThat(result.getCount()).isEqualTo(0);
        assertThat(result.getResults().size()).isEqualTo(0);
    }
}
