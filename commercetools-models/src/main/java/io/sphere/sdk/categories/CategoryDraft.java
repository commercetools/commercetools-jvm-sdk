package io.sphere.sdk.categories;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import io.sphere.sdk.annotations.FactoryMethod;
import io.sphere.sdk.annotations.ResourceDraftValue;
import io.sphere.sdk.models.LocalizedString;
import io.sphere.sdk.models.MetaAttributes;
import io.sphere.sdk.models.Reference;
import io.sphere.sdk.models.WithLocalizedSlug;
import io.sphere.sdk.types.CustomDraft;
import io.sphere.sdk.types.CustomFieldsDraft;

import javax.annotation.Nullable;

/**
 * Template for a new category.
 *
 * <p>For construction see {@link CategoryDraftBuilder}.</p>
 */
@JsonDeserialize(as = CategoryDraftDsl.class)
@ResourceDraftValue(factoryMethods = @FactoryMethod(parameterNames = {"name", "slug"}))
public interface CategoryDraft extends CustomDraft, WithLocalizedSlug, MetaAttributes {
    LocalizedString getName();

    LocalizedString getSlug();

    @Nullable
    LocalizedString getDescription();

    @Nullable
    Reference<Category> getParent();

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
}
