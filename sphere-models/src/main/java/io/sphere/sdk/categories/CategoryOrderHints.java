package io.sphere.sdk.categories;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.sphere.sdk.models.Base;
import io.sphere.sdk.utils.MapUtils;

import javax.annotation.Nullable;
import java.util.*;

public class CategoryOrderHints extends Base {
    @JsonIgnore
    private final Map<String, String> orderHints;

    @JsonCreator
    private CategoryOrderHints(final Map<String, String> orderHints) {
        this.orderHints = Optional.ofNullable(orderHints)
                .map(MapUtils::immutableCopyOf)
                .orElseGet(() -> new LinkedHashMap<>());
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
    private Map<String, String> getInternalValues() {
        return Collections.unmodifiableMap(orderHints);
    }
}
