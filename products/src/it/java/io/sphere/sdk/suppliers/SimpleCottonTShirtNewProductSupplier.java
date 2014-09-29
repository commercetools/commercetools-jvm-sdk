package io.sphere.sdk.suppliers;

import com.github.slugify.Slugify;
import io.sphere.sdk.models.LocalizedString;
import io.sphere.sdk.models.Reference;
import io.sphere.sdk.models.Referenceable;
import io.sphere.sdk.products.*;
import io.sphere.sdk.producttypes.ProductType;

import java.util.Locale;
import java.util.function.Supplier;

import static io.sphere.sdk.suppliers.TShirtNewProductTypeSupplier.*;

public class SimpleCottonTShirtNewProductSupplier implements Supplier<NewProduct> {
    private final Reference<ProductType> productType;
    private final String name;

    public SimpleCottonTShirtNewProductSupplier(final Referenceable<ProductType> productType, final String name) {
        this.productType = productType.toReference();
        this.name = name;
    }

    @Override
    public NewProduct get() {
        final NewProductVariant masterVariant = NewProductVariantBuilder.of()
                .plusAttribute(Sizes.ATTRIBUTE.valueOf(Sizes.S))
                .plusAttribute(Colors.ATTRIBUTE.valueOf(Colors.GREEN))
                .build();
        final LocalizedString slug = en(new Slugify().slugify(name));
        return NewProductBuilder.of(productType, en(name), slug, masterVariant)
                .description(en(name))
                .metaTitle(en("cotton t-shirt"))
                .metaDescription(en("cotton t-shirt description"))
                .metaKeywords(en("cotton, t-shirt, clothes"))
                .build();
    }

    private LocalizedString en(final String value) {
        return LocalizedString.of(Locale.ENGLISH, value);
    }
}
