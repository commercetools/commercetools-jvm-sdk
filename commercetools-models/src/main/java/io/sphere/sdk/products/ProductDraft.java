package io.sphere.sdk.products;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import io.sphere.sdk.annotations.FactoryMethod;
import io.sphere.sdk.annotations.ResourceDraftValue;
import io.sphere.sdk.categories.Category;
import io.sphere.sdk.models.*;
import io.sphere.sdk.producttypes.ProductType;
import io.sphere.sdk.search.SearchKeywords;
import io.sphere.sdk.states.State;
import io.sphere.sdk.taxcategories.TaxCategory;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Set;

/**
 * A template for a new {@link io.sphere.sdk.products.Product}.
 *
 * @see ProductDraftBuilder
 * @see io.sphere.sdk.products.commands.ProductCreateCommand
 */


@ResourceDraftValue(
        factoryMethods = {@FactoryMethod(parameterNames = {"productType", "name", "slug", "masterVariant"})},
        abstractBuilderClass = true)
@JsonDeserialize(as = ProductDraftDsl.class)
public interface ProductDraft extends WithLocalizedSlug, MetaAttributes, WithKey {


    LocalizedString getName();

    ResourceIdentifier<ProductType> getProductType();

    LocalizedString getSlug();

    @Nullable
    LocalizedString getDescription();

    Set<ResourceIdentifier<Category>> getCategories();

    @Nullable
    @Override
    LocalizedString getMetaTitle();

    @Nullable
    @Override
    LocalizedString getMetaDescription();

    @Nullable
    @Override
    LocalizedString getMetaKeywords();

    @Nullable
    ProductVariantDraft getMasterVariant();

    List<ProductVariantDraft> getVariants();

    @Nullable
    ResourceIdentifier<TaxCategory> getTaxCategory();

    SearchKeywords getSearchKeywords();

    @Nullable
    CategoryOrderHints getCategoryOrderHints();

    @Nullable
    ResourceIdentifier<State> getState();

    /**
     * Flag for publishing the product immediately.
     * <p>
     * {@include.example io.sphere.sdk.products.commands.ProductCreateCommandIntegrationTest#createPublishedProduct()}
     *
     * @return true if product should be published
     */
    @Nullable
    @JsonProperty("publish")
    Boolean isPublish();

    @Override
    @Nullable
    String getKey();
}
