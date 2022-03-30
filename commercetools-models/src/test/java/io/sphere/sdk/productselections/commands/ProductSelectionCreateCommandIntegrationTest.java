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
    @Test
    public void execution() throws Exception {
        final LocalizedString name  = en("Summer");
        final ProductSelectionDraft productSelectionDraft = ProductSelectionDraft.ofName(name);

        final ProductSelection productSelection = client().executeBlocking(ProductSelectionCreateCommand.of(productSelectionDraft));
        assertThat(productSelection.getType()).isEqualTo(ProductSelectionType.INDIVIDUAL);
        assertThat(productSelection.getName()).isEqualTo(name);
    }
}
