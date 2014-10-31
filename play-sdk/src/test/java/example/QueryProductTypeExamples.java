package example;

import io.sphere.sdk.client.JavaClient;
import io.sphere.sdk.producttypes.*;
import io.sphere.sdk.producttypes.commands.ProductTypeDeleteByIdCommand;
import io.sphere.sdk.producttypes.queries.ProductTypeQuery;
import io.sphere.sdk.queries.PagedQueryResult;
import io.sphere.sdk.queries.Predicate;

import java.util.concurrent.CompletableFuture;

public class QueryProductTypeExamples {
    private JavaClient client;
    private ProductType productType;

    public void queryAll() {
        final CompletableFuture<PagedQueryResult<ProductType>> result = client.execute(new ProductTypeQuery());
    }

    public void queryByAttributeName() {
        Predicate<ProductType> hasSizeAttribute = ProductTypeQuery.model().attributes().name().is("size");
        CompletableFuture<PagedQueryResult<ProductType>> result = client.execute(new ProductTypeQuery().withPredicate(hasSizeAttribute));
    }

    public void delete() {
        final ProductTypeDeleteByIdCommand command = new ProductTypeDeleteByIdCommand(productType);
        final CompletableFuture<ProductType> deletedProductType = client.execute(command);
    }
}
