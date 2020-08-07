package io.sphere.sdk.products.queries;

import javax.annotation.Nullable;
import java.util.List;

public interface LocaleProjectionRequestDsl<T> {
    T withLocaleProjection(@Nullable final List<String> localeProjection);

    @Nullable
    ProductProjectionQuery getLocaleProjection();
}
