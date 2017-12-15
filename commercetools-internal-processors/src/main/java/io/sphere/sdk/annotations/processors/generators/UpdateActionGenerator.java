package io.sphere.sdk.annotations.processors.generators;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.squareup.javapoet.*;
import io.sphere.sdk.annotations.*;
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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * Generates update action classes for interfaces annotated with {@link HasUpdateAction}.
 */
public class UpdateActionGenerator extends AbstractGenerator<ExecutableElement> {


    private static final ClassName BOXED_BOOLEAN = ClassName.get("java.lang", "Boolean");

    public UpdateActionGenerator(final Elements elements, final Types types, final Messager messager) {
        super(elements, types, messager);

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
        return generateUpdateAction(propertyMethod, propertyMethod.getAnnotation(HasUpdateAction.class));
    }

    protected TypeSpec generateUpdateAction(final ExecutableElement propertyMethod, HasUpdateAction hasUpdateActionInstance) {

        if (!(propertyMethod.getEnclosingElement() instanceof TypeElement)) {
            messager.printMessage(Diagnostic.Kind.ERROR, "@" + HasUpdateAction.class.getSimpleName() + "Enclosing element should be a class");
            return null;
        }

        final TypeElement annotatedEnclosingElement = (TypeElement) propertyMethod.getEnclosingElement();
        final Element packageElement = annotatedEnclosingElement.getEnclosingElement();
        final TypeMirror resourceType = typeUtils.getAnnotationValue(packageElement, PackageResourceInfo.class, "type")
                .flatMap(v -> Optional.ofNullable((TypeMirror) v.getValue()))
                .orElseGet(() -> types.getDeclaredType(annotatedEnclosingElement));
        final PropertyGenModel property = PropertyGenModel.of(propertyMethod);
        final String actionNameDerivedFromField = (property.isOptional() ? "set" : "change") + StringUtils.capitalize(property.getName());
        final String actionName = getFirstNonEmpty(hasUpdateActionInstance.value(),actionNameDerivedFromField);
        final String updateActionClassName = getFirstNonEmpty(hasUpdateActionInstance.className(),StringUtils.capitalize(actionName));

        final String includeExample = hasUpdateActionInstance.exampleBaseClass();
        final boolean generateDefaultFactory = hasUpdateActionInstance.generateDefaultFactory();
        final PropertySpec[] properties = hasUpdateActionInstance.fields();
        final FactoryMethod[] factoryMethods = hasUpdateActionInstance.factoryMethods();
        final CopyFactoryMethod[] copyFactoryMethods = hasUpdateActionInstance.copyFactoryMethods();
        boolean isAbstract = hasUpdateActionInstance.makeAbstract();
        final List<TypeName> superInterfaces = getSuperInterfaces(hasUpdateActionInstance);

        final String includeExampleJavaDoc = includeExample.isEmpty() ?
                "" :
                String.format("{@include.example %s#%s()}\n\n", includeExample, propertyMethod.getSimpleName().toString().replaceFirst("g", "s"));
        final ClassName className = ClassName.bestGuess(isAbstract ? "Abstract" + updateActionClassName : updateActionClassName);
        final ClassName concreteClassName = ClassName.bestGuess(updateActionClassName);

        final List<PropertyGenModel> propertyGenModelList = properties.length != 0 ?
                Arrays.stream(properties).map(this::toGenModel).collect(Collectors.toList()) : Arrays.asList(property);
        final String[] allParamsNames = propertyGenModelList.stream().map(propertyGenModel -> propertyGenModel.getName()).toArray(String[]::new);
        final List<FieldSpec> fields = propertyGenModelList.stream().map(this::createField).collect(Collectors.toList());
        final List<MethodSpec> getters = propertyGenModelList.stream().map(this::createGetMethod).collect(Collectors.toList());
        final List<MethodSpec> optionalAttributesSetters = propertyGenModelList.stream().filter(PropertyGenModel::isOptional)
                .map(propertyGenModel -> createWithMethod(propertyGenModelList, propertyGenModel, concreteClassName))
                .collect(Collectors.toList());
        final boolean allAttributesOptional = propertyGenModelList.stream().map(PropertyGenModel::isOptional).reduce(Boolean::logicalAnd).get();

        final ClassName annotatedTypeName = ClassName.get(annotatedEnclosingElement);
        final TypeSpec.Builder typeSpecBuilder = TypeSpec.classBuilder(className)
                .addJavadoc("Updates the {@code $L} property of a {@link $T}.\n", property.getName(), ClassName.get(resourceType))
                .addJavadoc("\n")
                .addJavadoc(includeExampleJavaDoc)
                .addJavadoc("@see $T#$L()\n", annotatedTypeName, propertyMethod.getSimpleName())
                .superclass(ParameterizedTypeName.get(ClassName.get(UpdateActionImpl.class), ClassName.get(resourceType)))
                .addModifiers(isAbstract ? new Modifier[]{Modifier.ABSTRACT} : new Modifier[]{Modifier.PUBLIC, Modifier.FINAL})
                .addAnnotation(AnnotationSpec.builder(Generated.class)
                        .addMember("value", "$S", getClass().getCanonicalName())
                        .addMember("comments", "$S", "Generated from: " + annotatedTypeName).build())
                .addFields(fields)
                .addMethods(getters)
                .addMethod(createConstructor(propertyGenModelList, concreteClassName, actionName, isAbstract ? Modifier.PROTECTED : Modifier.PRIVATE))
                .addMethods(createFactoryMethods(factoryMethods, propertyGenModelList, concreteClassName))
                .addMethods(createCopyFactoryMethods(copyFactoryMethods, concreteClassName, propertyGenModelList))
                .addSuperinterfaces(superInterfaces);
        copyDeprecatedAnnotation(property, typeSpecBuilder);

        if(generateDefaultFactory){
            typeSpecBuilder.addMethod(createFactoryMethod(propertyGenModelList, concreteClassName, Arrays.asList(allParamsNames), "of", false));
        }
        if (allAttributesOptional) {
            typeSpecBuilder.addMethod(createFactoryMethod(propertyGenModelList, concreteClassName, Arrays.asList(), "ofUnset", false));
        }
        //if there is only one field there is no need for withers since a default factory method would be included
        if (propertyGenModelList.size() > 1) {
            typeSpecBuilder.addMethods(optionalAttributesSetters);
        }
        return typeSpecBuilder.build();
    }


    private static List<TypeName> getSuperInterfaces(HasUpdateAction annotation) {
        try {
            annotation.superInterfaces();
        } catch (MirroredTypesException e) {
            return e.getTypeMirrors().stream().map(ClassName::get).collect(Collectors.toList());
        }

        return Arrays.asList();
    }

    protected MethodSpec createConstructor(final List<PropertyGenModel> properties, final ClassName actionName, String actionString, final Modifier... modifiers) {
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


    protected MethodSpec createGetMethod(final PropertyGenModel propertyGenModel) {
        final MethodSpec.Builder specBuilder = MethodSpec.methodBuilder(propertyGenModel.getMethodName())
                .addModifiers(Modifier.PUBLIC)
                .returns(propertyGenModel.getType())
                .addCode("return $L;\n", propertyGenModel.getJavaIdentifier());

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
                .map(p -> propertyGenModel.getName().equals(p.getName()) ? p.getJavaIdentifier() : p.getMethodName() + "()")
                .collect(Collectors.joining(", "));

        final MethodSpec.Builder builder = MethodSpec.methodBuilder("with" + StringUtils.capitalize(propertyGenModel.getName())).addModifiers(Modifier.PUBLIC)
                .returns(returnType)
                .addJavadoc("Creates a copied update action initialized with the given parameter, the rest of the parameters are copied from the original object.\n\n");

        builder.addJavadoc("@return new object initialized with the copied values from the original object\n");
        return builder
                .addParameters(createParameters(Arrays.asList(propertyGenModel), false, false))
                .addCode("return new $L($L);\n", returnType.simpleName(), callArguments)
                .build();
    }

    private PropertyGenModel toGenModel(final PropertySpec propertySpec) {

        TypeMirror typeMirror = null;
        try {
            propertySpec.type();
        } catch (MirroredTypeException e) {
            typeMirror = e.getTypeMirror();
        }
        final String jsonName = StringUtils.isEmpty(propertySpec.jsonName()) ? null : propertySpec.jsonName();
        return PropertyGenModel.of(escapeJavaKeyword(propertySpec.name()), jsonName, typeMirror, propertySpec.docLinkTaglet(),
                propertySpec.isOptional(), propertySpec.useReference());

    }

    @Override
    protected FieldSpec createField(final PropertyGenModel property, final Modifier modifier) {
        final FieldSpec.Builder builder = super.createFieldBuilder(property, modifier);
        final String jsonName = property.getJsonName();

        if (!StringUtils.isEmpty(jsonName)) {
            final AnnotationSpec jsonProperty = createJsonPropertyAnnotation(jsonName);
            builder.addAnnotation(jsonProperty);
        }

        builder.addModifiers(Modifier.FINAL);

        return builder.build();
    }


    private ParameterSpec createConstructorParameter(final PropertyGenModel propertyGenModel) {
        final ParameterSpec.Builder builder = ParameterSpec.builder(propertyGenModel.getType(), propertyGenModel.getJavaIdentifier(), Modifier.FINAL);
        if (propertyGenModel.isOptional()) {
            builder.addAnnotation(Nullable.class);
        }
        return builder.build();
    }

    private static String getFirstNonEmpty(final String... args) {
        return Arrays.stream(args).filter(((Predicate<String>) StringUtils::isEmpty).negate()).findFirst().orElseGet(String::new);
    }
}
