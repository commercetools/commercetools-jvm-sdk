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
                .addImport("io.sphere.sdk.reviews.queries.*")
                .classJavadoc(null)
                .modifiers("final")
                .classType()
                .className(s -> s + "Impl")
                .interfaces()
                .fields()
                .constructors()
                .methods()
                .getBuilder();
//        final QueryModelRules queryModelRules = new QueryModelRules(typeElement, builder);
//        queryModelRules.execute();
        return builder.build();
    }
}
