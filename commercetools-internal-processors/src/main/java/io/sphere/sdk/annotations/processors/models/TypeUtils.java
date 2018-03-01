package io.sphere.sdk.annotations.processors.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.ParameterizedTypeName;
import com.squareup.javapoet.TypeName;
import com.squareup.javapoet.TypeVariableName;
import io.sphere.sdk.annotations.HasBuilder;
import io.sphere.sdk.annotations.ResourceDraftValue;
import io.sphere.sdk.annotations.ResourceValue;
import io.sphere.sdk.models.Builder;

import javax.lang.model.element.*;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.ElementFilter;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;
import java.lang.annotation.Annotation;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Provides util methods for working with {@link TypeElement} and {@link ClassName}.
 */
public class TypeUtils {
    private final Elements elements;
    private final Types types;

    public TypeUtils(final Elements elements, final Types types) {
        this.elements = elements;
        this.types = types;
    }


    public String getPackageName(final Element element) {
        return elements.getPackageOf(element).getQualifiedName().toString();
    }

    public String getSimpleName(final Element element) {
        return element.getSimpleName().toString();
    }

    public String getSimpleName(final TypeMirror typeMirror) {
        final TypeElement typeElement = (TypeElement) types.asElement(typeMirror);
        return getSimpleName(typeElement);
    }

    public ClassName getHasBuilderImplType(final TypeElement typeElement) {
        final ClassName draftType = ClassName.get(typeElement);
        final HasBuilder hasBuilder = typeElement.getAnnotation(HasBuilder.class);
        return ClassName.get(draftType.packageName(), draftType.simpleName() + hasBuilder.implPrefix());
    }

    public ClassName getDraftImplType(final TypeElement typeElement) {
        final ClassName draftType = ClassName.get(typeElement);
        return ClassName.get(draftType.packageName(), draftType.simpleName() + "Dsl");
    }

    public TypeName getResourceValueImplType(final TypeElement resourceValueTypeElement) {
        final ClassName draftType = ClassName.get(resourceValueTypeElement);
        final ResourceValue resourceValue = resourceValueTypeElement.getAnnotation(ResourceValue.class);

        final String implSuffix = "Impl" + (resourceValue.abstractResourceClass() ? "Base" : "");
        final List<TypeName> typeParameters = resourceValueTypeElement.getTypeParameters().stream().map(TypeVariableName::get).collect(Collectors.toList());
        final ClassName resourceValueImpl = ClassName.get(draftType.packageName(), draftType.simpleName() + implSuffix);
        return typeParameters.isEmpty() ?
                resourceValueImpl :
                ParameterizedTypeName.get(resourceValueImpl, typeParameters.toArray(new TypeVariableName[typeParameters.size()]));
    }

    /**
     * Returns the type that the builder should return {@link Builder#build()}.
     *
     * @see ResourceDraftValue#builderReturnsDslClass()
     *
     * @param typeElement the type annotated with {@link ResourceDraftValue}
     * @return the builder return type
     */
    public ClassName getBuilderReturnType(final TypeElement typeElement) {
        final ResourceDraftValue resourceDraftValue = typeElement.getAnnotation(ResourceDraftValue.class);
        final ClassName draftType = ClassName.get(typeElement);
        return resourceDraftValue.builderReturnsDslClass() ?
                getDraftImplType(typeElement) : draftType;
    }

    public ClassName getConcreteBuilderType(final TypeMirror typeMirror) {
        final TypeElement typeElement = (TypeElement) types.asElement(typeMirror);
        return getConcreteBuilderType(typeElement);
    }

    public ClassName getConcreteBuilderType(final TypeElement typeElement) {
        final ClassName type = ClassName.get(typeElement);
        return getConcreteBuilderType(type);
    }

    public ClassName getConcreteBuilderType(final TypeName type) {
        return ClassName.bestGuess(type.toString() + "Builder");
    }

    public ClassName getBuilderType(final TypeElement typeElement) {
        final ClassName type = getConcreteBuilderType(typeElement);
        final ResourceDraftValue resourceDraftValue = typeElement.getAnnotation(ResourceDraftValue.class);
        return resourceDraftValue.abstractBuilderClass() ? ClassName.get(type.packageName(), type.simpleName() + "Base") : type;
    }

    /**
     * Returns all property methods - including inherited methods - as stream.
     *
     * @param typeElement the type element
     * @return methods sorted by their {@link PropertyGenModel#getPropertyName(ExecutableElement)}
     */
    public Stream<ExecutableElement> getAllPropertyMethods(final TypeElement typeElement) {
        final List<ExecutableElement> allMethods = ElementFilter.methodsIn(elements.getAllMembers(typeElement));
        final Comparator<ExecutableElement> nameComparator = Comparator.comparing(e -> e.getSimpleName().toString());
        final Comparator<ExecutableElement> typeComparator = Comparator.comparing(e -> e.getReturnType().toString());
        final Set<ExecutableElement> uniqueMethods = new TreeSet<>(nameComparator.thenComparing(typeComparator));
        uniqueMethods.addAll(allMethods);

        return uniqueMethods.stream()
                .filter(this::isPropertyMethod);
    }


    /**
     * Returns property methods - not included inherited methods - as stream.
     *
     * @param typeElement the type element
     * @return methods sorted by their {@link PropertyGenModel#getPropertyName(ExecutableElement)}
     */
    public Stream<ExecutableElement> getPropertyMethods(final TypeElement typeElement) {
        final List<ExecutableElement> allMethods = ElementFilter.methodsIn(typeElement.getEnclosedElements());
        final Comparator<ExecutableElement> nameComparator = Comparator.comparing(e -> e.getSimpleName().toString());
        final Comparator<ExecutableElement> typeComparator = Comparator.comparing(e -> e.getReturnType().toString());
        final Set<ExecutableElement> uniqueMethods = new TreeSet<>(nameComparator.thenComparing(typeComparator));
        uniqueMethods.addAll(allMethods);

        return uniqueMethods.stream()
                .filter(this::isPropertyMethod);
    }

    /**
     * Returns true iff. the given method name starts with {@code get} or {@code is} or is annotated with {@link JsonProperty}
     * and if the given method doesn't have a {@code static}, {@code default} modifier and isn't annotated with
     * {@link JsonIgnore}.
     *
     * @param method the method
     * @return true iff. the given method is a property method
     */
    public boolean isPropertyMethod(final ExecutableElement method) {
        final String methodName = method.getSimpleName().toString();
        final boolean hasGetterMethodName = !"getClass".equals(methodName) && methodName.startsWith("get") || methodName.startsWith("is");
        final boolean hasJsonPropertyAnnotation = method.getAnnotation(JsonProperty.class) != null;
        final boolean hasJsonIgnore = method.getAnnotation(JsonIgnore.class) != null;
        final Set<Modifier> modifiers = method.getModifiers();
        return (hasGetterMethodName || hasJsonPropertyAnnotation)
                && !hasJsonIgnore
                && !modifiers.contains(Modifier.STATIC)
                && !modifiers.contains(Modifier.DEFAULT);
    }

    public Optional<? extends AnnotationMirror> getAnnotationMirror(final Element element, final Class<? extends Annotation> clazz) {
        final String className = clazz.getName();
        final Optional<? extends AnnotationMirror> annotationMirror = element.getAnnotationMirrors()
                .stream()
                .filter(am -> am.getAnnotationType().toString().equals(className))
                .findFirst();
        return annotationMirror;
    }

    public Optional<AnnotationValue> getAnnotationValue(final Element element, final Class<? extends Annotation> clazz, final String name) {
        final Optional<? extends AnnotationMirror> optionalAnnotationMirror = getAnnotationMirror(element, clazz);

        if (optionalAnnotationMirror.isPresent()) {
            final AnnotationMirror annotationMirror = optionalAnnotationMirror.get();
            final Map<? extends ExecutableElement, ? extends AnnotationValue> elementValues = elements.getElementValuesWithDefaults(annotationMirror);

            final Optional<? extends Map.Entry<? extends ExecutableElement, ? extends AnnotationValue>> first = elementValues.entrySet()
                    .stream()
                    .filter(e -> e.getKey().getSimpleName().toString().equals(name))
                    .findFirst();

            final Optional<AnnotationValue> annotationValue = first.map(Map.Entry::getValue);

            return annotationValue;
        } else {
            return Optional.empty();
        }
    }

    /**
     * @param propertyType the property type element
     * @return true if the property type is a primitive type or an Enum.
     */
    public boolean isPrimitiveOrEnum(final TypeName propertyType){
        return isPrimitiveType(propertyType) || isEnumType(propertyType);
    }

    /**
     * @param propertyType the property type element
     * @return true if the property type is a primitive type.
     */
    public boolean isPrimitiveType(final TypeName propertyType) {
        return propertyType.isPrimitive() || propertyType.isBoxedPrimitive() || propertyType.toString().equals("java.lang.String");
    }

    /**
     * @param propertyType the property type element
     * @return true if the property type is an Enum type.
     */
    public boolean isEnumType(final TypeName propertyType) {
        boolean isEnum = false;
        try {
            isEnum = Class.forName(propertyType.toString()).isEnum();
        } catch (ClassNotFoundException e) { }
        return isEnum;
    }
}
