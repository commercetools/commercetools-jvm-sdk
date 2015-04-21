package io.sphere.sdk.products.search;

import io.sphere.sdk.categories.Category;
import io.sphere.sdk.products.ProductProjection;
import io.sphere.sdk.search.*;

import java.util.Optional;

/**
 * EXPERIMENTAL model to easily build product projection search requests.
 * Being it experimental, it can be modified in future releases therefore introducing breaking changes.
 */
public class ExperimentalProductProjectionSearchModel extends ProductDataSearchModelBase {

    private ExperimentalProductProjectionSearchModel(final Optional<? extends SearchModel<ProductProjection>> parent, final Optional<String> pathSegment) {
        super(parent, pathSegment);
    }

    static ExperimentalProductProjectionSearchModel get() {
        return new ExperimentalProductProjectionSearchModel(Optional.empty(), Optional.empty());
    }

    @Override
    public ProductVariantSearchModel allVariants() {
        return super.allVariants();
    }

    @Override
    public ReferenceSearchModel<ProductProjection, Category> categories() {
        return super.categories();
    }

    @Override
    public LocalizedStringsSearchModel<ProductProjection, SimpleSearchSortDirection> name() {
        return super.name();
    }

    @Override
    public DateTimeSearchModel<ProductProjection, SimpleSearchSortDirection> createdAt() {
        return super.createdAt();
    }

    @Override
    public DateTimeSearchModel<ProductProjection, SimpleSearchSortDirection> lastModifiedAt() {
        return super.lastModifiedAt();
    }
}
