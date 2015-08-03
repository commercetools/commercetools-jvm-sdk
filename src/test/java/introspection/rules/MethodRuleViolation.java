package introspection.rules;

import java.lang.reflect.Method;

public class MethodRuleViolation extends RuleViolation {
    private final Class<?> clazz;
    private final Method method;

    public MethodRuleViolation(final Class<?> clazz, final Method method) {
        this.clazz = clazz;
        this.method = method;
    }

    public Class<?> getClazz() {
        return clazz;
    }

    public Method getMethod() {
        return method;
    }

    @Override
    public String toString() {
        return "MethodRuleViolation{" +
                "clazz=" + clazz +
                ", method=" + method +
                '}';
    }
}
