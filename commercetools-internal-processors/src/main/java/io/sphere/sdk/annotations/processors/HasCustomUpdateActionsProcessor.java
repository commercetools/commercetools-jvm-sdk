package io.sphere.sdk.annotations.processors;


import com.squareup.javapoet.JavaFile;
import io.sphere.sdk.annotations.HasCustomUpdateActions;
import io.sphere.sdk.annotations.processors.generators.CustomUpdateActionsGenerator;
import io.sphere.sdk.annotations.processors.generators.UpdateActionsGenerator;

import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.ExecutableElement;
import javax.tools.Diagnostic;
import java.util.List;

@SupportedAnnotationTypes({"io.sphere.sdk.annotations.HasCustomUpdateActions"})
@SupportedSourceVersion(SourceVersion.RELEASE_8)
public class HasCustomUpdateActionsProcessor extends MethodLevelAnnotationProcessor<HasCustomUpdateActions> {

    public HasCustomUpdateActionsProcessor() {
        super(HasCustomUpdateActions.class);
    }

    @Override
    protected void generate(final ExecutableElement executableElement) {


        final List<JavaFile> javaFiles = new CustomUpdateActionsGenerator(processingEnv.getElementUtils(), processingEnv.getTypeUtils(),processingEnv.getMessager()).generate(executableElement);
        javaFiles.forEach(javaFile -> writeClass(javaFile));
    }

}
