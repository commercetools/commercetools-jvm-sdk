package io.sphere.sdk.annotations.processors.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.ParameterizedTypeName;
import com.squareup.javapoet.TypeName;
import io.sphere.sdk.models.Reference;
import org.apache.commons.lang3.StringUtils;

import javax.annotation.Nullable;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.ArrayType;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.TypeKind;
import javax.lang.model.type.TypeMirror;
import java.util.List;
import java.util.Objects;

/**
 * This class provides a model for properties of a {@link javax.lang.model.element.TypeElement}.
 */
public class PropertyGenModel {
    private static final ClassName BOXED_BOOLEAN = ClassName.get(Boolean.class);

    private final String name;

    private final String methodName;

    private final String jsonName;

    private final String javaIdentifier;

    private final TypeMirror type;

    private final String javadocLinkTag;

    private final boolean optional;

    private final boolean useReference;

    private final boolean deprecated;


    private PropertyGenModel(final String name, final String methodName, final String jsonName, final TypeMirror type, final String javaDocLinkTag,
                             final boolean optional, final boolean useReference, final boolean deprecated) {
        this.name = name;
        this.methodName = methodName;
        this.jsonName = jsonName;
        this.javaIdentifier = (SourceVersion.isKeyword(name) ? "_" : "") + name;
        this.type = type;
        this.javadocLinkTag = javaDocLinkTag;
        this.optional = optional;
        this.deprecated = deprecated;
        this.useReference = useReference;
    }

    public String getName() {
        return name;
    }

    public String getCapitalizedName() {
        return StringUtils.capitalize(getName());
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
        if (useReference) {
            return ParameterizedTypeName.get(ClassName.get(Reference.class), TypeName.get(type));
        } else if (type.getKind() == TypeKind.ARRAY) {
            final ArrayType arrayType = (ArrayType) type;
            return ParameterizedTypeName.get(ClassName.get(List.class), TypeName.get(arrayType.getComponentType()));
        } else {
            return TypeName.get(type);
        }
    }

    /**
     * Returns the getter method name for this property.
     *
     * @return the getter method name
     */
    public String getMethodName() {
        return methodName;
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

    public boolean isDeprecated() {
        return deprecated;
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

    public TypeName getTypeArgument(final int index) {
        if (type.getKind() == TypeKind.DECLARED) {
            final DeclaredType declaredType = (DeclaredType) this.type;

            return TypeName.get(declaredType.getTypeArguments().get(index));
        }
        return null;
    }

    /**
     * Replaces the parameterized type of this type with the given replacement type.
     * <p>
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
     * @return new property model
     */
    public static PropertyGenModel of(final ExecutableElement getterMethod) {
        final boolean optional = getterMethod.getAnnotation(Nullable.class) != null;
        final JsonProperty jsonProperty = getterMethod.getAnnotation(JsonProperty.class);
        final String jsonName = jsonProperty != null ? jsonProperty.value() : null;
        final String javadocLinkTag =
                String.format("{@link %s#%s()}", getterMethod.getEnclosingElement().getSimpleName(), getterMethod.getSimpleName());
        final boolean deprecated = getterMethod.getAnnotation(Deprecated.class) != null;
        return new PropertyGenModel(getPropertyName(getterMethod), getterMethod.getSimpleName().toString(), jsonName, getterMethod.getReturnType(), javadocLinkTag,
                optional, false, deprecated);
    }


    public static PropertyGenModel of(final String name, final String jsonName, final TypeMirror type, final String javaDocLinkTag, final boolean optional, boolean useReference) {
        final TypeName typeName = TypeName.get(type);
        final String methodNamePrefix = typeName.equals(BOXED_BOOLEAN) ? "is" : "get";
        final String methodName = methodNamePrefix + StringUtils.capitalize(name);
        return new PropertyGenModel(name, methodName, jsonName, type, javaDocLinkTag, optional, useReference, false);
    }

    /**
     * Returns the property name of the given property method.
     *
     * @param propertyMethod the getter method
     * @return the uncapitalized name of the property
     */
    public static String getPropertyName(final ExecutableElement propertyMethod) {
        final String name = propertyMethod.getSimpleName().toString();
        final int propertyNameIndex = name.startsWith("get") ? 3 : name.startsWith("is") ? 2 : 0;
        final String propertyName = StringUtils.uncapitalize(name.substring(propertyNameIndex));
        return propertyName;
    }
}
