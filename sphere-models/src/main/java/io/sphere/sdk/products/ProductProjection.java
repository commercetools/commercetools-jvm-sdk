package io.sphere.sdk.products;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import io.sphere.sdk.categories.Category;
import io.sphere.sdk.models.LocalizedStrings;
import io.sphere.sdk.models.Reference;
import io.sphere.sdk.models.Referenceable;
import io.sphere.sdk.models.Versioned;
import io.sphere.sdk.search.SearchKeywords;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

/**
  A projected representation of a product shows the product with its current or staged data.

  {@link io.sphere.sdk.products.ProductProjection}s are views of {@link io.sphere.sdk.products.Product}s.

 <p>To create, update or delete {@link io.sphere.sdk.products.ProductProjection}s you have to use the
 {@link io.sphere.sdk.products.Product} lifecycle classes.</p>

 <p id=operations>Operations:</p>
 <ul>
    <li>Fetch by ID with {@link io.sphere.sdk.products.queries.ProductProjectionByIdFetch}</li>
 </ul>

 If you need {@link io.sphere.sdk.products.ProductProjection} for a method but you have a product,
 you can transform a product into a product projection:

 {@include.example io.sphere.sdk.products.ProductProjectionTest#transformProductIntoProductProjection()}

 {@link ProductProjection#toReference()} returns not are reference to a {@link io.sphere.sdk.products.ProductProjection} but to a {@link io.sphere.sdk.products.Product}.

 */
@JsonDeserialize(as=ProductProjectionImpl.class)
public interface ProductProjection extends ProductLike<ProductProjection>, ProductDataLike, Referenceable<Product> {

    boolean hasStagedChanges();

    boolean isPublished();

    @Override
    default Reference<Product> toReference() {
        return Product.reference(getId());
    }

    static TypeReference<ProductProjection> typeReference(){
        return new TypeReference<ProductProjection>() {
            @Override
            public String toString() {
                return "TypeReference<ProductProjection>";
            }
        };
    }

    default Versioned<Product> toProductVersioned() {
        return Versioned.of(getId(), getVersion());
    }

    @Override
    Set<Reference<Category>> getCategories();

    @Override
    @Nullable
    LocalizedStrings getDescription();

    @Override
    ProductVariant getMasterVariant();

    @Nullable
    @Override
    LocalizedStrings getMetaDescription();

    @Nullable
    @Override
    LocalizedStrings getMetaKeywords();

    @Nullable
    @Override
    LocalizedStrings getMetaTitle();

    @Override
    LocalizedStrings getName();

    @Override
    LocalizedStrings getSlug();

    @Override
    List<ProductVariant> getVariants();

    @Override
    default List<ProductVariant> getAllVariants() {
        return ProductsPackage.getAllVariants(this);
    }

    default Optional<ProductVariant> findVariant(final VariantIdentifier identifier){
        return getId().equals(identifier.getProductId()) ? Optional.ofNullable(getVariant(identifier.getVariantId())) : Optional.empty();
    }

    default Optional<ProductVariant> findVariantBySky(final String sku) {
        Objects.requireNonNull(sku);
        return getAllVariants().stream().filter(v -> sku.equals(v.getSku())).findFirst();
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
}
