package io.sphere.sdk.producttypes.io.sphere.sdk.example;

import com.google.common.util.concurrent.ListenableFuture;
import io.sphere.sdk.client.JavaClient;
import io.sphere.sdk.models.LocalizedString;
import io.sphere.sdk.producttypes.NewProductType;
import io.sphere.sdk.producttypes.ProductType;
import io.sphere.sdk.producttypes.ProductTypeBuilder;
import io.sphere.sdk.producttypes.ProductTypeCreateCommand;
import io.sphere.sdk.producttypes.attributes.*;
import org.joda.time.DateTime;

import java.util.Arrays;
import java.util.List;

import static java.util.Locale.ENGLISH;
import static java.util.Locale.GERMAN;

public class CreateTShirtProductTypeExample {

    JavaClient client;
    NewProductType newProductType;

    public void createBackend() {
        ProductTypeCreateCommand command = new ProductTypeCreateCommand(newProductType);
        ListenableFuture<ProductType> future = client.execute(command);
    }

    public void createNewProductType() {
        LocalizedString sizeAttributeLabel = LocalizedString.of(ENGLISH, "size").plus(GERMAN, "Größe");
        List<PlainEnumValue> sizeValues = PlainEnumValueListBuilder.of("S", "S").add("M", "M").add("X", "X").build();
        AttributeDefinition sizeAttribute = EnumAttributeDefinitionBuilder.of("size", sizeAttributeLabel, sizeValues).
                required(true).attributeConstraint(AttributeConstraint.CombinationUnique).build();


        LocalizedString colorAttributeLabel = LocalizedString.of(ENGLISH, "color").plus(GERMAN, "Farbe");
        LocalizedEnumValue green = LocalizedEnumValue.
                of("green", LocalizedString.of(ENGLISH, "green").plus(GERMAN, "grün"));
        LocalizedEnumValue red = LocalizedEnumValue.of("red", LocalizedString.of(ENGLISH, "red").plus(GERMAN, "rot"));
        List<LocalizedEnumValue> colorValues = Arrays.asList(green, red);
        LocalizedEnumAttributeDefinition colorAttribute =
                LocalizedEnumAttributeDefinitionBuilder.of("color", colorAttributeLabel, colorValues).
                        required(true).attributeConstraint(AttributeConstraint.CombinationUnique).build();

        LocalizedString srpLabel = LocalizedString.of(ENGLISH, "suggested retail price").
                plus(GERMAN, "unverbindliche Preisempfehlung (UVP)");
        MoneyAttributeDefinition srpAttribute = MoneyAttributeDefinitionBuilder.of("srp", srpLabel).
                isSearchable(false).build();

        String productTypeName = "t-shirt";
        String productTypeDescription = "a 'T' shaped cloth";

        List<AttributeDefinition> attributes = Arrays.asList(sizeAttribute, colorAttribute, srpAttribute);
        NewProductType newProductType = NewProductType.of(productTypeName, productTypeDescription, attributes);
    }

    public void createProductTypeForUnitTest() {
        DateTime createdAt = new DateTime("2013-11-13T21:39:45.618-08:00");
        DateTime lastModifiedAt = createdAt.plusHours(2);
        //set createdAt/lastModifiedAt/version is optional
        ProductType productType = ProductTypeBuilder.of("product-type-id", newProductType).
                createdAt(createdAt).lastModifiedAt(lastModifiedAt).version(4).build();
    }
}
