package io.sphere.sdk.products.commands.updateactions;

import io.sphere.sdk.products.ProductUpdateScope;
import io.sphere.sdk.search.SearchKeywords;

/**
 * {@include.example io.sphere.sdk.products.commands.ProductUpdateCommandTest#setSearchKeywords()}
 */
public class SetSearchKeywords extends StageableProductUpdateAction {
    private final SearchKeywords searchKeywords;

    private SetSearchKeywords(final SearchKeywords searchKeywords, final ProductUpdateScope productUpdateScope) {
        super("setSearchKeywords", productUpdateScope);
        this.searchKeywords = searchKeywords;
    }

    public SearchKeywords getSearchKeywords() {
        return searchKeywords;
    }

    public static SetSearchKeywords of(final SearchKeywords searchKeywords, final ProductUpdateScope productUpdateScope) {
        return new SetSearchKeywords(searchKeywords, productUpdateScope);
    }
}
