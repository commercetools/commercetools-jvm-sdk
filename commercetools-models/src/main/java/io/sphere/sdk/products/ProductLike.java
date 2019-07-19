package io.sphere.sdk.products;

import io.sphere.sdk.commands.UpdateAction;
import io.sphere.sdk.models.Reference;
import io.sphere.sdk.models.ResourceView;
import io.sphere.sdk.models.WithKey;
import io.sphere.sdk.producttypes.ProductType;
import io.sphere.sdk.reviews.ReviewRatingStatistics;
import io.sphere.sdk.states.State;
import io.sphere.sdk.taxcategories.TaxCategory;

import javax.annotation.Nullable;
import java.util.List;

public interface ProductLike<T, O> extends ResourceView<T, O>, ProductIdentifiable, WithKey {
    Reference<ProductType> getProductType();

    @Nullable
    Reference<TaxCategory> getTaxCategory();

    @Nullable
    ReviewRatingStatistics getReviewRatingStatistics();

    @Nullable
    Reference<State> getState();

    /**
     * Key of the product.
     * @return key
     *
     * @see io.sphere.sdk.products.commands.updateactions.SetKey
     * @see io.sphere.sdk.products.queries.ProductByKeyGet
     * @see io.sphere.sdk.products.queries.ProductProjectionByKeyGet
     * @see io.sphere.sdk.products.commands.ProductDeleteCommand#ofKey(String, Long)
     * @see io.sphere.sdk.products.commands.ProductUpdateCommand#ofKey(String, Long, List)
     * @see io.sphere.sdk.products.commands.ProductUpdateCommand#ofKey(String, Long, UpdateAction)
     */
    @Override
    @Nullable
    String getKey();
}
