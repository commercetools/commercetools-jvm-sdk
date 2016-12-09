package io.sphere.sdk.annotations.processors;

import javax.lang.model.element.TypeElement;

final class QueryModelClassModelFactory extends ClassModelFactory {

    public QueryModelClassModelFactory(final TypeElement typeElement) {
        super(typeElement);
    }

    @Override
    public ClassModel createClassModel() {
        final ClassModelBuilder builder = ClassConfigurer.ofSource(typeElement)
                .subPackageFrom(typeElement, "queries")
                .imports()
                .defaultImports()
                .addImport("io.sphere.sdk.queries.*")
                .addImport("io.sphere.sdk.reviews.queries.*")
                .classJavadoc(null)
                .modifiers("public")
                .interfaceType()
                .className(s -> s + "QueryModel")
                .interfaces()
                .fields()
                .constructors()
                .methods()
                .getBuilder();
        final QueryModelRules queryModelRules = new QueryModelRules(typeElement, builder);
        queryModelRules.execute();
        return builder.build();
    }
}
