package io.sphere.sdk.products;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.google.common.base.Optional;
import io.sphere.sdk.categories.Category;
import io.sphere.sdk.models.LocalizedString;
import io.sphere.sdk.models.Reference;

import java.util.List;

@JsonDeserialize(as=ProductDataImpl.class)
public interface ProductData {

    public LocalizedString getName();

    public List<Reference<Category>> getCategories();

    public Optional<LocalizedString> getDescription();

    public LocalizedString getSlug();

    public Optional<LocalizedString> getMetaTitle();

    public Optional<LocalizedString> getMetaDescription();

    public Optional<LocalizedString> getMetaKeywords();

    public NewProductVariant getMasterVariant();

    public List<NewProductVariant> getVariants();
}
