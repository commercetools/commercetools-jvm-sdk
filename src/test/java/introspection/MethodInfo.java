package introspection;

import java.lang.reflect.Method;
import java.util.Arrays;

public class MethodInfo {
    private final Method method;

    public MethodInfo(final Method method) {
        this.method = method;
    }

    public boolean containsOptionalParameter() {
        return Arrays.stream(method.getParameterTypes()).anyMatch(OptionalInspection.isOptionalClass);
    }

    public boolean containsOptionalReturnType() {
        return OptionalInspection.isOptionalClass.test(method.getReturnType());
    }

    @Override
    public String toString() {
        return method.toString();
    }

    public Method getMethod() {
        return method;
    }
}
