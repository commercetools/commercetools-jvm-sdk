package test;

import io.sphere.sdk.models.Versioned;
import io.sphere.sdk.products.Product;
import io.sphere.sdk.products.ProductCreateCommand;
import io.sphere.sdk.products.ProductDeleteCommand;
import io.sphere.sdk.products.ProductQueryModel;
import io.sphere.sdk.producttypes.ProductType;
import io.sphere.sdk.producttypes.ProductTypeCreateCommand;
import io.sphere.sdk.producttypes.ProductTypeDeleteCommand;
import io.sphere.sdk.queries.PagedQueryResult;
import io.sphere.sdk.queries.QueryIntegrationTest;
import io.sphere.sdk.requests.ClientRequest;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;

import java.util.List;

public class ProductCrudIntegrationTest extends QueryIntegrationTest<Product> {
    private static ProductType productType;

    @BeforeClass
    public static void prepare() {
        productType = client().execute(new ProductTypeCreateCommand(new TShirtNewProductType()));
    }

    @AfterClass
    public static void cleanUp() {
        client().execute(new ProductTypeDeleteCommand(productType));
        productType = null;
    }

    @Override
    protected ClientRequest<Product> deleteCommand(final Versioned item) {
        return new ProductDeleteCommand(item);
    }

    @Override
    protected ClientRequest<Product> newCreateCommandForName(final String name) {
        return new ProductCreateCommand(new SimpleCottonTShirtNewProduct(productType, name));
    }

    @Override
    protected String extractName(final Product instance) {
        return null;
    }

    @Override
    protected ClientRequest<PagedQueryResult<Product>> queryRequestForQueryAll() {
        return null;
    }

    @Override
    protected ClientRequest<PagedQueryResult<Product>> queryObjectForName(final String name) {
        return Product.query().withPredicate(ProductQueryModel.get().name().is(name));
    }

    @Override
    protected ClientRequest<PagedQueryResult<Product>> queryObjectForNames(final List<String> names) {
        return null;
    }
}
