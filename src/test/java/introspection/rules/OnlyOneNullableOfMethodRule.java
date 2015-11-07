package introspection.rules;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.Optional;

public class OnlyOneNullableOfMethodRule extends MethodStrategyRule {
    @Override
    protected boolean classIsIncludedInRule(final Class<?> clazz) {
        return true;
    }

    @Override
    protected boolean methodIsIncludedInRule(final Method method) {
        return method.getName().equals("of")
                && Modifier.isStatic(method.getModifiers())
                && hasOneParameter(method)
                && Arrays.stream(method.getParameterAnnotations()[0]).anyMatch(anno -> anno.annotationType().getSimpleName().equals("Nullable"));
    }

    private boolean hasOneParameter(final Method method) {
        return method.getParameterTypes().length == 1;
    }

    @Override
    protected Optional<MethodRuleViolation> check(final Method method) {
        final Optional<Method> ruleBreakingMethodOption = Arrays.stream(method.getDeclaringClass().getMethods()).filter(m ->
                m.getName().equals("of")
                        && Modifier.isStatic(method.getModifiers())
                        && hasOneParameter(m)
                        && !m.equals(method))
                .findFirst();
        return ruleBreakingMethodOption.map(m -> new MethodRuleViolation(method.getDeclaringClass(), method, getClass().getSimpleName(), "other method: " + m));
    }
}
