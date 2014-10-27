package io.sphere.sdk;

import io.sphere.sdk.client.TestClient;
import io.sphere.sdk.products.Product;
import io.sphere.sdk.products.commands.ProductDeleteByIdCommand;
import io.sphere.sdk.products.commands.ProductUpdateCommand;
import io.sphere.sdk.products.commands.updateactions.Unpublish;
import io.sphere.sdk.products.queries.ProductQuery;
import io.sphere.sdk.producttypes.ProductType;
import io.sphere.sdk.producttypes.commands.ProductTypeDeleteByIdCommand;
import io.sphere.sdk.producttypes.queries.ProductTypeQuery;
import io.sphere.sdk.taxcategories.TaxCategory;
import io.sphere.sdk.taxcategories.commands.TaxCategoryDeleteByIdCommand;
import io.sphere.sdk.taxcategories.queries.TaxCategoryQuery;

import java.util.List;

public final class Database {
    private Database() {
    }

    public static void wipe(final TestClient client) {
        products(client).stream()
                .filter(p -> p.getMasterData().isPublished())
                .forEach(publishedProduct ->
                        client.execute(new ProductUpdateCommand(publishedProduct, Unpublish.of())));
        products(client).forEach(p -> client.execute(new ProductDeleteByIdCommand(p)));
        productTypes(client).forEach(p -> client.execute(new ProductTypeDeleteByIdCommand(p)));
        taxCategories(client).stream()
                .filter(t -> t.getName().equals("Standard tax"))
                .forEach(t -> client.execute(new TaxCategoryDeleteByIdCommand(t)));
    }

    private static List<TaxCategory> taxCategories(final TestClient client) {
        return client.execute(new TaxCategoryQuery()).getResults();
    }

    private static List<Product> products(final TestClient client) {
        return client.execute(new ProductQuery()).getResults();
    }

    private static List<ProductType> productTypes(final TestClient client) {
        return client.execute(new ProductTypeQuery()).getResults();
    }
}
