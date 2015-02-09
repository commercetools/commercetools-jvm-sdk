package io.sphere.sdk.search;

import io.sphere.sdk.models.Base;

import java.util.List;

public class TermFacetResult extends Base implements FacetResult {
    private final long missing;
    private final long total;
    private final long other;
    private final List<TermStats> terms;

    private TermFacetResult(final long missing, final long total, final long other, final List<TermStats> terms) {
        this.missing = missing;
        this.total = total;
        this.other = other;
        this.terms = terms;
    }

    /**
     * The number of resources which have no value for the corresponding field.
     * Use case: in a product search where a term facet is requested for a color attribute,
     * it would represent the amount of variants with no color associated.
     * @return amount of resources missing a value for the faceted field.
     */
    public long getMissing() {
        return missing;
    }

    /**
     * The number of resources matching some term in the facet.
     * @return amount of resources that match at least one term in the facet.
     */
    public long getTotal() {
        return total;
    }

    /**
     * The number of resources not included in the returned list of terms.
     * @return amount of resources matching the terms that could not be included in the facet result.
     */
    public long getOther() {
        return other;
    }

    /**
     * List of the different terms and amount of associated resources.
     * @return the list of distinct terms along with the number of matching resources.
     */
    public List<TermStats> getTerms() {
        return terms;
    }

    public static TermFacetResult of(final long missing, final long total, final long other, final List<TermStats> terms) {
        return new TermFacetResult(missing, total, other, terms);
    }
}
