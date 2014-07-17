package test;

import io.sphere.sdk.client.NotFoundException;
import io.sphere.sdk.client.SphereBackendException;
import io.sphere.sdk.models.Reference;
import io.sphere.sdk.models.Versioned;
import io.sphere.sdk.products.Product;
import io.sphere.sdk.products.ProductCreateCommand;
import io.sphere.sdk.products.ProductDeleteByIdCommand;
import io.sphere.sdk.products.ProductQueryModel;
import io.sphere.sdk.producttypes.ProductType;
import io.sphere.sdk.producttypes.ProductTypeCreateCommand;
import io.sphere.sdk.producttypes.ProductTypeDeleteByIdCommand;
import io.sphere.sdk.queries.*;
import io.sphere.sdk.requests.ClientRequest;
import io.sphere.sdk.utils.Log;
import org.junit.AfterClass;
import org.junit.BeforeClass;

import java.util.List;
import java.util.Locale;

public class ProductCrudIntegrationTest extends QueryIntegrationTest<Product> {
    private static ProductType productType;
    private static String productTypeName = new TShirtNewProductTypeSupplier().get().getName();

    @BeforeClass
    public static void prepare() throws Exception {
        PagedQueryResult<ProductType> queryResult = client().execute(ProductType.query().byName(productTypeName));
        queryResult.getResults().stream().forEach(pt -> deleteProductsAndProductType(pt));
        productType = client().execute(new ProductTypeCreateCommand(new TShirtNewProductTypeSupplier().get()));
        Thread.sleep(500);
    }

    @AfterClass
    public static void cleanUp() {
        deleteProductsAndProductType(productType);
        productType = null;
    }

    @Override
    protected ClientRequest<Product> deleteCommand(final Versioned item) {
        return new ProductDeleteByIdCommand(item);
    }

    @Override
    protected ClientRequest<Product> newCreateCommandForName(final String name) {
        return new ProductCreateCommand(new SimpleCottonTShirtNewProductSupplier(productType.toReference(), name).get());
    }

    @Override
    protected String extractName(final Product instance) {
        return instance.getMasterData().getCurrent().getName().get(Locale.ENGLISH).get();
    }

    @Override
    protected ClientRequest<PagedQueryResult<Product>> queryRequestForQueryAll() {
        return Product.query();
    }

    @Override
    protected ClientRequest<PagedQueryResult<Product>> queryObjectForName(final String name) {
        return Product.query().withPredicate(ProductQueryModel.get().masterData().current().name().lang(Locale.ENGLISH).is(name));
    }

    @Override
    protected ClientRequest<PagedQueryResult<Product>> queryObjectForNames(final List<String> names) {
        return Product.query().withPredicate(ProductQueryModel.get().masterData().current().name().lang(Locale.ENGLISH).isOneOf(names));
    }

    private static void deleteProductsAndProductType(final ProductType productType) {
        if (productType != null) {
            ProductQueryModel<ProductQueryModel<Product>> productQueryModelProductQueryModel = ProductQueryModel.get();
            ReferenceQueryModel<ProductQueryModel<Product>, ProductType> model = productQueryModelProductQueryModel.productType();
            Reference<ProductType> reference = productType.toReference();
            Predicate<ProductQueryModel<Product>> ofProductType = model.is(reference);
            QueryDsl<Product, ProductQueryModel<Product>> productsOfProductTypeQuery = Product.query().withPredicate(ofProductType);
            List<Product> products = client().execute(productsOfProductTypeQuery).getResults();
            products.stream().forEach(
                    product -> client().execute(new ProductDeleteByIdCommand(product))
            );
            deleteProductType(productType);
        }
    }

    private static void deleteProductType(ProductType productType) {

        try {
            client().execute(new ProductTypeDeleteByIdCommand(productType));
        } catch (Exception e) {
            Log.debug("no product type to delete");
        }
    }
}
