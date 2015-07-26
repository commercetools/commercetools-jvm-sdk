package introspection;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ClassInfo {

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
