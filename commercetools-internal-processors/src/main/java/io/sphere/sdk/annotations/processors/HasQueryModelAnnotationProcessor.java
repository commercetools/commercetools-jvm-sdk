package io.sphere.sdk.annotations.processors;

import io.sphere.sdk.annotations.HasQueryModel;

import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.TypeElement;

@SupportedAnnotationTypes({"io.sphere.sdk.annotations.HasQueryModel"})
@SupportedSourceVersion(SourceVersion.RELEASE_8)
public final class HasQueryModelAnnotationProcessor extends ClassLevelAnnotationProcessor<HasQueryModel> {

    public HasQueryModelAnnotationProcessor() {
        super(HasQueryModel.class);
    }

    @Override
    protected void generate(final TypeElement typeElement) {
        writeClass(typeElement, new QueryModelClassModelFactory(typeElement).createClassModel());
    }

}