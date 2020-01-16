package io.sphere.sdk.carts;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

@JsonDeserialize(as = CartsConfigurationImpl.class)
public interface CartsConfiguration {
    
    Integer getDeleteDaysAfterLastModification();

    static CartsConfiguration of(final Integer deleteDaysAfterLastModification) {
        return CartsConfigurationImpl.of(deleteDaysAfterLastModification);
    }
    
}