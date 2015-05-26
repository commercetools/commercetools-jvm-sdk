package io.sphere.sdk.producttypes;

import io.sphere.sdk.attributes.*;
import io.sphere.sdk.models.Referenceable;
import io.sphere.sdk.products.*;
import io.sphere.sdk.test.IntegrationTest;
import org.junit.Test;

import java.util.*;

import static io.sphere.sdk.products.ProductFixtures.withProduct;
import static io.sphere.sdk.producttypes.ProductTypeFixtures.withProductType;
import static java.util.Arrays.asList;
import static io.sphere.sdk.utils.SetUtils.asSet;
import static org.assertj.core.api.Assertions.assertThat;
import static io.sphere.sdk.test.SphereTestUtils.*;

import static java.util.stream.Collectors.toList;

public final class NestedAttributeIntegrationTest extends IntegrationTest {

    static class NutrientInfo {
        static AttributeGetterSetter<String> quantityContainedUOM = AttributeAccess.ofText().ofName("quantityContainedUOM");
        static AttributeGetterSetter<Double> quantityContained = AttributeAccess.ofDouble().ofName("quantityContained");
        static AttributeGetterSetter<String> measurementPrecision = AttributeAccess.ofText().ofName("measurementPrecision");
        static AttributeGetterSetter<String> nutrientTypeCode = AttributeAccess.ofText().ofName("nutrientTypeCode");
    }

    static class Nutrient {
        static AttributeGetterSetter<String> servingSizeUOM = AttributeAccess.ofText().ofName("servingSizeUOM");
        static AttributeGetterSetter<Double> servingSize = AttributeAccess.ofDouble().ofName("servingSize");
        static AttributeGetterSetter<Set<AttributeContainer>> nutrientInformation = AttributeAccess.ofNestedSet().ofName("nutrientInformation");
    }

    static class Banana {
        static AttributeGetterSetter<String> bananaColor = AttributeAccess.ofText().ofName("bananaColor");
        static AttributeGetterSetter<Set<AttributeContainer>> nutrients = AttributeAccess.ofNestedSet().ofName("nutrients");
    }

    @Test
    public void itShouldBePossibleToDeeplyNestNestedTypesInsideOfSetTypes() throws Exception {
        withProductType(client(), () -> createNutrientInformation(), nutrientInformationType -> {
            withProductType(client(), () -> createNutrient(nutrientInformationType), nutrientType -> {
                withProductType(client(), () -> createBanana(nutrientType), bananaType -> {
                    final ProductDraftBuilder productDraftBuilder = ProductDraftBuilder
                            .of(bananaType, en("Super Banana!"), en(slugify("super-banana")), createBananaVariant())
                            .description(en("Cool and refreshing blue banana!"));

                    withProduct(client(), productDraftBuilder, product -> {
                        final AttributeContainer attrs = product.getMasterData().getStaged().getMasterVariant();

                        assertThat(attrs.getAttribute(Banana.bananaColor)).contains("blue");
                        assertThat(attrs.getAttribute(Banana.nutrients).map(Set::size)).contains(2);

                        final List<AttributeContainer> nutrients = getSortedNutrients(attrs);

                        assertNutrient(nutrients.get(0), 0.05D, "KM", 1);
                        assertNutrientInfo(getNutrientInfos(nutrients.get(0)).get(0), "FIBTG", "PRECISE", 1.3D, "GR");

                        assertNutrient(nutrients.get(1), 1.5D, "M", 2);
                        assertNutrientInfo(getNutrientInfos(nutrients.get(1)).get(0), "FAT", "APPROXIMATELY", 0.06D, "KG");
                        assertNutrientInfo(getNutrientInfos(nutrients.get(1)).get(1), "FIBTG", "APPROXIMATELY", 3.8D, "GR");
                    });
                });
            });
        });
    }

    private List<AttributeContainer> getSortedNutrients(final AttributeContainer banana) {
        return banana.getAttribute(Banana.nutrients).get().stream()
            .sorted((c1, c2) -> c1.getAttribute(Nutrient.servingSize).get().compareTo(c2.getAttribute(Nutrient.servingSize).get()))
            .collect(toList());
    }

    private List<AttributeContainer> getNutrientInfos(final AttributeContainer nutrient) {
        return nutrient.getAttribute(Nutrient.nutrientInformation).get().stream()
            .sorted((c1, c2) -> c1.getAttribute(NutrientInfo.nutrientTypeCode).get().compareTo(c2.getAttribute(NutrientInfo.nutrientTypeCode).get()))
            .collect(toList());
    }

    private void assertNutrient(final AttributeContainer nutrient, final double servingSize,
                                final String servingSizeUOM, final int nutrientInformationSize) {
        assertThat(nutrient.getAttribute(Nutrient.servingSize)).contains(servingSize);
        assertThat(nutrient.getAttribute(Nutrient.servingSizeUOM)).contains(servingSizeUOM);
        assertThat(nutrient.getAttribute(Nutrient.nutrientInformation).map(Set::size)).contains(nutrientInformationSize);
    }

    private void assertNutrientInfo(final AttributeContainer nutrientInfo, final String nutrientTypeCode,
                                    final String measurementPrecision, final double quantityContained,
                                    final String quantityContainedUOM) {
        assertThat(nutrientInfo.getAttribute(NutrientInfo.nutrientTypeCode)).contains(nutrientTypeCode);
        assertThat(nutrientInfo.getAttribute(NutrientInfo.measurementPrecision)).contains(measurementPrecision);
        assertThat(nutrientInfo.getAttribute(NutrientInfo.quantityContained)).contains(quantityContained);
        assertThat(nutrientInfo.getAttribute(NutrientInfo.quantityContainedUOM)).contains(quantityContainedUOM);
    }

    private ProductVariantDraft createBananaVariant() {
        return ProductVariantDraftBuilder.of()
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
            .sku(randomKey())
            .build();
    }

    private ProductTypeDraft createNutrientInformation() {
        return ProductTypeDraft.of("NutrientInformation", "NutrientInformation",
                asList(
                        AttributeDefinitionBuilder.of(NutrientInfo.quantityContainedUOM.getName(),
                                en(NutrientInfo.quantityContainedUOM.getName()), TextType.of()).build(),
                        AttributeDefinitionBuilder.of(NutrientInfo.quantityContained.getName(),
                                en(NutrientInfo.quantityContained.getName()), NumberType.of()).build(),
                        AttributeDefinitionBuilder.of(NutrientInfo.measurementPrecision.getName(),
                                en(NutrientInfo.measurementPrecision.getName()), TextType.of()).build(),
                        AttributeDefinitionBuilder.of(NutrientInfo.nutrientTypeCode.getName(),
                                en(NutrientInfo.nutrientTypeCode.getName()), TextType.of()).build()));
    }

    private ProductTypeDraft createNutrient(final Referenceable<ProductType> nutrientInformationType) {
        return ProductTypeDraft.of("Nutrient", "Nutrient",
                asList(
                        AttributeDefinitionBuilder.of(Nutrient.servingSizeUOM.getName(),
                                en(Nutrient.servingSizeUOM.getName()), TextType.of()).build(),
                        AttributeDefinitionBuilder.of(Nutrient.servingSize.getName(),
                                en(Nutrient.servingSize.getName()), NumberType.of()).build(),
                        AttributeDefinitionBuilder.of(Nutrient.nutrientInformation.getName(),
                                en(Nutrient.nutrientInformation.getName()), SetType.of(NestedType.of(nutrientInformationType))).searchable(false).build()));
    }

    private ProductTypeDraft createBanana(Referenceable<ProductType> nutrientType) {
        return ProductTypeDraft.of("Banana", "Banana",
                asList(
                        AttributeDefinitionBuilder.of(Banana.bananaColor.getName(),
                                en(Banana.bananaColor.getName()), TextType.of()).build(),
                        AttributeDefinitionBuilder.of(Banana.nutrients.getName(),
                                en(Banana.nutrients.getName()), SetType.of(NestedType.of(nutrientType))).searchable(false).build()));
    }
}
