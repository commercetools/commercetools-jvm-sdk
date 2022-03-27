package io.sphere.sdk.productselections;

import io.sphere.sdk.client.BlockingSphereClient;
import io.sphere.sdk.models.LocalizedString;
import io.sphere.sdk.productselections.commands.ProductSelectionCreateCommand;
import io.sphere.sdk.productselections.commands.ProductSelectionDeleteCommand;

import java.util.function.Function;
import java.util.function.UnaryOperator;

import static io.sphere.sdk.test.SphereTestUtils.*;

public class ProductSelectionFixtures {
    public static ProductSelection createProductSelection(final BlockingSphereClient client, final ProductSelectionDraft productSelectionDraft) {
        return client.executeBlocking(ProductSelectionCreateCommand.of(productSelectionDraft));
    }

    public static ProductSelection createProductSelectionWithName(final BlockingSphereClient client) {
        final LocalizedString name = en("Winter");
        return createProductSelection(client, ProductSelectionDraft.ofName(name));
    }

    public static void withUpdateableProductSelection(final BlockingSphereClient client, final Function<ProductSelection, ProductSelection> f) {
        final LocalizedString name = en("Summer");
        withUpdateableProductSelection(client, ProductSelectionDraft.ofName(name), f);
    }

    public static void withUpdateableProductSelection(final BlockingSphereClient client, final ProductSelectionDraft productSelectionDraft, final Function<ProductSelection, ProductSelection> f) {
        final ProductSelection productSelection = client.executeBlocking(ProductSelectionCreateCommand.of(productSelectionDraft));
        final ProductSelection updatedEntry = f.apply(productSelection);
        client.executeBlocking(ProductSelectionDeleteCommand.of(updatedEntry));
    }

    public static void withProductSelection(final BlockingSphereClient client, final UnaryOperator<ProductSelectionDraftBuilder> builderMapping, final UnaryOperator<ProductSelection> op) {
        final LocalizedString name = en("Winter");
        final ProductSelectionDraft productSelectionDraft = builderMapping.apply(ProductSelectionDraftBuilder.of(name).key(randomKey())).build();
        final ProductSelection productSelection = client.executeBlocking(ProductSelectionCreateCommand.of(productSelectionDraft));
        final ProductSelection productSelectionToDelete = op.apply(productSelection);
        client.executeBlocking(ProductSelectionDeleteCommand.of(productSelectionToDelete));
    }

    public static void withProductSelection(final BlockingSphereClient client, final UnaryOperator<ProductSelection> op) {
        withProductSelection(client, a -> a, op);
    }
}
