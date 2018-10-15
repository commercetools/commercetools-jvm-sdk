package io.sphere.sdk.productdiscounts.commands;

import io.sphere.sdk.json.SphereJsonUtils;
import io.sphere.sdk.messages.queries.MessageQuery;
import io.sphere.sdk.models.LocalizedString;
import io.sphere.sdk.productdiscounts.*;
import io.sphere.sdk.productdiscounts.queries.ProductDiscountQuery;
import io.sphere.sdk.products.Price;
import io.sphere.sdk.products.Product;
import io.sphere.sdk.products.messages.ProductPriceDiscountsSetMessage;
import io.sphere.sdk.products.queries.ProductByIdGet;
import io.sphere.sdk.queries.Query;
import io.sphere.sdk.test.IntegrationTest;
import org.junit.BeforeClass;
import org.junit.Test;

import java.time.ZonedDateTime;
import java.util.List;

import static io.sphere.sdk.productdiscounts.ProductDiscountFixtures.withProductDiscount;
import static io.sphere.sdk.products.ProductFixtures.referenceableProduct;
import static io.sphere.sdk.test.SphereTestUtils.*;
import static org.assertj.core.api.Assertions.assertThat;

public class ProductDiscountCreateCommandIntegrationTest extends IntegrationTest {

    @BeforeClass
    public static void clean() {
        client().executeBlocking(ProductDiscountQuery.of().withPredicates(m -> m.name().locale(ENGLISH).is("example product discount")))
                .getResults()
                .forEach(discount -> client().executeBlocking(ProductDiscountDeleteCommand.of(discount)));
    }

    @Test
    public void execution() throws Exception {
        final ZonedDateTime start = ZonedDateTime.parse("2015-07-09T07:46:40.230Z");
        final ZonedDateTime end = start.plusYears(100);
        final Product product = referenceableProduct(client());
        final ProductDiscountPredicate predicate =
                ProductDiscountPredicate.of("product.id = \"" + product.getId() + "\"");
        final AbsoluteProductDiscountValue discountValue = AbsoluteProductDiscountValue.of(EURO_1);
        final LocalizedString name = en("demo product discount");
        final LocalizedString description = en("description");
        final boolean active = true;
        final String sortOrder = randomSortOrder();
        final ProductDiscountDraft discountDraft =
                ProductDiscountDraftBuilder.of()
                    .name(name)
                    .description(description)
                    .predicate(predicate)
                    .value(discountValue)
                    .sortOrder(sortOrder)
                    .isActive(active)
                    .validFrom(start)
                    .validUntil(end)
                    .build();

        final ProductDiscount productDiscount = client().executeBlocking(ProductDiscountCreateCommand.of(discountDraft));

        assertThat(productDiscount.getName()).isEqualTo(name);
        assertThat(productDiscount.getDescription()).isEqualTo(description);
        assertThat(productDiscount.getPredicate()).isEqualTo(predicate.toSpherePredicate());
        assertThat(productDiscount.getValue()).isEqualTo(discountValue);
        assertThat(productDiscount.getSortOrder()).isEqualTo(sortOrder);
        assertThat(productDiscount.isActive()).isEqualTo(active);
        assertThat(productDiscount.getValidFrom()).isEqualTo(start);
        assertThat(productDiscount.getValidUntil()).isEqualTo(end);
        final ProductByIdGet sphereRequest =
                ProductByIdGet.of(product)
                        .plusExpansionPaths(m -> m.masterData().staged().masterVariant().prices().discounted().discount());

        assertEventually(() -> {
            final Product discountedProduct = client().executeBlocking(sphereRequest);
            final List<Price> productPrices = discountedProduct.getMasterData().getStaged().getMasterVariant().getPrices();

            assertThat(productPrices)
                    .overridingErrorMessage("discount object in price is expanded")
                    .matches(prices -> prices.stream().anyMatch(price -> price.getDiscounted() != null && price.getDiscounted().getDiscount().getObj() != null));

            //Query that the messages are returned correctly
            final Query<ProductPriceDiscountsSetMessage> query = MessageQuery.of()
                    .withPredicates(m -> m.resource().is(product))
                    .forMessageType(ProductPriceDiscountsSetMessage.MESSAGE_HINT);
            final List<ProductPriceDiscountsSetMessage> results =
                    client().executeBlocking(query).getResults();
            assertThat(results).isNotEmpty();
            assertThat(results.get(0).getUpdatedPrices()).isNotNull();

        });
        // clean up test
        client().executeBlocking(ProductDiscountDeleteCommand.of(productDiscount));
    }

    @Test
    public void createByJson() {
        final ProductDiscountDraft productDiscountDraft = SphereJsonUtils.readObjectFromResource("drafts-tests/productDiscount.json", ProductDiscountDraft.class);
        withProductDiscount(client(), productDiscountDraft, productDiscount -> {
            assertThat(productDiscount.getName().get(ENGLISH)).isEqualTo("example product discount");
            assertThat(productDiscount.getValue()).isEqualTo(ProductDiscountValue.ofAbsolute(EURO_20));
            assertThat(productDiscount.isActive()).isTrue();
        });
    }
}