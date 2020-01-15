package io.sphere.sdk.carts;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

@JsonDeserialize(as = ShoppingListsConfigurationImpl.class)
public interface ShoppingListsConfiguration {

    Integer getDeleteDaysAfterLastModification();
    
    static ShoppingListsConfiguration of(final Integer deleteDaysAfterLastModification) {
        return ShoppingListsConfigurationImpl.of(deleteDaysAfterLastModification);
    }
    
}
