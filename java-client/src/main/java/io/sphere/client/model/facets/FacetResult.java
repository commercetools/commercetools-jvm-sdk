package io.sphere.client.model.facets;

import org.codehaus.jackson.annotate.JsonSubTypes;
import org.codehaus.jackson.annotate.JsonTypeInfo;

/** Calculated results for a facet, as part of {@link io.sphere.client.model.SearchResult}. */
@JsonTypeInfo(use=JsonTypeInfo.Id.NAME, include=JsonTypeInfo.As.PROPERTY, property="type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = TermFacetResult.class, name = "terms"),
        @JsonSubTypes.Type(value = RangeFacetResultRaw.class, name = "range")
})
public interface FacetResult {
}
