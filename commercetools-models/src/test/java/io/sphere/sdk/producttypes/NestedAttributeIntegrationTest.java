package io.sphere.sdk.producttypes;

import io.sphere.sdk.models.Referenceable;
import io.sphere.sdk.products.*;
import io.sphere.sdk.products.attributes.*;
import io.sphere.sdk.test.IntegrationTest;
import org.junit.Test;

import java.util.List;
import java.util.Set;

import static io.sphere.sdk.products.ProductFixtures.withProduct;
import static io.sphere.sdk.producttypes.ProductTypeFixtures.withProductType;
import static io.sphere.sdk.test.SphereTestUtils.*;
import static io.sphere.sdk.utils.SphereInternalUtils.asSet;
import static java.util.Arrays.asList;
import static java.util.stream.Collectors.toList;
import static org.assertj.core.api.Assertions.assertThat;

public class NestedAttributeIntegrationTest extends IntegrationTest {

    static class NutrientInfo {
        static NamedAttributeAccess<String> quantityContainedUOM = AttributeAccess.ofString().ofName("quantityContainedUOM");
        static NamedAttributeAccess<Double> quantityContained = AttributeAccess.ofDouble().ofName("quantityContained");
        static NamedAttributeAccess<String> measurementPrecision = AttributeAccess.ofString().ofName("measurementPrecision");
        static NamedAttributeAccess<String> nutrientTypeCode = AttributeAccess.ofString().ofName("nutrientTypeCode");
    }

    static class Nutrient {
        static NamedAttributeAccess<String> servingSizeUOM = AttributeAccess.ofString().ofName("servingSizeUOM");
        static NamedAttributeAccess<Double> servingSize = AttributeAccess.ofDouble().ofName("servingSize");
        static NamedAttributeAccess<Set<AttributeContainer>> nutrientInformation = AttributeAccess.ofNestedSet().ofName("nutrientInformation");
    }

    static class Banana {
        static NamedAttributeAccess<String> bananaColor = AttributeAccess.ofString().ofName("bananaColor");
        static NamedAttributeAccess<Set<AttributeContainer>> nutrients = AttributeAccess.ofNestedSet().ofName("nutrients");
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

                        assertThat(attrs.findAttribute(Banana.bananaColor)).contains("blue");
                        assertThat(attrs.findAttribute(Banana.nutrients).map(Set::size)).contains(2);

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
        return banana.findAttribute(Banana.nutrients).get().stream()
                .sorted((c1, c2) -> c1.findAttribute(Nutrient.servingSize).get().compareTo(c2.findAttribute(Nutrient.servingSize).get()))
                .collect(toList());
    }

    private List<AttributeContainer> getNutrientInfos(final AttributeContainer nutrient) {
        return nutrient.findAttribute(Nutrient.nutrientInformation).get().stream()
                .sorted((c1, c2) -> c1.findAttribute(NutrientInfo.nutrientTypeCode).get().compareTo(c2.findAttribute(NutrientInfo.nutrientTypeCode).get()))
                .collect(toList());
    }

    private void assertNutrient(final AttributeContainer nutrient, final double servingSize,
                                final String servingSizeUOM, final int nutrientInformationSize) {
        assertThat(nutrient.findAttribute(Nutrient.servingSize)).contains(servingSize);
        assertThat(nutrient.findAttribute(Nutrient.servingSizeUOM)).contains(servingSizeUOM);
        assertThat(nutrient.findAttribute(Nutrient.nutrientInformation).map(Set::size)).contains(nutrientInformationSize);
    }

    private void assertNutrientInfo(final AttributeContainer nutrientInfo, final String nutrientTypeCode,
                                    final String measurementPrecision, final double quantityContained,
                                    final String quantityContainedUOM) {
        assertThat(nutrientInfo.findAttribute(NutrientInfo.nutrientTypeCode)).contains(nutrientTypeCode);
        assertThat(nutrientInfo.findAttribute(NutrientInfo.measurementPrecision)).contains(measurementPrecision);
        assertThat(nutrientInfo.findAttribute(NutrientInfo.quantityContained)).contains(quantityContained);
        assertThat(nutrientInfo.findAttribute(NutrientInfo.quantityContainedUOM)).contains(quantityContainedUOM);
    }

    private ProductVariantDraft createBananaVariant() {
        return ProductVariantDraftBuilder.of()
                .attributes(Banana.bananaColor.draftOf("blue"),
                        Banana.nutrients.draftOf(asSet(AttributeContainerDraft.of(asList(
                        Nutrient.servingSize.draftOf(1.5D),
                        Nutrient.servingSizeUOM.draftOf("M"),
                        Nutrient.nutrientInformation.draftOf(asSet(AttributeContainerDraft.of(
                                NutrientInfo.nutrientTypeCode.draftOf("FIBTG"),
                                NutrientInfo.measurementPrecision.draftOf("APPROXIMATELY"),
                                NutrientInfo.quantityContained.draftOf(3.8D),
                                NutrientInfo.quantityContainedUOM.draftOf("GR")
                        ), AttributeContainerDraft.of(
                                NutrientInfo.nutrientTypeCode.draftOf("FAT"),
                                NutrientInfo.measurementPrecision.draftOf("APPROXIMATELY"),
                                NutrientInfo.quantityContained.draftOf(0.06D),
                                NutrientInfo.quantityContainedUOM.draftOf("KG")
                        )))
                )), AttributeContainerDraft.of(asList(
                        Nutrient.servingSize.draftOf(0.05D),
                        Nutrient.servingSizeUOM.draftOf("KM"),
                        Nutrient.nutrientInformation.draftOf(asSet(AttributeContainerDraft.of(asList(
                                NutrientInfo.nutrientTypeCode.draftOf("FIBTG"),
                                NutrientInfo.measurementPrecision.draftOf("PRECISE"),
                                NutrientInfo.quantityContained.draftOf(1.3D),
                                NutrientInfo.quantityContainedUOM.draftOf("GR")
                        ))))
                )))))
                .sku(randomKey())
                .build();
    }

    private ProductTypeDraft createNutrientInformation() {
        return ProductTypeDraft.of(randomKey(), "NutrientInformation", "NutrientInformation", asList(
                        AttributeDefinitionBuilder.of(NutrientInfo.quantityContainedUOM.getName(),
                                en(NutrientInfo.quantityContainedUOM.getName()), StringAttributeType.of()).build(),
                        AttributeDefinitionBuilder.of(NutrientInfo.quantityContained.getName(),
                                en(NutrientInfo.quantityContained.getName()), NumberAttributeType.of()).build(),
                        AttributeDefinitionBuilder.of(NutrientInfo.measurementPrecision.getName(),
                                en(NutrientInfo.measurementPrecision.getName()), StringAttributeType.of()).build(),
                        AttributeDefinitionBuilder.of(NutrientInfo.nutrientTypeCode.getName(),
                                en(NutrientInfo.nutrientTypeCode.getName()), StringAttributeType.of()).build()));
    }

    private ProductTypeDraft createNutrient(final Referenceable<ProductType> nutrientInformationType) {
        return ProductTypeDraft.of(randomKey(), "Nutrient", "Nutrient", asList(
                        AttributeDefinitionBuilder.of(Nutrient.servingSizeUOM.getName(),
                                en(Nutrient.servingSizeUOM.getName()), StringAttributeType.of()).build(),
                        AttributeDefinitionBuilder.of(Nutrient.servingSize.getName(),
                                en(Nutrient.servingSize.getName()), NumberAttributeType.of()).build(),
                        AttributeDefinitionBuilder.of(Nutrient.nutrientInformation.getName(),
                                en(Nutrient.nutrientInformation.getName()), SetAttributeType.of(NestedAttributeType.of(nutrientInformationType))).searchable(false).build()));
    }

    private ProductTypeDraft createBanana(Referenceable<ProductType> nutrientType) {
        return ProductTypeDraft.of(randomKey(), "Banana", "Banana", asList(
                        AttributeDefinitionBuilder.of(Banana.bananaColor.getName(),
                                en(Banana.bananaColor.getName()), StringAttributeType.of()).build(),
                        AttributeDefinitionBuilder.of(Banana.nutrients.getName(),
                                en(Banana.nutrients.getName()), SetAttributeType.of(NestedAttributeType.of(nutrientType))).searchable(false).build()));
    }
}
