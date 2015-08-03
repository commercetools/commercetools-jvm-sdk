package introspection.rules;

public class ClassRuleViolation extends RuleViolation {
    private final Class<?> clazz;
    private String rule;

    public ClassRuleViolation(final Class<?> clazz, final String rule) {
        this.clazz = clazz;
        this.rule = rule;
    }

    public Class<?> getClazz() {
        return clazz;
    }

    public String getRule() {
        return rule;
    }

    @Override
    public String toString() {
        return "ClassRuleViolation{" +
                "clazz=" + clazz +
                ", rule='" + rule + '\'' +
                '}';
    }
}
