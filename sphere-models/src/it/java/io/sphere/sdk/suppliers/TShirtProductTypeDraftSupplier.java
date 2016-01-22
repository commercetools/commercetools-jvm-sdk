package io.sphere.sdk.suppliers;

import io.sphere.sdk.products.attributes.*;
import io.sphere.sdk.models.EnumValue;
import io.sphere.sdk.models.LocalizedEnumValue;
import io.sphere.sdk.models.LocalizedString;
import io.sphere.sdk.producttypes.ProductTypeDraft;

import java.util.List;
import java.util.function.Supplier;

import static io.sphere.sdk.test.SphereTestUtils.randomKey;
import static io.sphere.sdk.utils.SphereInternalUtils.asImmutableList;
import static java.util.Arrays.asList;
import static java.util.Locale.ENGLISH;
import static java.util.Locale.GERMAN;

public class TShirtProductTypeDraftSupplier implements Supplier<ProductTypeDraft> {

    public static final String MONEY_ATTRIBUTE_NAME = "srp";

    public static class Colors {
        public static final LocalizedEnumValue GREEN =
                LocalizedEnumValue.of("green", LocalizedString.of(ENGLISH, "green").plus(GERMAN, "grün"));
        public static final LocalizedEnumValue RED =
                LocalizedEnumValue.of("red", LocalizedString.of(ENGLISH, "red").plus(GERMAN, "rot"));

        public static final List<LocalizedEnumValue> VALUES = asImmutableList(GREEN, RED);

        public static final NamedAttributeAccess<LocalizedEnumValue> ATTRIBUTE =
                AttributeAccess.ofLocalizedEnumValue().ofName("color");
    }

    public static class Sizes {
        public static final EnumValue S = EnumValue.of("S", "S");
        public static final EnumValue M = EnumValue.of("M", "M");
        public static final EnumValue X = EnumValue.of("X", "X");

        public static final List<EnumValue> VALUES = asImmutableList(S, M, X);

        public static final NamedAttributeAccess<EnumValue> ATTRIBUTE =
                AttributeAccess.ofEnumValue().ofName("size");
    }

    private final String name;

    public TShirtProductTypeDraftSupplier(final String name) {
        this.name = name;
    }

    @Override
    public ProductTypeDraft get() {
        return ProductTypeDraft.of(randomKey(), name, "a 'T' shaped cloth", createAttributes());
    }

    private static List<AttributeDefinition> createAttributes() {
        return asList(sizeAttribute(), colorAttribute(), srpAttribute());
    }

    private static AttributeDefinition sizeAttribute() {
        LocalizedString sizeAttributeLabel = LocalizedString.of(ENGLISH, "size").plus(GERMAN, "Größe");
        return AttributeDefinitionBuilder.of("size", sizeAttributeLabel, EnumAttributeType.of(Sizes.VALUES)).
                required(true).attributeConstraint(AttributeConstraint.COMBINATION_UNIQUE).build();
    }

    private static AttributeDefinition colorAttribute() {
        LocalizedString colorAttributeLabel = LocalizedString.of(ENGLISH, "color").plus(GERMAN, "Farbe");
        return AttributeDefinitionBuilder.of("color", colorAttributeLabel, LocalizedEnumAttributeType.of(Colors.VALUES)).
                required(true).attributeConstraint(AttributeConstraint.COMBINATION_UNIQUE).build();
    }

    private static AttributeDefinition srpAttribute() {
        LocalizedString srpLabel = LocalizedString.of(ENGLISH, "recommended retailer price (rrp)").
                plus(GERMAN, "unverbindliche Preisempfehlung (UVP)");
        return AttributeDefinitionBuilder.of(MONEY_ATTRIBUTE_NAME, srpLabel, MoneyAttributeType.of()).isSearchable(false).build();
    }
}

