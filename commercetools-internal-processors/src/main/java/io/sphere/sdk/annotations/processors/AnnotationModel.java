package io.sphere.sdk.annotations.processors;

import org.apache.commons.lang3.tuple.Pair;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

final class AnnotationModel {
    private String name;
    private String value;
    private Map<String, Object> values = new HashMap<>();

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(final String value) {
        this.value = value;
    }

    public Map<String, Object> getValues() {
        return values;
    }

    public void setValues(final Map<String, Object> values) {
        this.values = values;
    }
}
