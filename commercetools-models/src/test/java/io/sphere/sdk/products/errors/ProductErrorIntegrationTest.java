package io.sphere.sdk.products.errors;


import io.sphere.sdk.client.ErrorResponseException;
import io.sphere.sdk.products.Price;
import io.sphere.sdk.products.PriceDraft;
import io.sphere.sdk.products.Product;
import io.sphere.sdk.products.attributes.Attribute;
import io.sphere.sdk.products.attributes.AttributeDraft;
import io.sphere.sdk.products.commands.ProductUpdateCommand;
import io.sphere.sdk.products.commands.updateactions.AddPrice;
import io.sphere.sdk.products.commands.updateactions.AddVariant;
import io.sphere.sdk.products.commands.updateactions.SetKey;
import io.sphere.sdk.products.commands.updateactions.SetPrices;
import io.sphere.sdk.suppliers.TShirtProductTypeDraftSupplier;
import io.sphere.sdk.test.IntegrationTest;
import io.sphere.sdk.utils.MoneyImpl;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.List;

import static io.sphere.sdk.models.DefaultCurrencyUnits.EUR;
import static io.sphere.sdk.products.ProductFixtures.*;
import static io.sphere.sdk.test.SphereTestUtils.DE;
import static io.sphere.sdk.test.SphereTestUtils.randomKey;
import static java.util.Arrays.asList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

public class ProductErrorIntegrationTest extends IntegrationTest {

    @Test
    public void duplicatePriceScopeError() {
        final PriceDraft expectedPrice1 = PriceDraft.of(MoneyImpl.of(123, EUR));
        final PriceDraft expectedPrice2 = PriceDraft.of(MoneyImpl.of(123, EUR));
        final List<PriceDraft> expectedPriceList = asList(expectedPrice1, expectedPrice2);

        withUpdateablePricedProduct(client(), expectedPrice1, product -> {
            final Throwable throwable = catchThrowable(() -> client()
                    .executeBlocking(ProductUpdateCommand.of(product, SetPrices.of(1, expectedPriceList))));
            assertThat(throwable).isInstanceOf(ErrorResponseException.class);
            final ErrorResponseException e = (ErrorResponseException) throwable;
            assertThat(e.hasErrorCode(DuplicatePriceScopeError.CODE)).isTrue();
            assertThat(e.getErrors().get(0).getCode()).isEqualTo(DuplicatePriceScopeError.CODE);
            final DuplicatePriceScopeError error = e.getErrors().get(0).as(DuplicatePriceScopeError.class);
            final List<Price> prices = error.getConflictingPrices();

            assertThat(prices.get(0).getValue()).isEqualTo(product.getMasterData().getStaged().getMasterVariant().getPrices().get(0).getValue());

            return product;
        });
    }

    @Test
    public void duplicateAttributeValuesError() {
        withUpdateableProductOfMultipleVariants(client(), product -> {
            final AttributeDraft greenColor = AttributeDraft.of(
                    TShirtProductTypeDraftSupplier.Colors.ATTRIBUTE, TShirtProductTypeDraftSupplier.Colors.GREEN
            );
            final PriceDraft price = PriceDraft.of(MoneyImpl.of(new BigDecimal("12.34"), EUR)).withCountry(DE);
            final List<PriceDraft> prices = asList(price);
            final List<AttributeDraft> attributeValues = asList(
                    AttributeDraft.of(
                            TShirtProductTypeDraftSupplier.Sizes.ATTRIBUTE, TShirtProductTypeDraftSupplier.Sizes.S), greenColor
            );
            final AddVariant updateAction = AddVariant.of(attributeValues, prices);
            final Throwable throwable = catchThrowable(() -> client()
                    .executeBlocking(ProductUpdateCommand.of(product, updateAction)));

            assertThat(throwable).isInstanceOf(ErrorResponseException.class);
            final ErrorResponseException e = (ErrorResponseException) throwable;
            assertThat(e.hasErrorCode(DuplicateAttributeValuesError.CODE)).isTrue();
            assertThat(e.getErrors().get(0).getCode()).isEqualTo(DuplicateAttributeValuesError.CODE);
            final DuplicateAttributeValuesError error = e.getErrors().get(0).as(DuplicateAttributeValuesError.class);
            List<Attribute> attributes = error.getAttributes();

            assertThat(attributes.get(1).getValueAsJsonNode()).isEqualTo(attributeValues.get(1).getValue());

            return product;
        });
    }
}
