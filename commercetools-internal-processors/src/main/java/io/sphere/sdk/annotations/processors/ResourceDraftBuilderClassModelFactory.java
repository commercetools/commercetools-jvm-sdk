package io.sphere.sdk.annotations.processors;

import io.sphere.sdk.models.Base;

import javax.lang.model.element.TypeElement;

public class ResourceDraftBuilderClassModelFactory extends ClassModelFactory {

    public ResourceDraftBuilderClassModelFactory(final TypeElement typeElement) {
        super(typeElement);
    }

    @Override
    public ClassModel createClassModel() {
        return ClassConfigurer.ofSource(typeElement)
                .samePackageFromSource()
                .imports()
                .withDefaultImports()
                .addImport("io.sphere.sdk.models.Builder")
                .modifiers("public", "final")
                .classType()
                .className(input -> associatedBuilderName(input))
                .extending(Base.class)//TODO missing
                .implementingBasedOnSourceName(name -> "Builder<" + name + ">")
                .fields()
                .fieldsFromInterfaceBeanGetters()
                .constructors()
                .constructorForAllFields()
                .methods()
                .builderMethods()
                .buildMethod()
                .factoryMethodsAccordingToAnnotations()
                .build();
    }

}
