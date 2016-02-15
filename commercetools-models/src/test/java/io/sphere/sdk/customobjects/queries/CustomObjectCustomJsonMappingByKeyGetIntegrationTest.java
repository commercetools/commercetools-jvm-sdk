package io.sphere.sdk.customobjects.queries;

import io.sphere.sdk.customobjects.CustomObject;
import io.sphere.sdk.customobjects.CustomObjectFixtures;
import io.sphere.sdk.customobjects.demo.GsonFoo;
import io.sphere.sdk.customobjects.demo.GsonFooCustomObjectByKeyGet;
import io.sphere.sdk.queries.Get;
import io.sphere.sdk.test.IntegrationTest;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class CustomObjectCustomJsonMappingByKeyGetIntegrationTest extends IntegrationTest {
    @Test
    public void execution() throws Exception {
        CustomObjectFixtures.withCustomObject(client(), co -> {
            final String container = co.getContainer();
            final String key = co.getKey();
            final Get<CustomObject<GsonFoo>> fetch = new GsonFooCustomObjectByKeyGet(container, key);
            final CustomObject<GsonFoo> customObject = client().executeBlocking(fetch);
            assertThat(customObject).isNotNull();
            assertThat(customObject.toReference()).isEqualTo(co.toReference());
            final GsonFoo value = customObject.getValue();
            assertThat(value).isEqualTo(new GsonFoo("aString", 5L));
        });
    }

    @Test
    public void executionWithAbsent() throws Exception {
        final Get<CustomObject<GsonFoo>> fetch = new GsonFooCustomObjectByKeyGet("NOTPRESENT", "NOTPRESENT");
        final CustomObject<GsonFoo> customObject = client().executeBlocking(fetch);
        assertThat(customObject).isNull();
    }
}