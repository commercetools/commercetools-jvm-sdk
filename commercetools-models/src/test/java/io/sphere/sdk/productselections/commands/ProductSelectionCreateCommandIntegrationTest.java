package io.sphere.sdk.productselections.commands;

import io.sphere.sdk.json.SphereJsonUtils;
import io.sphere.sdk.models.LocalizedString;
import io.sphere.sdk.productselections.ProductSelection;
import io.sphere.sdk.productselections.ProductSelectionDraft;
import io.sphere.sdk.productselections.ProductSelectionType;
import io.sphere.sdk.productselections.queries.ProductSelectionQuery;
import io.sphere.sdk.test.IntegrationTest;
import io.sphere.sdk.types.commands.TypeDeleteCommand;
import io.sphere.sdk.types.queries.TypeQuery;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.IOException;

import static io.sphere.sdk.test.SphereTestUtils.en;
import static org.assertj.core.api.Assertions.assertThat;

public class ProductSelectionCreateCommandIntegrationTest extends IntegrationTest {
    @BeforeClass
    public static void prepare() {
        client().executeBlocking(TypeQuery.of().withPredicates(m -> m.key().is("json-demo-type-key")))
                .getResults()
                .forEach(type -> {
                    client().executeBlocking(ProductSelectionQuery.of().withPredicates(m -> m.custom().type().is(type)))
                            .getResults()
                            .forEach(productSelection -> client().executeBlocking(ProductSelectionDeleteCommand.of(productSelection)));
                    client().executeBlocking(TypeDeleteCommand.of(type));
                });
    }

    @Test
    public void execution() throws Exception {
        final LocalizedString name  = en("Summer");
        final ProductSelectionDraft productSelectionDraft = ProductSelectionDraft.ofName(name);

        final ProductSelection productSelection = client().executeBlocking(ProductSelectionCreateCommand.of(productSelectionDraft));
        assertThat(productSelection.getType()).isEqualTo(ProductSelectionType.INDIVIDUAL);
    }

    @Test
    public void productSelectionCreateFromJson() throws IOException {
        final ProductSelection productSelection = SphereJsonUtils.readObjectFromResource("productSelections/product-selection.json", ProductSelection.class);

        assertThat(productSelection.getType()).isEqualTo(ProductSelectionType.INDIVIDUAL);
    }

}
