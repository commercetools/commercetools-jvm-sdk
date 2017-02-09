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

public final class ProductDraftBuilder extends Base implements Builder<ProductDraft>, WithLocalizedSlug, MetaAttributes {

    private final ResourceIdentifier<ProductType> productType;
    private ProductVariantDraft masterVariant;
    private List<ProductVariantDraft> variants = Collections.emptyList();
    private LocalizedString name;
    private LocalizedString slug;
    private LocalizedString description;
    private LocalizedString metaTitle;
    private LocalizedString metaDescription;
    private LocalizedString metaKeywords;
    private Set<Reference<Category>> categories = Collections.emptySet();
    private SearchKeywords searchKeywords = SearchKeywords.of();
    private Reference<TaxCategory> taxCategory;
    @Nullable
    private Reference<State> state;
    @Nullable
    private CategoryOrderHints categoryOrderHints;
    @Nullable
    private Boolean publish;
    @Nullable
    private String key;

    private ProductDraftBuilder(final ResourceIdentifier<ProductType> productType, final LocalizedString name, final LocalizedString slug, final ProductVariantDraft masterVariant) {
        this.name = name;
        this.slug = slug;
        this.productType = productType;
        this.masterVariant = masterVariant;
    }

    public static ProductDraftBuilder of(final ProductDraft productDraft) {
        return of(productDraft.getProductType(), productDraft.getName(), productDraft.getSlug(), productDraft.getMasterVariant())
                .variants(productDraft.getVariants())
                .description(productDraft.getDescription())
                .metaTitle(productDraft.getMetaTitle())
                .metaDescription(productDraft.getMetaDescription())
                .metaKeywords(productDraft.getMetaKeywords())
                .categories(productDraft.getCategories())
                .searchKeywords(productDraft.getSearchKeywords())
                .taxCategory(productDraft.getTaxCategory())
                .state(productDraft.getState())
                .categoryOrderHints(productDraft.getCategoryOrderHints())
                .publish(productDraft.isPublish())
                .key(productDraft.getKey());
    }

    public static ProductDraftBuilder of(final ResourceIdentifiable<ProductType> productType, final LocalizedString name, final LocalizedString slug, final List<ProductVariantDraft> allVariants) {
        final ProductVariantDraft masterVariant = allVariants.stream().findFirst().orElse(null);
        final List<ProductVariantDraft> variants = allVariants.stream().skip(1).collect(Collectors.toList());
        return of(productType, name, slug, masterVariant)
                .plusVariants(variants);
    }

    public static ProductDraftBuilder of(final ResourceIdentifiable<ProductType> productType, final LocalizedString name, final LocalizedString slug, final ProductVariantDraft masterVariant) {
        return new ProductDraftBuilder(productType.toResourceIdentifier(), name, slug, masterVariant);
    }

    @Override
    public ProductDraft build() {
        return new ProductDraftImpl(productType, getName(), getSlug(), getDescription(), getCategories(), getMetaTitle(), getMetaDescription(), getMetaKeywords(), masterVariant, variants, getTaxCategory(), getSearchKeywords(), getState(), getCategoryOrderHints(), publish, key);
    }

    protected ProductDraftBuilder getThis() {
        return this;
    }

    public ProductDraftBuilder variants(final List<ProductVariantDraft> variants) {
        this.variants = variants != null
                ? Collections.unmodifiableList(new ArrayList<>(variants))
                : Collections.emptyList();
        return getThis();
    }

    public ProductDraftBuilder plusVariants(final ProductVariantDraft variantToAdd) {
        return variants(listOf(this.variants, variantToAdd));
    }

    public ProductDraftBuilder plusVariants(final List<ProductVariantDraft> variantsToAdd) {
        return variants(listOf(this.variants, variantsToAdd));
    }

    public ProductDraftBuilder masterVariant(final ProductVariantDraft masterVariant) {
        this.masterVariant = masterVariant;
        return getThis();
    }

    public ProductVariantDraft getMasterVariant() {
        return masterVariant;
    }

    public ResourceIdentifier<ProductType> getProductType() {
        return productType;
    }

    public List<ProductVariantDraft> getVariants() {
        return variants;
    }

    public ProductDraftBuilder slug(final LocalizedString slug) {
        this.slug = slug;
        return getThis();
    }

    public ProductDraftBuilder name(final LocalizedString name) {
        this.name = name;
        return getThis();
    }

    public ProductDraftBuilder description(@Nullable final LocalizedString description) {
        this.description = description;
        return getThis();
    }

    public ProductDraftBuilder metaTitle(@Nullable final LocalizedString metaTitle) {
        this.metaTitle = metaTitle;
        return getThis();
    }

    public ProductDraftBuilder metaDescription(@Nullable final LocalizedString metaDescription) {
        this.metaDescription = metaDescription;
        return getThis();
    }

    public ProductDraftBuilder metaKeywords(@Nullable final LocalizedString metaKeywords) {
        this.metaKeywords = metaKeywords;
        return getThis();
    }

    public ProductDraftBuilder categories(final Set<Reference<Category>> categories) {
        this.categories = categories;
        return getThis();
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
                .filter(x -> x != null)
                .map(cat -> cat.toReference())
                .collect(Collectors.toList());
        return categories(referenceList);
    }

    public ProductDraftBuilder searchKeywords(final SearchKeywords searchKeywords) {
        this.searchKeywords = searchKeywords;
        return getThis();
    }

    public ProductDraftBuilder categoryOrderHints(@Nullable final CategoryOrderHints categoryOrderHints) {
        this.categoryOrderHints = categoryOrderHints;
        return getThis();
    }

    public ProductDraftBuilder key(@Nullable final String key) {
        this.key = key;
        return getThis();
    }

    public LocalizedString getName() {
        return name;
    }

    public LocalizedString getSlug() {
        return slug;
    }

    @Nullable
    public LocalizedString getDescription() {
        return description;
    }

    @Nullable
    public LocalizedString getMetaTitle() {
        return metaTitle;
    }

    @Nullable
    public LocalizedString getMetaDescription() {
        return metaDescription;
    }

    @Nullable
    public LocalizedString getMetaKeywords() {
        return metaKeywords;
    }

    public Set<Reference<Category>> getCategories() {
        return categories;
    }

    public SearchKeywords getSearchKeywords() {
        return searchKeywords;
    }

    public Reference<TaxCategory> getTaxCategory() {
        return taxCategory;
    }

    public ProductDraftBuilder taxCategory(final Referenceable<TaxCategory> taxCategory) {
        this.taxCategory = taxCategory != null ? taxCategory.toReference() : null;
        return getThis();
    }

    public ProductDraftBuilder state(@Nullable final Referenceable<State> state) {
        this.state = Optional.ofNullable(state).map(Referenceable::toReference).orElse(null);
        return getThis();
    }

    public ProductDraftBuilder publish(@Nullable final Boolean publish) {
        this.publish = publish;
        return getThis();
    }

    @Nullable
    public Reference<State> getState() {
        return state;
    }

    @Nullable
    public CategoryOrderHints getCategoryOrderHints() {
        return categoryOrderHints;
    }

    @Nullable
    public Boolean isPublish() {
        return publish;
    }

    @Nullable
    public String getKey() {
        return key;
    }
}
