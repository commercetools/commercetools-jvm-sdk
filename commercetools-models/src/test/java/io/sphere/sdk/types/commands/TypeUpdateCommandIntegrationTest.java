package io.sphere.sdk.types.commands;

import io.sphere.sdk.models.EnumValue;
import io.sphere.sdk.models.LocalizedEnumValue;
import io.sphere.sdk.models.LocalizedString;
import io.sphere.sdk.models.TextInputHint;
import io.sphere.sdk.test.IntegrationTest;
import io.sphere.sdk.test.SphereTestUtils;
import io.sphere.sdk.types.*;
import io.sphere.sdk.types.commands.updateactions.*;
import org.junit.Test;

import java.util.List;
import java.util.stream.Collectors;

import static io.sphere.sdk.test.SphereTestUtils.*;
import static io.sphere.sdk.types.TypeFixtures.withUpdateableType;
import static io.sphere.sdk.utils.SphereInternalUtils.reverse;
import static org.assertj.core.api.Assertions.assertThat;

public class TypeUpdateCommandIntegrationTest extends IntegrationTest {
    @Test
    public void changeName() {
        withUpdateableType(client(), type -> {
            final LocalizedString newName = randomSlug();
            final Type updatedType = client().executeBlocking(TypeUpdateCommand.of(type, ChangeName.of(newName)));
            assertThat(updatedType.getName()).isEqualTo(newName);
            return updatedType;
        });
    }

    @Test
    public void setDescription() {
        withUpdateableType(client(), type -> {
            final LocalizedString newDescription = randomSlug();
            final Type updatedType = client().executeBlocking(TypeUpdateCommand.of(type, SetDescription.of(newDescription)));
            assertThat(updatedType.getDescription()).isEqualTo(newDescription);
            return updatedType;
        });
    }

    @Test
    public void addFieldDefinition() {
        withUpdateableType(client(), type -> {
            final String name = randomKey();
            final FieldDefinition fieldDefinition = FieldDefinition.of(StringFieldType.of(), name, en("label"), false, TextInputHint.SINGLE_LINE);
            final Type updatedType = client().executeBlocking(TypeUpdateCommand.of(type, AddFieldDefinition.of(fieldDefinition)));
            assertThat(updatedType.getFieldDefinitionByName(name)).isEqualTo(fieldDefinition);
            assertThat(updatedType.getFieldDefinitions()).hasSize(type.getFieldDefinitions().size() + 1);

            final Type updated2 = client().executeBlocking(TypeUpdateCommand.of(updatedType, RemoveFieldDefinition.of(name)));
            assertThat(updated2.getFieldDefinitions()).hasSize(type.getFieldDefinitions().size());

            return updated2;
        });
    }

    @Test
    public void changeLabel() {
        withUpdateableType(client(), type -> {
            final LocalizedString newLabel = randomSlug();
            final String name = type.getFieldDefinitions().get(0).getName();
            final Type updatedType = client().executeBlocking(TypeUpdateCommand.of(type, ChangeFieldDefinitionLabel.of(name, newLabel)));
            assertThat(updatedType.getFieldDefinitionByName(name).getLabel()).isEqualTo(newLabel);
            return updatedType;
        });
    }

    @Test
    public void changeFieldDefinitionOrder() {
        withUpdateableType(client(), type -> {
            final List<String> originalFieldDefinitionNames = type.getFieldDefinitions()
                    .stream()
                    .map(fieldDefinition -> fieldDefinition.getName())
                    .collect(Collectors.toList());
            List<String> values = reverse(originalFieldDefinitionNames);
            final Type updatedType = client().executeBlocking(TypeUpdateCommand.of(type, ChangeFieldDefinitionOrder.of(values)));
            final List<String> updatedValues = updatedType.getFieldDefinitions()
                    .stream()
                    .map(fieldDefinition -> fieldDefinition.getName())
                    .collect(Collectors.toList());
            assertThat(updatedValues).isEqualTo(values);
            return updatedType;
        });
    }

    @Test
    public void addEnumValue() {
        withUpdateableType(client(), type -> {
            final String name = TypeFixtures.ENUM_FIELD_NAME;
            final EnumValue newEnumValue = EnumValue.of("key-new", "label new");
            final Type updatedType = client().executeBlocking(TypeUpdateCommand.of(type, AddEnumValue.of(name, newEnumValue)));
            assertThat(updatedType.getFieldDefinitionByName(name).getType())
                    .isInstanceOf(EnumFieldType.class)
                    .matches(fieldType -> ((EnumFieldType) fieldType).getValues().contains(newEnumValue), "contains the new enum value");
            return updatedType;
        });
    }

    @Test
    public void changeEnumValueOrder() {
        withUpdateableType(client(), type -> {
            final String fieldName = TypeFixtures.ENUM_FIELD_NAME;
            final FieldType fieldType = type.getFieldDefinitionByName(fieldName).getType();
            final List<EnumValue> oldEnumValues = ((EnumFieldType) fieldType).getValues();
            final List<String> enumKeys = reverse(oldEnumValues.stream()
                    .map(enumValue -> enumValue.getKey())
                    .collect(Collectors.toList())
            );
            final Type updatedType = client().executeBlocking(TypeUpdateCommand.of(type, ChangeEnumValueOrder.of(fieldName, enumKeys)));
            final FieldType updatedFieldType = updatedType.getFieldDefinitionByName(fieldName).getType();
            final List<EnumValue> newEnumValues = ((EnumFieldType) updatedFieldType).getValues();
            assertThat(newEnumValues).containsAll(oldEnumValues);
            assertThat(newEnumValues.size()).isEqualTo(oldEnumValues.size());
            return updatedType;
        });
    }

    @Test
    public void addLocalizedEnumValue() {
        withUpdateableType(client(), type -> {
            final String name = TypeFixtures.LOCALIZED_ENUM_FIELD_NAME;
            final LocalizedEnumValue newLocalizedEnumValue = LocalizedEnumValue.of("key-new", en("label new"));
            final Type updatedType = client().executeBlocking(TypeUpdateCommand.of(type, AddLocalizedEnumValue.of(name, newLocalizedEnumValue)));
            assertThat(updatedType.getFieldDefinitionByName(name).getType())
                    .isInstanceOf(LocalizedEnumFieldType.class)
                    .matches(fieldType -> ((LocalizedEnumFieldType) fieldType).getValues().contains(newLocalizedEnumValue), "contains the new enum value");
            return updatedType;
        });
    }

    @Test
    public void changeLocalizedEnumValueOrder() {
        withUpdateableType(client(), type -> {
            final String fieldName = TypeFixtures.LOCALIZED_ENUM_FIELD_NAME;
            final FieldType fieldType = type.getFieldDefinitionByName(fieldName).getType();
            final List<LocalizedEnumValue> oldEnumValues = ((LocalizedEnumFieldType) fieldType).getValues();
            final List<String> enumKeys = reverse(oldEnumValues
                    .stream()
                    .map(enumValue -> enumValue.getKey())
                    .collect(Collectors.toList())
            );
            final Type updatedType = client().executeBlocking(TypeUpdateCommand.of(type, ChangeLocalizedEnumValueOrder.of(fieldName, enumKeys)));
            final FieldType updatedFieldType = updatedType.getFieldDefinitionByName(fieldName).getType();
            final List<LocalizedEnumValue> newEnumValues = ((LocalizedEnumFieldType) updatedFieldType).getValues();
            assertThat(newEnumValues).containsAll(oldEnumValues);
            assertThat(newEnumValues.size()).isEqualTo(oldEnumValues.size());
            return updatedType;
        });
    }
    
    @Test
    public void changeInputHint() {
        withUpdateableType(client(), type -> {
            final String fieldName = "string-field-name";
            final TextInputHint textInputHint = TextInputHint.MULTI_LINE;
            final Type updatedType = client().executeBlocking(TypeUpdateCommand.of(type, ChangeInputHint.of(fieldName, textInputHint)));
            assertThat(updatedType).isNotNull();
            assertThat(updatedType.getFieldDefinitionByName(fieldName)).isNotNull();
            assertThat(updatedType.getFieldDefinitionByName(fieldName).getInputHint()).isEqualTo(textInputHint);
            return updatedType;
        });
    }
    
    @Test
    public void changeEnumValueLabel() {
        withUpdateableType(client(), type -> {
            final String fieldName = "enum-field-name";
            final EnumValue value = EnumValue.of("key1", "b");
            final Type updatedType = client().executeBlocking(TypeUpdateCommand.of(type, ChangeEnumValueLabel.of(fieldName, value)));
            assertThat(updatedType).isNotNull();
            assertThat(updatedType.getFieldDefinitionByName(fieldName)).isNotNull();
            assertThat(((EnumFieldType)updatedType.getFieldDefinitionByName(fieldName).getType()).getValues().get(0)).isEqualTo(value);
            return updatedType;
        });
    }
    
    @Test
    public void changeLocalizedEnumValueLabel() {
        withUpdateableType(client(), type -> {
            final String fieldName = "localized-enum-field-name";
            final LocalizedEnumValue localizedEnumValue = LocalizedEnumValue.of("key1", SphereTestUtils.randomLocalizedString());
            final Type updatedType = client().executeBlocking(TypeUpdateCommand.of(type, ChangeLocalizedEnumValueLabel.of(fieldName, localizedEnumValue)));
            assertThat(updatedType).isNotNull();
            assertThat(updatedType.getFieldDefinitionByName(fieldName)).isNotNull();
            assertThat(((LocalizedEnumFieldType)updatedType.getFieldDefinitionByName(fieldName).getType()).getValues().get(0)).isEqualTo(localizedEnumValue);
            return updatedType;
        });
    }
    
    @Test
    public void updateByKey() {
        withUpdateableType(client(), (Type type) -> {
            final LocalizedString newName = randomSlug();
            final String key = type.getKey();
            final Long version = type.getVersion();
            final Type updatedType = client().executeBlocking(TypeUpdateCommand.ofKey(key, version, ChangeName.of(newName)));
            assertThat(updatedType.getName()).isEqualTo(newName);
            return updatedType;
        });
    }

    @Test
    public void changeKey() {
        withUpdateableType(client(), type -> {
            final String key = randomKey();
            final Type updatedType = client().executeBlocking(TypeUpdateCommand.of(type, ChangeKey.of(key)));
            assertThat(updatedType.getKey()).isEqualTo(key);
            return updatedType;
        });
    }
}