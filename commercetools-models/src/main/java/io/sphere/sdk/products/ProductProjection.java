package io.sphere.sdk.products;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import io.sphere.sdk.categories.Category;
import io.sphere.sdk.models.LocalizedString;
import io.sphere.sdk.models.Reference;
import io.sphere.sdk.models.Referenceable;
import io.sphere.sdk.models.Versioned;
import io.sphere.sdk.products.queries.ProductProjectionByIdGet;
import io.sphere.sdk.reviews.ReviewRatingStatistics;
import io.sphere.sdk.search.SearchKeywords;
import io.sphere.sdk.states.State;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

/**
  A projected representation of a product shows the product with its current or staged data.

  {@link io.sphere.sdk.products.ProductProjection}s are views of {@link io.sphere.sdk.products.Product}s.

 <p>To create, update or delete {@link io.sphere.sdk.products.ProductProjection}s you have to use the
 {@link io.sphere.sdk.products.Product} lifecycle classes.</p>

 <p id=operations>Operations:</p>
 <ul>
    <li>Fetch by ID with {@link ProductProjectionByIdGet}</li>
 </ul>

 If you need {@link io.sphere.sdk.products.ProductProjection} for a method but you have a product,
 you can transform a product into a product projection:

 {@include.example io.sphere.sdk.products.ProductProjectionTest#transformProductIntoProductProjection()}

 {@link ProductProjection#toReference()} returns not are reference to a {@link io.sphere.sdk.products.ProductProjection} but to a {@link io.sphere.sdk.products.Product}.

 */
@JsonDeserialize(as=ProductProjectionImpl.class)
public interface ProductProjection extends ProductLike<ProductProjection, Product>, ProductDataLike, Referenceable<Product>, Versioned<Product> {

    Boolean hasStagedChanges();

    Boolean isPublished();

    @Override
    default Reference<Product> toReference() {
        return Product.reference(getId());
    }

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
    static TypeReference<ProductProjection> typeReference() {
        return new TypeReference<ProductProjection>() {
            @Override
            public String toString() {
                return "TypeReference<ProductProjection>";
            }
        };
    }

    @Override
    Set<Reference<Category>> getCategories();

    @Override
    @Nullable
    LocalizedString getDescription();

    @Override
    ProductVariant getMasterVariant();

    @Nullable
    @Override
    LocalizedString getMetaDescription();

    @Nullable
    @Override
    LocalizedString getMetaKeywords();

    @Nullable
    @Override
    LocalizedString getMetaTitle();

    @Override
    LocalizedString getName();

    @Override
    LocalizedString getSlug();

    @Override
    List<ProductVariant> getVariants();

    @Override
    default List<ProductVariant> getAllVariants() {
        return ProductsPackage.getAllVariants(this);
    }

    default Optional<ProductVariant> findVariant(final ByIdVariantIdentifier identifier){
        return getId().equals(identifier.getProductId()) ? Optional.ofNullable(getVariant(identifier.getVariantId())) : Optional.empty();
    }

    /**
     * Finds a variant by SKU.
     * @param sku the sku for the variant
     * @return Optional of the found variant
     */
    default Optional<ProductVariant> findVariantBySku(final String sku) {
        Objects.requireNonNull(sku);
        return getAllVariants().stream().filter(v -> sku.equals(v.getSku())).findFirst();
    }

    /**
     * Finds all variants that match the search criteria used in the search request, if any.
     * @return the list containing all matching variants
     */
    default List<ProductVariant> findMatchingVariants() {
        return getAllVariants().stream()
                .filter(v -> Optional.ofNullable(v.isMatchingVariant()).orElse(false))
                .collect(Collectors.toList());
    }

    /**
     * Finds the first matching variant according to the search criteria used in the search request, if any.
     * @return the first product variant that matches the search criteria, or empty if none does
     */
    default Optional<ProductVariant> findFirstMatchingVariant() {
        return findMatchingVariants().stream().findFirst();
    }

    @Override
    @Nullable
    default ProductVariant getVariant(final int variantId){
        return ProductsPackage.getVariant(variantId, this).orElse(null);
    }

    @Override
    default ProductVariant getVariantOrMaster(final int variantId) {
        return ProductsPackage.getVariantOrMaster(variantId, this);
    }

    @Override
    SearchKeywords getSearchKeywords();

    @Override
    @Nullable
    CategoryOrderHints getCategoryOrderHints();

    @Nullable
    @Override
    ReviewRatingStatistics getReviewRatingStatistics();

    @Nullable
    @Override
    Reference<State> getState();

    @Nullable
    @Override
    String getKey();
}
