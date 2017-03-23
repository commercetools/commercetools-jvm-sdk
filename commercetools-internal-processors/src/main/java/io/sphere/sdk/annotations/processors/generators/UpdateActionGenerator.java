package io.sphere.sdk.annotations.processors.generators;

import com.squareup.javapoet.*;
import io.sphere.sdk.annotations.processors.models.PropertyGenModel;
import io.sphere.sdk.commands.UpdateActionImpl;
import org.apache.commons.lang3.StringUtils;

import javax.annotation.Generated;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.Elements;
import java.util.List;
import java.util.stream.Collectors;

public class UpdateActionGenerator extends AbstractGenerator {

    public UpdateActionGenerator(final Elements elements) {
        super(elements);
    }

    @Override
    protected String getPackageName(final TypeElement annotatedTypeElement) {
        return super.getPackageName(annotatedTypeElement).concat(".commands.updateactions");
    }

    @Override
    public TypeSpec generateType(final TypeElement annotatedTypeElement) {
        final List<ExecutableElement> propertyMethods = getAllPropertyMethodsSorted(annotatedTypeElement);
        final List<PropertyGenModel> propertyGenModels = getPropertyGenModels(propertyMethods);
        final List<MethodSpec> getMethods = propertyMethods.stream().map(this::createGetMethod).collect(Collectors.toList());
        final List<FieldSpec> fields = propertyGenModels.stream()
                .map((property) -> createFieldBuilder(property, Modifier.PRIVATE)
                        .addModifiers(Modifier.FINAL)
                        .build())
                .collect(Collectors.toList());
        final FieldSpec fieldSpec = fields.get(0);
        final String actionPrefix = propertyGenModels.get(0).isOptional() ? "set" : "change";
        final String updateAction = actionPrefix + StringUtils.capitalize(fieldSpec.name);
        final String updateActionClassName = StringUtils.capitalize(updateAction);
        final TypeSpec typeSpec = TypeSpec.classBuilder(updateActionClassName)
                .addJavadoc("$L $L to $L\n", propertyGenModels.get(0).isOptional() ? "Sets" : "Updates", fieldSpec.name, ClassName.get(annotatedTypeElement).simpleName())
                .addJavadoc("\n")
                .addJavadoc("{@doc.gen intro}\n")
                .superclass(ParameterizedTypeName.get(ClassName.get(UpdateActionImpl.class), ClassName.get(annotatedTypeElement)))
                .addModifiers(Modifier.PUBLIC, Modifier.FINAL)
                .addAnnotation(AnnotationSpec.builder(Generated.class)
                        .addMember("value", "$S", getClass().getCanonicalName())
                        .addMember("comments", "$S", "Generated from: " + annotatedTypeElement.getQualifiedName().toString()).build())
                .addFields(fields)
                .addMethod(createConstructor(propertyGenModels, updateAction))
                .addMethods(getMethods)
                .addMethod(createFactoryMethod(propertyGenModels, updateActionClassName))
                .build();
        return typeSpec;
    }

    private MethodSpec createConstructor(final List<PropertyGenModel> properties, final String updateAction) {
        final List<ParameterSpec> parameters = properties.stream()
                .map(this::createConstructorParameter)
                .collect(Collectors.toList());
        final MethodSpec.Builder builder = MethodSpec.constructorBuilder()
                .addParameters(parameters)
                .addModifiers(Modifier.PRIVATE)
                .addStatement("super($S)", updateAction);
        final List<String> parameterNames = properties.stream()
                .map(PropertyGenModel::getJavaIdentifier)
                .collect(Collectors.toList());
        parameterNames.forEach(n -> builder.addCode("this.$L = $L;\n", n, n));
        return builder.build();
    }

    private ParameterSpec createConstructorParameter(final PropertyGenModel propertyGenModel) {
        final ParameterSpec.Builder builder = ParameterSpec.builder(propertyGenModel.getType(), propertyGenModel.getJavaIdentifier(), Modifier.FINAL);
        return builder.build();
    }

    private MethodSpec createFactoryMethod(final List<PropertyGenModel> properties, final String updateActionClassName) {
        final List<ParameterSpec> parameters = properties.stream()
                .map(this::createConstructorParameter)
                .collect(Collectors.toList());
        final ParameterSpec parameterSpec = parameters.get(0);
        final MethodSpec of = MethodSpec.methodBuilder("of")
                .addParameter(parameterSpec)
                .addModifiers(Modifier.PUBLIC, Modifier.STATIC)
                .returns(ClassName.bestGuess(updateActionClassName))
                .addStatement("return new $T($L)", ClassName.bestGuess(updateActionClassName), parameterSpec.name)
                .build();
        return of;
    }

}
