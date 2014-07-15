package example;

import io.sphere.sdk.client.PlayJavaClient;
import io.sphere.sdk.producttypes.*;
import io.sphere.sdk.queries.PagedQueryResult;
import io.sphere.sdk.queries.Predicate;
import play.libs.F;

public class QueryProductTypeExamples {
    private PlayJavaClient client;
    private ProductType productType;

    public void queryAll() {
        final F.Promise<PagedQueryResult<ProductType>> result = client.execute(ProductType.query());
    }

    public void queryByAttributeName() {
        Predicate<ProductTypeQueryModel<ProductType>> hasSizeAttribute = ProductTypeQueryModel.get().attributes().name().is("size");
        F.Promise<PagedQueryResult<ProductType>> result = client.execute(ProductType.query().withPredicate(hasSizeAttribute));
    }

    public void delete() {
        final ProductTypeDeleteByIdCommand command = new ProductTypeDeleteByIdCommand(productType);
        final F.Promise<ProductType> deletedProductType = client.execute(command);
    }
}
