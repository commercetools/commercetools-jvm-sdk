package introspection.rules;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;

public abstract class MethodStrategyRule extends AbstractRule {
    protected abstract boolean classIsIncludedInRule(final Class<?> clazz);
    protected boolean methodIsIncludedInRule(final Method method) {
        return true;
    }
    protected abstract Optional<MethodRuleViolation> check(final Method method);

    @Override
    public final RulesReport check(final List<Class<?>> classes) {
        final List<RuleViolation> violations = classes.stream()
                .filter(this::classIsIncludedInRule)
                .map(clazz -> Arrays.stream(clazz.getMethods())
                                .filter(method -> methodIsIncludedInRule(method))
                                .map(method -> check(method))
                                .filter(Optional::isPresent)
                                .map(Optional::get)
                )
                .reduce(Stream.empty(), Stream::concat)
                .collect(toList());
        return new RulesReport(violations);
    }
}