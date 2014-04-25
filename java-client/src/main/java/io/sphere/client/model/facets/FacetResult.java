package io.sphere.client.model.facets;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

/** Calculated results for a facet, as part of {@link io.sphere.client.model.SearchResult}. */
@JsonTypeInfo(use=JsonTypeInfo.Id.NAME, include=JsonTypeInfo.As.PROPERTY, property="type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = TermFacetResult.class, name = "terms"),
        @JsonSubTypes.Type(value = RangeFacetResultRaw.class, name = "range")
})
public interface FacetResult {
}
