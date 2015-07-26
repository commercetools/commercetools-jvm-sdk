package introspection;

import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static introspection.IntrospectionUtils.readClassNames;

//test:runMain introspection.OptionalInspection
public class OptionalInspection {
    static final Predicate<Class<?>> isOptionalClass = type -> type.getName().equals("java.util.Optional");
    public static final Predicate<MethodInfo> CONTAINS_OPTIONAL_STUFF = m -> m.containsOptionalParameter() || m.containsOptionalReturnType();


    public static void main(String[] args) throws Exception {
        final Stream<String> classNameStream = readClassNames();
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


}
