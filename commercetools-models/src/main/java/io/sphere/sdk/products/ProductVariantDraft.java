package io.sphere.sdk.products;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import io.sphere.sdk.annotations.CopyFactoryMethod;
import io.sphere.sdk.annotations.FactoryMethod;
import io.sphere.sdk.annotations.ResourceDraftValue;
import io.sphere.sdk.models.AssetDraft;
import io.sphere.sdk.products.attributes.AttributeDraft;

import javax.annotation.Nullable;
import java.util.List;

/**
 * @see ProductVariantDraftBuilder
 */
@ResourceDraftValue(
        abstractBuilderClass = true,
        copyFactoryMethods = @CopyFactoryMethod(ProductVariant.class),
        factoryMethods = @FactoryMethod(parameterNames = {}))
@JsonDeserialize(as = ProductVariantDraftDsl.class)
public interface ProductVariantDraft {
    @Nullable
    String getSku();

    @Nullable
    List<PriceDraft> getPrices();

    @Nullable
    List<AttributeDraft> getAttributes();

    @Nullable
    List<Image> getImages();

    @Nullable
    String getKey();

    @Nullable
    List<AssetDraft> getAssets();
}
