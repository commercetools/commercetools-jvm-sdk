package io.sphere.sdk.stores;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import io.sphere.sdk.annotations.FactoryMethod;
import io.sphere.sdk.annotations.ResourceDraftValue;
import io.sphere.sdk.models.ResourceIdentifier;
import io.sphere.sdk.productselections.ProductSelection;


@JsonDeserialize(as = ProductSelectionSettingDraftDsl.class)
@ResourceDraftValue(factoryMethods = @FactoryMethod(parameterNames = {"productSelection","active"}))
public interface ProductSelectionSettingDraft {

    ResourceIdentifier<ProductSelection> getProductSelection();

    Boolean getActive();

}
