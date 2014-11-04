package io.sphere.sdk.producttypes;

import io.sphere.sdk.client.TestClient;
import io.sphere.sdk.products.Product;
import io.sphere.sdk.products.queries.ProductQuery;
import io.sphere.sdk.producttypes.commands.ProductTypeCreateCommand;
import io.sphere.sdk.producttypes.commands.ProductTypeDeleteByIdCommand;
import io.sphere.sdk.producttypes.queries.ProductTypeQuery;
import io.sphere.sdk.queries.PagedQueryResult;
import io.sphere.sdk.utils.SphereInternalLogger;

import java.util.function.Consumer;
import java.util.function.Supplier;

import static io.sphere.sdk.products.ProductFixtures.delete;

public final class ProductTypeFixtures {
    private ProductTypeFixtures() {
    }

    public static void withProductType(final TestClient client, final Supplier<ProductTypeDraft> creator, final Consumer<ProductType> user) {
        final SphereInternalLogger logger = SphereInternalLogger.getLogger("product-types.fixtures");
        final ProductTypeDraft productTypeDraft = creator.get();
        final String name = productTypeDraft.getName();
        final PagedQueryResult<ProductType> queryResult = client.execute(new ProductTypeQuery().byName(name));
        queryResult.getResults().forEach(productType -> {
            final PagedQueryResult<Product> pagedQueryResult = client.execute(new ProductQuery().byProductType(productType));
            delete(client, pagedQueryResult.getResults());
            client.execute(new ProductTypeDeleteByIdCommand(productType));

        });
        final ProductType productType = client.execute(new ProductTypeCreateCommand(productTypeDraft));
        logger.debug(() -> "created product type " + productType.getName() + " " + productType.getId());
        user.accept(productType);
        logger.debug(() -> "attempt to delete product type " + productType.getName() + " " + productType.getId());
        try {
            client.execute(new ProductTypeDeleteByIdCommand(productType));
        } catch (final Exception e) {
            final PagedQueryResult<Product> pagedQueryResult = client.execute(new ProductQuery().byProductType(productType));
            delete(client, pagedQueryResult.getResults());
            client.execute(new ProductTypeDeleteByIdCommand(productType));
        }
    }
}
