package io.sphere.sdk.annotations.processors;

import java.util.LinkedList;
import java.util.List;

import static java.util.Arrays.asList;

final class ClassModelBuilder {
    private String name;
    private String packageName;
    private String type;
    private List<String> modifiers = new LinkedList<>();
    private List<MethodModel> methods = new LinkedList<>();

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
        return r;
    }

    public ClassModelBuilder packageName(final String s) {
        this.packageName = s;
        return this;
    }

    public void addMethod(final MethodModel method) {
        methods.add(method);
    }

    public String getName() {
        return name;
    }
}
