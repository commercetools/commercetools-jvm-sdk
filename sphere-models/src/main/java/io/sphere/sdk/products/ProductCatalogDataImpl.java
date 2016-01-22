package io.sphere.sdk.products;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.sphere.sdk.models.Base;

import javax.annotation.Nullable;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static io.sphere.sdk.utils.SphereInternalUtils.listOf;

class ProductCatalogDataImpl extends Base implements ProductCatalogData {
    @JsonProperty("published")
    private final Boolean isPublished;
    @JsonProperty("hasStagedChanges")
    private final Boolean hasStagedChanges;
    @Nullable
    private final ProductData current;
    private final ProductData staged;

    @JsonCreator
    ProductCatalogDataImpl(final Boolean isPublished, @Nullable final ProductData current, final ProductData staged,
                           final Boolean hasStagedChanges) {
        this.isPublished = isPublished;
        this.current = current;
        this.staged = staged;
        this.hasStagedChanges = hasStagedChanges;
    }

    public Boolean isPublished() {
        return isPublished;
    }

    @Nullable
    public ProductData getCurrent() {
        return isPublished ? current : null;
    }

    public ProductData getStaged() {
        return staged;
    }

    public Boolean hasStagedChanges() {
        return hasStagedChanges;
    }

    public void setProductId(final String id) {
        final List<ProductData> currentAsList = Optional.ofNullable(current).map(c -> Collections.singletonList(c)).orElse(Collections.emptyList());
        listOf(currentAsList, staged).stream()
                .filter(x -> x instanceof ProductDataImpl)
                .forEach(x -> ((ProductDataImpl)x).setProductId(id));
    }
}
