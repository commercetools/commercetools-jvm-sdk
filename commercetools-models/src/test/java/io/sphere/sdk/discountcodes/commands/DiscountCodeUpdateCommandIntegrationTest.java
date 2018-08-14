package io.sphere.sdk.discountcodes.commands;

import io.sphere.sdk.cartdiscounts.CartDiscount;
import io.sphere.sdk.cartdiscounts.CartPredicate;
import io.sphere.sdk.discountcodes.DiscountCode;
import io.sphere.sdk.discountcodes.commands.updateactions.*;
import io.sphere.sdk.models.LocalizedString;
import io.sphere.sdk.models.Reference;
import io.sphere.sdk.test.IntegrationTest;
import net.jcip.annotations.NotThreadSafe;
import org.junit.Test;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;

import static io.sphere.sdk.cartdiscounts.CartDiscountFixtures.withCartDiscount;
import static io.sphere.sdk.discountcodes.DiscountCodeFixtures.withPersistentDiscountCode;
import static io.sphere.sdk.test.SphereTestUtils.*;
import static io.sphere.sdk.utils.SphereInternalUtils.listOf;
import static org.assertj.core.api.Assertions.assertThat;

@NotThreadSafe
public class DiscountCodeUpdateCommandIntegrationTest extends IntegrationTest {
    @Test
    public void setValidFromAndUntil() {
        withPersistentDiscountCode(client(), discountCode -> {
            final ZonedDateTime validFrom =
                    ZonedDateTime.of(2018, 8, 8, 0, 0, 0, 0, ZoneId.systemDefault());
            final ZonedDateTime validUntil = ZonedDateTime.now().plusYears(1);

            final DiscountCode updatedDiscountCode =
                    client().executeBlocking(DiscountCodeUpdateCommand.of(discountCode, SetValidFromAndUntil.of(validFrom, validUntil)));
            assertThat(updatedDiscountCode.getValidFrom()).isEqualTo(validFrom);
            assertThat(updatedDiscountCode.getValidUntil()).isEqualTo(validUntil);
        });
    }

    @Test
    public void setName() throws Exception {
        withPersistentDiscountCode(client(), discountCode -> {
            final LocalizedString newName = randomSlug();
            final DiscountCode updatedDiscountCode =
                    client().executeBlocking(DiscountCodeUpdateCommand.of(discountCode, SetName.of(newName)));
            assertThat(updatedDiscountCode.getName()).isEqualTo(newName);
        });
    }

    @Test
    public void setDescription() throws Exception {
        withPersistentDiscountCode(client(), discountCode -> {
            final LocalizedString newDescription = randomSlug();
            final DiscountCode updatedDiscountCode =
                    client().executeBlocking(DiscountCodeUpdateCommand.of(discountCode, SetDescription.of(newDescription)));
            assertThat(updatedDiscountCode.getDescription()).isEqualTo(newDescription);
        });
    }

    @Test
    public void setCartPredicate() throws Exception {
        withPersistentDiscountCode(client(), discountCode -> {
            final String predicateAsString =
                    //you need to change the predicate
                    Optional.ofNullable(discountCode.getCartPredicate()).map(p -> "1 = 1".equals(p)).orElse(false) ? "true = true" : "1 = 1";

            final CartPredicate cartPredicate = CartPredicate.of(predicateAsString);
            final DiscountCode updatedDiscountCode =
                    client().executeBlocking(DiscountCodeUpdateCommand.of(discountCode, SetCartPredicate.of(cartPredicate)));
            assertThat(updatedDiscountCode.getCartPredicate()).contains(cartPredicate.toSphereCartPredicate());
        });
    }

    @Test
    public void setMaxApplications() throws Exception {
        withPersistentDiscountCode(client(), discountCode -> {
            final long maxApplications = randomLong();
            final DiscountCode updatedDiscountCode =
                    client().executeBlocking(DiscountCodeUpdateCommand.of(discountCode, SetMaxApplications.of(maxApplications)));
            assertThat(updatedDiscountCode.getMaxApplications()).isEqualTo(maxApplications);
        });
    }
    
    @Test
    public void setMaxApplicationsPerCustomer() throws Exception {
        withPersistentDiscountCode(client(), discountCode -> {
            final long maxApplications = randomLong();
            final DiscountCode updatedDiscountCode =
                    client().executeBlocking(DiscountCodeUpdateCommand.of(discountCode, SetMaxApplicationsPerCustomer.of(maxApplications)));
            assertThat(updatedDiscountCode.getMaxApplicationsPerCustomer()).isEqualTo(maxApplications);
        });
    }


    @Test
    public void changeGroups() throws Exception {
        withPersistentDiscountCode(client(), discountCode -> {
            final long maxApplications = randomLong();
            final String GROUP_NAME = "GroupName";
            final DiscountCode updatedDiscountCode =
                    client().executeBlocking(DiscountCodeUpdateCommand.of(discountCode, ChangeGroups.of(asList(GROUP_NAME))));
            assertThat(updatedDiscountCode.getGroups()).containsExactly(GROUP_NAME);
        });
    }

    @SuppressWarnings("unchecked")
    @Test
    public void changeCartDiscounts() throws Exception {
        withCartDiscount(client(), randomKey(), cartDiscount ->
            withPersistentDiscountCode(client(), discountCode -> {
                final List<Reference<CartDiscount>> oldCartDiscounts = discountCode.getCartDiscounts();
                assertThat(oldCartDiscounts).doesNotContain(cartDiscount.toReference());

                final List<Reference<CartDiscount>> newDiscountsList =
                        listOf(oldCartDiscounts, cartDiscount.toReference());

                final DiscountCode updatedDiscountCode =
                        client().executeBlocking(DiscountCodeUpdateCommand.of(discountCode, ChangeCartDiscounts.of(newDiscountsList)));
                assertThat(updatedDiscountCode.getCartDiscounts()).isEqualTo(newDiscountsList);

                //clean up test
                client().executeBlocking(DiscountCodeUpdateCommand.of(updatedDiscountCode, ChangeCartDiscounts.of(oldCartDiscounts)));
            })
        );
    }
}