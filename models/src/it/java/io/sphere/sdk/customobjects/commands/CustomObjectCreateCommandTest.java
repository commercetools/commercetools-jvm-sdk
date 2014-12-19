package io.sphere.sdk.customobjects.commands;

import io.sphere.sdk.customobjects.CustomObjectFixtures;
import io.sphere.sdk.test.IntegrationTest;
import org.junit.Test;

import static org.junit.Assert.*;

public class CustomObjectCreateCommandTest extends IntegrationTest {
    @Test
    public void execution() throws Exception {
        CustomObjectFixtures.withCustomObject(client(), co -> {

        });
    }
}