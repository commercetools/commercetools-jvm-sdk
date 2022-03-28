package io.sphere.sdk.productselections;

import io.sphere.sdk.client.BlockingSphereClient;
import io.sphere.sdk.customers.Customer;
import io.sphere.sdk.customers.CustomerDraft;
import io.sphere.sdk.customers.CustomerSignInResult;
import io.sphere.sdk.customers.commands.CustomerCreateCommand;
import io.sphere.sdk.models.LocalizedString;
import io.sphere.sdk.models.ResourceIdentifier;
import io.sphere.sdk.productdiscounts.AbsoluteProductDiscountValue;
import io.sphere.sdk.productdiscounts.ProductDiscount;
import io.sphere.sdk.productdiscounts.ProductDiscountDraft;
import io.sphere.sdk.productdiscounts.ProductDiscountPredicate;
import io.sphere.sdk.productdiscounts.commands.ProductDiscountCreateCommand;
import io.sphere.sdk.products.Product;

import io.sphere.sdk.productselections.commands.ProductSelectionCreateCommand;
import io.sphere.sdk.productselections.commands.ProductSelectionDeleteCommand;
import io.sphere.sdk.stores.Store;
import io.sphere.sdk.stores.StoreFixtures;
import io.sphere.sdk.test.SphereTestUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.UnaryOperator;

import static io.sphere.sdk.products.ProductFixtures.withUpdateableProduct;
import static io.sphere.sdk.test.SphereTestUtils.*;

public class ProductSelectionFixtures {

    public static void withUpdateableProductSelection(final BlockingSphereClient client, final Function<ProductSelection, ProductSelection> function) {
        withUpdateableProductSelection(client, (productSelection, product) -> function.apply(productSelection));
    }

    public static void withUpdateableProductSelection(final BlockingSphereClient client, final BiFunction<ProductSelection, Product, ProductSelection> function) {
        final LocalizedString name = en("Summer");

        final ProductSelectionDraft productSelectionDraft =
                    ProductSelectionDraft.ofName(name);

        final ProductSelection productSelection = client.executeBlocking(ProductSelectionCreateCommand.of(productSelectionDraft));

        client.executeBlocking(ProductSelectionDeleteCommand.of(productSelection));
    }

    public static void withProductSelection(final BlockingSphereClient client, final ProductSelectionDraft draft, final Consumer<ProductSelection> consumer) {
        final ProductSelection productSelection = client.executeBlocking(ProductSelectionCreateCommand.of(draft));
        consumer.accept(productSelection);
        client.executeBlocking(ProductSelectionDeleteCommand.of(productSelection));
    }

    public static void withProductSelection(final BlockingSphereClient client, Consumer<ProductSelection> consumer) {
        final LocalizedString name = en("Winter");
        final ProductSelectionDraft productSelectionDraft = ProductSelectionDraft.ofName(name);
        final ProductSelection productSelection = client.executeBlocking(ProductSelectionCreateCommand.of(productSelectionDraft));
        consumer.accept(productSelection);
    }
}
