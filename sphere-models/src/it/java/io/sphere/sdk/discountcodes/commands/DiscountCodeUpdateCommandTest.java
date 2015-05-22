package io.sphere.sdk.discountcodes.commands;

import io.sphere.sdk.cartdiscounts.CartPredicate;
import io.sphere.sdk.discountcodes.DiscountCode;
import io.sphere.sdk.discountcodes.commands.updateactions.SetCartPredicate;
import io.sphere.sdk.discountcodes.commands.updateactions.SetDescription;
import io.sphere.sdk.discountcodes.commands.updateactions.SetName;
import io.sphere.sdk.models.LocalizedStrings;
import io.sphere.sdk.test.IntegrationTest;
import org.junit.Test;

import java.time.ZoneId;
import java.time.ZonedDateTime;

import static io.sphere.sdk.discountcodes.DiscountCodeFixtures.withPersistentDiscountCode;
import static io.sphere.sdk.test.SphereTestUtils.*;
import static java.lang.String.format;
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
    
    @Test
    public void setDescription() throws Exception {
        withPersistentDiscountCode(client(), discountCode -> {
            final LocalizedStrings newDescription = randomSlug();
            final DiscountCode updatedDiscountCode =
                    execute(DiscountCodeUpdateCommand.of(discountCode, SetDescription.of(newDescription)));
            assertThat(updatedDiscountCode.getDescription()).contains(newDescription);
        });
    }

    @Test
    public void setCartPredicate() throws Exception {
        withPersistentDiscountCode(client(), discountCode -> {
            final CartPredicate cartPredicate = CartPredicate.of("1 = 1");
            final DiscountCode updatedDiscountCode =
                    execute(DiscountCodeUpdateCommand.of(discountCode, SetCartPredicate.of(cartPredicate)));
            assertThat(updatedDiscountCode.getCartPredicate()).contains(cartPredicate.toSphereCartPredicate());
        });
    }
}