package io.sphere.sdk.customobjects.queries;

import io.sphere.sdk.customobjects.CustomObject;
import io.sphere.sdk.customobjects.demo.Foo;
import io.sphere.sdk.test.IntegrationTest;
import org.junit.Test;

import static io.sphere.sdk.customobjects.CustomObjectFixtures.withCustomObject;
import static org.assertj.core.api.Assertions.*;

public class CustomObjectByIdGetIntegrationTest extends IntegrationTest {
    @Test
    public void execution() throws Exception {
        withCustomObject(client(), existingCustomObject -> {
            final String id = existingCustomObject.getId();
            final CustomObjectByIdGet<Foo> fetch =
                    CustomObjectByIdGet.of(id, Foo.class);
            final CustomObject<Foo> customObject = client().executeBlocking(fetch);
            assertThat(customObject).isEqualTo(existingCustomObject);
        });
    }
}
