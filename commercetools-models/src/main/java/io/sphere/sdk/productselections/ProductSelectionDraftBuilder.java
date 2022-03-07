package io.sphere.sdk.productselections;

import io.sphere.sdk.models.*;

import javax.annotation.Nullable;


/**
 * Builder for {@link ProductSelectionDraft}.
 */
public final class ProductSelectionDraftBuilder extends ProductSelectionDraftBuilderBase<ProductSelectionDraftBuilder> {

  ProductSelectionDraftBuilder(@Nullable final String key, final LocalizedString name) {
    super(key, name);
  }

  public static ProductSelectionDraftBuilder of(final LocalizedString name) {
    return of(name);
  }
}
