package io.sphere.sdk.suppliers;

import io.sphere.sdk.attributes.*;
import io.sphere.sdk.models.LocalizedEnumValue;
import io.sphere.sdk.models.LocalizedString;
import io.sphere.sdk.models.PlainEnumValue;
import io.sphere.sdk.products.Product;
import io.sphere.sdk.producttypes.NewProductType;

import java.util.List;
import java.util.function.Supplier;

import static io.sphere.sdk.utils.ListUtils.asImmutableList;
import static java.util.Arrays.asList;
import static java.util.Locale.ENGLISH;
import static java.util.Locale.GERMAN;

public class TShirtNewProductTypeSupplier implements Supplier<NewProductType> {

    public static class Colors {
        public static final LocalizedEnumValue GREEN =
                LocalizedEnumValue.of("green", LocalizedString.of(ENGLISH, "green").plus(GERMAN, "grün"));
        public static final LocalizedEnumValue RED =
                LocalizedEnumValue.of("red", LocalizedString.of(ENGLISH, "red").plus(GERMAN, "rot"));

        public static final List<LocalizedEnumValue> VALUES = asImmutableList(GREEN, RED);

        public static final AttributeGetterSetter<Product, LocalizedEnumValue> ATTRIBUTE =
                TypeSafeAttributeAccess.ofLocalizedEnumValue().getterSetter("color");
    }

    public static class Sizes {
        public static final PlainEnumValue S = PlainEnumValue.of("S", "S");
        public static final PlainEnumValue M = PlainEnumValue.of("M", "M");
        public static final PlainEnumValue X = PlainEnumValue.of("X", "X");

        public static final List<PlainEnumValue> VALUES = asImmutableList(S, M, X);

        public static final AttributeGetterSetter<Product, PlainEnumValue> ATTRIBUTE =
                TypeSafeAttributeAccess.ofPlainEnumValue().getterSetter("size");
    }

    private final String name;

    public TShirtNewProductTypeSupplier(final String name) {
        this.name = name;
    }

    @Override
    public NewProductType get() {
        return NewProductType.of(name, "a 'T' shaped cloth", createAttributes());
    }

    private static List<AttributeDefinition> createAttributes() {
        return asList(sizeAttribute(), colorAttribute(), srpAttribute());
    }

    private static AttributeDefinition sizeAttribute() {
        LocalizedString sizeAttributeLabel = LocalizedString.of(ENGLISH, "size").plus(GERMAN, "Größe");
        return EnumAttributeDefinitionBuilder.of("size", sizeAttributeLabel, Sizes.VALUES).
                required(true).attributeConstraint(AttributeConstraint.CombinationUnique).build();
    }

    private static AttributeDefinition colorAttribute() {
        LocalizedString colorAttributeLabel = LocalizedString.of(ENGLISH, "color").plus(GERMAN, "Farbe");
        return LocalizedEnumAttributeDefinitionBuilder.of("color", colorAttributeLabel, Colors.VALUES).
                required(true).attributeConstraint(AttributeConstraint.CombinationUnique).build();
    }

    private static AttributeDefinition srpAttribute() {
        LocalizedString srpLabel = LocalizedString.of(ENGLISH, "recommended retailer price (rrp)").
                plus(GERMAN, "unverbindliche Preisempfehlung (UVP)");
        return MoneyAttributeDefinitionBuilder.of("srp", srpLabel).isSearchable(false).build();
    }
}

