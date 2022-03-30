package io.sphere.sdk.productselections;


import io.sphere.sdk.models.LocalizedString;
import io.sphere.sdk.types.CustomFieldsDraft;

import javax.annotation.Nullable;

/**
 * Dsl class for {@link ProductSelectionDraft}.
 */
public final class ProductSelectionDraftDsl extends ProductSelectionDraftDslBase<ProductSelectionDraftDsl> {

  ProductSelectionDraftDsl(@Nullable final CustomFieldsDraft custom, @Nullable final String key, final LocalizedString name) {
    super(custom, key, name);
  }

  public ProductSelectionDraftDsl withName(LocalizedString name) {
    return super.withName(name);
  }
}