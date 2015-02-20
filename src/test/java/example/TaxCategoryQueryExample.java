package example;

import io.sphere.sdk.client.SphereClient;
import io.sphere.sdk.queries.PagedQueryResult;
import io.sphere.sdk.taxcategories.TaxCategory;
import io.sphere.sdk.taxcategories.queries.TaxCategoryQuery;

import java.util.concurrent.CompletableFuture;

public class TaxCategoryQueryExample {

    SphereClient client;

    public void exampleQuery() {
        CompletableFuture<PagedQueryResult<TaxCategory>> future = client.execute(TaxCategoryQuery.of().byName("de19"));
    }
}
