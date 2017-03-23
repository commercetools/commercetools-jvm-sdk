package io.sphere.sdk.annotations.processors;

import io.sphere.sdk.annotations.HasUpdateActions;

import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.TypeElement;

@SupportedAnnotationTypes({"io.sphere.sdk.annotations.HasUpdateActions"})
@SupportedSourceVersion(SourceVersion.RELEASE_8)
public class HasUpdateActionsProcessor extends CommercetoolsAnnotationProcessor<HasUpdateActions> {

    public HasUpdateActionsProcessor() {
        super(HasUpdateActions.class);
    }

    @Override
    protected void generate(final TypeElement typeElement) {
//        final JavaFile javaFile = new SimpleUpdateActionGenerator(processingEnv.getElementUtils()).generate(typeElement);
//        writeClass(javaFile);
    }

}
