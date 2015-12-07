package io.sphere.sdk.types.queries;

import io.sphere.sdk.queries.LocalizedStringOptionalQueryModel;
import io.sphere.sdk.queries.StringQueryModel;

public interface FieldsQueryModel<T> {
    StringQueryModel<T> ofString(String name);

    LocalizedStringOptionalQueryModel<T> ofLocalizedString(String name);
}
