package io.sphere.sdk.shippingmethods.commands;

import io.sphere.sdk.shippingmethods.ShippingMethod;
import io.sphere.sdk.shippingmethods.commands.updateactions.ChangeName;
import io.sphere.sdk.test.IntegrationTest;
import org.junit.Test;

import static io.sphere.sdk.shippingmethods.ShippingMethodFixtures.withUpdateableShippingMethod;
import static io.sphere.sdk.test.SphereTestUtils.*;
import static org.fest.assertions.Assertions.assertThat;

public class ShippingMethodUpdateCommandTest extends IntegrationTest {
    @Test
    public void changeName() throws Exception {
        withUpdateableShippingMethod(client(), shippingMethod -> {
            final String newName = randomString();
            assertThat(shippingMethod.getName()).isNotEqualTo(newName);
            final ShippingMethodUpdateCommand cmd = ShippingMethodUpdateCommand.of(shippingMethod, ChangeName.of(newName));
            final ShippingMethod updatedShippingMethod = execute(cmd);
            assertThat(updatedShippingMethod.getName()).isEqualTo(newName);
            return updatedShippingMethod;
        });
    }
}