package io.sphere.sdk.products.commands.updateactions;

import io.sphere.sdk.commands.UpdateAction;
import io.sphere.sdk.products.Product;
import io.sphere.sdk.search.SearchKeywords;

/**
 * {@include.example io.sphere.sdk.products.commands.ProductUpdateCommandTest#setSearchKeywords()}
 */
public class SetSearchKeywords extends UpdateAction<Product> {
    private final SearchKeywords searchKeywords;

    private SetSearchKeywords(final SearchKeywords searchKeywords) {
        super("setSearchKeywords");
        this.searchKeywords = searchKeywords;
    }

    public SearchKeywords getSearchKeywords() {
        return searchKeywords;
    }

    public static SetSearchKeywords of(final SearchKeywords searchKeywords) {
        return new SetSearchKeywords(searchKeywords);
    }
}
