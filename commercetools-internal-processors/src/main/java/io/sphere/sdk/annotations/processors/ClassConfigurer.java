package io.sphere.sdk.annotations.processors;

import javax.lang.model.element.TypeElement;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Function;
import java.util.function.UnaryOperator;
import java.util.stream.Collectors;

import static io.sphere.sdk.annotations.processors.ClassModelFactory.createField;
import static io.sphere.sdk.annotations.processors.ResourceDraftBuilderClassModelFactory.BEAN_GETTER_PREDICATE;
import static java.util.Arrays.asList;
import static java.util.Collections.singletonList;

final class ClassConfigurer {
    public static SourceHolder ofSource(final TypeElement typeElement) {
        return new SourceHolder(typeElement);
    }

    public static class SourceHolder {
        private final TypeElement typeElement;

        private SourceHolder(final TypeElement typeElement) {
            this.typeElement = typeElement;
        }

        public PackageHolder samePackageFromSource() {
            return packageFrom(typeElement);
        }

        public PackageHolder packageFrom(final TypeElement typeElement) {
            final String packageName = ClassModelFactory.packageName(typeElement);
            return new PackageHolder(packageName, typeElement);
        }
    }

    public static class PackageHolder {
        private final String packageName;
        private final TypeElement typeElement;

        private PackageHolder(final String packageName, final TypeElement typeElement) {
            this.packageName = packageName;
            this.typeElement = typeElement;
        }

        public ImportHolder imports() {
            return new ImportHolder(typeElement, this);
        }
    }

    public static class ImportHolder {
        private final TypeElement typeElement;
        private final List<String> imports = new LinkedList<>();
        private final PackageHolder packageHolder;

        public ImportHolder(final TypeElement typeElement, final PackageHolder packageHolder) {

            this.typeElement = typeElement;
            this.packageHolder = packageHolder;
        }

        public ImportHolder withDefaultImports() {
            return addImport("javax.annotation.Nullable");
        }

        public ImportHolder addImport(final String s) {
            imports.add(s);
            return this;
        }

        public ClassModifierHolder modifiers(final String ... modifiers) {
            return modifiers(asList(modifiers));
        }

        public ClassModifierHolder modifiers(final List<String> modifiers) {
            return new ClassModifierHolder(modifiers, this);
        }
    }

    public static class ClassModifierHolder {
        private final List<String> modifiers;
        private final ImportHolder importHolder;

        private ClassModifierHolder(final List<String> modifiers, final ImportHolder importHolder) {
            this.modifiers = modifiers;
            this.importHolder = importHolder;
        }

        public ClassTypeHolder classType() {
            return new ClassTypeHolder(ClassType.CLASS, this, importHolder.packageHolder.typeElement);
        }
    }

    public static class ClassTypeHolder {
        private final ClassType classType;
        private final ClassModifierHolder classModifierHolder;
        private final TypeElement typeElement;

        private ClassTypeHolder(final ClassType classType, final ClassModifierHolder classModifierHolder, final TypeElement typeElement) {
            this.classType = classType;
            this.classModifierHolder = classModifierHolder;
            this.typeElement = typeElement;
        }

        public ClassNameHolder className(final UnaryOperator<String> op) {
            return new ClassNameHolder(op.apply(this.classModifierHolder.importHolder.packageHolder.typeElement.getSimpleName().toString()), this, typeElement);
        }
    }

    public static class ClassNameHolder {
        private final TypeElement typeElement;
        private final ClassModelBuilder builder;

        private ClassNameHolder(final String name, final ClassTypeHolder classTypeHolder, final TypeElement typeElement) {
            this.typeElement = typeElement;
            this.builder = ClassModelBuilder.of(name, classTypeHolder.classType);
            this.builder.packageName(classTypeHolder.classModifierHolder.importHolder.packageHolder.packageName);
            classTypeHolder.classModifierHolder.importHolder.imports.forEach(imp -> builder.addImport(imp));
            classTypeHolder.classModifierHolder.modifiers.forEach(modifier -> builder.addModifiers(modifier));
        }

        public BaseClassHolder extending(final String baseClassName) {
            return new BaseClassHolder(baseClassName, builder, typeElement);
        }

        public BaseClassHolder extending(final Class<?> baseClass) {
            return extending(baseClass.getName());
        }
    }

    public static class BaseClassHolder {
        private final ClassModelBuilder builder;
        private final TypeElement typeElement;

        private BaseClassHolder(final String baseClassName, final ClassModelBuilder builder, final TypeElement typeElement) {
            this.builder = builder;
            this.typeElement = typeElement;
            builder.setBaseClassName(baseClassName);
        }

        public InterfacesHolder implementingBasedOnSourceName(final Function<String, String> op) {
            return implementing(op.apply(typeElement.getSimpleName().toString()));
        }

        public InterfacesHolder implementing(final String ... interfaces) {
            return new InterfacesHolder(asList(interfaces), builder, typeElement);
        }

        public InterfacesHolder implementing(final TypeElement typeElement) {
            return implementing(typeElement.getSimpleName().toString());
        }
    }

    public static class InterfacesHolder {
        private final ClassModelBuilder builder;
        private final TypeElement typeElement;

        private InterfacesHolder(final List<String> interfaces, final ClassModelBuilder builder, final TypeElement typeElement) {
            this.builder = builder;
            this.typeElement = typeElement;
            builder.interfaces(interfaces);
        }

        public FieldsHolder fields() {
            return new FieldsHolder(builder, typeElement);
        }
    }

    public static class FieldsHolder {
        private final ClassModelBuilder builder;
        private final TypeElement typeElement;

        private FieldsHolder(final ClassModelBuilder builder, final TypeElement typeElement) {
            this.builder = builder;
            this.typeElement = typeElement;
        }

        public FieldsHolder fields(final List<FieldModel> fields) {
            fields.forEach(builder::addField);
            return this;
        }

        public FieldsHolder fieldsFromInterfaceBeanGetters() {
            final List<FieldModel> fields = typeElement.getEnclosedElements()
                    .stream()
                    .filter(BEAN_GETTER_PREDICATE)
                    .map(typeElement -> createField(typeElement))
                    .collect(Collectors.toList());
            return fields(fields);
        }

        public ConstructorsHolder constructors() {
            return new ConstructorsHolder(builder, typeElement);
        }
    }

    public static class ConstructorsHolder {
        private final ClassModelBuilder builder;
        private final TypeElement typeElement;

        private ConstructorsHolder(final ClassModelBuilder builder, final TypeElement typeElement) {
            this.builder = builder;
            this.typeElement = typeElement;
        }

        public ConstructorsHolder constructorForAllFields() {
            ResourceDraftBuilderClassModelFactory.addConstructors(builder);
            return new ConstructorsHolder(builder, typeElement);
        }

        public MethodsHolder methods() {
            return new MethodsHolder(builder, typeElement);
        }
    }

    public static class MethodsHolder {
        private final ClassModelBuilder builder;
        private final TypeElement typeElement;

        private MethodsHolder(final ClassModelBuilder builder, final TypeElement typeElement) {
            this.builder = builder;
            this.typeElement = typeElement;
        }

        public MethodsHolder factoryMethodsAccordingToAnnotations() {
            ClassModelFactory.addFactoryMethods(builder, typeElement);
            return this;
        }

        public MethodsHolder builderMethods() {
            ResourceDraftBuilderClassModelFactory.addBuilderMethods(builder, typeElement);
            return this;
        }

        public MethodsHolder buildMethod() {
            ResourceDraftBuilderClassModelFactory.addBuildMethod(builder, typeElement);
            return this;
        }

        public ClassModel build() {
            return builder.build();
        }
    }
}
