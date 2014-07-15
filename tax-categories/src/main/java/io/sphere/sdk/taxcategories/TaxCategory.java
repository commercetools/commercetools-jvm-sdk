package io.sphere.sdk.taxcategories;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import java.util.Optional;
import io.sphere.sdk.models.DefaultModel;
import io.sphere.sdk.models.Reference;

import java.util.List;

@JsonDeserialize(as=TaxCategoryImpl.class)
public interface TaxCategory extends DefaultModel<TaxCategory> {
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

    @Override
    default Reference<TaxCategory> toReference() {
        return reference(this);
    }

    public static Reference<TaxCategory> reference(final TaxCategory taxCategory) {
        return new Reference<>(typeId(), taxCategory.getId(), Optional.ofNullable(taxCategory));
    }

    public static String typeId(){
        return "tax-category";
    }

    public static TaxCategoryQuery query() {
        return new TaxCategoryQuery();
    }
}
