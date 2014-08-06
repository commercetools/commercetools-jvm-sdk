package io.sphere.sdk.taxcategories;

import java.util.Optional;
import io.sphere.sdk.queries.*;

public class TaxCategoryQueryModel extends EmbeddedQueryModel<TaxCategory> {

    private static final TaxCategoryQueryModel instance = new TaxCategoryQueryModel(Optional.<QueryModel<TaxCategory>>empty(), Optional.<String>empty());

    public static TaxCategoryQueryModel get() {
        return instance;
    }

    private TaxCategoryQueryModel(final Optional<? extends QueryModel<TaxCategory>> parent, final Optional<String> pathSegment) {
        super(parent, pathSegment);
    }

    public StringQuerySortingModel<TaxCategory> name() {
        return new StringQuerySortingModel<>(Optional.of(this), "name");
    }
}
