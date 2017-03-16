package io.sphere.sdk.products.commands.updateactions;

import io.sphere.sdk.products.Product;
import io.sphere.sdk.search.SearchKeywords;

import javax.annotation.Nullable;

/**
 * Sets the search keywords for a product.
 *
 * {@doc.gen intro}
 *
 * {@include.example io.sphere.sdk.products.commands.ProductUpdateCommandIntegrationTest#setSearchKeywords()}
 */
public final class SetSearchKeywords extends StagedProductUpdateActionImpl<Product> {
    private final SearchKeywords searchKeywords;

    private SetSearchKeywords(final SearchKeywords searchKeywords, @Nullable final Boolean staged) {
        super("setSearchKeywords", staged);
        this.searchKeywords = searchKeywords;
    }

    public SearchKeywords getSearchKeywords() {
        return searchKeywords;
    }

    public static SetSearchKeywords of(final SearchKeywords searchKeywords) {
        return of(searchKeywords, null);
    }

    public static SetSearchKeywords of(final SearchKeywords searchKeywords, @Nullable final Boolean staged) {
        return new SetSearchKeywords(searchKeywords, staged);
    }
}
