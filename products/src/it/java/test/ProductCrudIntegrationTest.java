package test;

import io.sphere.sdk.models.Versioned;
import io.sphere.sdk.products.Product;
import io.sphere.sdk.queries.PagedQueryResult;
import io.sphere.sdk.queries.QueryIntegrationTest;
import io.sphere.sdk.requests.ClientRequest;

import java.util.List;

public class ProductCrudIntegrationTest extends QueryIntegrationTest<Product> {
    @Override
    protected ClientRequest<Product> deleteCommand(final Versioned item) {
        return null;
    }

    @Override
    protected ClientRequest<Product> newCreateCommandForName(final String name) {
        return null;
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
        return null;
    }

    @Override
    protected ClientRequest<PagedQueryResult<Product>> queryObjectForNames(final List<String> names) {
        return null;
    }
}
