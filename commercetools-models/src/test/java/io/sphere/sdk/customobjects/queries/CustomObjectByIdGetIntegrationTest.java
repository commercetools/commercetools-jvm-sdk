package io.sphere.sdk.customobjects.queries;

import io.sphere.sdk.carts.CartFixtures;
import io.sphere.sdk.customobjects.CustomObject;
import io.sphere.sdk.customobjects.demo.Foo;
import io.sphere.sdk.expansion.ExpansionPath;
import io.sphere.sdk.test.IntegrationTest;
import org.junit.Test;

import static io.sphere.sdk.customobjects.CustomObjectFixtures.withCustomObject;
import static io.sphere.sdk.customobjects.CustomObjectFixtures.withCustomObjectAndCustomClasses;
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

    @Test
    public void expansionPath() {
        CartFixtures.withCart(client(), cart -> {
            withCustomObjectAndCustomClasses(client(), cart, existingCustomObject -> {
                final String id = existingCustomObject.getId();
                final CustomObjectByIdGet<MyCustomClass> fetch =
                        CustomObjectByIdGet.of(id, MyCustomClass.class);
                CustomObject<MyCustomClass> customObject = client().executeBlocking(fetch);
                assertThat(customObject).isEqualTo(existingCustomObject);
                assertThat(customObject.getValue().getCartReference().getObj()).isNull();

                final ExpansionPath<CustomObject<MyCustomClass>> expansionPath = ExpansionPath.of("value.cartReference");
                CustomObjectByIdGet<MyCustomClass> expandedFetch = fetch.withExpansionPaths(expansionPath);
                customObject = client().executeBlocking(expandedFetch);
                assertThat(customObject).isNotNull();
                assertThat(customObject.getValue().getCartReference().getObj()).isNotNull();
            });
            return cart;
        });
    }

}
