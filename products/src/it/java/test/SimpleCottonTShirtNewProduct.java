package test;

import com.github.slugify.Slugify;
import com.google.common.base.Optional;
import io.sphere.sdk.models.LocalizedString;
import io.sphere.sdk.models.Reference;
import io.sphere.sdk.products.Attribute;
import io.sphere.sdk.products.DefaultNewProduct;
import io.sphere.sdk.products.NewProductVariant;
import io.sphere.sdk.products.NewProductVariantBuilder;
import io.sphere.sdk.producttypes.ProductType;

import java.util.Locale;

public class SimpleCottonTShirtNewProduct extends DefaultNewProduct {
    private final String enName;

    public SimpleCottonTShirtNewProduct(final Reference<ProductType> productType, final String name) {
        super(productType);
        this.enName = name;
    }

    @Override
    public LocalizedString getName() {
        return en(enName);
    }

    @Override
    public LocalizedString getSlug() {
        return en("simple-cotton-t-shirt" + new Slugify().slugify(enName));
    }

    @Override
    public Optional<LocalizedString> getDescription() {
        return Optional.of(getName());
    }

    @Override
    public Optional<LocalizedString> getMetaTitle() {
        return Optional.of(en("getMetaTitle()"));
    }

    @Override
    public Optional<LocalizedString> getMetaDescription() {
        return Optional.of(en("getMetaDescription()"));
    }

    @Override
    public Optional<LocalizedString> getMetaKeywords() {
        return Optional.of(en("getMetaKeywords()"));
    }

    @Override
    public Optional<NewProductVariant> getMasterVariant() {
        return Optional.of(NewProductVariantBuilder.of().attributes(Attribute.of("size", "M"), Attribute.of("color", "red")).build());
    }

    private LocalizedString en(final String value) {
        return LocalizedString.of(Locale.ENGLISH, value);
    }
}
