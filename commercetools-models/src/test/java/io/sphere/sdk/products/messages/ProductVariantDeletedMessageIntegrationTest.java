package io.sphere.sdk.products.messages;

import io.sphere.sdk.messages.queries.MessageQuery;
import io.sphere.sdk.models.EnumValue;
import io.sphere.sdk.models.LocalizedEnumValue;
import io.sphere.sdk.models.LocalizedString;
import io.sphere.sdk.products.*;
import io.sphere.sdk.products.attributes.AttributeAccess;
import io.sphere.sdk.products.attributes.AttributeDraft;
import io.sphere.sdk.products.attributes.NamedAttributeAccess;
import io.sphere.sdk.products.commands.ProductDeleteCommand;
import io.sphere.sdk.products.commands.ProductUpdateCommand;
import io.sphere.sdk.products.commands.updateactions.*;
import io.sphere.sdk.queries.PagedQueryResult;
import io.sphere.sdk.queries.Query;
import io.sphere.sdk.suppliers.TShirtProductTypeDraftSupplier;
import io.sphere.sdk.test.IntegrationTest;
import io.sphere.sdk.utils.MoneyImpl;
import org.junit.Test;

import javax.money.MonetaryAmount;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import static io.sphere.sdk.models.DefaultCurrencyUnits.EUR;
import static io.sphere.sdk.products.ProductFixtures.createExternalImage;
import static io.sphere.sdk.products.ProductFixtures.withUpdateableProduct;
import static io.sphere.sdk.suppliers.TShirtProductTypeDraftSupplier.MONEY_ATTRIBUTE_NAME;
import static io.sphere.sdk.test.SphereTestUtils.*;
import static io.sphere.sdk.test.SphereTestUtils.DE;
import static io.sphere.sdk.test.SphereTestUtils.randomKey;
import static org.assertj.core.api.Assertions.assertThat;

public class ProductVariantDeletedMessageIntegrationTest extends IntegrationTest {

    @Test
    public void message() {

        final NamedAttributeAccess<MonetaryAmount> moneyAttribute =
                AttributeAccess.ofMoney().ofName(MONEY_ATTRIBUTE_NAME);
        final AttributeDraft moneyAttributeValue = AttributeDraft.of(moneyAttribute, EURO_10);

        final NamedAttributeAccess<LocalizedEnumValue> colorAttribute = TShirtProductTypeDraftSupplier.Colors.ATTRIBUTE;
        final LocalizedEnumValue color = TShirtProductTypeDraftSupplier.Colors.RED;
        final AttributeDraft colorAttributeValue = AttributeDraft.of(colorAttribute, color);

        final NamedAttributeAccess<EnumValue> sizeAttribute = TShirtProductTypeDraftSupplier.Sizes.ATTRIBUTE;
        final AttributeDraft sizeValue = AttributeDraft.of(sizeAttribute, TShirtProductTypeDraftSupplier.Sizes.M);


        withUpdateableProduct(client(), product -> {

            final Image image = createExternalImage();
            final PriceDraft price = PriceDraft.of(MoneyImpl.of(new BigDecimal("12.34"), EUR)).withCountry(DE);
            final List<PriceDraft> prices = asList(price);
            final List<AttributeDraft> attributeValues = asList(moneyAttributeValue, colorAttributeValue, sizeValue);
            final ProductUpdateCommand addVariantCommand =
                    ProductUpdateCommand.of(product, AddVariant.of(attributeValues, prices, randomKey(), true).withImages(Arrays.asList(image)));

            final Product productWithVariant = client().executeBlocking(addVariantCommand);
            final ProductVariant variant = productWithVariant.getMasterData().getStaged().getVariants().get(0);

            final Product productWithoutVariant = client().executeBlocking(ProductUpdateCommand.of(productWithVariant, RemoveVariant.ofVariantId(variant.getId())));

            Query<ProductVariantDeletedMessage> messageQuery = MessageQuery.of()
                    .withPredicates(m -> m.resource().is(product))
                    .withSort(m -> m.createdAt().sort().desc())
                    .withExpansionPaths(m -> m.resource())
                    .withLimit(1L)
                    .forMessageType(ProductVariantDeletedMessage.MESSAGE_HINT);

            assertEventually(() -> {
                final PagedQueryResult<ProductVariantDeletedMessage> queryResult = client().executeBlocking(messageQuery);

                assertThat(queryResult.head()).isPresent();
                final ProductVariantDeletedMessage message = queryResult.head().get();
                assertThat(message.getResource().getId()).isEqualTo(product.getId());
                assertThat(message.getRemovedImageUrls()).containsExactly(image.getUrl());
            });
            return productWithoutVariant;
        });
    }
}