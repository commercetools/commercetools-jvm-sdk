package example;

import io.sphere.sdk.client.PlayJavaClient;
import io.sphere.sdk.queries.PagedQueryResult;
import io.sphere.sdk.taxcategories.TaxCategory;
import io.sphere.sdk.taxcategories.queries.TaxCategoryQuery;
import org.junit.Test;
import play.libs.F;

public class TaxCategoryQueryExample {

    PlayJavaClient client;

    public void exampleQuery() throws Exception {
        F.Promise<PagedQueryResult<TaxCategory>> promise = client.execute(new TaxCategoryQuery().byName("de19"));
    }
}
