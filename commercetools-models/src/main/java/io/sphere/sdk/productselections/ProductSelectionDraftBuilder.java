package io.sphere.sdk.productselections;

import io.sphere.sdk.models.*;
import io.sphere.sdk.types.CustomFieldsDraft;

import javax.annotation.Nullable;


/**
 * Builder for {@link ProductSelectionDraft}.
 */
public final class ProductSelectionDraftBuilder extends ProductSelectionDraftBuilderBase<ProductSelectionDraftBuilder> {

  ProductSelectionDraftBuilder(@Nullable final CustomFieldsDraft custom, @Nullable final String key, final LocalizedString name) {
    super(custom, key, name);
  }
}
