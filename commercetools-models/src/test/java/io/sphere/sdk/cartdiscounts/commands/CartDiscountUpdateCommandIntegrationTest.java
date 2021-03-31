package io.sphere.sdk.cartdiscounts.commands;

import com.neovisionaries.i18n.CurrencyCode;
import io.sphere.sdk.cartdiscounts.*;
import io.sphere.sdk.cartdiscounts.commands.updateactions.*;
import io.sphere.sdk.commands.UpdateAction;
import io.sphere.sdk.models.LocalizedString;
import io.sphere.sdk.test.IntegrationTest;
import io.sphere.sdk.test.SphereTestUtils;
import io.sphere.sdk.utils.MoneyImpl;
import net.jcip.annotations.NotThreadSafe;
import org.javamoney.moneta.Money;
import org.junit.Test;

import javax.money.CurrencyUnit;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import static io.sphere.sdk.cartdiscounts.CartDiscountFixtures.withCartDiscount;
import static io.sphere.sdk.cartdiscounts.CartDiscountFixtures.withPersistentCartDiscount;
import static io.sphere.sdk.test.SphereTestUtils.*;
import static io.sphere.sdk.types.TypeFixtures.STRING_FIELD_NAME;
import static io.sphere.sdk.types.TypeFixtures.withUpdateableType;
import static java.lang.String.format;
import static org.assertj.core.api.Assertions.assertThat;

@NotThreadSafe
public class CartDiscountUpdateCommandIntegrationTest extends IntegrationTest {

    @Test
    public void setValidFromAndUntil() {
        withPersistentCartDiscount(client(), cartDiscount -> {
            final ZonedDateTime validFrom =
                    ZonedDateTime.of(2018, 8, 8, 0, 0, 0, 0, ZoneId.systemDefault());
            final ZonedDateTime validUntil = ZonedDateTime.now().plusYears(1);

            final CartDiscount updatedDiscountCode =
                    client().executeBlocking(CartDiscountUpdateCommand.of(cartDiscount, SetValidFromAndUntil.of(validFrom, validUntil)));
            assertThat(updatedDiscountCode.getValidFrom()).isEqualTo(validFrom);
            assertThat(updatedDiscountCode.getValidUntil()).isEqualTo(validUntil);
        });
    }

    @Test
    public void changeValue() throws Exception {
        withPersistentCartDiscount(client(), cartDiscount -> {
            final CartDiscountValue newValue = CartDiscountValue.ofAbsolute(MoneyImpl.of(randomInt(), EUR));

            assertThat(cartDiscount.getValue()).isNotEqualTo(newValue);

            final CartDiscount updatedDiscount =
                    client().executeBlocking(CartDiscountUpdateCommand.of(cartDiscount, ChangeValue.of(newValue)));

            assertThat(updatedDiscount.getValue()).isEqualTo(newValue);
        });
    }

    @Test
    public void changeCartPredicate() throws Exception {
        withPersistentCartDiscount(client(), cartDiscount -> {
            final String newPredicate = format("totalPrice > \"%d.00 EUR\"", randomInt());

            assertThat(cartDiscount.getCartPredicate()).isNotEqualTo(newPredicate);

            final CartDiscount updatedDiscount =
                    client().executeBlocking(CartDiscountUpdateCommand.of(cartDiscount, ChangeCartPredicate.of(newPredicate)));

            assertThat(updatedDiscount.getCartPredicate()).isEqualTo(newPredicate);
        });
    }

    @Test
    public void changeValidFromUntil() throws Exception {

        withPersistentCartDiscount(client(), cartDiscount -> {
            final ZonedDateTime start = ZonedDateTime.parse("2015-07-09T07:46:40.230Z");
            final ZonedDateTime end = start.plusYears(100);

            final CartDiscount updatedDiscount =
                    client().executeBlocking(CartDiscountUpdateCommand.of(cartDiscount, SetValidFrom.of(start),SetValidUntil.of(end)));
            assertThat(updatedDiscount.getValidFrom()).isEqualTo(start);
            assertThat(updatedDiscount.getValidUntil()).isEqualTo(end);
        });

    }

    @Test
    public void changeTarget() throws Exception {
        withPersistentCartDiscount(client(), cartDiscount -> {
            final CartDiscountTarget newTarget = cartDiscount.getTarget() instanceof LineItemsTarget ?
                    CustomLineItemsTarget.of("1 = 1") : LineItemsTarget.of("1 = 1");

            assertThat(cartDiscount.getTarget()).isNotEqualTo(newTarget);

            final CartDiscount updatedDiscount =
                    client().executeBlocking(CartDiscountUpdateCommand.of(cartDiscount, ChangeTarget.of(newTarget)));

            assertThat(updatedDiscount.getTarget()).isEqualTo(newTarget);
        });
    }

    @Test
    public void changeIsActive() throws Exception {
        withPersistentCartDiscount(client(), cartDiscount -> {
            final boolean newIsActice = !cartDiscount.isActive();

            assertThat(cartDiscount.isActive()).isNotEqualTo(newIsActice);

            final CartDiscount updatedDiscount =
                    client().executeBlocking(CartDiscountUpdateCommand.of(cartDiscount, ChangeIsActive.of(newIsActice)));

            assertThat(updatedDiscount.isActive()).isEqualTo(newIsActice);
        });
    }

    @Test
    public void changeName() throws Exception {
        withPersistentCartDiscount(client(), cartDiscount -> {
            final LocalizedString newName = randomSlug();

            assertThat(cartDiscount.getName()).isNotEqualTo(newName);

            final CartDiscount updatedDiscount =
                    client().executeBlocking(CartDiscountUpdateCommand.of(cartDiscount, ChangeName.of(newName)));

            assertThat(updatedDiscount.getName()).isEqualTo(newName);

            //clean up test, cart discount is reused by name
            client().executeBlocking(CartDiscountUpdateCommand.of(updatedDiscount, ChangeName.of(cartDiscount.getName())));
        });
    }

    @Test
    public void setDescription() throws Exception {
        withPersistentCartDiscount(client(), cartDiscount -> {
            final LocalizedString newDescription = randomSlug();

            assertThat(cartDiscount.getDescription()).isNotEqualTo(newDescription);

            final CartDiscount updatedDiscount =
                    client().executeBlocking(CartDiscountUpdateCommand.of(cartDiscount, SetDescription.of(newDescription)));

            assertThat(updatedDiscount.getDescription()).isEqualTo(newDescription);
        });
    }

    @Test
    public void changeSortOrder() throws Exception {
        withPersistentCartDiscount(client(), cartDiscount -> {
            final String newSortOrder = randomSortOrder();

            assertThat(cartDiscount.getSortOrder()).isNotEqualTo(newSortOrder);

            final CartDiscount updatedDiscount =
                    client().executeBlocking(CartDiscountUpdateCommand.of(cartDiscount, ChangeSortOrder.of(newSortOrder)));

            assertThat(updatedDiscount.getSortOrder()).isEqualTo(newSortOrder);
        });
    }

    @Test
    public void changeRequiresDiscountCode() throws Exception {
        withCartDiscount(
            client(),
            cartDiscountDraftBuilder -> cartDiscountDraftBuilder.requiresDiscountCode(true),
            cartDiscount -> {
                final boolean newRequiresDiscountCode = !cartDiscount.isRequiringDiscountCode();

                assertThat(cartDiscount.isRequiringDiscountCode()).isNotEqualTo(newRequiresDiscountCode);

                final CartDiscount updatedDiscount =
                        client().executeBlocking(CartDiscountUpdateCommand.of(cartDiscount, ChangeRequiresDiscountCode.of(newRequiresDiscountCode)));

                assertThat(updatedDiscount.isRequiringDiscountCode()).isEqualTo(newRequiresDiscountCode);
                return updatedDiscount;
            });
    }

    @Test
    public void setValidFrom() throws Exception {
        withPersistentCartDiscount(client(), cartDiscount -> {
            final ZonedDateTime dateTime = SphereTestUtils.now();

            assertThat(cartDiscount.getValidFrom()).isNotEqualTo(dateTime);

            final List<UpdateAction<CartDiscount>> updateActions =
                    asList(SetValidUntil.of(dateTime.plus(7, ChronoUnit.DAYS)), SetValidFrom.of(dateTime));
            final CartDiscount updatedDiscount = client().executeBlocking(CartDiscountUpdateCommand.of(cartDiscount, updateActions));

            assertThat(updatedDiscount.getValidFrom()).isEqualTo(dateTime);
        });
    }

    @Test
    public void setValidUntil() throws Exception {
        withPersistentCartDiscount(client(), cartDiscount -> {
            //until must be after valid from
            final ZonedDateTime dateTime = dateTimeAfterValidFromAndOldValidUntil(cartDiscount);

            assertThat(cartDiscount.getValidUntil()).isNotEqualTo(dateTime);

            final CartDiscount updatedDiscount =
                    client().executeBlocking(CartDiscountUpdateCommand.of(cartDiscount, SetValidUntil.of(dateTime)));

            assertThat(updatedDiscount.getValidUntil()).isEqualTo(dateTime);
        });
    }

    @Test
    public void setValidUntilUpdatingByKey(){
        withCartDiscount(client(), cartDiscount -> {
            final ZonedDateTime dateTime = dateTimeAfterValidFromAndOldValidUntil(cartDiscount);

            assertThat(cartDiscount.getValidUntil()).isNotEqualTo(dateTime);

            final CartDiscount updatedDiscount =
                    client().executeBlocking(CartDiscountUpdateCommand.ofKey(cartDiscount.getKey(), cartDiscount.getVersion(), SetValidUntil.of(dateTime)));

            assertThat(updatedDiscount.getValidUntil()).isEqualTo(dateTime);
            return updatedDiscount;
        });
    }

    @Test
    public void changeStackingMode() throws Exception {
        withCartDiscount(client(), cartDiscount -> {
            CartDiscount updatedDiscount = cartDiscount;
            for (final StackingMode stackingMode : Arrays.asList(StackingMode.STOP_AFTER_THIS_DISCOUNT, StackingMode.STACKING, StackingMode.STOP_AFTER_THIS_DISCOUNT)) {
                assertThat(updatedDiscount.getStackingMode()).isNotEqualTo(stackingMode);

                updatedDiscount =
                        client().executeBlocking(CartDiscountUpdateCommand.of(updatedDiscount, ChangeStackingMode.of(stackingMode)));

                assertThat(updatedDiscount.getStackingMode()).isEqualTo(stackingMode);
            }

            return updatedDiscount;
        });
    }

    @Test
    public void fixedCartDiscountMode() throws Exception {
        withCartDiscount(client(), draft -> draft.value(FixedCartDiscountValue.of(Money.of(10, EUR))), cartDiscount -> {
            assertThat(cartDiscount.getValue()).isInstanceOfSatisfying(FixedCartDiscountValue.class, fixedCartDiscountValue -> assertThat(fixedCartDiscountValue.getMoney().stream().findFirst().get().getCurrency()).isEqualTo(EUR));
            return cartDiscount;
        });
    }


    @Test
    public void setCustomType(){
        withUpdateableType(client(), type -> {
            withCartDiscount(client(), cartDiscount -> {
                final HashMap<String, Object> fields = new HashMap<>();
                fields.put(STRING_FIELD_NAME, "hello");
                final CartDiscountUpdateCommand updateCommand =
                        CartDiscountUpdateCommand.of(cartDiscount, SetCustomType.ofTypeIdAndObjects(type.getId(), fields));
                final CartDiscount updatedDiscount = client().executeBlocking(updateCommand);

                assertThat(updatedDiscount.getCustom().getType()).isEqualTo(type.toReference());
                assertThat(updatedDiscount.getCustom().getFieldAsString(STRING_FIELD_NAME)).isEqualTo("hello");

                final CartDiscountUpdateCommand updateCommand2 =
                        CartDiscountUpdateCommand.of(updatedDiscount,SetCustomField.ofObject(STRING_FIELD_NAME, "a new value"));
                final CartDiscount updatedDiscount2 = client().executeBlocking(updateCommand2);

                assertThat(updatedDiscount2.getCustom()
                        .getFieldAsString(STRING_FIELD_NAME)).isEqualTo("a new value");


                final CartDiscount updated2 =
                        client().executeBlocking(CartDiscountUpdateCommand.of(updatedDiscount2, SetCustomType.ofRemoveType()));
                assertThat(updated2.getCustom()).isNull();
                return updated2;
            });
            return type;
        });

    }

    @Test
    public void setKey(){
        withCartDiscount(client(), cartDiscount -> {
            final String newKey = SphereTestUtils.randomKey();
            final CartDiscount updatedCartDiscount = client().executeBlocking(CartDiscountUpdateCommand.ofKey(cartDiscount.getKey(), cartDiscount.getVersion(), SetKey.of(newKey)));
            assertThat(updatedCartDiscount).isNotNull();
            assertThat(updatedCartDiscount.getKey()).isEqualTo(newKey);
            return updatedCartDiscount;
        });
    }

    private ZonedDateTime dateTimeAfterValidFromAndOldValidUntil(final CartDiscount cartDiscount) {
        return Optional.ofNullable(cartDiscount.getValidUntil())
                .orElse(Optional.ofNullable(cartDiscount.getValidFrom()).orElse(SphereTestUtils.now()).plusSeconds(1000)).plusSeconds(1);
    }
}
