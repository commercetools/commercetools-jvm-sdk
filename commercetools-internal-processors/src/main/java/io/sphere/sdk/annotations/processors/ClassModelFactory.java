package io.sphere.sdk.annotations.processors;

import io.sphere.sdk.annotations.FactoryMethod;
import io.sphere.sdk.annotations.ResourceDraftValue;
import org.apache.commons.lang3.StringUtils;

import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.PackageElement;
import javax.lang.model.element.TypeElement;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static java.util.Arrays.asList;
import static java.util.Collections.singletonList;
import static java.util.Collections.singletonMap;
import static java.util.stream.Collectors.joining;

public abstract class ClassModelFactory {


    public static final Predicate<Element> BEAN_GETTER_PREDICATE = e -> ElementKind.METHOD.equals(e.getKind()) && e.getSimpleName().toString().matches("^get[A-Z].*");
    protected static final Predicate<Element> NORMAL_GETTERS_PREDICATE = e -> ElementKind.METHOD.equals(e.getKind()) && e.getSimpleName().toString().matches("^get[A-Z].*");
    protected final TypeElement typeElement;

    public ClassModelFactory(final TypeElement typeElement) {
        this.typeElement = typeElement;
    }

    public static void addFactoryMethods(final ClassModelBuilder builder, final TypeElement typeElement) {
        final ResourceDraftValue annotation = typeElement.getAnnotation(ResourceDraftValue.class);
        if (annotation != null) {
            for (final FactoryMethod factoryMethod : annotation.factoryMethods()) {
                addFactoryMethod(builder, factoryMethod);
            }
        }
    }

    static void addBuilderMethod(final Element element, final ClassModelBuilder builder) {
        final String methodName = element.getSimpleName().toString();
        final String fieldName = fieldNameFromGetter(methodName);
        final MethodModel method = new MethodModel();
        method.setName(fieldName);
        method.setReturnType(builder.getName());
        method.addModifiers("public");
        method.setParameters(singletonList(getBuilderMethodParameterModel(element, fieldName)));
        final HashMap<String, Object> values = new HashMap<>();
        values.put("fieldName", fieldName);
        method.setBody(Templates.render("builderMethodBody", values));
        builder.addMethod(method);
    }

    public static void addBuilderMethods(final ClassModelBuilder builder, final TypeElement typeElement) {
        typeElement.getEnclosedElements()
                .stream()
                .filter(ClassModelFactory.BEAN_GETTER_PREDICATE)
                .forEach(element -> addBuilderMethod(element, builder));
    }

    public static void addBuildMethod(final ClassModelBuilder builder, final TypeElement typeElement) {
        final MethodModel method = new MethodModel();
        method.addModifiers("public");
        final String dslName = ResourceDraftDslModelFactory.dslName(typeElement);
        method.setReturnType(dslName);
        method.setName("build");
        method.setBody("return new " + dslName + "(" + fieldNamesSortedString(builder) + ");");
        builder.addMethod(method);
    }

    public abstract ClassModel createClassModel();

    public static String packageName(final TypeElement typeElement) {
        final PackageElement packageElement = (PackageElement) typeElement.getEnclosingElement();
        return packageElement.getQualifiedName().toString();
    }

    protected AnnotationModel createJsonCreatorAnnotation() {
        final AnnotationModel jsonCreator = new AnnotationModel();
        jsonCreator.setName("JsonCreator");
        return jsonCreator;
    }

    public static FieldModel createField(final Element element) {
        final String methodName = element.getSimpleName().toString();
        final String fieldName = fieldNameFromGetter(methodName);
        final FieldModel field = new FieldModel();
        field.addModifiers("private");
        field.setType(getType(element));
        field.setName(fieldName);
        element.getAnnotationMirrors().forEach(a -> {
            final String annotationName = a.getAnnotationType().asElement().getSimpleName().toString();
            if ("Nullable".equals(annotationName)) {
                field.setAnnotations(singletonList(createNullableAnnotation()));
            }
        });
        return field;
    }

    public static AnnotationModel createNullableAnnotation() {
        final AnnotationModel am = new AnnotationModel();
        am.setName("Nullable");
        return am;
    }

    public static String fieldNameFromGetter(final String methodName) {
        final String s1 = methodName.toString().replaceAll("^get", "");
        final String s = ("" + s1.charAt(0)).toLowerCase() + s1.substring(1);
        return StringUtils.substringBefore(s, "(");
    }

    public static String getType(final Element element) {
        final ReturnTypeElementVisitor visitor = new ReturnTypeElementVisitor();
        return element.accept(visitor, null);
    }

    protected String witherNameFromGetter(final Element element) {
        return "with" + StringUtils.capitalize(fieldNameFromGetter(element.toString()));
    }

    static MethodParameterModel getBuilderMethodParameterModel(final Element element, final String fieldName) {
        final MethodParameterModel parameter = new MethodParameterModel();
        parameter.setModifiers(singletonList("final"));
        parameter.setName(fieldName);
        final String type = getType(element);
        parameter.setType(type);
        element.getAnnotationMirrors().forEach(a -> {
            final String annotationName = a.getAnnotationType().asElement().getSimpleName().toString();
            if ("Nullable".equals(annotationName)) {
                parameter.setAnnotations(singletonList(createNullableAnnotation()));
            }
        });
        return parameter;
    }

    static List<String> fieldNamesSorted(final ClassModelBuilder builder) {
        return instanceFieldsSorted(builder)
                .stream()
                .map(field -> field.getName())
                .collect(Collectors.toList());
    }

    static List<FieldModel> instanceFieldsSorted(final ClassModelBuilder builder) {
        return builder.build().getFields().stream()
                    .filter(f -> !f.getModifiers().contains("static"))
                    .sorted(Comparator.comparing(f -> f.getName()))
                    .collect(Collectors.toList());
    }

    static String fieldNamesSortedString(final ClassModelBuilder builder) {
        return fieldNamesSorted(builder).stream().collect(joining(", "));
    }

    public static List<MethodParameterModel> parametersForInstanceFields(final ClassModelBuilder builder) {
        return builder.build().getFields().stream()
                    .filter(f -> !f.getModifiers().contains("static"))
                    .sorted(Comparator.comparing(f -> f.getName()))
                    .map(f -> {
                        final MethodParameterModel p = new MethodParameterModel();
                        p.setModifiers(asList("final"));
                        p.setType(f.getType());
                        p.setName(f.getName());
                        return p;
                    })
                    .collect(Collectors.toList());
    }

    public static FieldModel getField(final ClassModelBuilder builder, final String fieldName) {
        return builder.build().getFields().stream()
                .filter(f -> f.getName().equals(fieldName))
                .findFirst().orElseThrow(() -> new RuntimeException("field " + fieldName + " not found in " + builder));
    }

    public static void addFactoryMethod(final ClassModelBuilder builder, final FactoryMethod factoryMethod) {
        final String name = factoryMethod.methodName();
        final MethodModel method = new MethodModel();
        method.setName(name);
        method.setModifiers(asList("public", "static"));
        method.setParameters(createParameterModels(builder, factoryMethod));
        method.setReturnType(builder.getName());
        final List<String> parameterNameList = asList(factoryMethod.parameterNames());
        final String constructorParameters = parametersForInstanceFields(builder).stream()
                .map(p -> parameterNameList.contains(p.getName()) ? p.getName() : null)
                .collect(joining(", "));
        method.setBody("return new " + builder.getName() + "(" + constructorParameters +");");
        builder.addMethod(method);
    }

    public static List<MethodParameterModel> createParameterModels(final ClassModelBuilder builder, final FactoryMethod factoryMethod) {
        return Arrays.stream(factoryMethod.parameterNames())
                .map(parameterName -> {
                    final FieldModel field = getField(builder, parameterName);
                    final MethodParameterModel m = new MethodParameterModel();
                    m.setName(parameterName);
                    m.setType(field.getType());
                    m.setModifiers(asList("final"));
                    return m;
                })
                .collect(Collectors.toList());
    }


    public static void addConstructors(final ClassModelBuilder builder) {
        final MethodModel c = new MethodModel();
        //no modifiers since it should be package scope
        final List<MethodParameterModel> parameters = parametersForInstanceFields(builder);
        c.setParameters(parameters);
        c.setName(builder.getName());
        c.setBody(Templates.render("fieldAssignments", singletonMap("assignments", parameters)));
        builder.addConstructor(c);
    }
}
