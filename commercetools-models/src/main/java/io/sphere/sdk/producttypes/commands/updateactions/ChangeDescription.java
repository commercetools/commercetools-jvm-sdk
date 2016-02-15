package io.sphere.sdk.producttypes.commands.updateactions;

import io.sphere.sdk.commands.UpdateActionImpl;
import io.sphere.sdk.producttypes.ProductType;

/**
 * Changes the description.
 *
 * {@doc.gen intro}
 *
 * {@include.example io.sphere.sdk.producttypes.commands.ProductTypeUpdateCommandIntegrationTest#changeDescription()}
 */
public final class ChangeDescription extends UpdateActionImpl<ProductType> {
    private final String description;

    private ChangeDescription(final String description) {
        super("changeDescription");
        this.description = description;
    }

    public static ChangeDescription of(final String description) {
        return new ChangeDescription(description);
    }

    public String getDescription() {
        return description;
    }
}
