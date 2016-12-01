package io.sphere.sdk.annotations.processors;

import io.sphere.sdk.models.Base;

import java.util.LinkedList;
import java.util.List;

import static java.util.Arrays.asList;

final class ClassModelBuilder extends Base {
    private String name;
    private String baseClassName;
    private String packageName;
    private String additions;
    private String javadoc;
    private String type;
    private final List<String> modifiers = new LinkedList<>();
    private final List<MethodModel> methods = new LinkedList<>();
    private final List<MethodModel> constructors = new LinkedList<>();
    private final List<FieldModel> fields = new LinkedList<>();
    private final List<String> imports = new LinkedList<>();
    private List<String> interfaces = new LinkedList<>();
    private String constructorEndContent;
    private List<AnnotationModel> annotations = new LinkedList<>();

    public ClassModelBuilder addModifiers(final String modifier, final String ... more) {
        modifiers.add(modifier);
        modifiers.addAll(asList(more));
        return this;
    }

    public static ClassModelBuilder of(final String name, final ClassType type) {
        final ClassModelBuilder b = new ClassModelBuilder();
        b.name = name;
        b.type = type.name().toLowerCase();
        return b;
    }

    public ClassModel build() {
        final ClassModel r = new ClassModel();
        r.setName(name);
        r.setType(type);
        r.setModifiers(modifiers);
        r.setPackageName(packageName);
        r.setMethods(methods);
        r.setFields(fields);
        r.setImports(imports);
        r.setInterfaces(interfaces);
        r.setConstructors(constructors);
        r.setBaseClassName(baseClassName);
        r.setJavadoc(javadoc);
        r.setAdditions(additions);
        r.setConstructorEndContent(constructorEndContent);
        r.setAnnotations(annotations);
        return r;
    }

    public ClassModelBuilder packageName(final String s) {
        this.packageName = s;
        return this;
    }

    public ClassModelBuilder addInterface(final String interfaceName) {
        interfaces.add(interfaceName);
        return this;
    }

    public ClassModelBuilder interfaces(final List<String> interfaces) {
        this.interfaces = interfaces;
        return this;
    }

    public ClassModelBuilder addMethod(final MethodModel method) {
        methods.add(method);
        return this;
    }

    public String getName() {
        return name;
    }

    public ClassModelBuilder addField(final FieldModel field) {
        fields.add(field);
        return this;
    }

    public ClassModelBuilder addImport(final String s) {
        imports.add(s);
        return this;
    }


    public ClassModelBuilder addConstructor(final MethodModel methodModel) {
        constructors.add(methodModel);
        return this;
    }

    public ClassModelBuilder addAnnotation(final AnnotationModel annotationModel) {
        annotations.add(annotationModel);
        return this;
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

    public void setConstructorEndContent(final String constructorEndContent) {
        this.constructorEndContent = constructorEndContent;
    }

    public String getConstructorEndContent() {
        return constructorEndContent;
    }
}
