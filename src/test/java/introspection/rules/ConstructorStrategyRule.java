package introspection.rules;

import java.lang.reflect.Constructor;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;

public abstract class ConstructorStrategyRule extends AbstractRule {
    protected abstract boolean classIsIncludedInRule(final Class<?> clazz);
    protected boolean constructorIsIncludedInRule(final Constructor<?> constructor) {
        return true;
    }
    protected abstract boolean isRuleConform(final Constructor<?> constructor);

    @Override
    public final RulesReport check(final List<Class<?>> classes) {
        final List<RuleViolation> violations = classes.stream()
                .filter(this::classIsIncludedInRule)
                .map(clazz -> Arrays.stream(clazz.getConstructors())
                                .filter(this::constructorIsIncludedInRule)
                                .map(constructor -> isRuleConform(constructor) ? Optional.<RuleViolation>empty() : Optional.<RuleViolation>of(new ConstructorRuleViolation(clazz, constructor, getClass().getSimpleName())))
                                .filter(Optional::isPresent)
                                .map(Optional::get)
                )
                .reduce(Stream.empty(), Stream::concat)
                .collect(toList());
        return new RulesReport(violations);
    }
}