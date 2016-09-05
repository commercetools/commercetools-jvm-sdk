package io.sphere.sdk.products;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
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
  A template for a new {@link io.sphere.sdk.products.Product}.

  @see ProductDraftBuilder
  @see io.sphere.sdk.products.commands.ProductCreateCommand
 */
@JsonDeserialize(as = ProductDraftImpl.class)
public interface ProductDraft extends WithLocalizedSlug, MetaAttributes {


    LocalizedString getName();

    ResourceIdentifier<ProductType> getProductType();

    LocalizedString getSlug();

    @Nullable
    LocalizedString getDescription();

    Set<Reference<Category>> getCategories();

    @Nullable
    @Override
    LocalizedString getMetaTitle();

    @Nullable
    @Override
    LocalizedString getMetaDescription();

    @Nullable
    @Override
    LocalizedString getMetaKeywords();

    ProductVariantDraft getMasterVariant();

    List<ProductVariantDraft> getVariants();

    @Nullable
    Reference<TaxCategory> getTaxCategory();

    SearchKeywords getSearchKeywords();

    @Nullable
    CategoryOrderHints getCategoryOrderHints();

    @Nullable
    Reference<State> getState();

    /**
     * Flag for publishing the product immediately.
     *
     * {@include.example io.sphere.sdk.products.commands.ProductCreateCommandIntegrationTest#createPublishedProduct()}
     *
     * @return true if product should be published
     */
    @Nullable
    Boolean isPublish();

    @Nullable
    String getKey();
}
