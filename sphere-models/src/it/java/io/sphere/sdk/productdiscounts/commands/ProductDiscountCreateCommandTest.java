package io.sphere.sdk.productdiscounts.commands;

import com.fasterxml.jackson.databind.JsonNode;
import io.sphere.sdk.client.JsonNodeSphereRequest;
import io.sphere.sdk.models.LocalizedStrings;
import io.sphere.sdk.productdiscounts.AbsoluteProductDiscountValue;
import io.sphere.sdk.productdiscounts.ProductDiscount;
import io.sphere.sdk.productdiscounts.ProductDiscountDraft;
import io.sphere.sdk.productdiscounts.ProductDiscountPredicate;
import io.sphere.sdk.products.Price;
import io.sphere.sdk.products.Product;
import io.sphere.sdk.products.queries.ProductByIdFetch;
import io.sphere.sdk.test.IntegrationTest;
import org.junit.Test;

import java.util.List;
import java.util.Optional;

import static io.sphere.sdk.products.ProductFixtures.referenceableProduct;
import static io.sphere.sdk.test.SphereTestUtils.*;
import static org.assertj.core.api.Assertions.assertThat;

public class ProductDiscountCreateCommandTest extends IntegrationTest {
    @Test
    public void execution() throws Exception {
        final Product product = referenceableProduct(client());
        final ProductDiscountPredicate predicate =
                ProductDiscountPredicate.of("product.id = \"" + product.getId() + "\"");
        final AbsoluteProductDiscountValue discountValue = AbsoluteProductDiscountValue.of(EURO_1);
        final LocalizedStrings name = en("demo product discount");
        final Optional<LocalizedStrings> description = Optional.of(en("description"));
        final boolean active = true;
        final String sortOrder = randomSortOrder();
        final ProductDiscountDraft discountDraft =
                ProductDiscountDraft.of(name, description, predicate, discountValue, sortOrder, active);

        final ProductDiscount productDiscount = execute(ProductDiscountCreateCommand.of(discountDraft));

        assertThat(productDiscount.getName()).isEqualTo(name);
        assertThat(productDiscount.getDescription()).isEqualTo(description);
        assertThat(productDiscount.getPredicate()).isEqualTo(predicate.toSpherePredicate());
        assertThat(productDiscount.getValue()).isEqualTo(discountValue);
        assertThat(productDiscount.getSortOrder()).isEqualTo(sortOrder);
        assertThat(productDiscount.isActive()).isEqualTo(active);

        final ProductByIdFetch sphereRequest = ProductByIdFetch.of(product).plusExpansionPaths(m -> m.masterData().staged().masterVariant().prices().discounted().discount());

        final Product discountedProduct = execute(sphereRequest).get();
        final List<Price> productPrices = discountedProduct.getMasterData().getStaged().getMasterVariant().getPrices();

        assertThat(productPrices.get(0).getDiscounted().get().getDiscount().getObj())
                .overridingErrorMessage("discount object in price is expanded")
                .isPresent();
//        clean up test
        execute(ProductDiscountDeleteCommand.of(productDiscount));
    }
}