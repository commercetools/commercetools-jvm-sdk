package io.sphere.sdk.producttypes.commands;

import io.sphere.sdk.models.EnumValue;
import io.sphere.sdk.models.LocalizedEnumValue;
import io.sphere.sdk.models.LocalizedString;
import io.sphere.sdk.models.TextInputHint;
import io.sphere.sdk.products.attributes.*;
import io.sphere.sdk.producttypes.ProductType;
import io.sphere.sdk.producttypes.ProductTypeDraft;
import io.sphere.sdk.producttypes.commands.updateactions.*;
import io.sphere.sdk.test.IntegrationTest;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;

import static io.sphere.sdk.producttypes.ProductTypeFixtures.withUpdateableProductType;
import static io.sphere.sdk.suppliers.TShirtProductTypeDraftSupplier.Colors;
import static io.sphere.sdk.suppliers.TShirtProductTypeDraftSupplier.Sizes;
import static io.sphere.sdk.test.SphereTestUtils.*;
import static io.sphere.sdk.utils.SphereInternalUtils.reverse;
import static java.util.Collections.singletonList;
import static java.util.Locale.GERMAN;
import static java.util.Locale.GERMANY;
import static org.assertj.core.api.Assertions.assertThat;

public class ProductTypeUpdateCommandIntegrationTest extends IntegrationTest {

    @Test
    public void setInputTip() throws Exception {
        withUpdateableProductType(client(), productType -> {
            final String attributeName = "attributeName";
            final AttributeDefinition attributeDefinition = AttributeDefinitionBuilder
                    .of(attributeName, randomSlug(), StringAttributeType.of())
                    .build();
            assertThat(productType.getAttribute(attributeName)).isNull();

            final LocalizedString inputTip = en("inputTip");
            final ProductType updatedProductType =
                    client().executeBlocking(ProductTypeUpdateCommand.of(productType,
                            asList(AddAttributeDefinition.of(attributeDefinition), SetInputTip.of(attributeName, inputTip))));
            assertThat(updatedProductType.getAttribute(attributeName).getInputTip()).isEqualTo(inputTip);
            
            return updatedProductType;
        });
    }

    @Test
    public void changeName() throws Exception {
        withUpdateableProductType(client(), productType -> {
            final String name = randomKey();
            final ProductType updatedProductType =
                    client().executeBlocking(ProductTypeUpdateCommand.of(productType, ChangeName.of(name)));
            assertThat(updatedProductType.getName()).isEqualTo(name);
            return updatedProductType;
        });
    }

    @Test
    public void changeNameByKeyUpdate() throws Exception {
        withUpdateableProductType(client(), productType -> {
            final String name = randomKey();
            final ProductType updatedProductType =
                    client().executeBlocking(ProductTypeUpdateCommand.ofKey(productType.getKey(), productType.getVersion(), ChangeName.of(name)));
            assertThat(updatedProductType.getName()).isEqualTo(name);
            return updatedProductType;
        });
    }

    @Test
    public void changeDescription() throws Exception {
        withUpdateableProductType(client(), productType -> {
            final String description = randomKey();
            final ProductType updatedProductType =
                    client().executeBlocking(ProductTypeUpdateCommand.of(productType, ChangeDescription.of(description)));
            assertThat(updatedProductType.getDescription()).isEqualTo(description);
            return updatedProductType;
        });
    }

    @Test
    public void addAttributeDefinition() throws Exception {
        withUpdateableProductType(client(), productType -> {
            //add
            final String attributeName = "foostring";
            final AttributeDefinition foostring =
                    AttributeDefinitionBuilder.of(attributeName, LocalizedString.of(ENGLISH, "foo string"), StringAttributeType.of()).build();
            final ProductType withFoostring = client().executeBlocking(ProductTypeUpdateCommand.of(productType, AddAttributeDefinition.of(foostring)));
            final AttributeDefinition loadedDefinition = withFoostring.getAttribute(attributeName);
            assertThat(loadedDefinition.getAttributeType()).isEqualTo(StringAttributeType.of());

            //remove
            final ProductType withoutFoostring = client().executeBlocking(ProductTypeUpdateCommand.of(withFoostring, RemoveAttributeDefinition.of(attributeName)));
            assertThat(withoutFoostring.findAttribute(attributeName)).isEmpty();
            return withoutFoostring;
        });
    }


    @Test
    public void changeAttributeConstraintUpdateActionTest() throws Exception {
        withUpdateableProductType(client(), productType -> {
            //add
            final String attributeName = "foostring";
            final AttributeDefinition foostring =
                    AttributeDefinitionBuilder.of(attributeName, LocalizedString.of(ENGLISH, "foo string"), StringAttributeType.of()).attributeConstraint(AttributeConstraint.SAME_FOR_ALL).build();
            final ProductType withFoostring = client().executeBlocking(ProductTypeUpdateCommand.of(productType, AddAttributeDefinition.of(foostring)));
            final AttributeDefinition loadedDefinition = withFoostring.getAttribute(attributeName);
            assertThat(loadedDefinition.getAttributeType()).isEqualTo(StringAttributeType.of());
            assertThat(loadedDefinition.getAttributeConstraint()).isEqualTo(AttributeConstraint.SAME_FOR_ALL);

            final ProductType updatedProductType = client().executeBlocking(ProductTypeUpdateCommand.of(withFoostring, ChangeAttributeConstraint.of("foostring",AttributeConstraint.NONE)));
            final AttributeDefinition updatedDefinition = updatedProductType.getAttribute(attributeName);
            assertThat(updatedDefinition.getAttributeConstraint()).isEqualTo(AttributeConstraint.NONE);


            //remove
            final ProductType withoutFoostring = client().executeBlocking(ProductTypeUpdateCommand.of(updatedProductType, RemoveAttributeDefinition.of(attributeName)));
            assertThat(withoutFoostring.findAttribute(attributeName)).isEmpty();
            return withoutFoostring;
        });
    }

    @Test
    public void changeLabel() throws Exception {
        withUpdateableProductType(client(), productType -> {
            final String attributeName = "color";
            assertThat(productType.findAttribute(attributeName)).isPresent();
            final LocalizedString label = LocalizedString.of(ENGLISH, "the color label");

            final ProductType updatedProductType = client().executeBlocking(ProductTypeUpdateCommand.of(productType, ChangeAttributeDefinitionLabel.of(attributeName, label)));

            assertThat(updatedProductType.getAttribute(attributeName).getLabel()).isEqualTo(label);

            return updatedProductType;
        });
    }

    @Test
    public void addEnumValue() throws Exception {
        withUpdateableProductType(client(), productType -> {
            final String attributeName = "size";
            assertThat(productType.findAttribute(attributeName)).isPresent();
            final EnumValue value = EnumValue.of("XXXL", "XXXL");

            final ProductType updatedProductType = client().executeBlocking(ProductTypeUpdateCommand.of(productType,
                    AddEnumValue.of(attributeName, value)));

            assertThat(updatedProductType.getAttribute(attributeName).getAttributeType())
                    .isInstanceOf(EnumAttributeType.class)
                    .matches(type -> ((EnumAttributeType) type).getValues().contains(value));

            return updatedProductType;
        });
    }


    @Test
    public void addLocalizedEnumValue() throws Exception {
        withUpdateableProductType(client(), productType -> {
            final String attributeName = "color";
            assertThat(productType.getAttribute(attributeName)).isNotNull();
            final LocalizedEnumValue value =
                    LocalizedEnumValue.of("brown", LocalizedString.of(Locale.ENGLISH, "brown").plus(GERMAN, "braun"));


            final ProductType updatedProductType = client().executeBlocking(ProductTypeUpdateCommand.of(productType,
                    AddLocalizedEnumValue.of(attributeName, value)));

            assertThat(updatedProductType.getAttribute(attributeName).getAttributeType())
                    .isInstanceOf(LocalizedEnumAttributeType.class)
                    .matches(type -> ((LocalizedEnumAttributeType) type).getValues().contains(value));

            return updatedProductType;
        });
    }

    @Test
    public void removeEnumValuesByKey() throws Exception {
        withUpdateableProductType(client(), productType -> {
            final String attributeName = "size";

            final ProductType updatedProductType = client().executeBlocking(ProductTypeUpdateCommand.of(productType,
                    RemoveEnumValues.of(attributeName, Arrays.asList("S", "X"))));

            assertThat(updatedProductType.getAttribute(attributeName).getAttributeType())
                    .isInstanceOf(EnumAttributeType.class)
                    .matches(type -> ((EnumAttributeType) type).getValues().size() == 1);

            return updatedProductType;
        });
    }

    @Test
    public void removeEnumValues() throws Exception {
        withUpdateableProductType(client(), productType -> {
            final String attributeName = Sizes.ATTRIBUTE.getName();

            final ProductType updatedProductType = client().executeBlocking(ProductTypeUpdateCommand.of(productType,
                    RemoveEnumValues.ofEnumValue(attributeName, Arrays.asList(Sizes.S, Sizes.X))));

            assertThat(updatedProductType.getAttribute(attributeName).getAttributeType())
                    .isInstanceOf(EnumAttributeType.class)
                    .matches(type -> ((EnumAttributeType) type).getValues().size() == 1);

            return updatedProductType;
        });
    }

    @Test
    public void removeLocalizedEnumValues() throws Exception {
        withUpdateableProductType(client(), productType -> {
            final String attributeName = Colors.ATTRIBUTE.getName();

            final ProductType updatedProductType = client().executeBlocking(ProductTypeUpdateCommand.of(productType,
                    RemoveEnumValues.ofLocalizedEnumValue(attributeName, Arrays.asList(Colors.GREEN, Colors.RED))));

            assertThat(updatedProductType.getAttribute(attributeName).getAttributeType())
                    .isInstanceOf(LocalizedEnumAttributeType.class)
                    .matches(type -> ((LocalizedEnumAttributeType) type).getValues().isEmpty());

            return updatedProductType;
        });
    }

    @Test
    public void changeAttributeOrder() throws Exception {
        withUpdateableProductType(client(), productType -> {

            final List<AttributeDefinition> attributeDefinitions = reverse(productType.getAttributes());
            final ProductType updatedProductType = client().executeBlocking(ProductTypeUpdateCommand.of(productType, ChangeAttributeOrder.of(attributeDefinitions)));

            assertThat(updatedProductType.getAttributes()).isEqualTo(attributeDefinitions);

            return updatedProductType;
        });
    }

    @Test
    public void changeAttributeOrderByName() {
        withUpdateableProductType(client(), productType -> {

            final List<AttributeDefinition> attributeDefinitions = reverse(productType.getAttributes());
            final List<String> attributeNames = attributeDefinitions.stream()
                    .map(AttributeDefinition::getName)
                    .collect(Collectors.toList());

            final ProductType updatedProductType = client().executeBlocking(ProductTypeUpdateCommand.of(productType, ChangeAttributeOrderByName.of(attributeNames)));

            assertThat(updatedProductType.getAttributes()).isEqualTo(attributeDefinitions);

            return updatedProductType;
        });
    }

    @Test
    public void changeEnumValueOrder() throws Exception {
        withUpdateableProductType(client(), productType -> {
            final String attributeName = "size";
            final EnumAttributeType attributeType = (EnumAttributeType) productType.getAttribute(attributeName)
                    .getAttributeType();
            final List<EnumValue> values = reverse(attributeType.getValues());

            final ProductType updatedProductType = client().executeBlocking(ProductTypeUpdateCommand.of(productType,
                    ChangeEnumValueOrder.of(attributeName, values)));

            final EnumAttributeType updatedType = (EnumAttributeType) updatedProductType
                    .getAttribute(attributeName).getAttributeType();
            assertThat(updatedType.getValues()).isEqualTo(values);

            return updatedProductType;
        });
    }

    @Test
    public void changeLocalizedEnumValueOrder() throws Exception {
        withUpdateableProductType(client(), productType -> {
            final String attributeName = "color";
            final LocalizedEnumAttributeType attributeType = (LocalizedEnumAttributeType) productType.getAttribute(attributeName)
                    .getAttributeType();
            final List<LocalizedEnumValue> values = reverse(attributeType.getValues());

            final ProductType updatedProductType = client().executeBlocking(ProductTypeUpdateCommand.of(productType,
                    ChangeLocalizedEnumValueOrder.of(attributeName, values)));

            final LocalizedEnumAttributeType updatedType = (LocalizedEnumAttributeType) updatedProductType.getAttribute(attributeName)
                    .getAttributeType();
            assertThat(updatedType.getValues()).isEqualTo(values);

            return updatedProductType;
        });
    }

    @Test
    public void changeIsSearchable() throws Exception {
        final String key = randomKey();
        final String attributeName = "stringattribute";
        final AttributeDefinition attributeDefinition = AttributeDefinitionBuilder
                .of(attributeName, randomSlug(), StringAttributeType.of())
                .isSearchable(false)
                .build();
        final List<AttributeDefinition> attributes = singletonList(attributeDefinition);
        withUpdateableProductType(client(), () -> ProductTypeDraft.of(key, key, key, attributes), productType -> {
            assertThat(productType.getAttribute(attributeName).isSearchable()).isFalse();

            final ProductTypeUpdateCommand cmd =
                    ProductTypeUpdateCommand.of(productType, ChangeIsSearchable.of(attributeName, true));
            final ProductType updatedProductType = client().executeBlocking(cmd);

            assertThat(updatedProductType.getAttribute(attributeName).isSearchable()).isTrue();

            return updatedProductType;
        });
    }

    @Test
    public void changeInputHint() throws Exception {
        final String key = randomKey();
        final String attributeName = "stringAttribute";
        final AttributeDefinition attributeDefinition = AttributeDefinitionBuilder
                .of(attributeName, randomSlug(), StringAttributeType.of())
                .inputHint(TextInputHint.SINGLE_LINE)
                .build();
        final List<AttributeDefinition> attributes = singletonList(attributeDefinition);
        withUpdateableProductType(client(), () -> ProductTypeDraft.of(key, key, key, attributes), productType -> {
            assertThat(productType.getAttribute(attributeName).getInputHint()).isEqualTo(TextInputHint.SINGLE_LINE);

            final ProductTypeUpdateCommand cmd =
                    ProductTypeUpdateCommand.of(productType, ChangeInputHint.of(attributeName, TextInputHint.MULTI_LINE));
            final ProductType updatedProductType = client().executeBlocking(cmd);

            assertThat(updatedProductType.getAttribute(attributeName).getInputHint()).isEqualTo(TextInputHint.MULTI_LINE);

            return updatedProductType;
        });
    }

    @Test
    public void setKey() throws Exception {
        withUpdateableProductType(client(), productType -> {
            final String newKey = randomKey();
            final ProductType updatedProductType =
                    client().executeBlocking(ProductTypeUpdateCommand.of(productType, SetKey.of(newKey)));
            assertThat(updatedProductType.getKey()).isEqualTo(newKey);
            return updatedProductType;
        });
    }

    @Test
    public void changePlainEnumValueLabel() throws Exception {
        final String attributeName = randomKey();
        final AttributeDefinition attributeDefinition = AttributeDefinitionBuilder.of(attributeName, randomSlug(),
                EnumAttributeType.of(
                        EnumValue.of("key1", "label 1"),
                        EnumValue.of("key2", "label 2")
                )).build();
        final String key = randomKey();
        final ProductTypeDraft productTypeDraft = ProductTypeDraft.of(key, key, key, singletonList(attributeDefinition));
        withUpdateableProductType(client(), () -> productTypeDraft, productType -> {
            final ProductType updatedProductType = client().executeBlocking(ProductTypeUpdateCommand.of(productType, ChangePlainEnumValueLabel.of(attributeName, EnumValue.of("key2", "label 2 (updated)"))));

            final EnumAttributeType updatedAttributeType = (EnumAttributeType) updatedProductType.getAttribute(attributeName).getAttributeType();
            assertThat(updatedAttributeType.getValues())
                    .containsExactly(EnumValue.of("key1", "label 1"), EnumValue.of("key2", "label 2 (updated)"));

            return updatedProductType;
        });
    }

    @Test
    public void changeLocalizedEnumValueLabel() throws Exception {
        final String attributeName = randomKey();
        final LocalizedString label1 = LocalizedString.ofEnglish("label 1");
        final LocalizedString label2 = LocalizedString.ofEnglish("label 2");
        final LocalizedString newLabel2 = LocalizedString.ofEnglish("label 2 (updated)");
        final AttributeDefinition attributeDefinition = AttributeDefinitionBuilder.of(attributeName, randomSlug(),
                LocalizedEnumAttributeType.of(
                        LocalizedEnumValue.of("key1", label1),
                        LocalizedEnumValue.of("key2", label2)
                )).build();
        final String key = randomKey();
        final ProductTypeDraft productTypeDraft = ProductTypeDraft.of(key, key, key, singletonList(attributeDefinition));
        withUpdateableProductType(client(), () -> productTypeDraft, productType -> {
            final ProductType updatedProductType = client().executeBlocking(ProductTypeUpdateCommand.of(productType, ChangeLocalizedEnumValueLabel.of(attributeName, LocalizedEnumValue.of("key2", newLabel2))));

            final LocalizedEnumAttributeType updatedAttributeType =
                    (LocalizedEnumAttributeType) updatedProductType.getAttribute(attributeName).getAttributeType();
            assertThat(updatedAttributeType.getValues())
                    .containsExactly(LocalizedEnumValue.of("key1", label1), LocalizedEnumValue.of("key2", newLabel2));

            return updatedProductType;
        });
    }


    @Test
    public void changeEnumValueKey(){
        withUpdateableProductType(client(), productType -> {
            final String attributeName = "color";
            final LocalizedEnumAttributeType attributeType = (LocalizedEnumAttributeType) productType.getAttribute(attributeName)
                    .getAttributeType();
            assertThat(attributeType.getValues()).contains(LocalizedEnumValue.of("red", LocalizedString.of(ENGLISH,"red",GERMAN,"rot")));
            final ProductType updatedProductType = client().executeBlocking(ProductTypeUpdateCommand.of(productType, ChangeEnumKey.of(attributeName, "red","rouge")));
            final LocalizedEnumAttributeType updatedAttributeType = (LocalizedEnumAttributeType) updatedProductType.getAttribute(attributeName)
                    .getAttributeType();
            assertThat(updatedAttributeType.getValues()).contains(LocalizedEnumValue.of("rouge", LocalizedString.of(ENGLISH,"red",GERMAN,"rot")));
            return updatedProductType;
        });
    }

    @Test
    public void changAttributeDefinitionName(){
        withUpdateableProductType(client(), productType -> {
            final String attributeName = "color";
            final String newAttributeName = "couleur";
            assertThat(productType.getAttribute(attributeName)).isNotNull();
            assertThat(productType.getAttribute(newAttributeName)).isNull();
            final ProductType updatedProductType = client().executeBlocking(ProductTypeUpdateCommand.of(productType, ChangeAttributeName.of(attributeName,newAttributeName)));
            assertThat(updatedProductType.getAttribute(attributeName)).isNull();
            assertThat(updatedProductType.getAttribute(newAttributeName)).isNotNull();
            return updatedProductType;
        });
    }
}