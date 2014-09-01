package example;

import io.sphere.sdk.models.LocalizedEnumValue;
import io.sphere.sdk.models.LocalizedString;
import io.sphere.sdk.models.PlainEnumValue;
import io.sphere.sdk.models.PlainEnumValueListBuilder;
import io.sphere.sdk.producttypes.NewProductType;
import io.sphere.sdk.producttypes.attributes.*;

import java.util.Arrays;
import java.util.List;
import java.util.function.Supplier;

import static java.util.Locale.ENGLISH;
import static java.util.Locale.GERMAN;

public class TShirtNewProductTypeSupplier implements Supplier<NewProductType> {
    @Override
    public NewProductType get() {
        return NewProductType.of("t-shirt", "a 'T' shaped cloth", createAttributes());
    }

    private static List<AttributeDefinition> createAttributes() {
        return Arrays.asList(sizeAttribute(), colorAttribute(), srpAttribute());
    }

    private static AttributeDefinition sizeAttribute() {
        LocalizedString sizeAttributeLabel = LocalizedString.of(ENGLISH, "size").plus(GERMAN, "Größe");
        List<PlainEnumValue> sizeValues = PlainEnumValueListBuilder.of("S", "S").add("M", "M").add("X", "X").build();
        return EnumAttributeDefinitionBuilder.of("size", sizeAttributeLabel, sizeValues).
                required(true).attributeConstraint(AttributeConstraint.CombinationUnique).build();
    }

    private static AttributeDefinition colorAttribute() {
        LocalizedString colorAttributeLabel = LocalizedString.of(ENGLISH, "color").plus(GERMAN, "Farbe");
        LocalizedEnumValue green = LocalizedEnumValue.
                of("green", LocalizedString.of(ENGLISH, "green").plus(GERMAN, "grün"));
        LocalizedEnumValue red = LocalizedEnumValue.of("red", LocalizedString.of(ENGLISH, "red").plus(GERMAN, "rot"));
        List<LocalizedEnumValue> colorValues = Arrays.asList(green, red);
        return LocalizedEnumAttributeDefinitionBuilder.of("color", colorAttributeLabel, colorValues).
                required(true).attributeConstraint(AttributeConstraint.CombinationUnique).build();
    }

    private static AttributeDefinition srpAttribute() {
        LocalizedString srpLabel = LocalizedString.of(ENGLISH, "recommended retailer price (rrp)").
                plus(GERMAN, "unverbindliche Preisempfehlung (UVP)");
        return MoneyAttributeDefinitionBuilder.of("srp", srpLabel).isSearchable(false).build();
    }
}
