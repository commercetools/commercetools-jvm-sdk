package io.sphere.sdk.annotations.processors;

import org.apache.commons.lang3.StringUtils;

import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.PackageElement;
import javax.lang.model.element.TypeElement;
import java.util.Comparator;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static java.util.Arrays.asList;
import static java.util.Collections.singletonList;
import static java.util.stream.Collectors.joining;

public abstract class ClassModelFactory {


    protected static final Predicate<Element> NORMAL_GETTERS_PREDICATE = e -> ElementKind.METHOD.equals(e.getKind()) && e.getSimpleName().toString().matches("^get[A-Z].*");

      public abstract ClassModel createClassModel();

    protected String packageName(final TypeElement typeElement) {
        final PackageElement packageElement = (PackageElement) typeElement.getEnclosingElement();
        return packageElement.getQualifiedName().toString();
    }

    protected AnnotationModel createJsonCreatorAnnotation() {
        final AnnotationModel jsonCreator = new AnnotationModel();
        jsonCreator.setName("JsonCreator");
        return jsonCreator;
    }

    protected FieldModel createField(final Element element) {
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

    protected AnnotationModel createNullableAnnotation() {
        final AnnotationModel am = new AnnotationModel();
        am.setName("Nullable");
        return am;
    }

    protected String fieldNameFromGetter(final String methodName) {
        final String s1 = methodName.toString().replaceAll("^get", "");
        final String s = ("" + s1.charAt(0)).toLowerCase() + s1.substring(1);
        return StringUtils.substringBefore(s, "(");
    }

    protected String getType(final Element element) {
        final ReturnTypeElementVisitor visitor = new ReturnTypeElementVisitor();
        return element.accept(visitor, null);
    }

    protected String witherNameFromGetter(final Element element) {
        return "with" + StringUtils.capitalize(fieldNameFromGetter(element.toString()));
    }

    protected MethodParameterModel getBuilderMethodParameterModel(final Element element, final String fieldName) {
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

    protected List<String> fieldNamesSorted(final ClassModelBuilder builder) {
        return instanceFieldsSorted(builder)
                .stream()
                .map(field -> field.getName())
                .collect(Collectors.toList());
    }

    protected List<FieldModel> instanceFieldsSorted(final ClassModelBuilder builder) {
        return builder.build().getFields().stream()
                    .filter(f -> !f.getModifiers().contains("static"))
                    .sorted(Comparator.comparing(f -> f.getName()))
                    .collect(Collectors.toList());
    }

    protected String fieldNamesSortedString(final ClassModelBuilder builder) {
        return fieldNamesSorted(builder).stream().collect(joining(", "));
    }

    protected List<MethodParameterModel> parametersForInstanceFields(final ClassModelBuilder builder) {
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

    protected FieldModel getField(final ClassModelBuilder builder, final String fieldName) {
        final FieldModel fieldModel = builder.build().getFields().stream()
                .filter(f -> f.getName().equals(fieldName))
                .findFirst().orElseThrow(() -> new RuntimeException("field " + fieldName + " not found in " + builder));
        return fieldModel;
    }
}
