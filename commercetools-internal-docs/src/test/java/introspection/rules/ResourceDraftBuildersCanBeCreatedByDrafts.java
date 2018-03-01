package introspection.rules;

import java.lang.reflect.Modifier;
import java.util.Arrays;

public class ResourceDraftBuildersCanBeCreatedByDrafts extends ClassStrategyRule {
    @Override
    protected boolean classIsIncludedInRule(final Class<?> clazz) {
        final String simpleName = clazz.getSimpleName();
        return simpleName.endsWith("DraftBuilder")
                && !simpleName.equals("CustomLineItemImportDraftBuilder")
                && !simpleName.equals("OrderImportDraftBuilder")
                && !simpleName.equals("ProductVariantImportDraftBuilder")
                && !simpleName.equals("ExternalTaxRateDraftBuilder")
                && !simpleName.equals("AssetDraftBuilder")
                && !simpleName.equals("CartScoreDraftBuilder")
                && !simpleName.equals("CartValueDraftBuilder")
                && !simpleName.equals("LineItemImportDraftBuilder");
    }

    @Override
    protected boolean isRuleConform(final Class<?> clazz) {
        return Arrays.stream(clazz.getMethods())
                .filter(m -> m.getName().equals("of"))
                .filter(m -> Modifier.isStatic(m.getModifiers()))
                .filter(m -> m.getParameterTypes().length == 1)
                .anyMatch(m -> {
                    final String classNameOfDraft = clazz.getSimpleName().replace("Builder", "");
                    final String simpleName = m.getParameterTypes()[0].getSimpleName();
                    return simpleName.equals(classNameOfDraft);
                });
    }
}
