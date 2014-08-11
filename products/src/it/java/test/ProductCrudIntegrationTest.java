package test;

import io.sphere.sdk.products.*;
import io.sphere.sdk.products.commands.ProductCreateCommand;
import io.sphere.sdk.products.commands.ProductDeleteByIdCommand;
import io.sphere.sdk.products.queries.ProductQuery;
import io.sphere.sdk.products.queries.ProductQueryModel;
import io.sphere.sdk.producttypes.ProductType;
import io.sphere.sdk.producttypes.commands.ProductTypeCreateCommand;
import io.sphere.sdk.producttypes.commands.ProductTypeDeleteByIdCommand;
import io.sphere.sdk.producttypes.queries.ProductTypeQuery;
import io.sphere.sdk.queries.*;
import io.sphere.sdk.requests.ClientRequest;
import org.junit.AfterClass;
import org.junit.BeforeClass;

import java.util.List;
import java.util.Locale;

import static io.sphere.sdk.utils.SphereInternalLogger.getLogger;

public class ProductCrudIntegrationTest extends QueryIntegrationTest<Product> {
    private static ProductType productType;
    private final static String productTypeName = "t-shirt-" + ProductCrudIntegrationTest.class.getName();

    @BeforeClass
    public static void prepare() throws Exception {
        PagedQueryResult<ProductType> queryResult = client().execute(new ProductTypeQuery().byName(productTypeName));
        queryResult.getResults().forEach(pt -> deleteProductsAndProductType(pt));
        productType = client().execute(new ProductTypeCreateCommand(new TShirtNewProductTypeSupplier(productTypeName).get()));
    }

    @AfterClass
    public static void cleanUp() {
        deleteProductsAndProductType(productType);
        productType = null;
    }

    @Override
    protected ClientRequest<Product> deleteCommand(final Product item) {
        return new ProductDeleteByIdCommand(item);
    }

    @Override
    protected ClientRequest<Product> newCreateCommandForName(final String name) {
        return new ProductCreateCommand(new SimpleCottonTShirtNewProductSupplier(productType, name).get());
    }

    @Override
    protected String extractName(final Product instance) {
        return instance.getMasterData().getCurrent().getName().get(Locale.ENGLISH).get();
    }

    @Override
    protected ClientRequest<PagedQueryResult<Product>> queryRequestForQueryAll() {
        return new ProductQuery();
    }

    @Override
    protected ClientRequest<PagedQueryResult<Product>> queryObjectForName(final String name) {
        return new ProductQuery().withPredicate(ProductQueryModel.get().masterData().current().name().lang(Locale.ENGLISH).is(name));
    }

    @Override
    protected ClientRequest<PagedQueryResult<Product>> queryObjectForNames(final List<String> names) {
        return new ProductQuery().withPredicate(ProductQueryModel.get().masterData().current().name().lang(Locale.ENGLISH).isOneOf(names));
    }

    private static void deleteProductsAndProductType(final ProductType productType) {
        if (productType != null) {
            ProductQueryModel productQueryModelProductQueryModel = ProductQueryModel.get();
            Predicate<Product> ofProductType = productQueryModelProductQueryModel.productType().is(productType);
            QueryDsl<Product> productsOfProductTypeQuery = new ProductQuery().withPredicate(ofProductType);
            List<Product> products = client().execute(productsOfProductTypeQuery).getResults();
            products.forEach(
                    product -> client().execute(new ProductDeleteByIdCommand(product))
            );
            deleteProductType(productType);
        }
    }

    private static void deleteProductType(ProductType productType) {

        try {
            client().execute(new ProductTypeDeleteByIdCommand(productType));
        } catch (Exception e) {
            getLogger("test.fixtures").debug(() -> "no product type to delete");
        }
    }
}
