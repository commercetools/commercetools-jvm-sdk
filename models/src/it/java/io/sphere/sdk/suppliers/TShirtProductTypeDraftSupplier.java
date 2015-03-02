package io.sphere.sdk.suppliers;

import io.sphere.sdk.attributes.*;
import io.sphere.sdk.models.LocalizedEnumValue;
import io.sphere.sdk.models.LocalizedStrings;
import io.sphere.sdk.models.PlainEnumValue;
import io.sphere.sdk.products.Product;
import io.sphere.sdk.producttypes.ProductTypeDraft;

import java.util.List;
import java.util.function.Supplier;

import static io.sphere.sdk.utils.ListUtils.asImmutableList;
import static java.util.Arrays.asList;
import static java.util.Locale.ENGLISH;
import static java.util.Locale.GERMAN;

public class TShirtProductTypeDraftSupplier implements Supplier<ProductTypeDraft> {

    public static final String MONEY_ATTRIBUTE_NAME = "srp";

    public static class Colors {
        public static final LocalizedEnumValue GREEN =
                LocalizedEnumValue.of("green", LocalizedStrings.of(ENGLISH, "green").plus(GERMAN, "grün"));
        public static final LocalizedEnumValue RED =
                LocalizedEnumValue.of("red", LocalizedStrings.of(ENGLISH, "red").plus(GERMAN, "rot"));

        public static final List<LocalizedEnumValue> VALUES = asImmutableList(GREEN, RED);

        public static final AttributeGetterSetter<Product, LocalizedEnumValue> ATTRIBUTE =
                AttributeAccess.ofLocalizedEnumValue().ofName("color");
    }

    public static class Sizes {
        public static final PlainEnumValue S = PlainEnumValue.of("S", "S");
        public static final PlainEnumValue M = PlainEnumValue.of("M", "M");
        public static final PlainEnumValue X = PlainEnumValue.of("X", "X");

        public static final List<PlainEnumValue> VALUES = asImmutableList(S, M, X);

        public static final AttributeGetterSetter<Product, PlainEnumValue> ATTRIBUTE =
                AttributeAccess.ofPlainEnumValue().ofName("size");
    }

    private final String name;

    public TShirtProductTypeDraftSupplier(final String name) {
        this.name = name;
    }

    @Override
    public ProductTypeDraft get() {
        return ProductTypeDraft.of(name, "a 'T' shaped cloth", createAttributes());
    }

    private static List<AttributeDefinition> createAttributes() {
        return asList(sizeAttribute(), colorAttribute(), srpAttribute());
    }

    private static AttributeDefinition sizeAttribute() {
        LocalizedStrings sizeAttributeLabel = LocalizedStrings.of(ENGLISH, "size").plus(GERMAN, "Größe");
        return AttributeDefinitionBuilder.of("size", sizeAttributeLabel, new EnumType(Sizes.VALUES)).
                required(true).attributeConstraint(AttributeConstraint.COMBINATION_UNIQUE).build();
    }

    private static AttributeDefinition colorAttribute() {
        LocalizedStrings colorAttributeLabel = LocalizedStrings.of(ENGLISH, "color").plus(GERMAN, "Farbe");
        return AttributeDefinitionBuilder.of("color", colorAttributeLabel, new LocalizedEnumType(Colors.VALUES)).
                required(true).attributeConstraint(AttributeConstraint.COMBINATION_UNIQUE).build();
    }

    private static AttributeDefinition srpAttribute() {
        LocalizedStrings srpLabel = LocalizedStrings.of(ENGLISH, "recommended retailer price (rrp)").
                plus(GERMAN, "unverbindliche Preisempfehlung (UVP)");
        return AttributeDefinitionBuilder.of(MONEY_ATTRIBUTE_NAME, srpLabel, new MoneyType()).isSearchable(false).build();
    }
}

