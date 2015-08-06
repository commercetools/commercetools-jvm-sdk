package io.sphere.sdk.customobjects.queries;

import io.sphere.sdk.customobjects.CustomObject;
import io.sphere.sdk.customobjects.CustomObjectFixtures;
import io.sphere.sdk.customobjects.demo.GsonFoo;
import io.sphere.sdk.customobjects.demo.GsonFooCustomObjectByKeyGet;
import io.sphere.sdk.queries.Get;
import io.sphere.sdk.test.IntegrationTest;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class CustomObjectCustomJsonMappingByKeyGetTest extends IntegrationTest {
    @Test
    public void execution() throws Exception {
        CustomObjectFixtures.withCustomObject(client(), co -> {
            final Get<CustomObject<GsonFoo>> fetch = new GsonFooCustomObjectByKeyGet(co.getContainer(), co.getKey());
            final CustomObject<GsonFoo> customObject = execute(fetch);
            assertThat(customObject).isNotNull();
            assertThat(customObject.toReference()).isEqualTo(co.toReference());
        });
    }

    @Test
    public void executionWithAbsent() throws Exception {
        final Get<CustomObject<GsonFoo>> fetch = new GsonFooCustomObjectByKeyGet("NOTPRESENT", "NOTPRESENT");
        final CustomObject<GsonFoo> customObject = execute(fetch);
        assertThat(customObject).isNull();
    }
}