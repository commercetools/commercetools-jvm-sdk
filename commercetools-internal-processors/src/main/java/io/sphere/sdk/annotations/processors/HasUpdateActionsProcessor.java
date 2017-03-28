package io.sphere.sdk.annotations.processors;

import com.squareup.javapoet.JavaFile;
import io.sphere.sdk.annotations.HasUpdateActions;
import io.sphere.sdk.annotations.processors.generators.UpdateActionsGenerator;

import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.TypeElement;
import java.util.List;

@SupportedAnnotationTypes({"io.sphere.sdk.annotations.HasUpdateActions"})
@SupportedSourceVersion(SourceVersion.RELEASE_8)
public class HasUpdateActionsProcessor extends CommercetoolsAnnotationProcessor<HasUpdateActions> {

    public HasUpdateActionsProcessor() {
        super(HasUpdateActions.class);
    }

    @Override
    protected void generate(final TypeElement typeElement) {
        final List<JavaFile> javaFiles = new UpdateActionsGenerator(processingEnv.getElementUtils()).generate(typeElement);
        javaFiles.forEach(javaFile -> writeClass(javaFile));
    }

}
