package io.sphere.sdk.annotations.processors.generators;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.squareup.javapoet.*;
import io.sphere.sdk.annotations.CopyFactoryMethod;
import io.sphere.sdk.annotations.FactoryMethod;
import io.sphere.sdk.annotations.HasCustomUpdateAction;
import io.sphere.sdk.annotations.PropertySpec;
import io.sphere.sdk.annotations.processors.models.PropertyGenModel;
import io.sphere.sdk.commands.UpdateActionImpl;
import org.apache.commons.lang3.StringUtils;

import javax.annotation.Generated;
import javax.annotation.Nullable;
import javax.annotation.processing.Messager;
import javax.lang.model.element.Element;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.MirroredTypeException;
import javax.lang.model.type.MirroredTypesException;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;
import javax.tools.Diagnostic;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Generates update action classes for interfaces annotated with {@link io.sphere.sdk.annotations.HasCustomUpdateAction}.
 */
public class CustomUpdateActionGenerator extends AbstractGenerator<ExecutableElement> {

    private final Messager messager;

    public CustomUpdateActionGenerator(Elements elements, Types types, Messager messager) {
        super(elements, types);
        this.messager = messager;

    }

    @Override
    protected String getPackageName(final Element annotatedTypeElement) {
        return super.getPackageName(annotatedTypeElement).concat(".commands.updateactions");
    }

    @Override
    public TypeSpec generateType(final ExecutableElement annotatedTypeElement) {

        final TypeSpec typeSpecList = generateUpdateAction(annotatedTypeElement);

        return typeSpecList;
    }

    protected TypeSpec generateUpdateAction(final ExecutableElement propertyMethod) {
        return generateUpdateAction(propertyMethod,propertyMethod.getAnnotation(HasCustomUpdateAction.class));
    }

    protected TypeSpec generateUpdateAction(final ExecutableElement propertyMethod,HasCustomUpdateAction hasCustomUpdateActionInstance) {

        if (!(propertyMethod.getEnclosingElement() instanceof TypeElement)) {
            messager.printMessage(Diagnostic.Kind.ERROR, "@" + HasCustomUpdateAction.class.getSimpleName() + "Enclosing element should be a class");
            return null;
        }

        final TypeElement annotatedEnclosingElement = (TypeElement) propertyMethod.getEnclosingElement();


        final String actionName = hasCustomUpdateActionInstance.name();
        final String includeExample = hasCustomUpdateActionInstance.exampleBaseClass();
        final PropertySpec[] properties = hasCustomUpdateActionInstance.fields();
        final FactoryMethod[] factoryMethods = hasCustomUpdateActionInstance.factoryMethods();
        final CopyFactoryMethod[] copyFactoryMethods = hasCustomUpdateActionInstance.copyFactoryMethods();
        boolean isAbstract = hasCustomUpdateActionInstance.makeAbstract();
        final List<TypeName> superInterfaces = getSuperInterfaces(hasCustomUpdateActionInstance);

        final String updateActionClassName = StringUtils.capitalize(actionName);

        final String includeExampleJavaDoc = includeExample.isEmpty() ?
                "" :
                String.format("{@include.example %s#%s()}\n\n", includeExample, propertyMethod.getSimpleName().toString().replaceFirst("g", "s"));
        final ClassName className = ClassName.bestGuess(isAbstract ? "Abstract" + updateActionClassName : updateActionClassName);
        final ClassName concreteClassName = ClassName.bestGuess(updateActionClassName);

        final List<PropertyGenModel> propertyGenModelList = Arrays.stream(properties).map(this::toGenModel).collect(Collectors.toList());
        final String[] allParamsNames = propertyGenModelList.stream().map(propertyGenModel -> propertyGenModel.getName()).toArray(String[]::new);
        final List<FieldSpec> fields = propertyGenModelList.stream().map(this::createField).collect(Collectors.toList());
        final List<MethodSpec> getters = propertyGenModelList.stream().map(this::createGetMethod).collect(Collectors.toList());
        final List<MethodSpec> optionalAttributesSetters = propertyGenModelList.stream().filter(PropertyGenModel::isOptional)
                .map(propertyGenModel -> createWithMethod(propertyGenModelList, propertyGenModel, concreteClassName))
                .collect(Collectors.toList());
        final boolean allAttributesOptional = propertyGenModelList.stream().map(PropertyGenModel::isOptional).reduce(Boolean::logicalAnd).get();


        final TypeSpec.Builder typeSpecBuilder = TypeSpec.classBuilder(className)
                .addJavadoc("$L the {@code $L} property of a {@link $T}.\n",
                        "Updates", propertyMethod.getSimpleName(), ClassName.get(annotatedEnclosingElement))
                .addJavadoc("\n")
                .addJavadoc(includeExampleJavaDoc)
                .addJavadoc("@see $T#$L()\n", ClassName.get(annotatedEnclosingElement), propertyMethod.getSimpleName())
                .superclass(ParameterizedTypeName.get(ClassName.get(UpdateActionImpl.class), ClassName.get(annotatedEnclosingElement)))
                .addModifiers(isAbstract ? new Modifier[]{Modifier.ABSTRACT} : new Modifier[]{Modifier.PUBLIC, Modifier.FINAL})
                .addAnnotation(AnnotationSpec.builder(Generated.class)
                        .addMember("value", "$S", getClass().getCanonicalName())
                        .addMember("comments", "$S", "Generated from: " + annotatedEnclosingElement.getQualifiedName().toString()).build())
                .addFields(fields)
                .addMethods(getters)
                .addMethod(createConstructor(propertyGenModelList, concreteClassName,actionName, isAbstract ? Modifier.PROTECTED : Modifier.PRIVATE))
                .addMethod(createFactoryMethod(propertyGenModelList, concreteClassName, allParamsNames, "of", false))
                .addMethods(createFactoryMethods(factoryMethods, propertyGenModelList, concreteClassName))
                .addMethods(createCopyFactoryMethods(copyFactoryMethods, concreteClassName, propertyGenModelList))
                .addMethods(optionalAttributesSetters)
                .addSuperinterfaces(superInterfaces);

        if (allAttributesOptional)
            typeSpecBuilder.addMethod(createFactoryMethod(propertyGenModelList, concreteClassName, new String[]{}, "ofUnset", false));

        return typeSpecBuilder.build();
    }


    private static List<TypeName> getSuperInterfaces(HasCustomUpdateAction annotation) {
        try {
            annotation.superInterfaces();
        } catch (MirroredTypesException e) {
            return e.getTypeMirrors().stream().map(ClassName::get).collect(Collectors.toList());
        }

        return Arrays.asList();
    }

    protected MethodSpec createConstructor(final List<PropertyGenModel> properties, final ClassName actionName,String actionString, final Modifier... modifiers) {
        final List<ParameterSpec> parameters = properties.stream()
                .map(this::createConstructorParameter)
                .collect(Collectors.toList());

        final MethodSpec.Builder builder = MethodSpec.constructorBuilder()
                .addParameters(parameters)
                .addStatement("super($S)", actionString)
                .addModifiers(modifiers);

        final List<String> parameterNames = properties.stream()
                .map(PropertyGenModel::getJavaIdentifier)
                .collect(Collectors.toList());
        parameterNames.forEach(n -> builder.addCode("this.$L = $L;\n", n, n));

        return builder.build();
    }

    private List<MethodSpec> createCopyFactoryMethods(CopyFactoryMethod[] factoryMethods, ClassName returnType, List<PropertyGenModel> propertyGenModels) {
        List<TypeMirror> typeMirrors = new ArrayList<>();
        for (int i = 0; i < factoryMethods.length; i++) {

            try {
                factoryMethods[i].value();
            } catch (MirroredTypeException e) {
                typeMirrors.add(e.getTypeMirror());
            }
        }

        return typeMirrors.stream()
                .map(typeMirror -> elements.getTypeElement(typeMirror.toString()))
                .map(element -> createCopyFactoryMethod(element, returnType, getAllPropertyMethodsSorted(element), propertyGenModels)).collect(Collectors.toList());
    }


    private MethodSpec createGetMethod(final PropertyGenModel propertyGenModel) {


        MethodSpec.Builder specBuilder = MethodSpec.methodBuilder("get" + StringUtils.capitalize(propertyGenModel.getName()))
                .addModifiers(Modifier.PUBLIC)
                .returns(propertyGenModel.getType())
                .addCode("return $L;\n", propertyGenModel.getName());

        if (propertyGenModel.isOptional()) {
            specBuilder.addAnnotation(Nullable.class);
        }

        if (!StringUtils.isEmpty(propertyGenModel.getJsonName())) {
            AnnotationSpec annotationSpec = AnnotationSpec.builder(JsonProperty.class)
                    .addMember("value", "\"" + propertyGenModel.getJsonName() + "\"").build();
            specBuilder.addAnnotation(annotationSpec);
        }

        return specBuilder.build();
    }

    protected MethodSpec createWithMethod(final List<PropertyGenModel> properties, final PropertyGenModel propertyGenModel, final ClassName returnType) {

        final String callArguments = properties.stream()
                .map(p -> propertyGenModel.getName().equals(p.getName()) ? p.getJavaIdentifier() : "get" + StringUtils.capitalize(p.getName()) + "()")
                .collect(Collectors.joining(", "));

        final MethodSpec.Builder builder = MethodSpec.methodBuilder("with" + StringUtils.capitalize(propertyGenModel.getName())).addModifiers(Modifier.PUBLIC)
                .returns(returnType)
                .addJavadoc("Creates a copied object initialized with the given value.\n\n");

        builder.addJavadoc("@return new object initialized with the copied values from the original object\n");
        return builder
                .addParameters(createParameters(Arrays.asList(propertyGenModel), false, false))
                .addCode("return new $L($L);\n", returnType.simpleName(), callArguments)
                .build();
    }

    private PropertyGenModel toGenModel(final PropertySpec propertySpec) {

        TypeMirror typeMirror = null;
        try {
            propertySpec.fieldType();
        } catch (MirroredTypeException e) {
            typeMirror = e.getTypeMirror();
        }
        final String jsonName = StringUtils.isEmpty(propertySpec.jsonName()) ? null : propertySpec.jsonName();
        return PropertyGenModel.of(escapeJavaKeyword(propertySpec.name()), jsonName, typeMirror, propertySpec.docLinkTaglet(), propertySpec.isOptional(), propertySpec.useReference());

    }


    protected FieldSpec createField(final PropertyGenModel property, final Modifier modifier) {
        final FieldSpec.Builder builder = super.createFieldBuilder(property, modifier);
        final String jsonName = property.getJsonName();

        if (!StringUtils.isEmpty(jsonName)) {
            final AnnotationSpec jsonProperty = createJsonPropertyAnnotation(jsonName);
            builder.addAnnotation(jsonProperty);
        }

        if (!property.isOptional()) {
            builder.addModifiers(Modifier.FINAL);
        }

        return builder.build();
    }


    private ParameterSpec createConstructorParameter(final PropertyGenModel propertyGenModel) {
        final ParameterSpec.Builder builder = ParameterSpec.builder(propertyGenModel.getType(), propertyGenModel.getJavaIdentifier(), Modifier.FINAL);
        if (propertyGenModel.isOptional()) {
            builder.addAnnotation(Nullable.class);
        }
        return builder.build();
    }

}
