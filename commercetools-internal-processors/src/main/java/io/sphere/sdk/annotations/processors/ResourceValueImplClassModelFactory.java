package io.sphere.sdk.annotations.processors;

import javax.lang.model.element.TypeElement;

public class ResourceValueImplClassModelFactory extends ClassModelFactory {

    public ResourceValueImplClassModelFactory(final TypeElement typeElement) {
        super(typeElement);
    }

    @Override
    public ClassModel createClassModel() {
        return ClassConfigurer.ofSource(typeElement)
                .samePackageFromSource()
                .imports()
                .defaultImports()
                .addImport("io.sphere.sdk.models.ResourceImpl")
                .classJavadoc(null)
                .modifiers("final")
                .classType()
                .className(ResourceValueImplClassModelFactory::implName)
                .extending("ResourceImpl<" + implName(typeElement) + ">")
                .implementing(typeElement)
                .fields()
                .fieldsFromInterfaceBeanGetters(true)
                .constructors()
                .resourceConstructorForAllFields()
                .methods()
                .gettersForFields()
                .build();
    }

    public static String implName(final TypeElement typeElement) {
        return implName(typeElement.getSimpleName().toString());
    }

    public static String implName(final String name) {
        return "Generated" + name + "Impl";
    }
}
