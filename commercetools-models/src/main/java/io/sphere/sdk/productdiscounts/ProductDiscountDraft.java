package io.sphere.sdk.productdiscounts;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import io.sphere.sdk.annotations.FactoryMethod;
import io.sphere.sdk.annotations.ResourceDraftValue;
import io.sphere.sdk.models.LocalizedString;
import io.sphere.sdk.models.WithKey;

import javax.annotation.Nullable;
import java.time.ZonedDateTime;

@JsonDeserialize(as = ProductDiscountDraftDsl.class)
@ResourceDraftValue(
        factoryMethods = {@FactoryMethod(parameterNames = {}),
                          @FactoryMethod(parameterNames = {"active", "description", "name", "predicate", "sortOrder", "value"})
}, abstractBuilderClass = true)
public interface ProductDiscountDraft extends WithKey {
    
    @Nullable
    String getKey();
    
    @JsonProperty("isActive")
    Boolean isActive();

    @Nullable
    LocalizedString getDescription();

    LocalizedString getName();

    String getPredicate();

    String getSortOrder();

    @Nullable
    ZonedDateTime getValidFrom();

    @Nullable
    ZonedDateTime getValidUntil();

    ProductDiscountValue getValue();

    static ProductDiscountDraft of(final LocalizedString name, final LocalizedString description, final ProductDiscountPredicate predicate, final ProductDiscountValue value, final String sortOrder, final boolean active) {
        return ProductDiscountDraftDsl.of(active, description, name, predicate.toSpherePredicate(), sortOrder, value);
    }

    static ProductDiscountDraft of(final LocalizedString name, @Nullable final String key, final LocalizedString description, final ProductDiscountPredicate predicate, final ProductDiscountValue value, final String sortOrder, final boolean active) {
        return ProductDiscountDraftDsl.of(active, description, name, predicate.toSpherePredicate(), sortOrder, value).withKey(key);
    }
}
