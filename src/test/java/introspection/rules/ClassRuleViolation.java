package introspection.rules;

public class ClassRuleViolation extends RuleViolation {
    private final Class<?> clazz;

    public ClassRuleViolation(final Class<?> clazz) {
        this.clazz = clazz;
    }

    public Class<?> getClazz() {
        return clazz;
    }

    @Override
    public String toString() {
        return "ClassRuleViolation{" +
                "clazz=" + clazz +
                '}';
    }
}
