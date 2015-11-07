package introspection.rules;

import java.lang.reflect.Method;

public class MethodRuleViolation extends RuleViolation {
    private final Class<?> clazz;
    private final Method method;
    private final String rule;
    private final String details;

    public MethodRuleViolation(final Class<?> clazz, final Method method, final String rule, final String details) {
        this.clazz = clazz;
        this.method = method;
        this.rule = rule;
        this.details = details;
    }

    public MethodRuleViolation(final Class<?> clazz, final Method method, final String rule) {
        this.clazz = clazz;
        this.method = method;
        this.rule = rule;
        details = null;
    }

    public Class<?> getClazz() {
        return clazz;
    }

    public Method getMethod() {
        return method;
    }

    public String getRule() {
        return rule;
    }

    public String getDetails() {
        return details;
    }

    @Override
    public String toString() {
        return "MethodRuleViolation{" +
                "clazz=" + clazz +
                ", method=" + method +
                ", rule=" + rule +
                ", details=" + details +
                '}';
    }
}
