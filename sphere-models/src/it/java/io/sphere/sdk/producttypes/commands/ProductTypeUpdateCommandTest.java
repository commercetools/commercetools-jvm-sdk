package io.sphere.sdk.producttypes.commands;

import io.sphere.sdk.attributes.*;
import io.sphere.sdk.models.LocalizedStrings;
import io.sphere.sdk.models.PlainEnumValue;
import io.sphere.sdk.producttypes.ProductType;
import io.sphere.sdk.producttypes.commands.updateactions.*;
import io.sphere.sdk.test.IntegrationTest;
import org.junit.Test;

import static io.sphere.sdk.producttypes.ProductTypeFixtures.*;
import static io.sphere.sdk.test.SphereTestUtils.ENGLISH;
import static io.sphere.sdk.test.SphereTestUtils.randomKey;
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
                    AttributeDefinitionBuilder.of(attributeName, LocalizedStrings.of(ENGLISH, "foo string"), TextType.of()).build();
            final ProductType withFoostring = execute(ProductTypeUpdateCommand.of(productType, AddAttributeDefinition.of(foostring)));
            final AttributeDefinition loadedDefinition = withFoostring.getAttribute(attributeName).get();
            assertThat(loadedDefinition.getAttributeType()).isEqualTo(TextType.of());

            //remove
            final ProductType withoutFoostring = execute(ProductTypeUpdateCommand.of(withFoostring, RemoveAttributeDefinition.of(attributeName)));
            assertThat(withoutFoostring.getAttribute(attributeName)).isEmpty();
            return withoutFoostring;
        });
    }

    @Test
    public void changeLabel() throws Exception {
        withUpdateableProductType(client(), productType -> {
            final String attributeName = "color";
            assertThat(productType.getAttribute(attributeName)).isPresent();
            final LocalizedStrings label = LocalizedStrings.of(ENGLISH, "the color label");

            final ProductType updatedProductType = execute(ProductTypeUpdateCommand.of(productType, ChangeAttributeDefinitionLabel.of(attributeName, label)));

            assertThat(updatedProductType.getAttribute(attributeName).get().getLabel()).isEqualTo(label);

            return updatedProductType;
        });
    }

    @Test
    public void addPlainEnumValue() throws Exception {
        withUpdateableProductType(client(), productType -> {
            final String attributeName = "size";
            final PlainEnumValue value = PlainEnumValue.of("XXXL", "XXXL");
            assertThat(productType.getAttribute(attributeName)).isPresent();

            final ProductType updatedProductType = execute(ProductTypeUpdateCommand.of(productType, AddPlainEnumValue.of(attributeName, value)));

            assertThat(updatedProductType.getAttribute(attributeName).get().getAttributeType())
                    .isInstanceOf(EnumType.class)
                    .matches(type -> ((EnumType)type).getValues().contains(value));

            return updatedProductType;
        });
    }
}