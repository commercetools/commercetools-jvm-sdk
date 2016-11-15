package io.sphere.sdk.annotations.processors;

import io.sphere.sdk.annotations.ResourceDraftValue;
import io.sphere.sdk.models.Base;

import javax.lang.model.element.TypeElement;

final class ResourceDraftDslClassModelFactory extends ClassModelFactory {

    public ResourceDraftDslClassModelFactory(final TypeElement typeElement) {
        super(typeElement);
    }

    @Override
    public ClassModel createClassModel() {
        return ClassConfigurer.ofSource(typeElement)
                .samePackageFromSource()
                .imports()
                .defaultImports()
                .classJavadoc(null)
                .modifiers("public", "final")
                .classType()
                .className(ResourceDraftDslClassModelFactory::dslName)
                .extending(Base.class)
                .implementing(typeElement)
                .fields()
                .fieldsFromInterfaceBeanGetters(true)
                .constructors()
                .constructorForAllFields()
                .additionalConstructorContent(ResourceDraftValue.class, ResourceDraftValue::additionalDslConstructorEndContent)
                .methods()
                .gettersForFields()
                .withers()
                .factoryMethodsAccordingToAnnotations()
                .factoryMethodOfInterfaceInstance(true)
                .additionalContents(ResourceDraftValue.class, ResourceDraftValue::additionalDslClassContents)
                .build();
    }

    public static String dslName(final TypeElement typeElement) {
        return dslName(typeElement.getSimpleName().toString());
    }

    public static String dslName(final String name) {
        return name + "Dsl";
    }
}
