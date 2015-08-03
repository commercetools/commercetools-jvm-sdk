package introspection.rules;

import java.util.List;

import static java.util.stream.Collectors.joining;

public class RulesReport {
    private final List<RuleViolation> violations;

    public RulesReport(final List<RuleViolation> violations) {
        this.violations = violations;
    }

    public boolean isOk() {
        return getViolations().isEmpty();
    }

    public List<RuleViolation> getViolations() {
        return violations;
    }

    @Override
    public String toString() {
        return "RulesReport{" +
                "violations=\n" + violations.stream().map(Object::toString).collect(joining("\n")) +
                '}';
    }
}
