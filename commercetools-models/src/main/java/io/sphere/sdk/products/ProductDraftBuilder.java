package io.sphere.sdk.products;

import io.sphere.sdk.categories.Category;
import io.sphere.sdk.models.*;
import io.sphere.sdk.producttypes.ProductType;
import io.sphere.sdk.search.SearchKeywords;
import io.sphere.sdk.states.State;
import io.sphere.sdk.taxcategories.TaxCategory;

import javax.annotation.Nullable;
import java.util.*;
import java.util.stream.Collectors;

import static io.sphere.sdk.utils.SphereInternalUtils.listOf;

public final class ProductDraftBuilder extends ProductDraftBuilderBase<ProductDraftBuilder> {


    ProductDraftBuilder(final Set<ResourceIdentifier<Category>> categories,
                        @Nullable final CategoryOrderHints categoryOrderHints,
                        @Nullable final LocalizedString description,
                        @Nullable final String key,
                        @Nullable final ProductVariantDraft masterVariant,
                        @Nullable final LocalizedString metaDescription,
                        @Nullable final LocalizedString metaKeywords,
                        @Nullable final LocalizedString metaTitle,
                        final LocalizedString name,
                        final ResourceIdentifier<ProductType> productType,
                        @Nullable final Boolean publish,
                        final SearchKeywords searchKeywords,
                        final LocalizedString slug,
                        @Nullable final ResourceIdentifier<State> state,
                        @Nullable final ResourceIdentifier<TaxCategory> taxCategory,
                        final List<ProductVariantDraft> variants) {
        super(categories, categoryOrderHints, description, key, masterVariant, metaDescription, metaKeywords, metaTitle,
                name, productType, publish, searchKeywords, slug, state, taxCategory, variants);
        init();
    }

    private void init(){
        variants = Optional.ofNullable(variants).orElse(Collections.emptyList());
        categories = Optional.ofNullable(categories).orElse(Collections.emptySet());
        searchKeywords = Optional.ofNullable(searchKeywords).orElse(SearchKeywords.of());
    }

    public static ProductDraftBuilder of(final ResourceIdentifiable<ProductType> productType,
                                         final LocalizedString name,
                                         final LocalizedString slug,
                                         final List<ProductVariantDraft> allVariants) {
        final ProductVariantDraft masterVariant = allVariants.stream().findFirst().orElse(null);
        final List<ProductVariantDraft> variants = allVariants.stream().skip(1).collect(Collectors.toList());
        return of(productType, name, slug, masterVariant)
                .plusVariants(variants);
    }

    public static ProductDraftBuilder of(final ResourceIdentifiable<ProductType> productType,
                                         final LocalizedString name,
                                         final LocalizedString slug,
                                         @Nullable final ProductVariantDraft masterVariant) {
        return of(productType.toResourceIdentifier(),name,slug,masterVariant);
    }


    public ProductDraftBuilder variants(final List<ProductVariantDraft> variants) {
        this.variants = variants != null
                ? Collections.unmodifiableList(new ArrayList<>(variants))
                : Collections.emptyList();
        return ProductDraftBuilder.this;
    }

    public ProductDraftBuilder plusVariants(final ProductVariantDraft variantToAdd) {
        return variants(listOf(this.variants, variantToAdd));
    }

    public ProductDraftBuilder plusVariants(final List<ProductVariantDraft> variantsToAdd) {
        return variants(listOf(this.variants, variantsToAdd));
    }


    public ProductDraftBuilder categories(final List<Reference<Category>> categories) {
        return categories(new LinkedHashSet<>(categories));
    }

    /**
     * Adds categories to this product draft. Alias for {@link #categories(List)} which takes the category objects as parameter.
     *
     * @param categories categories which the product belongs to
     * @return this builder
     */
    public ProductDraftBuilder categoriesAsObjectList(final List<Category> categories) {
        final List<Reference<Category>> referenceList = categories.stream()
                .filter(Objects::nonNull)
                .map(Category::toReference)
                .collect(Collectors.toList());
        return categories(referenceList);
    }


    public ProductDraftBuilder state(@Nullable final Referenceable<State> state) {
        this.state = Optional.ofNullable(state).map(Referenceable::toResourceIdentifier).orElse(null);
        return ProductDraftBuilder.this;
    }

    public ProductDraftBuilder state(@Nullable Reference<State> state) {
        return super.state(state);
    }

    public ProductDraftBuilder taxCategory(@Nullable Referenceable<TaxCategory> taxCategory) {
        return super.taxCategory(Optional.ofNullable(taxCategory).map(Referenceable::toResourceIdentifier).orElse(null));
    }

    public ProductDraftBuilder taxCategory(@Nullable Reference<TaxCategory> taxCategory) {
        return super.taxCategory(taxCategory);
    }
}
