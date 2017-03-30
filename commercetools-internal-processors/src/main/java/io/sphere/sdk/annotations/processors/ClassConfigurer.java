package io.sphere.sdk.annotations.processors;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.sphere.sdk.annotations.FactoryMethod;
import io.sphere.sdk.annotations.ResourceDraftValue;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.ImmutablePair;

import javax.lang.model.element.*;
import javax.lang.model.type.DeclaredType;
import java.lang.annotation.Annotation;
import java.util.*;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.UnaryOperator;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.lang.String.format;
import static java.util.Arrays.asList;
import static java.util.Collections.singletonList;
import static java.util.Collections.singletonMap;
import static java.util.stream.Collectors.joining;
import static javax.lang.model.element.Modifier.STATIC;
import static org.apache.commons.lang3.StringUtils.capitalize;

/**
 * high level configuration util to generate classes based on an existing class or interface
 */
final class ClassConfigurer {
    public static final Predicate<Element> BEAN_GETTER_PREDICATE = e -> {
        final String simpleName = e.getSimpleName().toString();
        final boolean isMethod = ElementKind.METHOD.equals(e.getKind());
        return isMethod && hasBeanGetterName(simpleName) && hasNoParameters(e) && !isDefaultMethod(e);
    };

    private static boolean isDefaultMethod(final Element e) {
        return e instanceof ExecutableElement && ((ExecutableElement) e).isDefault();
    }

    private static boolean hasNoParameters(final Element e) {
        return e instanceof ExecutableElement && ((ExecutableElement) e).getParameters().isEmpty();
    }

    private static boolean hasBeanGetterName(final String simpleName) {
        return simpleName.matches("^get[A-Z].*") || simpleName.matches("^is[A-Z].*");
    }

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
            final String packageName = packageName(typeElement);
            return new PackageHolder(packageName, typeElement);
        }

        public PackageHolder subPackageFrom(final TypeElement typeElement, final String subpackage) {
            final String packageName = packageName(typeElement) + "." + subpackage;
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

        public ImportHolder defaultImports() {
            return addImport("javax.annotation.Nullable")
                    .addImport("io.sphere.sdk.models.*")
                    .addImport("java.util.*")
                    .addImport("io.sphere.sdk.utils.*")
                    .addImport("com.fasterxml.jackson.annotation.*");
        }

        public ImportHolder addImport(final String s) {
            imports.add(s);
            return this;
        }

        public JavadocHolder classJavadoc(final String s) {
            return new JavadocHolder(typeElement, this, s);
        }
    }

    public static class JavadocHolder {
        private final TypeElement typeElement;
        private final ImportHolder importHolder;
        private final String javadoc;

        public JavadocHolder(final TypeElement typeElement, final ImportHolder importHolder, final String javadoc) {
            this.typeElement = typeElement;
            this.importHolder = importHolder;
            this.javadoc = javadoc;
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
        private final JavadocHolder holder;

        private ClassModifierHolder(final List<String> modifiers, final JavadocHolder holder) {
            this.modifiers = modifiers;
            this.holder = holder;
        }

        public ClassTypeHolder classType() {
            return new ClassTypeHolder(ClassType.CLASS, this, holder.typeElement);
        }

        public ClassTypeHolder interfaceType() {
            return new ClassTypeHolder(ClassType.INTERFACE, this, holder.typeElement);
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
            return new ClassNameHolder(op.apply(this.classModifierHolder.holder.importHolder.packageHolder.typeElement.getSimpleName().toString()), this, typeElement);
        }
    }

    public static class ClassNameHolder {
        private final TypeElement typeElement;
        private final ClassModelBuilder builder;

        private ClassNameHolder(final String name, final ClassTypeHolder classTypeHolder, final TypeElement typeElement) {
            this.typeElement = typeElement;
            this.builder = ClassModelBuilder.of(name, classTypeHolder.classType);
            this.builder.packageName(classTypeHolder.classModifierHolder.holder.importHolder.packageHolder.packageName);
            classTypeHolder.classModifierHolder.holder.importHolder.imports.forEach(imp -> builder.addImport(imp));
            classTypeHolder.classModifierHolder.modifiers.forEach(modifier -> builder.addModifiers(modifier));
            builder.setJavadoc(classTypeHolder.classModifierHolder.holder.javadoc);
        }

        public InterfacesHolder extending(final String baseClassName) {
            builder.setBaseClassName(baseClassName);
            return interfaces();
        }

        public InterfacesHolder extending(final Class<?> baseClass) {
            return extending(baseClass.getName());
        }

        public InterfacesHolder interfaces() {
            return new InterfacesHolder(builder, typeElement);
        }
    }

    public static class InterfacesHolder {
        private final ClassModelBuilder builder;
        private final TypeElement typeElement;

        private InterfacesHolder(final ClassModelBuilder builder, final TypeElement typeElement) {
            this.builder = builder;
            this.typeElement = typeElement;
        }

        public InterfacesHolder implementingBasedOnSourceName(final Function<String, String> op) {
            return implementing(op.apply(typeElement.getSimpleName().toString()));
        }

        public InterfacesHolder implementing(final String ... interfaces) {
            final List<String> i = new LinkedList<>(builder.build().getInterfaces());
            i.addAll(asList(interfaces));
            builder.interfaces(i);
            return this;
        }

        public InterfacesHolder implementing(final TypeElement typeElement) {
            return implementing(typeElement.getSimpleName().toString());
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

        public FieldsHolder fieldsFromInterfaceBeanGetters(final boolean finalFields) {
            final Stream<Element> beanGetterStream = createBeanGetterStream(typeElement);
            final List<FieldModel> fields = beanGetterStream
                    .map(typeElement -> createField(typeElement, finalFields))
                    .collect(Collectors.toList());
            return fields(fields);
        }

        private static class DistinctElementWrapper {
            private final Element element;

            private DistinctElementWrapper(final Element element) {
                this.element = element;
            }

            @Override
            public boolean equals(final Object o) {
                if (this == o) return true;
                if (!(o instanceof DistinctElementWrapper)) return false;
                final DistinctElementWrapper that = (DistinctElementWrapper) o;
                return Objects.equals(element.getSimpleName(), that.element.getSimpleName());
            }

            @Override
            public int hashCode() {
                return Objects.hash(element.getSimpleName());
            }
        }

        public ConstructorsHolder constructors() {
            return new ConstructorsHolder(builder, typeElement);
        }
    }

    private static Stream<? extends Element> elementStreamIncludingInterfaces(final TypeElement typeElement) {
        final Stream<? extends Element> interfacesMethodsStream = typeElement.getInterfaces().stream()
                .filter(i -> i instanceof DeclaredType)
                .map(i -> (DeclaredType) i)
                .flatMap(i -> {
                    if (i.asElement() instanceof TypeElement) return elementStreamIncludingInterfaces((TypeElement) i.asElement());
                    else return Stream.empty();
                });
        return Stream.concat(typeElement.getEnclosedElements().stream(), interfacesMethodsStream);
    }

    public static class ConstructorsHolder {
        private final ClassModelBuilder builder;
        private final TypeElement typeElement;

        private ConstructorsHolder(final ClassModelBuilder builder, final TypeElement typeElement) {
            this.builder = builder;
            this.typeElement = typeElement;
        }

        public ConstructorsHolder constructorForAllFields() {
            final MethodModel c = new MethodModel();
            //no modifiers since it should be package scope
            final List<MethodParameterModel> parameters = parametersForInstanceFields(builder, typeElement);
            c.setParameters(parameters);
            c.setName(builder.getName());
            c.setAnnotations(singletonList(createJsonCreatorAnnotation()));
            c.setBody(Templates.render("fieldAssignments", singletonMap("assignments", parameters)));
            builder.addImport("com.fasterxml.jackson.annotation.JsonCreator");
            builder.addConstructor(c);
            return new ConstructorsHolder(builder, typeElement);
        }

        public MethodsHolder methods() {
            return new MethodsHolder(builder, typeElement);
        }

        public <A extends Annotation> ConstructorsHolder additionalConstructorContent(final Class<A> clazz, final Function<A, String> endContentGetter) {
            final A annotation = typeElement.getAnnotation(clazz);
            builder.setConstructorEndContent(endContentGetter.apply(annotation));
            return this;
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
            final ResourceDraftValue annotation = typeElement.getAnnotation(ResourceDraftValue.class);
            if (annotation != null) {
                for (final FactoryMethod factoryMethod : annotation.factoryMethods()) {
                    addFactoryMethod(builder, factoryMethod, typeElement);
                }
            }
            return this;
        }

        public MethodsHolder builderMethods() {
            typeElement.getEnclosedElements()
                    .stream()
                    .filter(BEAN_GETTER_PREDICATE)
                    .forEach(element -> addBuilderMethod(element, builder));
            return this;
        }

        public ClassModel build() {
            return builder.build();
        }

        public ClassModelBuilder getBuilder() {
            return builder;
        }

        public MethodsHolder gettersForFields() {
            final List<FieldModel> fieldModels = instanceFieldsSorted(builder);
            fieldModels.forEach(field -> builder.addMethod(createGetter(field, typeElement)));
            return this;
        }

        public MethodsHolder withers() {
            addDslMethods(builder, typeElement);
            addNewBuilderMethod(builder, typeElement);
            return this;
        }

        public MethodsHolder factoryMethodOfInterfaceInstance(final boolean finalFields) {
            final MethodModel m = new MethodModel();
            m.setModifiers(asList("public", "static"));
            m.setName("of");
            m.setReturnType(builder.getName());
            final MethodParameterModel p = new MethodParameterModel();
            p.setModifiers(singletonList("final"));
            p.setType(typeElement.getSimpleName().toString());
            p.setName("template");
            m.setParameters(singletonList(p));
            final String dsd = typeElement.getEnclosedElements()
                    .stream()
                    .filter(BEAN_GETTER_PREDICATE)
                    .map(element -> {
                        final FieldModel field = createField(element, finalFields);
                        return ImmutablePair.of(field.getName(), element.getSimpleName().toString());
                    })
                    .sorted(Comparator.comparing(ImmutablePair::getLeft))
                    .map(pair -> format("template.%s()", pair.getRight()))
                    .collect(joining(", "));
            final String body = "return new " + builder.getName() + "(" + dsd + ");";
            m.setBody(body);
            builder.addMethod(m);
            return this;
        }

        public <A extends Annotation> MethodsHolder additionalContents(final Class<A> a, final Function<A, String[]> op) {
            final A annotation = typeElement.getAnnotation(a);
            if (annotation != null) {
                final String[] thingsToAdd = op.apply(annotation);
                final String s = Arrays.stream(thingsToAdd).collect(joining("\n"));
                builder.setAdditions(s);
            }
            return this;
        }

        public <A extends Annotation> MethodsHolder gettersForFieldsInCase(final Class<A> clazz, final Predicate<A> predicate) {
            return predicate.test(typeElement.getAnnotation(clazz)) ? gettersForFields() : this;
        }
    }

    private static MethodModel createGetter(final FieldModel field, final TypeElement typeElement) {
        final boolean isBoolean = field.getType().equals("java.lang.Boolean");
        final String methodNameStart = isBoolean ? "is" : "get";
        final String fieldName = field.getName();
        final String methodName = methodNameStart + capitalize(unescapeJavaKeyword(fieldName));
        final MethodModel method = new MethodModel();
        method.setName(methodName);
        method.setReturnType(field.getType());
        method.setBody("return " + fieldName + ";");
        method.addModifiers("public");

        final List<AnnotationModel> annotationModels = field.getAnnotations()
                .stream()
                .filter(a -> asList("Nullable", "JsonProperty").contains(a.getName()))
                .map(a -> {
                    final AnnotationModel am = new AnnotationModel();
                    am.setName(a.getName());
                    am.setValue(a.getValue());
                    return am;
                })
                .collect(Collectors.toList());
        method.setAnnotations(annotationModels);

        final List<AnnotationModel> list = new LinkedList<>(method.getAnnotations());
        final boolean overrides = elementStreamIncludingInterfaces(typeElement).anyMatch(x -> x.getSimpleName().equals(methodName));
        if (overrides) {
            list.add(overrideAnnotation());
        }
        method.setAnnotations(list);
        return method;
    }

    private static String unescapeJavaKeyword(final String fieldName) {
        return fieldName.replace("_", "");
    }

    private static AnnotationModel overrideAnnotation() {
        final AnnotationModel a = new AnnotationModel();
        a.setName("Override");
        return a;
    }

    private static void addDslMethods(final ClassModelBuilder builder, final TypeElement typeElement) {
        typeElement.getEnclosedElements()
                .stream()
                .filter(BEAN_GETTER_PREDICATE)
                .forEach(element -> addDslMethod(element, builder));
    }

    private static void addDslMethod(final Element element, final ClassModelBuilder builder) {
        final MethodModel method = createWitherMethod(element, builder);
        builder.addMethod(method);
        if (method.getParameters().get(0).getType().equals("java.lang.Boolean")) {
            final MethodModel methodWithIsName = createWitherMethod(element, builder);
            methodWithIsName.setName(methodWithIsName.getName().replaceFirst("with", "withIs"));
            builder.addMethod(methodWithIsName);
        }
    }

    private static MethodModel createWitherMethod(final Element element, final ClassModelBuilder builder) {
        final String fieldName = fieldNameFromGetter(element);
        final MethodModel method = new MethodModel();
        final String name = witherNameFromGetter(element);
        method.setName(name);
        method.setReturnType(builder.getName());
        method.addModifiers("public");
        final MethodParameterModel parameter = getBuilderMethodParameterModel(element, fieldName);
        method.setParameters(singletonList(parameter));
        final HashMap<String, Object> values = new HashMap<>();
        values.put("fieldName", fieldName);
        final String template = parameter.getType().startsWith("io.sphere.sdk.models.Referenceable<") ? "referenceableWitherMethodBody" : "witherMethodBody";
        method.setBody(Templates.render(template, values));
        return method;
    }

    private static void addNewBuilderMethod(final ClassModelBuilder builder, final TypeElement typeElement) {
        final MethodModel method = new MethodModel();
        method.setModifiers(singletonList("private"));
        final String associatedBuilderName = builderName(typeElement);
        builder.addImport(builder.build().getPackageName() + "." + associatedBuilderName);
        method.setReturnType(associatedBuilderName);
        method.setName("newBuilder");
        method.setBody("return new " + associatedBuilderName + "(" + fieldNamesSortedString(builder) + ");");
        builder.addMethod(method);
    }

    private static String builderName(final String originalClassName) {
        return originalClassName + "Builder";
    }

    private static String builderName(final TypeElement typeElement) {
        return builderName(typeElement.getSimpleName().toString());
    }

    private static FieldModel createField(final Element element, final boolean finalFields) {
        final String fieldName = fieldNameFromGetter(element);
        final FieldModel field = new FieldModel();
        field.addModifiers("private");
        if (finalFields) {
            field.addModifiers("final");
        }
        field.setType(getType(element));
        field.setName(fieldName);
        final List<AnnotationModel> annotations = element.getAnnotationMirrors().stream().map(a -> {
            final String annotationName = a.getAnnotationType().asElement().getSimpleName().toString();
            if (asList("Nullable", "JsonProperty").contains(annotationName)) {
                final AnnotationModel annotationModel = new AnnotationModel();
                annotationModel.setName(annotationName);
                if (annotationName.equals("JsonProperty")) {
                    final String value = element.getAnnotation(JsonProperty.class).value();
                    annotationModel.setValue(value);
                }
                return annotationModel;
            } else {
                return null;
            }
        })
                .filter(x -> x != null)
                .collect(Collectors.toList());
        field.setAnnotations(annotations);
        return field;
    }

    private static void addFactoryMethod(final ClassModelBuilder builder, final FactoryMethod factoryMethod, final TypeElement typeElement) {
        final String name = factoryMethod.methodName();
        final MethodModel method = new MethodModel();
        method.setName(name);
        method.setModifiers(asList("public", "static"));
        method.setParameters(createParameterModels(builder, factoryMethod));
        method.setReturnType(builder.getName());
        final List<String> parameterNameList = asList(factoryMethod.parameterNames());
        final String constructorParameters = parametersForInstanceFields(builder, typeElement).stream()
                .map(p -> parameterNameList.contains(unescapeJavaKeyword(p.getName())) ? p.getName() : null)
                .collect(joining(", "));
        method.setBody("return new " + builder.getName() + "(" + constructorParameters +");");
        builder.addMethod(method);
    }

    private static List<MethodParameterModel> createParameterModels(final ClassModelBuilder builder, final FactoryMethod factoryMethod) {
        return Arrays.stream(factoryMethod.parameterNames())
                .map(parameterName -> {
                    final FieldModel field = getField(builder, parameterName);
                    final MethodParameterModel m = new MethodParameterModel();
                    m.setName(escapeJavaKeyword(parameterName));
                    final String type = factoryMethod.useLowercaseBooleans() && field.getType().endsWith(".Boolean")
                            ? "boolean"
                            : field.getType();
                    m.setType(type);
                    m.setModifiers(asList("final"));
                    return m;
                })
                .collect(Collectors.toList());
    }

    private static String escapeJavaKeyword(final String parameterName) {
        return parameterName.replaceAll("^default$", "_default");
    }

    private static FieldModel getField(final ClassModelBuilder builder, final String fieldName) {
        return builder.build().getFields().stream()
                .filter(f -> f.getName().equals(fieldName) || unescapeJavaKeyword(f.getName()).equals(fieldName))
                .findFirst().orElseThrow(() -> new RuntimeException("field " + fieldName + " not found in " + builder));
    }

    private static List<MethodParameterModel> parametersForInstanceFields(final ClassModelBuilder builder, final TypeElement typeElement) {
        return builder.build().getFields().stream()
                .filter(f -> !f.getModifiers().contains("static"))
                .sorted(Comparator.comparing(fieldModel -> unescapeJavaKeyword(fieldModel.getName())))
                .map(f -> {
                    final MethodParameterModel p = new MethodParameterModel();
                    p.setModifiers(asList("final"));
                    p.setType(f.getType());
                    p.setName(f.getName());
                    if (f.getType().endsWith("Boolean")) {
                        final Element element = elementStreamIncludingInterfaces(typeElement)
                                .filter(t -> t.getSimpleName().toString().equals("is" + capitalize(unescapeJavaKeyword(f.getName()))))
                                .filter(t -> t.getAnnotation(JsonProperty.class) != null)
                                .findFirst()
                                .orElseThrow(() -> new RuntimeException("Boolean getter '" + f.getName() + "' for " + typeElement + " needs @JsonProperty annotation to find the right name"));

                        final JsonProperty jsonProperty = element.getAnnotation(JsonProperty.class);
                        final AnnotationModel annotationModel = new AnnotationModel();
                        annotationModel.setName(jsonProperty.annotationType().getName());
                        annotationModel.setValue(jsonProperty.value());

                        p.setAnnotations(singletonList(annotationModel));
                    }
                    return p;
                })
                .collect(Collectors.toList());
    }

    private static String witherNameFromGetter(final Element element) {
        return "with" + capitalize(unescapeJavaKeyword(fieldNameFromGetter(element)));
    }

    private static void addBuilderMethod(final Element element, final ClassModelBuilder builder) {
        final MethodModel method = createMethodModelForBuilderMethod(element, builder);
        builder.addMethod(method);

        if (method.getParameters().get(0).getType().equals("java.lang.Boolean") && !method.getName().startsWith("is")) {
            final MethodModel methodStartingWithIs = createMethodModelForBuilderMethod(element, builder);
            final String newName = "is" + capitalize(unescapeJavaKeyword(method.getName()));
            methodStartingWithIs.setName(newName);
            builder.addMethod(methodStartingWithIs);
        }
    }

    private static MethodModel createMethodModelForBuilderMethod(final Element element, final ClassModelBuilder builder) {
        final String fieldName = fieldNameFromGetter(element);
        final MethodModel method = new MethodModel();
        method.setName(fieldName);
        method.setReturnType(builder.getName());
        method.addModifiers("public");
        final MethodParameterModel parameter = getBuilderMethodParameterModel(element, fieldName);
        method.setParameters(singletonList(parameter));
        final HashMap<String, Object> values = new HashMap<>();
        values.put("fieldName", fieldName);
        final String template = parameter.getType().startsWith("io.sphere.sdk.models.Referenceable<") ? "referenceableBuilderMethodBody" : "builderMethodBody";
        method.setBody(Templates.render(template, values));
        return method;
    }

    public static String packageName(final TypeElement typeElement) {
        final PackageElement packageElement = (PackageElement) typeElement.getEnclosingElement();
        return packageElement.getQualifiedName().toString();
    }

    private static AnnotationModel createJsonCreatorAnnotation() {
        final AnnotationModel jsonCreator = new AnnotationModel();
        jsonCreator.setName("JsonCreator");
        return jsonCreator;
    }

    private static AnnotationModel createNullableAnnotation() {
        final AnnotationModel am = new AnnotationModel();
        am.setName("Nullable");
        return am;
    }

    static String fieldNameFromGetter(final Element element) {
        final String methodName = element.getSimpleName().toString();
        return fieldNameFromGetter(methodName);
    }

    static String fieldNameFromGetter(final String methodName) {
        final String firstPartGetterName = methodName.startsWith("get") ? "get" : "is";
        final String s1 = methodName.toString().replaceAll("^" + firstPartGetterName, "");
        final String s = ("" + s1.charAt(0)).toLowerCase() + s1.substring(1);
        return escapeJavaKeyword(StringUtils.substringBefore(s, "("));
    }

    static String getType(final Element element) {
        final ReturnTypeElementVisitor visitor = new ReturnTypeElementVisitor();
        return element.accept(visitor, null);
    }

    private static MethodParameterModel getBuilderMethodParameterModel(final Element element, final String fieldName) {
        final MethodParameterModel parameter = new MethodParameterModel();
        parameter.setModifiers(singletonList("final"));
        parameter.setName(fieldName);
        final String type = getType(element);
        if (type.startsWith("io.sphere.sdk.models.Reference<")) {
            final String newType = type.replace("io.sphere.sdk.models.Reference<", "io.sphere.sdk.models.Referenceable<");
            parameter.setType(newType);
        } else {
            parameter.setType(type);
        }
        element.getAnnotationMirrors().forEach(a -> {
            final String annotationName = a.getAnnotationType().asElement().getSimpleName().toString();
            if ("Nullable".equals(annotationName)) {
                parameter.setAnnotations(singletonList(createNullableAnnotation()));
            }
        });
        return parameter;
    }

    private static List<String> fieldNamesSorted(final ClassModelBuilder builder) {
        return instanceFieldsSorted(builder)
                .stream()
                .map(field -> field.getName())
                .collect(Collectors.toList());
    }

    private static List<FieldModel> instanceFieldsSorted(final ClassModelBuilder builder) {
        return builder.build().getFields().stream()
                .filter(f -> !f.getModifiers().contains("static"))
                .sorted(Comparator.comparing(f -> f.getName()))
                .collect(Collectors.toList());
    }

    private static String fieldNamesSortedString(final ClassModelBuilder builder) {
        return fieldNamesSorted(builder).stream().collect(joining(", "));
    }

    public static Stream<Element> createBeanGetterStream(final TypeElement typeElement) {
        final Stream<? extends Element> elementStream = elementStreamIncludingInterfaces(typeElement);
        return elementStream
                .filter(BEAN_GETTER_PREDICATE)
                .map(FieldsHolder.DistinctElementWrapper::new)
                .distinct()
                .map(wrapper -> wrapper.element);
    }

    public static Stream<ExecutableElement> instanceMethodStream(final TypeElement typeElement) {
        final Stream<? extends Element> elementStream = elementStreamIncludingInterfaces(typeElement);
        return elementStream
                .filter(e -> ElementKind.METHOD.equals(e.getKind()) && !e.getModifiers().contains(STATIC))
                .map(FieldsHolder.DistinctElementWrapper::new)
                .distinct()
                .map(wrapper -> (ExecutableElement) wrapper.element);
    }
}
