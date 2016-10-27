package io.sphere.sdk.annotations.processors;

import com.github.jknack.handlebars.EscapingStrategy;
import com.github.jknack.handlebars.Handlebars;
import com.github.jknack.handlebars.Template;
import com.github.jknack.handlebars.io.ClassPathTemplateLoader;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.io.Writer;
import java.util.Map;
import java.util.concurrent.CompletionException;

public final class Templates {
    private Templates() {
    }

    private static Handlebars handlebars;
    private static Template classTemplate;

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
            throw new CompletionException(e);
        }
    }
}
