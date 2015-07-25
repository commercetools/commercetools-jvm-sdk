package introspection;

import java.lang.reflect.Method;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

//test:runMain introspection.OptionalInspection
public class OptionalInspection {
    static final Predicate<Class<?>> isOptionalClass = type -> type.getName().equals("java.util.Optional");
    public static final Predicate<MethodInfo> CONTAINS_OPTIONAL_STUFF = m -> m.containsOptionalParameter() || m.containsOptionalReturnType();


    public static void main(String[] args) throws Exception {
        final String javadocAllClassesFrameHtmlContent = new String(Files.readAllBytes(Paths.get("target/javaunidoc/allclasses-frame.html")));
        final Stream<String> classNameStream = streamClassNames(Pattern.compile("title=\"class in ([^\"]+)\" target=\"classFrame\">([^<]+)</a>").matcher(javadocAllClassesFrameHtmlContent));
        final List<ClassInfo> classesWithAnyOptional = classNameStream.map(className -> {
            try {
                final Class<?> clazz = Class.forName(className);
                return new ClassInfo(clazz);
            } catch (ClassNotFoundException e) {
                return null;
            }
        })
        .filter(i -> i.getMethods().stream().anyMatch(m -> m.containsOptionalParameter() || m.containsOptionalReturnType()))
        .collect(Collectors.toList());

        classesWithAnyOptional
//TODO, search for optional fields

                .forEach(clazzInfo -> {
                    System.err.println(clazzInfo);
                    System.err.println("methods with Optional parameters:");
                    clazzInfo.getMethodInfoStream().filter(m -> m.containsOptionalParameter()).forEach(m -> System.err.println(m));
                    System.err.println("");
                    System.err.println("methods with Optional return type:");
                    clazzInfo.getMethodInfoStream().filter(m -> m.containsOptionalReturnType()).forEach(m -> System.err.println(m));
                    System.err.println();
                    System.err.println();
                });

        System.err.println("classes affected: " + classesWithAnyOptional.size());
        System.err.println("methods affected: " + classesWithAnyOptional.stream().mapToLong(c -> {
            final long count = c.getMethodInfoStream().filter(CONTAINS_OPTIONAL_STUFF).count();
            return count;
        }).sum());
    }

    private static class MethodInfo {
        private final Method method;

        public MethodInfo(final Method method) {
            this.method = method;
        }

        public boolean containsOptionalParameter() {
            return Arrays.stream(method.getParameterTypes()).anyMatch(isOptionalClass);
        }

        public boolean containsOptionalReturnType() {
            return isOptionalClass.test(method.getReturnType());
        }

        @Override
        public String toString() {
            return method.toString();
        }
    }


    private static class ClassInfo {

        private final Class<?> clazz;

        public ClassInfo(final Class<?> clazz) {
            this.clazz = clazz;
        }

        public List<MethodInfo> getMethods() {
            return getMethodStream().map(MethodInfo::new).collect(Collectors.toList());
        }

        public Stream<Method> getMethodStream() {
            return Arrays.asList(clazz.getDeclaredMethods()).stream();
        }

        public Stream<MethodInfo> getMethodInfoStream() {
            return  getMethodStream().map(MethodInfo::new);
        }

        @Override
        public String toString() {
            return "ClassInfo{" +
                    "clazz=" + clazz +
                    '}';
        }
    }

    private static Stream<String> streamClassNames(final Matcher matcher) {
        final MatcherFindSplitIterator matcherFindSplitIterator = new MatcherFindSplitIterator(matcher);
        return StreamSupport.stream(matcherFindSplitIterator, false).map(m -> m.group(1) + "." + m.group(2));
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
}
