package io.sphere.sdk.producttypes.commands.updateactions;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.sphere.sdk.commands.UpdateActionImpl;
import io.sphere.sdk.producttypes.ProductType;

/**
 * Change Attribute Definition IsSearchable.
 *
 * {@doc.gen intro}
 *
 * {@include.example io.sphere.sdk.producttypes.commands.ProductTypeUpdateCommandIntegrationTest#changeIsSearchable()}
 */
public final class ChangeIsSearchable extends UpdateActionImpl<ProductType> {
    private final String attributeName;
    private final Boolean searchable;

    private ChangeIsSearchable(final String attributeName, final Boolean searchable) {
        super("changeIsSearchable");
        this.attributeName = attributeName;
        this.searchable = searchable;
    }

    public static ChangeIsSearchable of(final String attributeName, final Boolean searchable) {
        return new ChangeIsSearchable(attributeName, searchable);
    }

    public String getAttributeName() {
        return attributeName;
    }

    @JsonProperty("isSearchable")
    public Boolean isSearchable() {
        return searchable;
    }
}
