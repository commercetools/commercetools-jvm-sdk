package io.sphere.sdk.customobjects.queries;

import io.sphere.sdk.customobjects.CustomObject;
import io.sphere.sdk.customobjects.CustomObjectFixtures;
import io.sphere.sdk.customobjects.demo.GsonFoo;
import io.sphere.sdk.customobjects.demo.GsonFooCustomObjectByKeyFetch;
import io.sphere.sdk.queries.Fetch;
import io.sphere.sdk.test.IntegrationTest;

import org.junit.Test;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

public class CustomObjectCustomJsonMappingByKeyFetchTest extends IntegrationTest {
    @Test
    public void execution() throws Exception {
        CustomObjectFixtures.withCustomObject(client(), co -> {
            final Fetch<CustomObject<GsonFoo>> fetch = new GsonFooCustomObjectByKeyFetch(co.getContainer(), co.getKey());
            final CustomObject<GsonFoo> customObject = execute(fetch);
            assertThat(customObject).isNotNull();
            assertThat(customObject.toReference()).isEqualTo(co.toReference());
        });
    }

    @Test
    public void executionWithAbsent() throws Exception {
        final Fetch<CustomObject<GsonFoo>> fetch = new GsonFooCustomObjectByKeyFetch("NOTPRESENT", "NOTPRESENT");
        final CustomObject<GsonFoo> customObject = execute(fetch);
        assertThat(customObject).isNull();
    }
}