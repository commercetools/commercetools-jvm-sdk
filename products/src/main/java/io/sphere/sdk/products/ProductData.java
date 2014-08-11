package io.sphere.sdk.products;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import java.util.Optional;
import io.sphere.sdk.categories.Category;
import io.sphere.sdk.models.LocalizedString;
import io.sphere.sdk.models.Reference;
import io.sphere.sdk.models.WithLocalizedSlug;

import java.util.List;

/**
 * For construction in unit tests use {@link io.sphere.sdk.products.ProductDataBuilder}.
 */
@JsonDeserialize(as=ProductDataImpl.class)
public interface ProductData extends WithLocalizedSlug {

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
