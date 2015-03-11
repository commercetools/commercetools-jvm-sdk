package io.sphere.sdk.producttypes;

import com.github.slugify.Slugify;
import io.sphere.sdk.attributes.*;
import io.sphere.sdk.models.LocalizedStrings;
import io.sphere.sdk.models.Reference;
import io.sphere.sdk.products.*;
import io.sphere.sdk.products.commands.ProductCreateCommand;
import io.sphere.sdk.products.commands.ProductDeleteCommand;
import io.sphere.sdk.products.queries.ProductByIdFetch;
import io.sphere.sdk.producttypes.commands.ProductTypeCreateCommand;
import io.sphere.sdk.producttypes.commands.ProductTypeDeleteCommand;
import io.sphere.sdk.test.IntegrationTest;
import io.sphere.sdk.utils.TestCleaner;
import org.junit.Test;

import java.util.*;

import static java.util.Arrays.asList;
import static io.sphere.sdk.utils.SetUtils.asSet;
import static org.fest.assertions.Assertions.assertThat;
import static java.util.stream.Collectors.toList;

public final class NestedAttributeIntegrationTest extends IntegrationTest {

    interface NutrientInfo {
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
    public void itShouldBePossibleToDeeplyNestNestedTypesInsideOfSetTypes() throws Exception {
        TestCleaner.withCleaner(cleaner -> {
            final ProductType nutrientInformationType = cleaner.add(createNutrientInformation(), this::deleteProductType);
            final ProductType nutrientType = cleaner.add(createNutrient(nutrientInformationType.toReference()), this::deleteProductType);
            final ProductType bananaType = cleaner.add(createBanana(nutrientType.toReference()), this::deleteProductType);

            ProductVariantDraft masterVariant = ProductVariantDraftBuilder.of()
                .plusAttribute(Banana.bananaColor.valueOf("blue"))
                .plusAttribute(Banana.nutrients.valueOf(asSet(
                    AttributeContainer.of(asList(
                        Nutrient.servingSize.valueOf(1.5D),
                        Nutrient.servingSizeUOM.valueOf("M"),
                        Nutrient.nutrientInformation.valueOf(asSet(
                            AttributeContainer.of(asList(
                                NutrientInfo.nutrientTypeCode.valueOf("FIBTG"),
                                NutrientInfo.measurementPrecision.valueOf("APPROXIMATELY"),
                                NutrientInfo.quantityContained.valueOf(3.8D),
                                NutrientInfo.quantityContainedUOM.valueOf("GR")
                            )),
                            AttributeContainer.of(asList(
                                NutrientInfo.nutrientTypeCode.valueOf("FAT"),
                                NutrientInfo.measurementPrecision.valueOf("APPROXIMATELY"),
                                NutrientInfo.quantityContained.valueOf(0.06D),
                                NutrientInfo.quantityContainedUOM.valueOf("KG")
                            ))
                        ))
                    )),
                    AttributeContainer.of(asList(
                        Nutrient.servingSize.valueOf(0.05D),
                        Nutrient.servingSizeUOM.valueOf("KM"),
                        Nutrient.nutrientInformation.valueOf(asSet(
                            AttributeContainer.of(asList(
                                NutrientInfo.nutrientTypeCode.valueOf("FIBTG"),
                                NutrientInfo.measurementPrecision.valueOf("PRECISE"),
                                NutrientInfo.quantityContained.valueOf(1.3D),
                                NutrientInfo.quantityContainedUOM.valueOf("GR")
                            ))
                        ))
                    ))
                )))
                .sku(UUID.randomUUID().toString())
                .build();

            ProductDraft createProductCommand = ProductDraftBuilder
                .of(bananaType, en("Super Banana!"), en(new Slugify().slugify("super-banana")), masterVariant)
                .description(en("Cool and refreshing blue banana!"))
                .build();

            Product createdProduct = cleaner.add(execute(ProductCreateCommand.of(createProductCommand)), this::deleteProduct);
            Optional<Product> product = execute(ProductByIdFetch.of(createdProduct.getId()));

            assertThat(product.isPresent()).isTrue();

            AttributeContainer attrs = product.get().getMasterData().getStaged().getMasterVariant();

            assertThat(attrs.getAttribute(Banana.bananaColor)).isEqualTo(Optional.of("blue"));
            assertThat(attrs.getAttribute(Banana.nutrients).map(Set::size)).isEqualTo(Optional.of(2));

            List<AttributeContainer> nutrients = attrs.getAttribute(Banana.nutrients).get().stream()
                .sorted((c1, c2) -> c1.getAttribute(Nutrient.servingSize).get().compareTo(c2.getAttribute(Nutrient.servingSize).get()))
                .collect(toList());

            AttributeContainer firstNutrient = nutrients.get(0);

            assertThat(firstNutrient.getAttribute(Nutrient.servingSize)).isEqualTo(Optional.of(0.05D));
            assertThat(firstNutrient.getAttribute(Nutrient.servingSizeUOM)).isEqualTo(Optional.of("KM"));
            assertThat(firstNutrient.getAttribute(Nutrient.nutrientInformation).map(Set::size)).isEqualTo(Optional.of(1));

            List<AttributeContainer> firstNutrientInfos = firstNutrient.getAttribute(Nutrient.nutrientInformation).get().stream()
                .sorted((c1, c2) -> c1.getAttribute(NutrientInfo.nutrientTypeCode).get().compareTo(c2.getAttribute(NutrientInfo.nutrientTypeCode).get()))
                .collect(toList());

            assertThat(firstNutrientInfos.get(0).getAttribute(NutrientInfo.nutrientTypeCode)).isEqualTo(Optional.of("FIBTG"));
            assertThat(firstNutrientInfos.get(0).getAttribute(NutrientInfo.measurementPrecision)).isEqualTo(Optional.of("PRECISE"));
            assertThat(firstNutrientInfos.get(0).getAttribute(NutrientInfo.quantityContained)).isEqualTo(Optional.of(1.3D));
            assertThat(firstNutrientInfos.get(0).getAttribute(NutrientInfo.quantityContainedUOM)).isEqualTo(Optional.of("GR"));

            AttributeContainer secondNutrient = nutrients.get(1);

            assertThat(secondNutrient.getAttribute(Nutrient.servingSize)).isEqualTo(Optional.of(1.5D));
            assertThat(secondNutrient.getAttribute(Nutrient.servingSizeUOM)).isEqualTo(Optional.of("M"));
            assertThat(secondNutrient.getAttribute(Nutrient.nutrientInformation).map(Set::size)).isEqualTo(Optional.of(2));

            List<AttributeContainer> secondNutrientInfos = secondNutrient.getAttribute(Nutrient.nutrientInformation).get().stream()
                .sorted((c1, c2) -> c1.getAttribute(NutrientInfo.nutrientTypeCode).get().compareTo(c2.getAttribute(NutrientInfo.nutrientTypeCode).get()))
                .collect(toList());

            assertThat(secondNutrientInfos.get(0).getAttribute(NutrientInfo.nutrientTypeCode)).isEqualTo(Optional.of("FAT"));
            assertThat(secondNutrientInfos.get(0).getAttribute(NutrientInfo.measurementPrecision)).isEqualTo(Optional.of("APPROXIMATELY"));
            assertThat(secondNutrientInfos.get(0).getAttribute(NutrientInfo.quantityContained)).isEqualTo(Optional.of(0.06D));
            assertThat(secondNutrientInfos.get(0).getAttribute(NutrientInfo.quantityContainedUOM)).isEqualTo(Optional.of("KG"));

            assertThat(secondNutrientInfos.get(1).getAttribute(NutrientInfo.nutrientTypeCode)).isEqualTo(Optional.of("FIBTG"));
            assertThat(secondNutrientInfos.get(1).getAttribute(NutrientInfo.measurementPrecision)).isEqualTo(Optional.of("APPROXIMATELY"));
            assertThat(secondNutrientInfos.get(1).getAttribute(NutrientInfo.quantityContained)).isEqualTo(Optional.of(3.8D));
            assertThat(secondNutrientInfos.get(1).getAttribute(NutrientInfo.quantityContainedUOM)).isEqualTo(Optional.of("GR"));
        });
    }

    private ProductType createNutrientInformation() {
        return execute(ProductTypeCreateCommand.of(ProductTypeDraft.of("NutrientInformation", "NutrientInformation",
            asList(
                AttributeDefinitionBuilder.of(NutrientInfo.quantityContainedUOM.getName(),
                    en(NutrientInfo.quantityContainedUOM.getName()), TextType.of()).searchable(false).build(),
                AttributeDefinitionBuilder.of(NutrientInfo.quantityContained.getName(),
                    en(NutrientInfo.quantityContained.getName()), NumberType.of()).searchable(false).build(),
                AttributeDefinitionBuilder.of(NutrientInfo.measurementPrecision.getName(),
                    en(NutrientInfo.measurementPrecision.getName()), TextType.of()).searchable(false).build(),
                AttributeDefinitionBuilder.of(NutrientInfo.nutrientTypeCode.getName(),
                    en(NutrientInfo.nutrientTypeCode.getName()), TextType.of()).searchable(false).build()))));
    }

    private ProductType createNutrient(Reference<ProductType> nutrientInformationType) {
        return execute(ProductTypeCreateCommand.of(ProductTypeDraft.of("Nutrient", "Nutrient",
            asList(
                AttributeDefinitionBuilder.of(Nutrient.servingSizeUOM.getName(),
                    en(Nutrient.servingSizeUOM.getName()), TextType.of()).searchable(false).build(),
                AttributeDefinitionBuilder.of(Nutrient.servingSize.getName(),
                    en(Nutrient.servingSize.getName()), NumberType.of()).searchable(false).build(),
                AttributeDefinitionBuilder.of(Nutrient.nutrientInformation.getName(),
                    en(Nutrient.nutrientInformation.getName()), SetType.of(NestedType.of(nutrientInformationType))).searchable(false).build()))));
    }

    private ProductType createBanana(Reference<ProductType> nutrientType) {
        return execute(ProductTypeCreateCommand.of(ProductTypeDraft.of("Banana", "Banana",
            asList(
                AttributeDefinitionBuilder.of(Banana.bananaColor.getName(),
                    en(Banana.bananaColor.getName()), TextType.of()).searchable(false).build(),
                AttributeDefinitionBuilder.of(Banana.nutrients.getName(),
                    en(Banana.nutrients.getName()), SetType.of(NestedType.of(nutrientType))).searchable(false).build()))));
    }

    private LocalizedStrings en(final String value) {
        return LocalizedStrings.of(Locale.ENGLISH, value);
    }

    private void deleteProductType(ProductType productType) {
        execute(ProductTypeDeleteCommand.of(productType));
    }

    private void deleteProduct(Product product) {
        execute(ProductDeleteCommand.of(product));
    }
}
