package io.sphere.sdk.categories;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import io.sphere.sdk.annotations.CopyFactoryMethod;
import io.sphere.sdk.annotations.FactoryMethod;
import io.sphere.sdk.annotations.ResourceDraftValue;
import io.sphere.sdk.models.*;
import io.sphere.sdk.types.CustomDraft;
import io.sphere.sdk.types.CustomFieldsDraft;

import javax.annotation.Nullable;
import java.util.List;

/**
 * Template for a new category.
 *
 * <p>For construction see {@link CategoryDraftBuilder}.</p>
 */
@JsonDeserialize(as = CategoryDraftDsl.class)
@ResourceDraftValue(
        abstractBuilderClass = true,
        copyFactoryMethods = @CopyFactoryMethod(Category.class),
        factoryMethods = @FactoryMethod(parameterNames = {"name", "slug"}))
public interface CategoryDraft extends CustomDraft, WithLocalizedSlug, MetaAttributes, WithKey {

    @Override
    @Nullable
    String getKey();

    LocalizedString getName();

    LocalizedString getSlug();

    @Nullable
    LocalizedString getDescription();

    @Nullable
    ResourceIdentifier<Category> getParent();

    @Nullable
    String getOrderHint();

    @Nullable
    String getExternalId();

    @Nullable
    CustomFieldsDraft getCustom();

    @Nullable
    LocalizedString getMetaTitle();

    @Nullable
    LocalizedString getMetaDescription();

    @Nullable
    LocalizedString getMetaKeywords();

    @Nullable
    List<AssetDraft> getAssets();
}
