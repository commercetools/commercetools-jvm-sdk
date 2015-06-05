package io.sphere.sdk.producttypes.commands;

import io.sphere.sdk.producttypes.ProductType;
import io.sphere.sdk.producttypes.ProductTypeDraft;
import io.sphere.sdk.producttypes.queries.ProductTypeQuery;
import io.sphere.sdk.producttypes.queries.ProductTypeQueryModel;
import io.sphere.sdk.queries.Query;
import io.sphere.sdk.suppliers.TShirtProductTypeDraftSupplier;
import io.sphere.sdk.test.IntegrationTest;
import org.junit.Test;

import static org.assertj.core.api.Assertions.*;

public class ProductTypeDeleteCommandTest extends IntegrationTest {
    @Test
    public void execution() throws Exception {
        final ProductType productType = execute(ProductTypeCreateCommand.of(getDraft()));

        execute(ProductTypeDeleteCommand.of(productType));

        final Query<ProductType> query = ProductTypeQuery.of()
                .withPredicate(ProductTypeQueryModel.of().id().is(productType.getId()));
        assertThat(execute(query).head()).isEmpty();
    }

    private ProductTypeDraft getDraft() {
        return new TShirtProductTypeDraftSupplier(getProductTypeName()).get();
    }

    private String getProductTypeName() {
        return ProductTypeDeleteCommandTest.class.getName();
    }
}