package introspection.rules;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.Optional;

public class NeverMixStaticAndInstanceMethodOfSameNameRule extends MethodStrategyRule {
    @Override
    protected boolean classIsIncludedInRule(final Class<?> clazz) {
        return true;
    }

    @Override
    protected boolean methodIsIncludedInRule(final Method method) {
        return Modifier.isStatic(method.getModifiers());
    }

    @Override
    protected Optional<MethodRuleViolation> check(final Method method) {
        final Optional<Method> evilMethodOption = Arrays.stream(method.getDeclaringClass().getMethods()).filter(cm -> cm.getName().equals(method.getName()) && !Modifier.isStatic(cm.getModifiers())).findFirst();
        return evilMethodOption.map(m -> new MethodRuleViolation(method.getDeclaringClass(), method, getClass().getSimpleName(), m.toString()));
    }
}
