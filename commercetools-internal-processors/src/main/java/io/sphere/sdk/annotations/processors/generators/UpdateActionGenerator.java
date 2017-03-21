package io.sphere.sdk.annotations.processors.generators;

import com.squareup.javapoet.*;
import io.sphere.sdk.annotations.HasUpdateActions;
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
    public TypeSpec generateType(final TypeElement resourceValueTypeElement) {

        final HasUpdateActions annotation = resourceValueTypeElement.getAnnotation(HasUpdateActions.class);

        final List<ExecutableElement> propertyMethods = getAllPropertyMethodsSorted(resourceValueTypeElement);
        final List<PropertyGenModel> propertyGenModels = getPropertyGenModels(propertyMethods);
        final List<FieldSpec> fields = propertyGenModels.stream()
                .map((property) -> createFieldBuilder(property, Modifier.PRIVATE)
                        .addModifiers(Modifier.FINAL)
                        .build())
                .collect(Collectors.toList());
        final List<MethodSpec> getMethods = propertyMethods.stream().map(this::createGetMethod).collect(Collectors.toList());
        final String updateAction = "set" + StringUtils.capitalize(fields.get(0).name);
        final String updateActionClassName = StringUtils.capitalize(updateAction);

        final TypeSpec typeSpec = TypeSpec.classBuilder(updateActionClassName)
                .superclass(ParameterizedTypeName.get(ClassName.get(UpdateActionImpl.class), ClassName.get(resourceValueTypeElement)))
                .addModifiers(Modifier.FINAL)
                .addAnnotation(AnnotationSpec.builder(Generated.class)
                        .addMember("value", "$S", getClass().getCanonicalName())
                        .addMember("comments", "$S", "Generated from: " + resourceValueTypeElement.getQualifiedName().toString()).build())
                .addFields(fields)
                .addMethod(createConstructor(propertyGenModels, updateAction))
                .addMethods(getMethods)
//                .addMethod(createFactoryMethod(annotation.factoryMethod(), propertyGenModels, ))
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


}
