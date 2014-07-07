package io.sphere.sdk.taxcategories;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.google.common.base.Optional;
import io.sphere.sdk.models.DefaultModel;

import java.util.List;

@JsonDeserialize(as=TaxCategoryImpl.class)
public interface TaxCategory extends DefaultModel {
    String getName();

    Optional<String> getDescription();

    List<TaxRate> getTaxRates();

    public static TypeReference<TaxCategory> typeReference(){
        return new TypeReference<TaxCategory>() {
            @Override
            public String toString() {
                return "TypeReference<TaxCategory>";
            }
        };
    }

    public static TaxCategoryQuery query() {
        return new TaxCategoryQuery();
    }
}
