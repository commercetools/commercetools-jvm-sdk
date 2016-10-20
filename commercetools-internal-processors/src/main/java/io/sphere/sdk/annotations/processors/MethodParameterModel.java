package io.sphere.sdk.annotations.processors;

import java.util.LinkedList;
import java.util.List;

final class MethodParameterModel {
    private String name;
    private String type;
    private List<String> modifiers = new LinkedList<>();

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(final String type) {
        this.type = type;
    }

    public List<String> getModifiers() {
        return modifiers;
    }

    public void setModifiers(final List<String> modifiers) {
        this.modifiers = modifiers;
    }
}
