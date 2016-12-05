package io.sphere.sdk.annotations.processors;

import java.util.LinkedList;
import java.util.List;

import static java.util.Arrays.asList;

final class MethodModel {
    private String name;
    private String returnType;
    private String body;
    private List<String> modifiers = new LinkedList<>();
    private List<MethodParameterModel> parameters = new LinkedList<>();
    private List<AnnotationModel> annotations = new LinkedList<>();

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public String getReturnType() {
        return returnType;
    }

    public void setReturnType(final String returnType) {
        this.returnType = returnType;
    }

    public String getBody() {
        return body;
    }

    public void setBody(final String body) {
        this.body = body;
    }

    public List<String> getModifiers() {
        return modifiers;
    }

    public void addModifiers(final String modifier, final String ... moreModifiers) {
        if (this.modifiers == null) {
            this.modifiers = new LinkedList<>();
        }
        this.modifiers.add(modifier);
        this.modifiers.addAll(asList(moreModifiers));
    }

    public void setModifiers(final List<String> modifiers) {
        this.modifiers = modifiers;
    }

    public List<MethodParameterModel> getParameters() {
        return parameters;
    }

    public void setParameters(final List<MethodParameterModel> parameters) {
        this.parameters = parameters;
    }

    public List<AnnotationModel> getAnnotations() {
        return annotations;
    }

    public void setAnnotations(final List<AnnotationModel> annotations) {
        this.annotations = annotations;
    }

    public void addAnnotation(final AnnotationModel annotationModel) {
        annotations.add(annotationModel);
    }
}
