package de.commercetools.internal.filters;

import com.google.common.collect.Range;
import com.google.common.collect.Ranges;
import de.commercetools.internal.facets.FacetExpressionBase;
import de.commercetools.internal.util.SearchUtil;
import de.commercetools.sphere.client.QueryParam;
import net.jcip.annotations.Immutable;

import java.math.BigDecimal;
import java.util.List;

import static de.commercetools.internal.util.ListUtil.list;
import static de.commercetools.internal.util.SearchUtil.*;

/** Helper facet that makes ElasticSearch return min and max price across returned products . */
@Immutable
public class DynamicPriceRangeHelperFacet extends FacetExpressionBase {
    public DynamicPriceRangeHelperFacet() {
        super(SearchUtil.Names.priceFull);
    }
    // use a big range of (1 to 10 million) - hopefully all product prices fall into this range
    private final Range<BigDecimal> bigRange = Ranges.closed(new BigDecimal(0), new BigDecimal(10*1000*1000));

    @Override public List<QueryParam> createQueryParams() {
        String rangeString = longRangeToParam.apply(toCentRange.apply(bigRange));
        return list(createRangeFacetParam(
                attribute,
                // use an alias so this facet doesn't conflict with other potential facets
                // on variants.price.centAmount
                rangeString + " as " + SearchUtil.HelperFacetAliases.dynamicPriceFacetAlias));
    }
}
