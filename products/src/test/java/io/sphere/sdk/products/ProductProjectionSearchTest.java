package io.sphere.sdk.products;

import io.sphere.sdk.products.queries.ProductProjectionQuery;
import io.sphere.sdk.products.search.ProductProjectionSearch;
import org.junit.Test;

import static java.util.Arrays.asList;

public class ProductProjectionSearchTest {

    @Test
    public void canCreateTermsForCategories() throws Exception {
        //ProductProjectionSearch.model().categories().all();
        //ProductProjectionSearch.model().categories().isIn("asdf");
        //ProductProjectionSearch.model().categories().isIn(asList("asdf", "asdf"));
    }

    @Test
    public void canCreateTermFacetsForPrice() throws Exception {
        //ProductProjectionSearch.model().variants().price().centAmount().all();
        //ProductProjectionSearch.model().variants().price().centAmount().is("456");
        //ProductProjectionSearch.model().variants().price().centAmount().isIn(asList("345", "345"));
    }

    @Test
    public void canCreateRangeFacetsForPrice() throws Exception {
        //ProductProjectionSearch.model().variants().price().centAmount().all();
        //ProductProjectionSearch.model().variants().price().centAmount().lessThan();
        //ProductProjectionSearch.model().variants().price().centAmount().greaterThan();
        //ProductProjectionSearch.model().variants().price().centAmount().atLeast();
        //ProductProjectionSearch.model().variants().price().centAmount().atMost();
    }

    @Test
    public void canCreateRangeFacets() throws Exception {
        //ProductProjectionSearch.model().variants().price().centAmount().asRange(asList("asdfds"));
    }
}
