package introspection.rules;

import java.util.List;
import java.util.Optional;

import static java.util.stream.Collectors.toList;

public abstract class ClassStrategyRule extends AbstractRule {
    protected abstract boolean classIsIncludedInRule(final Class<?> clazz);
    protected abstract boolean isRuleConform(final Class<?> clazz);

    @Override
    public final RulesReport check(final List<Class<?>> classes) {
        final List<RuleViolation> violations = classes.stream()
                .filter(this::classIsIncludedInRule)
                .map(clazz -> isRuleConform(clazz) ? Optional.<RuleViolation>empty() : Optional.<RuleViolation>of(new ClassRuleViolation(clazz, getClass().getSimpleName())))
                .filter(violationOptional -> violationOptional.isPresent())
                .map(violationOptional -> violationOptional.get())
                .collect(toList());
        return new RulesReport(violations);
    }
}
