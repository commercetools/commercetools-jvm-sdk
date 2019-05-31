package io.sphere.sdk.productdiscounts.commands;

import io.sphere.sdk.productdiscounts.ProductDiscount;
import io.sphere.sdk.productdiscounts.ProductDiscountFixtures;
import io.sphere.sdk.productdiscounts.queries.ProductDiscountQuery;
import io.sphere.sdk.queries.PagedQueryResult;
import io.sphere.sdk.test.IntegrationTest;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class ProductDiscountDeleteCommandIntegrationTest extends IntegrationTest {

    @Test
    public void deleteById() {
        ProductDiscountFixtures.withProductDiscount(client(), productDiscount -> {
            client().executeBlocking(ProductDiscountDeleteCommand.of(productDiscount));
            PagedQueryResult<ProductDiscount> result = client().executeBlocking(ProductDiscountQuery.of().withPredicates(m -> m.id().is(productDiscount.getId())));
            assertThat(result.getResults()).isEmpty();
        });
    }

    @Test
    public void deleteByKey() {
        ProductDiscountFixtures.withProductDiscount(client(), productDiscount -> {
            client().executeBlocking(ProductDiscountDeleteCommand.ofKey(productDiscount.getKey(), productDiscount.getVersion()));
            PagedQueryResult<ProductDiscount> result = client().executeBlocking(ProductDiscountQuery.of().withPredicates(m -> m.id().is(productDiscount.getId())));
            assertThat(result.getResults()).isEmpty();
        });
    }
}