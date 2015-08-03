package introspection.rules;

import java.lang.reflect.Constructor;

public class ConstructorRuleViolation extends RuleViolation {
    private final Class<?> clazz;
    private final Constructor<?> constructor;
    private String rule;

    public ConstructorRuleViolation(final Class<?> clazz, final Constructor<?> constructor, final String rule) {
        this.clazz = clazz;
        this.constructor = constructor;
        this.rule = rule;
    }

    public Class<?> getClazz() {
        return clazz;
    }

    public Constructor<?> getConstructor() {
        return constructor;
    }

    @Override
    public String toString() {
        return "Constructor<?>RuleViolation{" +
                "clazz=" + clazz +
                ", constructor=" + constructor +
                ", rule=" + rule +
                '}';
    }
}
