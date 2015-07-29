package introspection.rules;

import java.lang.reflect.Constructor;

public class ConstructorRuleViolation extends RuleViolation {
    private final Class<?> clazz;
    private final Constructor<?> constructor;

    public ConstructorRuleViolation(final Class<?> clazz, final Constructor<?> constructor) {
        this.clazz = clazz;
        this.constructor = constructor;
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
                '}';
    }
}
