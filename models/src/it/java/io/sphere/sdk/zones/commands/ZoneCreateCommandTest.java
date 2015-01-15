package io.sphere.sdk.zones.commands;


import io.sphere.sdk.test.IntegrationTest;
import io.sphere.sdk.zones.ZonesFixtures;
import org.junit.Test;

public class ZoneCreateCommandTest extends IntegrationTest {
    @Test
    public void execution() throws Exception {
        ZonesFixtures.withZone(client(), zone -> {});
    }
}