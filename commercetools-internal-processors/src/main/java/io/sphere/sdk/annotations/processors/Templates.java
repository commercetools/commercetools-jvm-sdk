package io.sphere.sdk.annotations.processors;

import com.github.jknack.handlebars.EscapingStrategy;
import com.github.jknack.handlebars.Handlebars;
import com.github.jknack.handlebars.Template;
import com.github.jknack.handlebars.io.ClassPathTemplateLoader;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.io.Writer;
import java.util.Map;

final class Templates {
    private Templates() {
    }

    private static final Handlebars handlebars;
    private static final Template classTemplate;

    static {
        final ClassPathTemplateLoader loader = new ClassPathTemplateLoader();
        loader.setPrefix("/commercetools-jvm-sdk-processor-templates");
        handlebars = new Handlebars(loader).with(EscapingStrategy.NOOP);
        try {
            classTemplate = handlebars.compile("class");
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    public static void writeClass(final ClassModel context, final Writer writer) throws IOException {
        classTemplate.apply(context, writer);
    }

    public static String render(final String templateName, final Map<String, Object> values) {
        try {
            return handlebars.compile(templateName).apply(values);
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    public static void write(final String template, final Map<String, Object> values, final Writer writer) throws IOException {
        handlebars.compile(template).apply(values, writer);
    }
}
