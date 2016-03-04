package io.sphere.sdk.producttypes.commands;

import io.sphere.sdk.models.EnumValue;
import io.sphere.sdk.models.LocalizedEnumValue;
import io.sphere.sdk.models.LocalizedString;
import io.sphere.sdk.products.attributes.*;
import io.sphere.sdk.producttypes.ProductType;
import io.sphere.sdk.producttypes.ProductTypeDraft;
import io.sphere.sdk.producttypes.commands.updateactions.*;
import io.sphere.sdk.test.IntegrationTest;
import org.junit.Test;

import java.util.List;
import java.util.Locale;

import static io.sphere.sdk.producttypes.ProductTypeFixtures.withUpdateableProductType;
import static io.sphere.sdk.test.SphereTestUtils.*;
import static io.sphere.sdk.utils.SphereInternalUtils.reverse;
import static java.util.Collections.singletonList;
import static java.util.Locale.GERMAN;
import static org.assertj.core.api.Assertions.assertThat;

public class ProductTypeUpdateCommandIntegrationTest extends IntegrationTest {
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
                    .matches(type -> ((EnumAttributeType)type).getValues().contains(value));

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
                    .matches(type -> ((LocalizedEnumAttributeType)type).getValues().contains(value));

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
}