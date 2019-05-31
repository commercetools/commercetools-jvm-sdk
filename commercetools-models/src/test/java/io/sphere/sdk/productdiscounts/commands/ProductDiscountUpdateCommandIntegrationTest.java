package io.sphere.sdk.productdiscounts.commands;

import io.sphere.sdk.models.LocalizedString;
import io.sphere.sdk.productdiscounts.*;
import io.sphere.sdk.productdiscounts.commands.updateactions.*;
import io.sphere.sdk.test.IntegrationTest;
import org.junit.*;

import java.time.ZonedDateTime;

import static io.sphere.sdk.productdiscounts.ProductDiscountFixtures.withUpdateableProductDiscount;
import static org.assertj.core.api.Assertions.*;
import static io.sphere.sdk.test.SphereTestUtils.*;

public class ProductDiscountUpdateCommandIntegrationTest extends IntegrationTest {
    @Test
    public void changeValue() throws Exception {
        withUpdateableProductDiscount(client(), discount -> {
            final ProductDiscountValue productDiscountValue = AbsoluteProductDiscountValue.of(EURO_30);

            final ProductDiscount updatedDiscount = client().executeBlocking(ProductDiscountUpdateCommand.of(discount, ChangeValue.of(productDiscountValue)));

            assertThat(updatedDiscount.getValue()).isEqualTo(productDiscountValue);
            return updatedDiscount;
        });
    }
    
    @Test
    public void changeValueByKey() throws Exception {
        withUpdateableProductDiscount(client(), discount -> {
            final ProductDiscountValue productDiscountValue = AbsoluteProductDiscountValue.of(EURO_30);

            final ProductDiscount updatedDiscount = client().executeBlocking(ProductDiscountUpdateCommand.ofKey(discount.getKey(), discount.getVersion(), ChangeValue.of(productDiscountValue)));

            assertThat(updatedDiscount.getValue()).isEqualTo(productDiscountValue);
            return updatedDiscount;
        });
    }

    @Test
    public void changePredicate() throws Exception {
        withUpdateableProductDiscount(client(), discount -> {
            final String predicateAsString = "sku = \"AB-12\"";
            final ProductDiscountPredicate predicate = ProductDiscountPredicate
                    .of(predicateAsString);

            final ProductDiscount updatedDiscount = client().executeBlocking(ProductDiscountUpdateCommand.of(discount, ChangePredicate.of(predicate)));

            final String updatedPredicate = updatedDiscount.getPredicate();
            assertThat(updatedPredicate).isEqualTo(predicateAsString);
            return updatedDiscount;
        });
    }

    @Test
    public void changeIsActive() throws Exception {
        withUpdateableProductDiscount(client(), discount -> {
            final boolean newIsActive = !discount.isActive();

            final ProductDiscount updatedDiscount = client().executeBlocking(ProductDiscountUpdateCommand.of(discount, ChangeIsActive.of(newIsActive)));

            assertThat(updatedDiscount.isActive()).isEqualTo(newIsActive);
            return updatedDiscount;
        });
    }

    @Test
    public void changeValidFromUntil() throws Exception {
        withUpdateableProductDiscount(client(), discount -> {
            final ZonedDateTime start = ZonedDateTime.parse("2015-07-09T07:46:40.230Z");
            final ZonedDateTime end = start.plusYears(100);

            final ProductDiscount updatedDiscount = client().executeBlocking(ProductDiscountUpdateCommand.of(discount, asList(SetValidFrom.of(start),SetValidUntil.of(end))));

            assertThat(updatedDiscount.getValidFrom()).isEqualTo(start);
            assertThat(updatedDiscount.getValidUntil()).isEqualTo(end);
            return updatedDiscount;
        });
    }

    @Test
    public void setValidFromAndUntil() throws Exception {
        withUpdateableProductDiscount(client(), discount -> {
            final ZonedDateTime start = ZonedDateTime.parse("2015-07-09T07:46:40.230Z");
            final ZonedDateTime end = start.plusYears(100);

            final ProductDiscount updatedDiscount = client().executeBlocking(ProductDiscountUpdateCommand.of(discount, SetValidFromAndUntil.of(start,end )));

            assertThat(updatedDiscount.getValidFrom()).isEqualTo(start);
            assertThat(updatedDiscount.getValidUntil()).isEqualTo(end);
            return updatedDiscount;
        });
    }

    @Test
    public void changeName() throws Exception {
        withUpdateableProductDiscount(client(), discount -> {
            final LocalizedString newName = randomSlug();

            final ProductDiscount updatedDiscount = client().executeBlocking(ProductDiscountUpdateCommand.of(discount, ChangeName.of(newName)));

            assertThat(updatedDiscount.getName()).isEqualTo(newName);
            return updatedDiscount;
        });
    }

    @Test
    public void setDescription() throws Exception {
        withUpdateableProductDiscount(client(), discount -> {
            final LocalizedString newDescription = randomSlug();

            final ProductDiscount updatedDiscount = client().executeBlocking(ProductDiscountUpdateCommand.of(discount, SetDescription.of(newDescription)));

            assertThat(updatedDiscount.getDescription()).isEqualTo(newDescription);
            return updatedDiscount;
        });
    }

    @Test
    public void changeSortOrder() throws Exception {
        withUpdateableProductDiscount(client(), discount -> {
            final String newSortOrder = randomSortOrder();

            final ProductDiscount updatedDiscount = client().executeBlocking(ProductDiscountUpdateCommand.of(discount, ChangeSortOrder.of(newSortOrder)));

            assertThat(updatedDiscount.getSortOrder()).isEqualTo(newSortOrder);
            return updatedDiscount;
        });
    }
    
    @Test
    public void setKey() throws Exception {
        withUpdateableProductDiscount(client(), discount -> {
            final String newKey = randomKey();
            final ProductDiscount updatedDiscount = client().executeBlocking(ProductDiscountUpdateCommand.of(discount, SetKey.of(newKey)));
            assertThat(updatedDiscount.getKey()).isEqualTo(newKey);
            return updatedDiscount;
        });
    }
}