package io.sphere.sdk.discountcodes.commands;

import io.sphere.sdk.cartdiscounts.CartPredicate;
import io.sphere.sdk.discountcodes.DiscountCode;
import io.sphere.sdk.discountcodes.commands.updateactions.*;
import io.sphere.sdk.models.LocalizedStrings;
import io.sphere.sdk.test.IntegrationTest;
import org.junit.Test;

import java.time.ZoneId;
import java.time.ZonedDateTime;

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
            final String predicateAsString =
                    //you need to change the predicate
                    discountCode.getCartPredicate().map(p -> "1 = 1".equals(p)).orElse(false) ? "true = true" : "1 = 1";

            final CartPredicate cartPredicate = CartPredicate.of(predicateAsString);
            final DiscountCode updatedDiscountCode =
                    execute(DiscountCodeUpdateCommand.of(discountCode, SetCartPredicate.of(cartPredicate)));
            assertThat(updatedDiscountCode.getCartPredicate()).contains(cartPredicate.toSphereCartPredicate());
        });
    }

    @Test
    public void setMaxApplications() throws Exception {
        withPersistentDiscountCode(client(), discountCode -> {
            final long maxApplications = randomLong();
            final DiscountCode updatedDiscountCode =
                    execute(DiscountCodeUpdateCommand.of(discountCode, SetMaxApplications.of(maxApplications)));
            assertThat(updatedDiscountCode.getMaxApplications()).contains(maxApplications);
        });
    }
    
    @Test
    public void setMaxApplicationsPerCustomer() throws Exception {
        withPersistentDiscountCode(client(), discountCode -> {
            final long maxApplications = randomLong();
            final DiscountCode updatedDiscountCode =
                    execute(DiscountCodeUpdateCommand.of(discountCode, SetMaxApplicationsPerCustomer.of(maxApplications)));
            assertThat(updatedDiscountCode.getMaxApplicationsPerCustomer()).contains(maxApplications);
        });
    }
}