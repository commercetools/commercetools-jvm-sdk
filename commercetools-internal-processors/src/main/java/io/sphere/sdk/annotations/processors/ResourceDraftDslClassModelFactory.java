package io.sphere.sdk.annotations.processors;

import io.sphere.sdk.models.Base;

import javax.lang.model.element.TypeElement;

public class ResourceDraftDslClassModelFactory extends ClassModelFactory {

    public ResourceDraftDslClassModelFactory(final TypeElement typeElement) {
        super(typeElement);
    }

    @Override
    public ClassModel createClassModel() {
        return ClassConfigurer.ofSource(typeElement)
                .samePackageFromSource()
                .imports()
                .defaultImports()
                .modifiers("public", "final")
                .classType()
                .className(ResourceDraftDslClassModelFactory::dslName)
                .extending(Base.class)
                .implementing(typeElement)
                .fields()
                .fieldsFromInterfaceBeanGetters()
                .constructors()
                .constructorForAllFields()
                .methods()
                .gettersForFields()
                .withers()
                .factoryMethodsAccordingToAnnotations()
                .build();
    }

    public static String dslName(final TypeElement typeElement) {
        return dslName(typeElement.getSimpleName().toString());
    }

    public static String dslName(final String name) {
        return "Generated" + name + "Dsl";
    }
}
