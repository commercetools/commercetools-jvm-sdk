package example;

import io.sphere.sdk.products.ProductProjection;
import io.sphere.sdk.products.expansion.ProductProjectionExpansionModel;
import io.sphere.sdk.products.queries.ProductProjectionQuery;
import io.sphere.sdk.products.queries.ProductProjectionQueryModel;
import org.junit.Test;

import java.util.Locale;

public class QueryMetaDslDemo {
    @Test
    public void demo1() throws Exception {
        final ProductProjectionQuery query = ProductProjectionQuery.ofCurrent()
                .withPredicates(m -> m.productType().id().is("product-type-id-1"))
                .withSort(m -> m.name().lang(Locale.ENGLISH).sort().asc())
                .withExpansionPaths(m -> m.productType());
    }

    @Test
    public void demo2() throws Exception {
        //you need to know and import this classes
        final ProductProjectionQueryModel queryModel = ProductProjectionQueryModel.of();
        final ProductProjectionExpansionModel<ProductProjection> expansionModel = ProductProjectionExpansionModel.of();

        final ProductProjectionQuery query = ProductProjectionQuery.ofCurrent()
                .withPredicates(queryModel.productType().id().is("product-type-id-1"))
                .withSort(queryModel.name().lang(Locale.ENGLISH).sort().asc())
                .withExpansionPaths(expansionModel.productType());
    }
}
