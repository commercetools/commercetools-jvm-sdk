package io.sphere.sdk.discountcodes.commands;

import io.sphere.sdk.discountcodes.DiscountCode;
import io.sphere.sdk.discountcodes.commands.updateactions.SetName;
import io.sphere.sdk.models.LocalizedStrings;
import io.sphere.sdk.test.IntegrationTest;
import org.junit.Test;

import static io.sphere.sdk.discountcodes.DiscountCodeFixtures.withPersistentDiscountCode;
import static io.sphere.sdk.test.SphereTestUtils.*;
import static org.assertj.core.api.Assertions.*;

public class DiscountCodeUpdateCommandTest extends IntegrationTest {
    @Test
    public void setName() throws Exception {
        withPersistentDiscountCode(client(), discountCode -> {
            final LocalizedStrings newName = randomSlug();
            final DiscountCode updatedDiscountCode =
                    execute(DiscountCodeUpdateCommand.of(discountCode, SetName.of(newName)));
            assertThat(updatedDiscountCode.getName()).contains(newName);
        });
    }
}