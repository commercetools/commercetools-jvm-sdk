package io.sphere.sdk.annotations.processors;

import io.sphere.sdk.annotations.ResourceInfo;
import org.apache.commons.lang3.tuple.Pair;

import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.TypeElement;
import java.lang.annotation.Annotation;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SupportedSourceVersion(SourceVersion.RELEASE_8)
public abstract class CommandEndpointAnnotationProcessor<A extends Annotation> extends ClassLevelAnnotationProcessor<A> {

    protected CommandEndpointAnnotationProcessor(final Class<A> clazz) {
        super(clazz);
    }

    @Override
    protected void generate(final TypeElement typeElement) {
        final Map<String, Object> values = values(typeElement);
        final String resourceName = getResourceName(typeElement);
        final String packagePrefix = ClassConfigurer.packageName(typeElement) + ".commands.";
        final List<Pair<String, String>> templates = getClassNameTemplateNameList(resourceName);
        templates.forEach(p -> writeClass(typeElement, packagePrefix + p.getLeft(), p.getRight(), values));
    }

    protected abstract List<Pair<String, String>> getClassNameTemplateNameList(final String resourceName);

    protected final Map<String, Object> values(final TypeElement typeElement) {
        final HashMap<String, Object> map = new HashMap<>();
        map.put("package", ClassConfigurer.packageName(typeElement));
        map.put("resourceName", getResourceName(typeElement));
        final ResourceInfo resourceInfo = typeElement.getAnnotation(ResourceInfo.class);
        if (resourceInfo == null) {
            throw new NullPointerException("" + ResourceInfo.class.getSimpleName() + " is missing in " + typeElement);
        }
        map.put("resourcePluralName", resourceInfo.pluralName());
        map.put("pathElement", resourceInfo.pathElement());
        map.put("commonImports", Arrays.asList(resourceInfo.commonImports()));

        final A annotation = typeElement.getAnnotation(clazz);
        final List<String> examples = Arrays.asList(includeExamplesExtraction(annotation));
        map.put("includeExamples", examples);
        map.put("isForId", true);
        map.put("javadocSummary", javadocSummaryExtraction(annotation));
        additions(annotation, map);
        return map;
    }

    protected void additions(final A annotation, final HashMap<String, Object> map) {

    }

    protected abstract String[] includeExamplesExtraction(final A annotation);

    protected abstract String javadocSummaryExtraction(final A annotation);
}