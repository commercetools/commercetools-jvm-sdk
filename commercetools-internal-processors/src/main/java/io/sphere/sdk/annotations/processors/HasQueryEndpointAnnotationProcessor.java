package io.sphere.sdk.annotations.processors;

import io.sphere.sdk.annotations.HasQueryEndpoint;
import io.sphere.sdk.annotations.ResourceInfo;

import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.TypeElement;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import static java.util.Arrays.asList;

@SupportedAnnotationTypes({"io.sphere.sdk.annotations.HasQueryEndpoint"})
@SupportedSourceVersion(SourceVersion.RELEASE_8)
public final class HasQueryEndpointAnnotationProcessor extends ClassLevelAnnotationProcessor<HasQueryEndpoint> {

    public HasQueryEndpointAnnotationProcessor() {
        super(HasQueryEndpoint.class);
    }

    @Override
    protected void generate(final TypeElement typeElement) {
        final String fullyQualifiedNameQueryInterface = ClassConfigurer.packageName(typeElement) + ".queries." + typeElement.getSimpleName() + "Query";
        final Map<String, Object> values = queryInterfaceClassValues(typeElement);
        writeClass(typeElement, fullyQualifiedNameQueryInterface, "queries/resourceQueryInterface", values);
        writeClass(typeElement, fullyQualifiedNameQueryInterface + "Impl", "queries/resourceQueryImplementation", values);
        writeClass(typeElement, fullyQualifiedNameQueryInterface + "Builder", "queries/resourceQueryBuilder", values);
    }

    private Map<String, Object> queryInterfaceClassValues(final TypeElement typeElement) {
        final HashMap<String, Object> map = new HashMap<>();
        map.put("package", ClassConfigurer.packageName(typeElement));
        map.put("resourceName", getResourceName(typeElement));
        final ResourceInfo resourceInfo = typeElement.getAnnotation(ResourceInfo.class);
        map.put("resourcePluralName", resourceInfo.pluralName());
        map.put("pathElement", resourceInfo.pathElement());
        map.put("commonImports", Arrays.asList(resourceInfo.commonImports()));
        final HasQueryEndpoint queryInfo = typeElement.getAnnotation(HasQueryEndpoint.class);
        map.put("extras", asList(queryInfo.additionalContentsQueryInterface()));
        return map;
    }
}