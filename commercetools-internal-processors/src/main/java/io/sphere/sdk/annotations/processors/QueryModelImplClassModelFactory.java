package io.sphere.sdk.annotations.processors;

import javax.lang.model.element.TypeElement;

final class QueryModelImplClassModelFactory extends ClassModelFactory {

    public QueryModelImplClassModelFactory(final TypeElement typeElement) {
        super(typeElement);
    }

    @Override
    public ClassModel createClassModel() {
        final ClassModelBuilder builder = ClassConfigurer.ofSource(typeElement)
                .samePackageFromSource()
                .imports()
                .defaultImports()
                .addImport("io.sphere.sdk.queries.*")
                .classJavadoc(null)
                .modifiers("final")
                .classType()
                .className(s -> s + "Impl")
                .interfaces()
                .fields()
                .constructors()
                .methods()
                .getBuilder();
        new QueryModelImplRules(typeElement, builder).execute();
        return builder.build();
    }
}
