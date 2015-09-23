package io.sphere.sdk.types.commands;

import io.sphere.sdk.models.LocalizedString;
import io.sphere.sdk.models.TextInputHint;
import io.sphere.sdk.test.IntegrationTest;
import io.sphere.sdk.types.FieldDefinition;
import io.sphere.sdk.types.StringType;
import io.sphere.sdk.types.Type;
import io.sphere.sdk.types.commands.updateactions.*;
import org.junit.Test;

import static io.sphere.sdk.test.SphereTestUtils.*;
import static io.sphere.sdk.types.TypeFixtures.withUpdateableType;
import static org.assertj.core.api.Assertions.assertThat;

public class TypeUpdateCommandTest extends IntegrationTest {
    @Test
    public void changeName() {
        withUpdateableType(client(), type -> {
            final LocalizedString newName = randomSlug();
            final Type updatedType = execute(TypeUpdateCommand.of(type, ChangeName.of(newName)));
            assertThat(updatedType.getName()).isEqualTo(newName);
            return updatedType;
        });
    }

    @Test
    public void setDescription() {
        withUpdateableType(client(), type -> {
            final LocalizedString newDescription = randomSlug();
            final Type updatedType = execute(TypeUpdateCommand.of(type, SetDescription.of(newDescription)));
            assertThat(updatedType.getDescription()).isEqualTo(newDescription);
            return updatedType;
        });
    }

    @Test
    public void addFieldDefinition() {
        withUpdateableType(client(), type -> {
            final String name = randomKey();
            final FieldDefinition fieldDefinition = FieldDefinition.of(StringType.of(), name, en("label"), false, TextInputHint.SINGLE_LINE);
            final Type updatedType = execute(TypeUpdateCommand.of(type, AddFieldDefinition.of(fieldDefinition)));
            assertThat(updatedType.getFieldDefinitions().get(1)).isEqualTo(fieldDefinition);
            assertThat(updatedType.getFieldDefinitions()).hasSize(type.getFieldDefinitions().size() + 1);

            final Type updated2 = execute(TypeUpdateCommand.of(updatedType, RemoveFieldDefinition.of(name)));
            assertThat(updated2.getFieldDefinitions()).hasSize(type.getFieldDefinitions().size());

            return updated2;
        });
    }

    @Test
    public void changeLabel() {
        withUpdateableType(client(), type -> {
            final LocalizedString newLabel = randomSlug();
            final String name = type.getFieldDefinitions().get(0).getName();
            final Type updatedType = execute(TypeUpdateCommand.of(type, ChangeFieldDefinitionLabel.of(name, newLabel)));
            assertThat(updatedType.getFieldDefinitions().get(0).getLabel()).isEqualTo(newLabel);
            return updatedType;
        });
    }
}