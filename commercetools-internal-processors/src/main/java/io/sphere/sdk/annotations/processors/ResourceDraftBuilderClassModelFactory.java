package io.sphere.sdk.annotations.processors;

import io.sphere.sdk.models.Base;

import javax.lang.model.element.TypeElement;

import static io.sphere.sdk.annotations.processors.ResourceDraftDslClassModelFactory.dslName;
import static java.lang.String.format;

public class ResourceDraftBuilderClassModelFactory extends ClassModelFactory {

    public ResourceDraftBuilderClassModelFactory(final TypeElement typeElement) {
        super(typeElement);
    }

    @Override
    public ClassModel createClassModel() {
        return ClassConfigurer.ofSource(typeElement)
                .samePackageFromSource()
                .imports()
                .defaultImports()
                .addImport("io.sphere.sdk.models.Builder")
                .classJavadoc(format("Builder for {@link %s}.", typeElement.getSimpleName().toString()))
                .modifiers("public", "final")
                .classType()
                .className(input -> builderName(input))
                .extending(Base.class)//TODO missing
                .implementingBasedOnSourceName(name -> "Builder<" + dslName(typeElement) + ">")
                .fields()
                .fieldsFromInterfaceBeanGetters(false)
                .constructors()
                .draftConstructorForAllFields()
                .methods()
                .builderMethods()
                .buildMethod()
                .factoryMethodsAccordingToAnnotations()
                .factoryMethodFromInterfaceInstance(false)
                .build();
    }

    public static String builderName(final String originClassName) {
        return "Generated" + originClassName + "Builder";
    }

    public static String builderName(final TypeElement typeElement) {
        return builderName(typeElement.getSimpleName().toString());
    }
}
