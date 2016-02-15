package io.sphere.sdk.zones.commands;

import com.neovisionaries.i18n.CountryCode;
import io.sphere.sdk.test.IntegrationTest;
import io.sphere.sdk.zones.Location;
import io.sphere.sdk.zones.Zone;
import io.sphere.sdk.zones.ZoneFixtures;
import io.sphere.sdk.zones.commands.updateactions.AddLocation;
import io.sphere.sdk.zones.commands.updateactions.ChangeName;
import io.sphere.sdk.zones.commands.updateactions.RemoveLocation;
import io.sphere.sdk.zones.commands.updateactions.SetDescription;
import org.junit.BeforeClass;
import org.junit.Test;

import static io.sphere.sdk.test.SphereTestUtils.randomString;
import static org.assertj.core.api.Assertions.assertThat;

public class ZoneUpdateCommandIntegrationTest extends IntegrationTest {
    @BeforeClass
    public static void deleteRemainingZone() throws Exception {
        ZoneFixtures.deleteZonesForCountries(client(), CountryCode.AM, CountryCode.AN, CountryCode.AO, CountryCode.AQ);
    }

    @Test
    public void changeName() throws Exception {
        ZoneFixtures.withUpdateableZone(client(), zone -> {
            final String newName = randomString();
            assertThat(zone.getName()).isNotEqualTo(newName);
            final ZoneUpdateCommand command = ZoneUpdateCommand.of(zone, ChangeName.of(newName));
            final Zone updatedZone = client().executeBlocking(command);
            assertThat(updatedZone.getName()).isEqualTo(newName);
            return updatedZone;
        }, CountryCode.AM);
    }

    @Test
    public void setDescription() throws Exception {
        ZoneFixtures.withUpdateableZone(client(), zone -> {
            final String newDescription = randomString();
            assertThat(zone.getDescription()).isNotEqualTo(newDescription);
            final ZoneUpdateCommand command = ZoneUpdateCommand.of(zone, SetDescription.of(newDescription));
            final Zone updatedZone = client().executeBlocking(command);
            assertThat(updatedZone.getDescription()).isEqualTo(newDescription);
            return updatedZone;
        }, CountryCode.AN);
    }

    @Test
    public void addLocationAndRemoveLocation() throws Exception {
        ZoneFixtures.withUpdateableZone(client(), zone -> {
            //adding a location
            final Location newLocation = Location.of(CountryCode.AQ, "state");
            assertThat(zone.getLocations().contains(newLocation)).isFalse();
            final ZoneUpdateCommand addCommand = ZoneUpdateCommand.of(zone, AddLocation.of(newLocation));
            final Zone zoneWithNewLocation = client().executeBlocking(addCommand);
            assertThat(zoneWithNewLocation.getLocations()).contains(newLocation);

            //removing a location
            final ZoneUpdateCommand removeCommand = ZoneUpdateCommand.of(zoneWithNewLocation, RemoveLocation.of(newLocation));
            final Zone zoneWithoutNewLocation = client().executeBlocking(removeCommand);
            assertThat(zoneWithoutNewLocation.getLocations().contains(newLocation)).isFalse();

            return zoneWithoutNewLocation;
        }, CountryCode.AO);
    }
}