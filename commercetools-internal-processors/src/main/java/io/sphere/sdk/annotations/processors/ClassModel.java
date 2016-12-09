package io.sphere.sdk.annotations.processors;

import java.util.LinkedList;
import java.util.List;

final class ClassModel {
    private String name;
    private String type;
    private String javadoc;
    private String packageName;
    private String additions;
    private String baseClassName;
    private String codeGeneratedInfo;
    private List<String> modifiers = new LinkedList<>();
    private List<MethodModel> methods = new LinkedList<>();
    private List<MethodModel> constructors = new LinkedList<>();
    private List<FieldModel> fields = new LinkedList<>();
    private List<String> imports = new LinkedList<>();
    private List<String> interfaces = new LinkedList<>();
    private String constructorEndContent;
    private boolean isInterface;
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
        isInterface = ClassType.INTERFACE.toString().toLowerCase().equals(type);
    }

    public List<String> getModifiers() {
        return modifiers;
    }

    public void setModifiers(final List<String> modifiers) {
        this.modifiers = modifiers;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(final String packageName) {
        this.packageName = packageName;
    }

    public List<MethodModel> getMethods() {
        return methods;
    }

    public void setMethods(final List<MethodModel> methods) {
        this.methods = methods;
    }

    public List<FieldModel> getFields() {
        return fields;
    }

    public void setFields(final List<FieldModel> fields) {
        this.fields = fields;
    }

    public List<String> getImports() {
        return imports;
    }

    public void setImports(final List<String> imports) {
        this.imports = imports;
    }

    public List<String> getInterfaces() {
        return interfaces;
    }

    public void setInterfaces(final List<String> interfaces) {
        this.interfaces = interfaces;
    }

    public List<MethodModel> getConstructors() {
        return constructors;
    }

    public void setConstructors(final List<MethodModel> constructors) {
        this.constructors = constructors;
    }

    public String getFullyQualifiedName() {
        return packageName + "." + name;
    }

    public String getBaseClassName() {
        return baseClassName;
    }

    public void setBaseClassName(final String baseClassName) {
        this.baseClassName = baseClassName;
    }

    public String getJavadoc() {
        return javadoc;
    }

    public void setJavadoc(final String javadoc) {
        this.javadoc = javadoc;
    }

    public String getAdditions() {
        return additions;
    }

    public void setAdditions(final String additions) {
        this.additions = additions;
    }

    public String getConstructorEndContent() {
        return constructorEndContent;
    }

    public void setConstructorEndContent(final String constructorEndContent) {
        this.constructorEndContent = constructorEndContent;
    }

    public boolean isInterface() {
        return isInterface;
    }

    public List<AnnotationModel> getAnnotations() {
        return annotations;
    }

    public void setAnnotations(final List<AnnotationModel> annotations) {
        this.annotations = annotations;
    }

    public String getCodeGeneratedInfo() {
        return codeGeneratedInfo;
    }

    public void setCodeGeneratedInfo(final String codeGeneratedInfo) {
        this.codeGeneratedInfo = codeGeneratedInfo;
    }
}
