package io.sphere.sdk.carts;

import com.fasterxml.jackson.annotation.JsonCreator;
import io.sphere.sdk.models.Base;

class ShoppingListsConfigurationImpl extends Base implements ShoppingListsConfiguration {

    private Integer deleteDaysAfterLastModification;

    @JsonCreator
    public ShoppingListsConfigurationImpl(Integer deleteDaysAfterLastModification) {
        this.deleteDaysAfterLastModification = deleteDaysAfterLastModification;
    }

    @Override
    public Integer getDeleteDaysAfterLastModification() {
        return this.deleteDaysAfterLastModification;
    }
    
    static ShoppingListsConfiguration of(final Integer deleteDaysAfterLastModification) {
        return new ShoppingListsConfigurationImpl(deleteDaysAfterLastModification);
    }
    
}
