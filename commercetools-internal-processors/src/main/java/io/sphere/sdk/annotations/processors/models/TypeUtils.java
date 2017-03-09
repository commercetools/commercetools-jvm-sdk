package io.sphere.sdk.annotations.processors.models;

import com.squareup.javapoet.ClassName;
import io.sphere.sdk.annotations.ResourceDraftValue;
import io.sphere.sdk.annotations.ResourceValue;
import io.sphere.sdk.models.Builder;

import javax.lang.model.element.Element;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.ElementFilter;
import javax.lang.model.util.Elements;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Stream;

/**
 * Provides util methods for working with {@link TypeElement} and {@link ClassName}.
 */
public class TypeUtils {
    private final Elements elements;

    public TypeUtils(final Elements elements) {
        this.elements = elements;
    }


    public String getPackageName(final TypeElement typeElement) {
        return elements.getPackageOf(typeElement).getQualifiedName().toString();
    }

    public String getSimpleName(final Element element) {
        return element.getSimpleName().toString();
    }

    public ClassName getDraftImplType(final TypeElement typeElement) {
        final ClassName draftType = ClassName.get(typeElement);
        return ClassName.get(draftType.packageName(), draftType.simpleName() + "Dsl");
    }


    public ClassName getResourceValueImplType(final TypeElement resourceValueTypeElement) {
        final ClassName draftType = ClassName.get(resourceValueTypeElement);
        final ResourceValue resourceValue = resourceValueTypeElement.getAnnotation(ResourceValue.class);

        final String implSuffix = "Impl" + (resourceValue.abstractResourceClass() ? "Base" : "");
        return ClassName.get(draftType.packageName(), draftType.simpleName() + implSuffix);
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

    public ClassName getConcreteBuilderType(final TypeElement typeElement) {
        final ClassName type = ClassName.get(typeElement);
        return ClassName.get(type.packageName(), type.simpleName() + "Builder");
    }

    public ClassName getBuilderType(final TypeElement typeElement) {
        final ClassName type = getConcreteBuilderType(typeElement);
        final ResourceDraftValue resourceDraftValue = typeElement.getAnnotation(ResourceDraftValue.class);
        return resourceDraftValue.abstractBuilderClass() ? ClassName.get(type.packageName(), type.simpleName() + "Base") : type;
    }

    /**
     * Returns all getter methods - including inherited methods - as stream.
     *
     * @param typeElement the type element
     * @return methods sorted by their {@link PropertyGenModel#getPropertyName(ExecutableElement)}
     */
    public Stream<ExecutableElement> getAllGetterMethods(TypeElement typeElement) {
        final List<ExecutableElement> allMethods = ElementFilter.methodsIn(elements.getAllMembers(typeElement));
        final Comparator<ExecutableElement> nameComparator = Comparator.comparing(e -> e.getSimpleName().toString());
        final Comparator<ExecutableElement> typeComparator = Comparator.comparing(e -> e.getReturnType().toString());
        final Set<ExecutableElement> uniqueMethods = new TreeSet<>(nameComparator.thenComparing(typeComparator));
        uniqueMethods.addAll(allMethods);

        return uniqueMethods.stream()
                .filter(this::isGetterMethod);
    }

    /**
     * Returns true iff. the given method name starts with {@code get} or {@code is} and if the given method isn't static.
     *
     * @param method the method
     * @return true iff. the given method is a getter method
     */
    public boolean isGetterMethod(final ExecutableElement method) {
        final String methodName = method.getSimpleName().toString();
        final boolean hasGetterMethodName = !"getClass".equals(methodName) && methodName.startsWith("get") || methodName.startsWith("is");
        final Set<Modifier> modifiers = method.getModifiers();
        return hasGetterMethodName && !modifiers.contains(Modifier.STATIC) && !modifiers.contains(Modifier.DEFAULT);
    }
}
