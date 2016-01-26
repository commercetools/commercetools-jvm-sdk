package io.sphere.sdk.taxcategories.commands.updateactions;

import io.sphere.sdk.commands.UpdateActionImpl;
import io.sphere.sdk.taxcategories.TaxCategory;

/**
 * Updates the name of a tax category.
 *
 * {@doc.gen intro}
 *
 * {@include.example io.sphere.sdk.taxcategories.commands.TaxCategoryUpdateCommandTest#changeName()}
 */
public class ChangeName extends UpdateActionImpl<TaxCategory> {
    private final String name;

    private ChangeName(final String name) {
        super("changeName");
        this.name = name;
    }

    public static ChangeName of(final String name) {
        return new ChangeName(name);
    }

    public String getName() {
        return name;
    }
}
