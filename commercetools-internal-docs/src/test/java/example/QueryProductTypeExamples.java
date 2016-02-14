package example;

import io.sphere.sdk.client.SphereClient;
import io.sphere.sdk.commands.DeleteCommand;
import io.sphere.sdk.producttypes.*;
import io.sphere.sdk.producttypes.commands.ProductTypeDeleteCommand;
import io.sphere.sdk.producttypes.queries.ProductTypeQuery;
import io.sphere.sdk.producttypes.queries.ProductTypeQueryModel;
import io.sphere.sdk.queries.PagedQueryResult;
import io.sphere.sdk.queries.QueryPredicate;

import java.util.concurrent.CompletionStage;

public class QueryProductTypeExamples {
    private SphereClient client;
    private ProductType productType;

    public void queryAll() {
        final CompletionStage<PagedQueryResult<ProductType>> result = client.execute(ProductTypeQuery.of());
    }

    public void queryByAttributeName() {
        QueryPredicate<ProductType> hasSizeAttribute = ProductTypeQueryModel.of().attributes().name().is("size");
        CompletionStage<PagedQueryResult<ProductType>> result = client.execute(ProductTypeQuery.of().withPredicates(hasSizeAttribute));
    }

    public void delete() {
        final DeleteCommand<ProductType> command = ProductTypeDeleteCommand.of(productType);
        final CompletionStage<ProductType> deletedProductType = client.execute(command);
    }
}
