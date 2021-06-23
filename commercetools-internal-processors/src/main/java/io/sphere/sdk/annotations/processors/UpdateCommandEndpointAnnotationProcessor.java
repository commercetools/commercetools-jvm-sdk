package io.sphere.sdk.annotations.processors;

import com.google.common.base.CaseFormat;
import com.google.common.base.Converter;
import io.sphere.sdk.annotations.HasUpdateCommand;
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

@SupportedAnnotationTypes({"io.sphere.sdk.annotations.HasUpdateCommand"})
@SupportedSourceVersion(SourceVersion.RELEASE_8)
public class UpdateCommandEndpointAnnotationProcessor<A extends Annotation> extends CommandEndpointAnnotationProcessor<HasUpdateCommand> {
    public UpdateCommandEndpointAnnotationProcessor() {
        super(HasUpdateCommand.class);
    }

    @Override
    protected List<Pair<String, String>> getClassNameTemplateNameList(final String resourceName) {
        return asList(
                ImmutablePair.of(resourceName + "UpdateCommand", "commands/updateCommandInterface"),
                ImmutablePair.of(resourceName + "UpdateCommandImpl", "commands/updateCommandImplementation")
        );
    }

    @Override
    protected String[] includeExamplesExtraction(final HasUpdateCommand annotation) {
        return annotation.includeExamples();
    }

    @Override
    protected String javadocSummaryExtraction(final HasUpdateCommand annotation) {
        return annotation.javadocSummary();
    }

    @Override
    protected void additions(final HasUpdateCommand annotation, final HashMap<String, Object> map) {
        final Converter<String, String> hyphenizer = CaseFormat.UPPER_CAMEL.converterTo(CaseFormat.LOWER_HYPHEN);
        final List<Map<String, String>> updateWith = new ArrayList<>();
        for (final String property : annotation.updateWith()) {
            final Map<String, String> updateWithProperties = new HashMap<>();
            updateWithProperties.put("name", property);
            updateWithProperties.put("capitalizedName", capitalize(property));
            updateWithProperties.put("hyphenizedName", hyphenizer.convert(property));
            updateWith.add(updateWithProperties);
        }
        map.put("updateWith", updateWith);
    }
}
