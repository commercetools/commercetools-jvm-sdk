package io.sphere.sdk.products;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.sphere.sdk.models.Base;
import io.sphere.sdk.utils.SphereInternalUtils;

import javax.annotation.Nullable;
import java.util.*;

public final class CategoryOrderHints extends Base {
    @JsonIgnore
    private final Map<String, String> orderHints;

    @JsonCreator
    private CategoryOrderHints(final Map<String, String> orderHints) {
        this.orderHints = Optional.ofNullable(orderHints)
                .map(SphereInternalUtils::immutableCopyOf)
                .orElseGet(() -> new LinkedHashMap<>());
    }

    @JsonIgnore
    public static CategoryOrderHints of(final String categoryId, final String orderHint) {
        final Map<String, String> entry = Collections.singletonMap(categoryId, orderHint);
        return of(entry);
    }

    @JsonIgnore
    public static CategoryOrderHints of(final Map<String, String> orderHints) {
        return new CategoryOrderHints(orderHints);
    }

    @Nullable
    public String get(final String categoryId) {
        return orderHints.get(categoryId);
    }

    @SuppressWarnings("unused")//used by Jackson JSON mapper
    @JsonAnySetter
    private void set(final String categoryId, final String value) {
        orderHints.put(categoryId, value);
    }

    @JsonAnyGetter//@JsonUnwrap supports not maps, but this construct puts map content on top level
    public Map<String, String> getAsMap() {
        return Collections.unmodifiableMap(orderHints);
    }
}
