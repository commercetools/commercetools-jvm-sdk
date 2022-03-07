package io.sphere.sdk.productselections;


import io.sphere.sdk.models.LocalizedString;

import javax.annotation.Nullable;

/**
 * Dsl class for {@link ProductSelectionDraft}.
 */
public final class ProductSelectionDraftDsl extends ProductSelectionDraftDslBase<ProductSelectionDraftDsl> {

  ProductSelectionDraftDsl(@Nullable final String key, final LocalizedString name) {
    super(key, name);
  }

  public static ProductSelectionDraftDsl of(final LocalizedString name) {
    return of(name);
  }
}