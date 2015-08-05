package io.sphere.sdk.productdiscounts;

import io.sphere.sdk.client.TestClient;
import io.sphere.sdk.models.LocalizedString;
import io.sphere.sdk.productdiscounts.commands.ProductDiscountCreateCommand;
import io.sphere.sdk.productdiscounts.commands.ProductDiscountDeleteCommand;
import io.sphere.sdk.products.Product;

import java.util.function.BiFunction;
import java.util.function.Function;

import static io.sphere.sdk.products.ProductFixtures.referenceableProduct;
import static io.sphere.sdk.test.SphereTestUtils.*;

public class ProductDiscountFixtures {
    public static void withUpdateableProductDiscount(final TestClient client, final Function<ProductDiscount, ProductDiscount> function) {
        withUpdateableProductDiscount(client, (discount, product) -> function.apply(discount));
    }

    public static void withUpdateableProductDiscount(final TestClient client, final BiFunction<ProductDiscount, Product, ProductDiscount> function) {
        final Product product = referenceableProduct(client);
        final ProductDiscountPredicate predicate =
                ProductDiscountPredicate.of("product.id = \"" + product.getId() + "\"");
        final AbsoluteProductDiscountValue discountValue = AbsoluteProductDiscountValue.of(EURO_1);
        final LocalizedString name = en("demo product discount");
        final LocalizedString description = en("description");
        final boolean active = false;
        final String sortOrder = randomSortOrder();
        final ProductDiscountDraft discountDraft =
                ProductDiscountDraft.of(name, description, predicate, discountValue, sortOrder, active);

        final ProductDiscount productDiscount = client.execute(ProductDiscountCreateCommand.of(discountDraft));

        final ProductDiscount updatedDiscount = function.apply(productDiscount, product);
        client.execute(ProductDiscountDeleteCommand.of(updatedDiscount));
    }

}
