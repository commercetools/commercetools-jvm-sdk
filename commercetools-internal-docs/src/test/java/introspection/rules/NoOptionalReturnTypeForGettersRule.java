package introspection.rules;

import introspection.MethodInfo;

import java.lang.reflect.Method;
import java.util.Optional;

public class NoOptionalReturnTypeForGettersRule extends MethodStrategyRule {
    @Override
    protected boolean classIsIncludedInRule(final Class<?> clazz) {
        return true;
    }

    @Override
    protected boolean methodIsIncludedInRule(final Method method) {
        return !method.getName().startsWith("find") && !method.getName().equals("head") && !method.getName().startsWith("calculate");
    }

    @Override
    protected Optional<MethodRuleViolation> check(final Method method) {
        return (!new MethodInfo(method).containsOptionalReturnType()) ? Optional.empty() : Optional.of(new MethodRuleViolation(method.getDeclaringClass(), method, getClass().getSimpleName()));
    }
}
