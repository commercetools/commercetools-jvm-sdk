package introspection.rules;

import introspection.OptionalInspection;

import java.lang.reflect.Constructor;
import java.util.Arrays;

public class NoOptionalParametersInPublicConstructorRule extends ConstructorStrategyRule {

    @Override
    protected boolean classIsIncludedInRule(final Class<?> clazz) {
        return true;
    }

    @Override
    protected boolean isRuleConform(final Constructor<?> constructor) {
        return !Arrays.stream(constructor.getParameterTypes()).anyMatch(OptionalInspection.isOptionalClass);
    }
}
