package io.sphere.sdk.annotations.processors;

import io.sphere.sdk.annotations.ResourceDraftValue;
import io.sphere.sdk.models.Base;

import javax.lang.model.element.TypeElement;

import static io.sphere.sdk.annotations.processors.ResourceDraftDslClassModelFactory.dslName;
import static java.lang.String.format;

final class ResourceDraftBuilderClassModelFactory extends ClassModelFactory {

    public ResourceDraftBuilderClassModelFactory(final TypeElement typeElement) {
        super(typeElement);
    }

    @Override
    public ClassModel createClassModel() {
        final ResourceDraftValue a = typeElement.getAnnotation(ResourceDraftValue.class);
        final boolean builderStereotypeDslClass = a.useBuilderStereotypeDslClass();
        final String interfaceSimpleName = typeElement.getSimpleName().toString();
        return ClassConfigurer.ofSource(typeElement)
                .samePackageFromSource()
                .imports()
                .defaultImports()
                .addImport("io.sphere.sdk.models.Builder")
                .classJavadoc(format("Builder for {@link %s}.", interfaceSimpleName))
                .modifiers("public", "final")
                .classType()
                .className(input -> builderName(input))
                .extending(Base.class)//TODO missing
                .implementingBasedOnSourceName(name -> builderStereotypeDslClass ? "Builder<" + dslName(typeElement) + ">" : "Builder<" + interfaceSimpleName + ">")
                .implementing(a.additionalBuilderInterfaces())
                .fields()
                .fieldsFromInterfaceBeanGetters(false)
                .constructors()
                .constructorForAllFields()
                .methods()
                .builderMethods()
                .buildMethod()
                .factoryMethodsAccordingToAnnotations()
                .factoryMethodOfInterfaceInstance(false)
                .gettersForFieldsInCase(ResourceDraftValue.class, ResourceDraftValue::gettersForBuilder)
                .additionalContents(ResourceDraftValue.class, ResourceDraftValue::additionalBuilderClassContents)
                .build();
    }

    public static String builderName(final String originalClassName) {
        return originalClassName + "Builder";
    }

    public static String builderName(final TypeElement typeElement) {
        return builderName(typeElement.getSimpleName().toString());
    }
}
