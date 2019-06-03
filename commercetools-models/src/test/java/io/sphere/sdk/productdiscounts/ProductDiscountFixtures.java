package io.sphere.sdk.productdiscounts;

import io.sphere.sdk.client.BlockingSphereClient;
import io.sphere.sdk.models.LocalizedString;
import io.sphere.sdk.productdiscounts.commands.ProductDiscountCreateCommand;
import io.sphere.sdk.productdiscounts.commands.ProductDiscountDeleteCommand;
import io.sphere.sdk.products.Product;
import io.sphere.sdk.products.ProductVariantDraftBuilder;
import io.sphere.sdk.products.queries.ProductByIdGet;
import io.sphere.sdk.test.SphereTestUtils;

import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;

import static io.sphere.sdk.products.ProductFixtures.*;
import static io.sphere.sdk.test.SphereTestUtils.*;

public class ProductDiscountFixtures {
    public static void withUpdateableProductDiscount(final BlockingSphereClient client, final Function<ProductDiscount, ProductDiscount> function) {
        withUpdateableProductDiscount(client, (discount, product) -> function.apply(discount));
    }

    public static void withUpdateableProductDiscount(final BlockingSphereClient client, final BiFunction<ProductDiscount, Product, ProductDiscount> function) {
        withUpdateableProduct(client, builder -> builder.masterVariant(ProductVariantDraftBuilder.of().price(PRICE).build()), product -> {
            final ProductDiscountPredicate predicate =
                    ProductDiscountPredicate.of("product.id = \"" + product.getId() + "\"");
            final AbsoluteProductDiscountValue discountValue = AbsoluteProductDiscountValue.of(EURO_1);
            final LocalizedString name = en("demo product discount");
            final LocalizedString description = en("description");
            final boolean active = true;
            final String sortOrder = randomSortOrder();
            final String key = SphereTestUtils.randomKey();
            final ProductDiscountDraft discountDraft =
                    ProductDiscountDraft.of(name, key, description, predicate, discountValue, sortOrder, active);

            final ProductDiscount productDiscount = client.executeBlocking(ProductDiscountCreateCommand.of(discountDraft));

            final ProductDiscount updatedDiscount = function.apply(productDiscount, product);
            client.executeBlocking(ProductDiscountDeleteCommand.of(updatedDiscount));
            return client.executeBlocking(ProductByIdGet.of(product));
        });
    }

    public static void withProductDiscount(final BlockingSphereClient client, final ProductDiscountDraft draft, final Consumer<ProductDiscount> consumer) {
        final ProductDiscount productDiscount = client.executeBlocking(ProductDiscountCreateCommand.of(draft));
        consumer.accept(productDiscount);
        client.executeBlocking(ProductDiscountDeleteCommand.of(productDiscount));
    }
    
    public static void withProductDiscount(final BlockingSphereClient client, Consumer<ProductDiscount> consumer) {
        withUpdateableProduct(client, product -> {
            final ProductDiscountPredicate predicate =
                    ProductDiscountPredicate.of("product.id = \"" + product.getId() + "\"");
            final AbsoluteProductDiscountValue discountValue = AbsoluteProductDiscountValue.of(EURO_1);
            final LocalizedString name = en("demo product discount");
            final LocalizedString description = en("description");
            final boolean active = true;
            final String sortOrder = randomSortOrder();
            final String key = SphereTestUtils.randomKey();
            final ProductDiscountDraft discountDraft =
                    ProductDiscountDraft.of(name, key, description, predicate, discountValue, sortOrder, active);
            final ProductDiscount productDiscount = client.executeBlocking(ProductDiscountCreateCommand.of(discountDraft));
            consumer.accept(productDiscount);
            
            return product;
        });
    }
}
