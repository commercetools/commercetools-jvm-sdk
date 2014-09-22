package test;

import com.github.slugify.Slugify;
import io.sphere.sdk.models.LocalizedString;
import io.sphere.sdk.models.Reference;
import io.sphere.sdk.models.Referenceable;
import io.sphere.sdk.products.*;
import io.sphere.sdk.producttypes.ProductType;
import io.sphere.sdk.attributes.Attribute;
import io.sphere.sdk.attributes.AttributeGetterSetter;
import io.sphere.sdk.attributes.AttributeTypes;

import java.util.Locale;
import java.util.function.Supplier;

//used as demo case
public class SimpleCottonTShirtNewProductSupplier implements Supplier<NewProduct> {
    final Reference<ProductType> productType;
    final String name;

    public SimpleCottonTShirtNewProductSupplier(final Referenceable<ProductType> productType, final String name) {
        this.productType = productType.toReference();
        this.name = name;
    }

    @Override
    public NewProduct get() {
        //creating an attribute flexible but not type-safe
        Attribute size = Attribute.of("size", "M");

        AttributeGetterSetter<Product, String> COLOR = AttributeTypes.ofString().getterSetter("color");
        Attribute color = Attribute.of(COLOR, "red");//compiler will warn you if the value is not a String in this case

        NewProductVariant masterVariant = NewProductVariantBuilder.of().attributes(size, color).build();

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
