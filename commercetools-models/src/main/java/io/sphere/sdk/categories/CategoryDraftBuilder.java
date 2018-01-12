package io.sphere.sdk.categories;

import io.sphere.sdk.models.*;
import io.sphere.sdk.types.CustomFieldsDraft;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Optional;

public final class CategoryDraftBuilder extends CategoryDraftBuilderBase<CategoryDraftBuilder>{

    protected CategoryDraftBuilder(@Nullable final List<AssetDraft> assets,
                                   @Nullable final CustomFieldsDraft custom, @Nullable final LocalizedString description,
                                   @Nullable final String externalId, @Nullable final String key,
                                   @Nullable final LocalizedString metaDescription, @Nullable final LocalizedString metaKeywords,
                                   @Nullable final LocalizedString metaTitle, final LocalizedString name,
                                   @Nullable final String orderHint, @Nullable final ResourceIdentifier<Category> parent,
                                   final LocalizedString slug) {

        super(assets,custom,description,externalId,key,metaDescription,metaKeywords,metaTitle,name,orderHint,parent,slug);

    }


    /**
     * @deprecated use {@link CategoryDraftBuilder#parent(ResourceIdentifier)} instead
     */
    @Deprecated
    public CategoryDraftBuilder parent(@Nullable final Referenceable<Category> parent) {
        this.parent = Optional.ofNullable(parent).map(Referenceable::toReference).orElse(null);
        return this;
    }
}
