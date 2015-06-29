package io.sphere.sdk.productdiscounts.commands;

import io.sphere.sdk.productdiscounts.*;
import io.sphere.sdk.productdiscounts.commands.updateactions.ChangePredicate;
import io.sphere.sdk.productdiscounts.commands.updateactions.ChangeValue;
import io.sphere.sdk.test.IntegrationTest;
import org.junit.*;

import static io.sphere.sdk.productdiscounts.ProductDiscountFixtures.withUpdateableProductDiscount;
import static org.assertj.core.api.Assertions.*;
import static io.sphere.sdk.test.SphereTestUtils.*;

public class ProductDiscountUpdateCommandTest extends IntegrationTest {
    @Test
    public void changeValue() throws Exception {
        withUpdateableProductDiscount(client(), discount -> {
            final ProductDiscountValue productDiscountValue = AbsoluteProductDiscountValue.of(EURO_30);

            final ProductDiscount updatedDiscount = execute(ProductDiscountUpdateCommand.of(discount, ChangeValue.of(productDiscountValue)));

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

            final ProductDiscount updatedDiscount = execute(
                    ProductDiscountUpdateCommand.of(discount, ChangePredicate.of(predicate)));

            final String updatedPredicate = updatedDiscount.getPredicate();
            assertThat(updatedPredicate).isEqualTo(predicateAsString);
            return updatedDiscount;
        });
    }
}