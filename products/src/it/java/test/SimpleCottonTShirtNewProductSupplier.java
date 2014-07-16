package test;

import com.github.slugify.Slugify;
import io.sphere.sdk.models.LocalizedString;
import io.sphere.sdk.models.Reference;
import io.sphere.sdk.products.*;
import io.sphere.sdk.producttypes.ProductType;

import java.util.Locale;
import java.util.function.Supplier;

//used as demo case
public class SimpleCottonTShirtNewProductSupplier implements Supplier<NewProduct> {
    final Reference<ProductType> productType;
    final String name;

    public SimpleCottonTShirtNewProductSupplier(final Reference<ProductType> productType, final String name) {
        this.productType = productType;
        this.name = name;
    }

    @Override
    public NewProduct get() {
        NewProductVariant masterVariant = NewProductVariantBuilder.of().attributes(Attribute.of("size", "M"), Attribute.of("color", "red")).build();
        return NewProductBuilder.of(productType, en(name), en(new Slugify().slugify(name))).
                description(en(name)).
                metaTitle(en("cotton t-shirt")).
                metaDescription(en("cotton t-shirt description")).
                metaKeywords(en("cotton, t-shirt, clothes")).
                masterVariant(masterVariant).build();
    }

    private LocalizedString en(final String value) {
        return LocalizedString.of(Locale.ENGLISH, value);
    }
}
