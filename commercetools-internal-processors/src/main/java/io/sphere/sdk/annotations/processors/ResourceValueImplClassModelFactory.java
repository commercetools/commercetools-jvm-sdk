package io.sphere.sdk.annotations.processors;

import io.sphere.sdk.models.Base;

import javax.lang.model.element.TypeElement;

final class ResourceValueImplClassModelFactory extends ClassModelFactory {

    public ResourceValueImplClassModelFactory(final TypeElement typeElement) {
        super(typeElement);
    }

    @Override
    public ClassModel createClassModel() {
        return ClassConfigurer.ofSource(typeElement)
                .samePackageFromSource()
                .imports()
                .defaultImports()
                .classJavadoc(null)
                .modifiers("final")
                .classType()
                .className(ResourceValueImplClassModelFactory::implName)
                .extending(Base.class)
                .implementing(typeElement)
                .fields()
                .fieldsFromInterfaceBeanGetters(true)
                .constructors()
                .constructorForAllFields()
                .methods()
                .gettersForFields()
                .build();
    }

    public static String implName(final TypeElement typeElement) {
        return implName(typeElement.getSimpleName().toString());
    }

    public static String implName(final String name) {
        return name + "Impl";
    }
}
