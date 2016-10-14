package introspection.rules;

import java.lang.reflect.Modifier;
import java.util.List;

import static java.util.Arrays.asList;

public class ClassesAreFinalRule extends ClassStrategyRule {
    private final List<String> classNamesWhiteList =
            asList(("ReferenceAttributeType, CartShippingInfoImpl, ExpansionModel, MessageImpl," +
                    ".LocalizedToStringProductAttributeConverter, MetaModelUpdateCommandDslImpl," +
                    "PagedQueryResult, SortableSearchModel, QueryModelImpl, SearchModelImpl," +
                    "SphereError, LocalizedToStringProductAttributeConverter,ExpansionModelImpl," +
                    "ReferenceFacetSearchModelImpl,TermFacetSearchModelImpl,TermFilterExpression," +
                    "TermFilterSearchModelImpl, DefaultProductAttributeFormatter, SolutionInfo").split(",( )?"));


    @Override
    protected boolean classIsIncludedInRule(final Class<?> clazz) {
        return !(Modifier.isFinal(clazz.getModifiers()) || Modifier.isAbstract(clazz.getModifiers()) || clazz.isAnnotation() || clazz.isInterface() || clazz.getSimpleName().endsWith("Exception") || classNamesWhiteList.contains(clazz.getSimpleName()));
    }

    @Override
    protected boolean isRuleConform(final Class<?> clazz) {
        return false;
    }
}
