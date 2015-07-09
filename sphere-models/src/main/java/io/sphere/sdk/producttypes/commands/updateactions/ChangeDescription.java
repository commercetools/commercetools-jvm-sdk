package io.sphere.sdk.producttypes.commands.updateactions;

import io.sphere.sdk.commands.UpdateAction;
import io.sphere.sdk.producttypes.ProductType;

/**
 *
 * {@include.example io.sphere.sdk.producttypes.commands.ProductTypeUpdateCommandTest#changeDescription()}
 */
public class ChangeDescription extends UpdateAction<ProductType> {
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
