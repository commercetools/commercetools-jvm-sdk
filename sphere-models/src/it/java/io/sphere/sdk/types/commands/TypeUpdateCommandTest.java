package io.sphere.sdk.types.commands;

import io.sphere.sdk.models.LocalizedString;
import io.sphere.sdk.test.IntegrationTest;
import io.sphere.sdk.types.Type;
import io.sphere.sdk.types.commands.updateactions.*;
import org.junit.Test;

import static io.sphere.sdk.test.SphereTestUtils.randomSlug;
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
}