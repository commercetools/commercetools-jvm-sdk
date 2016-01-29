package io.sphere.sdk.products;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import java.util.Optional;
import io.sphere.sdk.models.Resource;
import io.sphere.sdk.models.Reference;
import io.sphere.sdk.productdiscounts.ProductDiscount;
import io.sphere.sdk.reviews.Review;
import io.sphere.sdk.reviews.ReviewRatingStatistics;
import io.sphere.sdk.states.State;

import javax.annotation.Nullable;

/**

 <p>A sellable good.</p>

 <p>Consider to use {@link io.sphere.sdk.products.ProductProjection} for queries if you don't need the whole product data so you can safe traffic and memory.</p>

 @see io.sphere.sdk.products.commands.ProductCreateCommand
 @see io.sphere.sdk.products.commands.ProductUpdateCommand
 @see io.sphere.sdk.products.commands.ProductDeleteCommand
 @see io.sphere.sdk.products.queries.ProductQuery
 @see io.sphere.sdk.products.queries.ProductByIdGet
 @see io.sphere.sdk.products.ProductProjection
 @see io.sphere.sdk.categories.Category
 @see io.sphere.sdk.producttypes.ProductType
 @see io.sphere.sdk.productdiscounts.ProductDiscount
 @see ProductDiscount#getReferences()
 @see io.sphere.sdk.productdiscounts.DiscountedPrice
 @see Review#getTarget()
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

    /**
     * Creates a container which contains the full Java type information to deserialize this class from JSON.
     *
     * @see io.sphere.sdk.json.SphereJsonUtils#readObject(byte[], TypeReference)
     * @see io.sphere.sdk.json.SphereJsonUtils#readObject(String, TypeReference)
     * @see io.sphere.sdk.json.SphereJsonUtils#readObject(com.fasterxml.jackson.databind.JsonNode, TypeReference)
     * @see io.sphere.sdk.json.SphereJsonUtils#readObjectFromResource(String, TypeReference)
     *
     * @return type reference
     */
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

    /**
     * A type hint for references which resource type is linked in a reference.
     * @see Reference#getTypeId()
     * @return type hint
     */
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
