package io.sphere.sdk.models;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import io.sphere.sdk.annotations.CopyFactoryMethod;
import io.sphere.sdk.annotations.FactoryMethod;
import io.sphere.sdk.annotations.ResourceDraftValue;
import io.sphere.sdk.types.CustomDraft;
import io.sphere.sdk.types.CustomFieldsDraft;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Set;

/**
 * Draft for an {@link Asset}.
 *
 * @see AssetDraftBuilder
 */
@JsonDeserialize(as = AssetDraftDsl.class)
@ResourceDraftValue(
        abstractBuilderClass = true,
        copyFactoryMethods = @CopyFactoryMethod(Asset.class),
        factoryMethods = @FactoryMethod(parameterNames = { "sources", "name"}))
public interface AssetDraft extends CustomDraft, WithKey{

    @Override
    String getKey();

    List<AssetSource> getSources();

    LocalizedString getName();

    @Nullable
    LocalizedString getDescription();

    @Nullable
    Set<String> getTags();

    @Override
    @Nullable
    CustomFieldsDraft getCustom();
}
