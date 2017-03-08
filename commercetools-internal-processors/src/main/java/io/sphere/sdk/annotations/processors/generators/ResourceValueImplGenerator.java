package io.sphere.sdk.annotations.processors.generators;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.squareup.javapoet.*;
import io.sphere.sdk.annotations.processors.models.PropertyGenModel;
import io.sphere.sdk.models.Base;

import javax.annotation.Generated;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.Elements;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Generates implementation classes for interfaces annotated with {@link io.sphere.sdk.annotations.ResourceValue}.
 */
public class ResourceValueImplGenerator extends AbstractGenerator {
    public ResourceValueImplGenerator(final Elements elements) {
        super(elements);
    }

    @Override
    public JavaFile generate(final TypeElement annotatedTypeElement) {
        final ClassName implTypeName = typeUtils.getValueImplType(annotatedTypeElement);

        final List<ExecutableElement> getterMethods = getGetterMethodsSorted(annotatedTypeElement);
        final List<PropertyGenModel> propertyGenModels = getPropertyGenModels(getterMethods);
        final List<FieldSpec> fields = propertyGenModels.stream().map(this::createField).collect(Collectors.toList());

        final List<MethodSpec> getMethods = getterMethods.stream().map(this::createGetMethod).collect(Collectors.toList());

        TypeSpec typeSpec = TypeSpec.classBuilder(implTypeName)
                .superclass(ClassName.get(Base.class))
                .addSuperinterface(ClassName.get(annotatedTypeElement.asType()))
                .addModifiers(Modifier.FINAL)
                .addAnnotation(AnnotationSpec.builder(Generated.class)
                    .addMember("value", "$S", getClass().getCanonicalName())
                    .addMember("comments", "$S", "Generated from: " + annotatedTypeElement.getQualifiedName().toString()).build())
                .addFields(fields)
                .addMethod(createConstructor(propertyGenModels))
                .addMethods(getMethods)
                .build();

        final JavaFile javaFile = JavaFile.builder(implTypeName.packageName(), typeSpec).build();
        return javaFile;
    }

    @Override
    public String getGeneratedFileSuffix() {
        return "Impl";
    }

    private MethodSpec createConstructor(final List<PropertyGenModel> properties) {
        final List<ParameterSpec> parameters = properties.stream()
                .map(this::createConstructorParameter)
                .collect(Collectors.toList());

        final MethodSpec.Builder builder = MethodSpec.constructorBuilder()
                .addParameters(parameters);
        final List<String> parameterNames = properties.stream()
                .map(PropertyGenModel::getJavaIdentifier)
                .collect(Collectors.toList());
        parameterNames.forEach(n -> builder.addCode("this.$L = $L;\n", n, n));

        return builder.build();
    }

    private ParameterSpec createConstructorParameter(final PropertyGenModel propertyGenModel) {
        return ParameterSpec.builder(propertyGenModel.getType(), propertyGenModel.getJavaIdentifier(), Modifier.FINAL).build();
    }

    @Override
    protected MethodSpec.Builder createGetMethodBuilder(final ExecutableElement getterMethod) {
        final MethodSpec.Builder builder = super.createGetMethodBuilder(getterMethod);

        final JsonProperty jsonProperty = getterMethod.getAnnotation(JsonProperty.class);
        final String jsonName = jsonProperty != null ? jsonProperty.value() : null;

        if (jsonName != null) {
            return builder.addAnnotation(createJsonPropertyAnnotation(jsonName));
        }

        return builder;
    }

    @Override
    protected FieldSpec.Builder createFieldBuilder(final PropertyGenModel property, final Modifier modifier) {
        final FieldSpec.Builder builder = super.createFieldBuilder(property, modifier);
        final String jsonName = property.getJsonName();

        if (jsonName != null) {
            final AnnotationSpec jsonProperty = createJsonPropertyAnnotation(jsonName);
            return builder.addAnnotation(jsonProperty);
        }

        return builder;
    }

    private AnnotationSpec createJsonPropertyAnnotation(final String jsonName) {
        return AnnotationSpec.builder(JsonProperty.class)
                        .addMember("value", "$S", jsonName)
                        .build();
    }
}
