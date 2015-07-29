package introspection.rules;

import java.util.List;

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
                "violations=" + violations +
                '}';
    }
}
