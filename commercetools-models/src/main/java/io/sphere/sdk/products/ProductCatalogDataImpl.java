package io.sphere.sdk.products;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.annotation.Nullable;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static io.sphere.sdk.utils.SphereInternalUtils.listOf;

class ProductCatalogDataImpl extends ProductCatalogDataImplBase {

    @JsonCreator
    ProductCatalogDataImpl(@Nullable final ProductData current,
                               @JsonProperty("hasStagedChanges") final Boolean hasStagedChanges,
                               @JsonProperty("published") final Boolean published, final ProductData staged) {
        super(current, hasStagedChanges, published, staged);
    }

    @Nullable
    public ProductData getCurrent() {
        return isPublished() ? super.getCurrent() : null;
    }

    @Nullable
    public ProductData getCurrentUnsafe() {
        return super.getCurrent();
    }

    // https://github.com/commercetools/commercetools-jvm-sdk/issues/239
    void setProductId(final String id) {
        final List<ProductData> currentAsList = Optional.ofNullable(getCurrent()).map(c -> Collections.singletonList(c)).orElse(Collections.emptyList());
        listOf(currentAsList, getStaged()).stream()
                .filter(x -> x instanceof ProductDataImpl)
                .forEach(x -> ((ProductDataImpl)x).setProductId(id));
    }
}
