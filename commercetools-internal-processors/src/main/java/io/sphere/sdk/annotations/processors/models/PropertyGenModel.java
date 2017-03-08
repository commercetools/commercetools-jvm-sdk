package io.sphere.sdk.annotations.processors.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.ParameterizedTypeName;
import com.squareup.javapoet.TypeName;
import org.apache.commons.lang3.StringUtils;

import javax.annotation.Nullable;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.TypeKind;
import javax.lang.model.type.TypeMirror;
import java.util.Objects;

/**
 * This class provides a model for properties of a {@link javax.lang.model.element.TypeElement}.
 */
public class PropertyGenModel {
    private final String name;

    private final String jsonName;

    private final String javaIdentifier;

    private final TypeMirror type;

    private final String javadocLinkTag;

    private final boolean optional;

    private PropertyGenModel(final String name, final String jsonName, final TypeMirror type, final String javaDocLinkTag, final boolean optional) {
        this.name = name;
        this.jsonName = jsonName;
        this.javaIdentifier = (SourceVersion.isKeyword(name) ? "_" : "") + name;
        this.type = type;
        this.javadocLinkTag = javaDocLinkTag;
        this.optional = optional;
    }

    public String getName() {
        return name;
    }

    /**
     * Returns the name of this poroperty as specified with {@link com.fasterxml.jackson.annotation.JsonProperty}
     *
     * @return the json name or null if the {@link com.fasterxml.jackson.annotation.JsonProperty} isn't present
     */
    public String getJsonName() {
        return jsonName;
    }

    public String getJavaIdentifier() {
        return javaIdentifier;
    }

    public TypeName getType() {
        return TypeName.get(type);
    }

    /**
     * Returns the javadoc link tag that links back this properties getter method.
     *
     * @return the javadoc link tag pointing to this property
     */
    public String getJavadocLinkTag() {
        return javadocLinkTag;
    }

    public boolean isOptional() {
        return optional;
    }

    /**
     * Returns true if the qualified name of this type is equal to the canonical name of the given class.
     *
     * @param clazz the class
     * @return true iff. this type is the same as the given parameter
     */
    public boolean hasSameType(final Class<?> clazz) {
        if (type.getKind() == TypeKind.DECLARED) {
            final DeclaredType declaredType = (DeclaredType) this.type;
            final TypeElement typeElement = (TypeElement) declaredType.asElement();
            return typeElement.getQualifiedName().contentEquals(clazz.getCanonicalName());
        } else {
            return false;
        }
    }

    /**
     * Replaces the parameterized type of this type with the given replacement type.
     *
     * This is useful to replace a reference type {@code Reference<Example>} with a
     * referenceable type {@type Referenceable<Example>}.
     *
     * @param replacementType the replacement type
     * @return a new type name with the replacement type which has the same type arguments as t
     */
    public TypeName replaceParameterizedType(final Class<?> replacementType) {
        if (type.getKind() == TypeKind.DECLARED) {
            final DeclaredType declaredType = (DeclaredType) this.type;

            final TypeName[] typeArguments = declaredType.getTypeArguments().stream()
                    .map(TypeName::get)
                    .toArray(TypeName[]::new);

            return ParameterizedTypeName.get(ClassName.get(replacementType), typeArguments);
        }
        return getType();
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final PropertyGenModel that = (PropertyGenModel) o;
        return Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    @Override
    public String toString() {
        return "PropertyGenModel{" +
                "name='" + name + '\'' +
                ", javaIdentifier='" + javaIdentifier + '\'' +
                ", type=" + type +
                ", optional=" + optional +
                '}';
    }

    /**
     * Creates an instance from the given getter method.
     *
     * @param getterMethod the getter method
     *
     * @return new property model
     */
    public static PropertyGenModel of(final ExecutableElement getterMethod) {
        final boolean optional = getterMethod.getAnnotation(Nullable.class) != null;
        final JsonProperty jsonProperty = getterMethod.getAnnotation(JsonProperty.class);
        final String jsonName = jsonProperty != null ? jsonProperty.value() : null;
        final String javadocLinkTag =
                String.format("{@link %s#%s()}", getterMethod.getEnclosingElement().getSimpleName(), getterMethod.getSimpleName());
        return new PropertyGenModel(getPropertyName(getterMethod), jsonName, getterMethod.getReturnType(), javadocLinkTag, optional);
    }

    /**
     * Returns the property name of the given getter method.
     *
     * @param getterMethod the getter method
     * @return the uncapitalized name of the property
     */
    public static String getPropertyName(final ExecutableElement getterMethod) {
        final String name = getterMethod.getSimpleName().toString();
        final int propertyNameIndex = name.startsWith("is") ? 2 : 3;
        final String propertyName = StringUtils.uncapitalize(name.substring(propertyNameIndex));
        return propertyName;
    }
}
