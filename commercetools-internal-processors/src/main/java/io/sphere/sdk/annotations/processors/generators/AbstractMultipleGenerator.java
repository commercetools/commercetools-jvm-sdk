package io.sphere.sdk.annotations.processors.generators;

import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.TypeSpec;

import javax.lang.model.element.TypeElement;
import javax.lang.model.util.Elements;
import java.util.ArrayList;
import java.util.List;

abstract class AbstractMultipleGenerator extends BaseAbstractGenerator {

    AbstractMultipleGenerator(final Elements elements) {
        super(elements);
    }

    public final List<JavaFile> generate(final TypeElement annotatedTypeElement) {
        List<JavaFile> javaFiles = new ArrayList<>();
        final List<TypeSpec> typeSpecs = generateTypes(annotatedTypeElement);
        typeSpecs.forEach(typeSpec -> {
            final JavaFile javaFile = JavaFile.builder(getPackageName(annotatedTypeElement), typeSpec)
                    .build();
            javaFiles.add(javaFile);
        });
        return javaFiles;
    }

    protected abstract List<TypeSpec> generateTypes(final TypeElement annotatedTypeElement);

}
