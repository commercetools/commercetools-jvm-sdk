package io.sphere.sdk.zones.commands;

import io.sphere.sdk.test.IntegrationTest;
import io.sphere.sdk.zones.Zone;
import io.sphere.sdk.zones.commands.updateactions.ChangeName;
import io.sphere.sdk.zones.commands.updateactions.SetDescription;
import org.junit.Test;

import java.util.Optional;

import static io.sphere.sdk.test.SphereTestUtils.randomString;
import static io.sphere.sdk.zones.ZonesFixtures.withZone;
import static org.fest.assertions.Assertions.assertThat;

public class ZoneUpdateCommandTest extends IntegrationTest {
    @Test
    public void changeName() throws Exception {
        withZone(client(), zone -> {
            final String newName = randomString();
            assertThat(zone.getName()).isNotEqualTo(newName);
            final ZoneUpdateCommand command = ZoneUpdateCommand.of(zone, ChangeName.of(newName));
            final Zone updatedZone = execute(command);
            assertThat(updatedZone.getName()).isEqualTo(newName);
            return updatedZone;
        });
    }

    @Test
    public void setDescription() throws Exception {
        withZone(client(), zone -> {
            final Optional<String> newDescription = Optional.of(randomString());
            assertThat(zone.getDescription()).isNotEqualTo(newDescription);
            final ZoneUpdateCommand command = ZoneUpdateCommand.of(zone, SetDescription.of(newDescription));
            final Zone updatedZone = execute(command);
            assertThat(updatedZone.getDescription()).isEqualTo(newDescription);
            return updatedZone;
        });

    }
}