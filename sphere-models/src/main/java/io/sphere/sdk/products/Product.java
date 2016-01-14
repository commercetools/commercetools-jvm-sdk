package io.sphere.sdk.products;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import java.util.Optional;
import io.sphere.sdk.models.Resource;
import io.sphere.sdk.models.Reference;
import io.sphere.sdk.reviews.ReviewRatingStatistics;
import io.sphere.sdk.states.State;

import javax.annotation.Nullable;

/**

 <p>A sellable good.</p>

 <p> id=operationsOperations:</p>
 <ul>
    <li>Create a product in SPHERE.IO with {@link io.sphere.sdk.products.commands.ProductCreateCommand}.</li>
    <li>Query a product with {@link io.sphere.sdk.products.queries.ProductQuery}.</li>
    <li>Update a product with {@link io.sphere.sdk.products.commands.ProductUpdateCommand}.</li>
    <li>Delete a product with {@link io.sphere.sdk.products.commands.ProductDeleteCommand}.</li>
 </ul>

 <p>Consider to use {@link io.sphere.sdk.products.ProductProjection} for queries if you don't need the whole product data so you can safe traffic and memory.</p>

 @see io.sphere.sdk.products.ProductProjection
 @see io.sphere.sdk.categories.Category
 @see io.sphere.sdk.producttypes.ProductType
 @see io.sphere.sdk.productdiscounts.ProductDiscount
 */
@JsonDeserialize(as=ProductImpl.class)
public interface Product extends ProductLike<Product, Product>, Resource<Product> {

    ProductCatalogData getMasterData();

    /**
     * Returns this state of this Product.
     *
     * @return state of this product or null
     *
     * @see io.sphere.sdk.products.commands.updateactions.TransitionState
     */
    @Nullable
    Reference<State> getState();

    static TypeReference<Product> typeReference(){
        return new TypeReference<Product>() {
            @Override
            public String toString() {
                return "TypeReference<Product>";
            }
        };
    }

    @Override
    default Reference<Product> toReference() {
        return reference(this);
    }

    static String referenceTypeId() {
        return "product";
    }

    /**
     *
     * @deprecated use {@link #referenceTypeId()} instead
     * @return referenceTypeId
     */
    @Deprecated
    static String typeId(){
        return "product";
    }

    static Reference<Product> reference(final Product product) {
        return Reference.of(referenceTypeId(), product.getId(), product);
    }

    static Reference<Product> reference(final String id) {
        return Reference.of(referenceTypeId(), id);
    }

    @Nullable
    default ProductProjection toProjection(final ProductProjectionType productProjectionType) {
        return Optional.ofNullable(getMasterData().get(productProjectionType))
                .map(productData -> new ProductToProductProjectionWrapper(this, productProjectionType))
                .orElse(null);
    }

    static Reference<Product> referenceOfId(final String id) {
        return Reference.of(referenceTypeId(), id);
    }

    @Nullable
    @Override
    ReviewRatingStatistics getReviewRatingStatistics();
}
