package io.sphere.sdk.annotations.processors;

import io.sphere.sdk.models.Base;

import java.util.LinkedList;
import java.util.List;

import static java.util.Arrays.asList;

public class FieldModel extends Base {
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

    public void addModifiers(final String modifier, final String ... moreModifiers) {
        modifiers.add(modifier);
        modifiers.addAll(asList(moreModifiers));
    }

    public List<AnnotationModel> getAnnotations() {
        return annotations;
    }

    public void setAnnotations(final List<AnnotationModel> annotations) {
        this.annotations = annotations;
    }
}
