package introspection;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Objects;
import java.util.Spliterator;
import java.util.function.Consumer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import static java.util.stream.Collectors.toList;

public class IntrospectionUtils {
    public static Stream<String> readClassNames() throws IOException {
        final String javadocAllClassesFrameHtmlContent = new String(Files.readAllBytes(Paths.get("target/site/apidocs/allclasses-frame.html")));
        return streamClassNames(Pattern.compile("title=\"[^ ]+ in ([^\"]+)\" target=\"classFrame\">(?:<span class=\"interfaceName\">)?([^<]+)(?:</span>)?</a>").matcher(javadocAllClassesFrameHtmlContent));
    }

    public static List<Class<?>> publicClassesOfProject() throws IOException {
        try {
            return readClassNames()
                    .filter(name -> !name.contains("io.sphere.sdk.client.metrics."))
                    .filter(name -> !name.contains("io.sphere.sdk.plugin."))
                    .filter(name -> !name.contains("org.javamoney.moneta."))
                    .filter(name -> !name.contains("javax.money."))
                    .filter(name -> !name.contains("io.sphere.sdk.test"))
                    .filter(name -> !name.contains("AsyncRetrySupervisor"))
                    .filter(name -> !name.contains("io.sphere.sdk.annotations.processors"))
                    .filter(name -> !name.contains("AddBillingAddressId"))
                    .filter(name -> !name.contains("AddShippingAddressIds"))
                    .filter(name -> !name.contains("RemoveBillingAddressId"))
                    .filter(name -> !name.contains("RemoveShippingAddressId"))
                    .map(name -> {
                        try {
                            return Class.forName(name);
                        } catch (ClassNotFoundException e) {
                            if (!name.endsWith("Test")) {
                                throw new RuntimeException(e);
                            } else {
                                return null;
                            }
                        }
                    })
                    .filter(x -> x != null)
                    .collect(toList());
        } catch (final FileNotFoundException e) {
            throw new IOException("Generate Javadoc first.", e);
        }
    }

    private static Stream<String> streamClassNames(final Matcher matcher) {
        final MatcherFindSplitIterator matcherFindSplitIterator = new MatcherFindSplitIterator(matcher);
        return StreamSupport.stream(matcherFindSplitIterator, false).map(m -> m.group(1) + "." + m.group(2));
    }

    private static class MatcherFindSplitIterator implements Spliterator<MatcherGroupView> {

        private final Matcher matcher;

        public MatcherFindSplitIterator(final Matcher matcher) {
            this.matcher = matcher;
        }

        @Override
        public boolean tryAdvance(final Consumer<? super MatcherGroupView> action) {
            Objects.requireNonNull(action);
            if(matcher.find()) {
                action.accept(new MatcherGroupView(matcher));
                return true;
            }
            return false;
        }

        @Override
        public Spliterator<MatcherGroupView> trySplit() {
            return null;
        }

        @Override
        public long estimateSize() {
            return 1000;
        }

        @Override
        public int characteristics() {
            return IMMUTABLE | NONNULL | ORDERED | SIZED;
        }
    }

    private static class MatcherGroupView {
        private final Matcher matcher;

        public MatcherGroupView(final Matcher matcher) {
            this.matcher = matcher;
        }

        public String group(int group) {
            return matcher.group(group);
        }
    }
}
