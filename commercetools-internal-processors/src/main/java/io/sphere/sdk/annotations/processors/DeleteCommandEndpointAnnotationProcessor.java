package io.sphere.sdk.annotations.processors;

import com.google.common.base.CaseFormat;
import com.google.common.base.Converter;
import io.sphere.sdk.annotations.HasDeleteCommand;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;

import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.util.Arrays.asList;
import static org.apache.commons.text.WordUtils.capitalize;

@SupportedAnnotationTypes({"io.sphere.sdk.annotations.HasDeleteCommand"})
@SupportedSourceVersion(SourceVersion.RELEASE_8)
public class DeleteCommandEndpointAnnotationProcessor<A extends Annotation> extends CommandEndpointAnnotationProcessor<HasDeleteCommand> {
    public DeleteCommandEndpointAnnotationProcessor() {
        super(HasDeleteCommand.class);
    }

    @Override
    protected List<Pair<String, String>> getClassNameTemplateNameList(final String resourceName) {
        return asList(
                ImmutablePair.of(resourceName + "DeleteCommand", "commands/deleteCommandInterface"),
                ImmutablePair.of(resourceName + "DeleteCommandImpl", "commands/deleteCommandImplementation")
        );
    }

    @Override
    protected String[] includeExamplesExtraction(final HasDeleteCommand annotation) {
        return annotation.includeExamples();
    }

    @Override
    protected String javadocSummaryExtraction(final HasDeleteCommand annotation) {
        return annotation.javadocSummary();
    }

    @Override
    protected void additions(final HasDeleteCommand annotation, final HashMap<String, Object> map) {
        final Converter<String, String> hyphenizer = CaseFormat.UPPER_CAMEL.converterTo(CaseFormat.LOWER_HYPHEN);
        final List<Map<String, String>> deleteWith = new ArrayList<>();
        for (final String property : annotation.deleteWith()) {
            final Map<String, String> deleteWithProperties = new HashMap<>();
            deleteWithProperties.put("name", property);
            deleteWithProperties.put("capitalizedName", capitalize(property));
            deleteWithProperties.put("hyphenizedName", hyphenizer.convert(property));
            deleteWith.add(deleteWithProperties);
        }
        map.put("deleteWith", deleteWith);
        map.put("canEraseUsersData", annotation.canEraseUsersData());
    }
}
