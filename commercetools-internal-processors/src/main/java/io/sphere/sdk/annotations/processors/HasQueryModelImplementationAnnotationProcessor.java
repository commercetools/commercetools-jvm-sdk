package io.sphere.sdk.annotations.processors;

import io.sphere.sdk.annotations.HasQueryModelImplementation;

import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.TypeElement;

@SupportedAnnotationTypes({"io.sphere.sdk.annotations.HasQueryModelImplementation"})
@SupportedSourceVersion(SourceVersion.RELEASE_8)
public final class HasQueryModelImplementationAnnotationProcessor extends ClassLevelAnnotationProcessor<HasQueryModelImplementation> {

    public HasQueryModelImplementationAnnotationProcessor() {
        super(HasQueryModelImplementation.class);
    }

    @Override
    protected void generate(final TypeElement typeElement) {
        writeClass(typeElement, new QueryModelImplClassModelFactory(typeElement).createClassModel());
    }
}