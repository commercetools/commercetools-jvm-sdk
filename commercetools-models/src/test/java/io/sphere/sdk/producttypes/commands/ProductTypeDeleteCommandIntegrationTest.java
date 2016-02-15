package io.sphere.sdk.producttypes.commands;

import io.sphere.sdk.producttypes.ProductType;
import io.sphere.sdk.producttypes.ProductTypeDraft;
import io.sphere.sdk.producttypes.queries.ProductTypeQuery;
import io.sphere.sdk.queries.Query;
import io.sphere.sdk.suppliers.TShirtProductTypeDraftSupplier;
import io.sphere.sdk.test.IntegrationTest;
import org.junit.Test;

import static org.assertj.core.api.Assertions.*;

public class ProductTypeDeleteCommandIntegrationTest extends IntegrationTest {
    @Test
    public void execution() throws Exception {
        final ProductType productType = client().executeBlocking(ProductTypeCreateCommand.of(getDraft()));

        client().executeBlocking(ProductTypeDeleteCommand.of(productType));

        final Query<ProductType> query = ProductTypeQuery.of()
                .withPredicates(m -> m.id().is(productType.getId()));
        assertThat(client().executeBlocking(query).head()).isEmpty();
    }

    @Test
    public void executionByKey() throws Exception {
        final ProductType productType = client().executeBlocking(ProductTypeCreateCommand.of(getDraft()));

        client().executeBlocking(ProductTypeDeleteCommand.ofKey(productType.getKey(), productType.getVersion()));

        final Query<ProductType> query = ProductTypeQuery.of()
                .withPredicates(m -> m.id().is(productType.getId()));
        assertThat(client().executeBlocking(query).head()).isEmpty();
    }

    private ProductTypeDraft getDraft() {
        return new TShirtProductTypeDraftSupplier(getProductTypeName()).get();
    }

    private String getProductTypeName() {
        return ProductTypeDeleteCommandIntegrationTest.class.getName();
    }
}