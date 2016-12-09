package io.sphere.sdk.annotations.processors;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

final class MethodParameterModel {
    private String name;
    private String type;
    private List<String> modifiers = new LinkedList<>();
    private List<AnnotationModel> annotations = new LinkedList<>();

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

    public List<AnnotationModel> getAnnotations() {
        return annotations;
    }

    public void setAnnotations(final List<AnnotationModel> annotations) {
        this.annotations = annotations;
    }

    public static MethodParameterModel ofTypeAndName(final String type, final String name) {
        final MethodParameterModel result = new MethodParameterModel();
        result.setType(type);
        result.setName(name);
        result.setModifiers(Collections.singletonList("final"));
        return result;
    }
}
