package io.sphere.sdk.producttypes;

import io.sphere.sdk.attributes.*;
import io.sphere.sdk.models.LocalizedStrings;
import io.sphere.sdk.models.Reference;
import io.sphere.sdk.products.Product;
import io.sphere.sdk.producttypes.commands.ProductTypeCreateCommand;
import io.sphere.sdk.producttypes.commands.ProductTypeDeleteCommand;
import io.sphere.sdk.test.IntegrationTest;
import org.junit.Test;

import java.util.Arrays;
import java.util.Locale;
import java.util.Set;

public final class NestedAttributeIntegrationTest extends IntegrationTest {

    interface NutrientInformation {
        static AttributeGetterSetter<Product, String> quantityContainedUOM = AttributeAccess.ofText().ofName("quantityContainedUOM");
        static AttributeGetterSetter<Product, Double> quantityContained = AttributeAccess.ofDouble().ofName("quantityContained");
        static AttributeGetterSetter<Product, String> measurementPrecision = AttributeAccess.ofText().ofName("measurementPrecision");
        static AttributeGetterSetter<Product, String> nutrientTypeCode = AttributeAccess.ofText().ofName("nutrientTypeCode");
    }

    interface Nutrient {
        static AttributeGetterSetter<Product, String> servingSizeUOM = AttributeAccess.ofText().ofName("servingSizeUOM");
        static AttributeGetterSetter<Product, Double> servingSize = AttributeAccess.ofDouble().ofName("servingSize");
        static AttributeGetterSetter<Product, Set<AttributeContainer>> nutrientInformation = AttributeAccess.ofNestedSet().ofName("nutrientInformation");
    }

    interface Banana {
        static AttributeGetterSetter<Product, String> bananaColor = AttributeAccess.ofText().ofName("bananaColor");
        static AttributeGetterSetter<Product, Set<AttributeContainer>> nutrients = AttributeAccess.ofNestedSet().ofName("nutrients");
    }

    @Test
    public void nestedAttribute() throws Exception {
        final ProductType nutrientInformationType = createNutrientInformation();
        final ProductType nutrientType = createNutrient(nutrientInformationType.toReference());
        final ProductType bananaType = createBanana(nutrientType.toReference());

        try {
            // TODO: create product of new product type and check it!
        } finally {
            execute(ProductTypeDeleteCommand.of(bananaType));
            execute(ProductTypeDeleteCommand.of(nutrientType));
            execute(ProductTypeDeleteCommand.of(nutrientInformationType));
        }
    }

    private ProductType createNutrientInformation() {
        return execute(ProductTypeCreateCommand.of(ProductTypeDraft.of("NutrientInformation", "NutrientInformation",
            Arrays.asList(
                AttributeDefinitionBuilder.of(NutrientInformation.quantityContainedUOM.getName(),
                    LocalizedStrings.of(Locale.ENGLISH, NutrientInformation.quantityContainedUOM.getName()), TextType.of()).searchable(false).build(),
                AttributeDefinitionBuilder.of(NutrientInformation.quantityContained.getName(),
                    LocalizedStrings.of(Locale.ENGLISH, NutrientInformation.quantityContained.getName()), NumberType.of()).searchable(false).build(),
                AttributeDefinitionBuilder.of(NutrientInformation.measurementPrecision.getName(),
                    LocalizedStrings.of(Locale.ENGLISH, NutrientInformation.measurementPrecision.getName()), TextType.of()).searchable(false).build(),
                AttributeDefinitionBuilder.of(NutrientInformation.nutrientTypeCode.getName(),
                    LocalizedStrings.of(Locale.ENGLISH, NutrientInformation.nutrientTypeCode.getName()), TextType.of()).searchable(false).build()))));
    }

    private ProductType createNutrient(Reference<ProductType> nutrientInformationType) {
        return execute(ProductTypeCreateCommand.of(ProductTypeDraft.of("Nutrient", "Nutrient",
            Arrays.asList(
                AttributeDefinitionBuilder.of(Nutrient.servingSizeUOM.getName(),
                    LocalizedStrings.of(Locale.ENGLISH, Nutrient.servingSizeUOM.getName()), TextType.of()).searchable(false).build(),
                AttributeDefinitionBuilder.of(Nutrient.servingSize.getName(),
                    LocalizedStrings.of(Locale.ENGLISH, Nutrient.servingSize.getName()), NumberType.of()).searchable(false).build(),
                AttributeDefinitionBuilder.of(Nutrient.nutrientInformation.getName(),
                    LocalizedStrings.of(Locale.ENGLISH, Nutrient.nutrientInformation.getName()), SetType.of(NestedType.of(nutrientInformationType))).searchable(false).build()))));
    }

    private ProductType createBanana(Reference<ProductType> nutrientType) {
        return execute(ProductTypeCreateCommand.of(ProductTypeDraft.of("Banana", "Banana",
            Arrays.asList(
                AttributeDefinitionBuilder.of(Banana.bananaColor.getName(),
                    LocalizedStrings.of(Locale.ENGLISH, Banana.bananaColor.getName()), TextType.of()).searchable(false).build(),
                AttributeDefinitionBuilder.of(Banana.nutrients.getName(),
                    LocalizedStrings.of(Locale.ENGLISH, Banana.nutrients.getName()), SetType.of(NestedType.of(nutrientType))).searchable(false).build()))));
    }

}
