package introspection.rules;

import java.util.List;

@FunctionalInterface
public interface Rule {
    RulesReport check(List<Class<?>> classes);
}
