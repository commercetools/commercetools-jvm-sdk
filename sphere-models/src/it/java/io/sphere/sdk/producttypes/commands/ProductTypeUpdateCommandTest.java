package io.sphere.sdk.producttypes.commands;

import io.sphere.sdk.attributes.*;
import io.sphere.sdk.models.EnumValue;
import io.sphere.sdk.models.LocalizedEnumValue;
import io.sphere.sdk.models.LocalizedString;
import io.sphere.sdk.producttypes.ProductType;
import io.sphere.sdk.producttypes.commands.updateactions.*;
import io.sphere.sdk.test.IntegrationTest;
import io.sphere.sdk.utils.ListUtils;
import org.junit.Test;

import java.util.List;
import java.util.Locale;

import static io.sphere.sdk.producttypes.ProductTypeFixtures.*;
import static io.sphere.sdk.test.SphereTestUtils.ENGLISH;
import static io.sphere.sdk.test.SphereTestUtils.randomKey;
import static java.util.Locale.GERMAN;
import static org.assertj.core.api.Assertions.assertThat;

public class ProductTypeUpdateCommandTest extends IntegrationTest {
    @Test
    public void changeName() throws Exception {
        withUpdateableProductType(client(), productType -> {
            final String name = randomKey();
            final ProductType updatedProductType =
                    execute(ProductTypeUpdateCommand.of(productType, ChangeName.of(name)));
            assertThat(updatedProductType.getName()).isEqualTo(name);
            return updatedProductType;
        });
    }

    @Test
    public void changeDescription() throws Exception {
        withUpdateableProductType(client(), productType -> {
            final String description = randomKey();
            final ProductType updatedProductType =
                    execute(ProductTypeUpdateCommand.of(productType, ChangeDescription.of(description)));
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
                    AttributeDefinitionBuilder.of(attributeName, LocalizedString.of(ENGLISH, "foo string"), TextType.of()).build();
            final ProductType withFoostring = execute(ProductTypeUpdateCommand.of(productType, AddAttributeDefinition.of(foostring)));
            final AttributeDefinition loadedDefinition = withFoostring.getAttribute(attributeName);
            assertThat(loadedDefinition.getAttributeType()).isEqualTo(TextType.of());

            //remove
            final ProductType withoutFoostring = execute(ProductTypeUpdateCommand.of(withFoostring, RemoveAttributeDefinition.of(attributeName)));
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

            final ProductType updatedProductType = execute(ProductTypeUpdateCommand.of(productType, ChangeAttributeDefinitionLabel.of(attributeName, label)));

            assertThat(updatedProductType.getAttribute(attributeName).getLabel()).isEqualTo(label);

            return updatedProductType;
        });
    }

    @Test
    public void addPlainEnumValue() throws Exception {
        withUpdateableProductType(client(), productType -> {
            final String attributeName = "size";
            assertThat(productType.findAttribute(attributeName)).isPresent();
            final EnumValue value = EnumValue.of("XXXL", "XXXL");

            final ProductType updatedProductType = execute(ProductTypeUpdateCommand.of(productType,
                    AddPlainEnumValue.of(attributeName, value)));

            assertThat(updatedProductType.getAttribute(attributeName).getAttributeType())
                    .isInstanceOf(EnumType.class)
                    .matches(type -> ((EnumType)type).getValues().contains(value));

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


            final ProductType updatedProductType = execute(ProductTypeUpdateCommand.of(productType,
                    AddLocalizedEnumValue.of(attributeName, value)));

            assertThat(updatedProductType.getAttribute(attributeName).getAttributeType())
                    .isInstanceOf(LocalizedEnumType.class)
                    .matches(type -> ((LocalizedEnumType)type).getValues().contains(value));

            return updatedProductType;
        });
    }

    @Test
    public void changeAttributeOrder() throws Exception {
        withUpdateableProductType(client(), productType -> {

            final List<AttributeDefinition> attributeDefinitions = ListUtils.reverse(productType.getAttributes());
            final ProductType updatedProductType = execute(ProductTypeUpdateCommand.of(productType, ChangeAttributeOrder.of(attributeDefinitions)));

            assertThat(updatedProductType.getAttributes()).isEqualTo(attributeDefinitions);

            return updatedProductType;
        });
    }

    @Test
    public void changePlainEnumValueOrder() throws Exception {
        withUpdateableProductType(client(), productType -> {
            final String attributeName = "size";
            final EnumType attributeType = (EnumType) productType.getAttribute(attributeName)
                    .getAttributeType();
            final List<EnumValue> values = ListUtils.reverse(attributeType.getValues());

            final ProductType updatedProductType = execute(ProductTypeUpdateCommand.of(productType,
                    ChangePlainEnumValueOrder.of(attributeName, values)));

            final EnumType updatedType = (EnumType) updatedProductType
                    .getAttribute(attributeName).getAttributeType();
            assertThat(updatedType.getValues()).isEqualTo(values);

            return updatedProductType;
        });
    }

    @Test
    public void changeLocalizedEnumValueOrder() throws Exception {
        withUpdateableProductType(client(), productType -> {
            final String attributeName = "color";
            final LocalizedEnumType attributeType = (LocalizedEnumType) productType.getAttribute(attributeName)
                    .getAttributeType();
            final List<LocalizedEnumValue> values = ListUtils.reverse(attributeType.getValues());

            final ProductType updatedProductType = execute(ProductTypeUpdateCommand.of(productType,
                    ChangeLocalizedEnumValueOrder.of(attributeName, values)));

            final LocalizedEnumType updatedType = (LocalizedEnumType) updatedProductType.getAttribute(attributeName)
                    .getAttributeType();
            assertThat(updatedType.getValues()).isEqualTo(values);

            return updatedProductType;
        });
    }
}