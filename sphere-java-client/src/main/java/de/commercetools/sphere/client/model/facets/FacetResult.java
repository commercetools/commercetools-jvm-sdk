package de.commercetools.sphere.client.model.facets;

import org.codehaus.jackson.annotate.JsonSubTypes;
import org.codehaus.jackson.annotate.JsonTypeInfo;

/** Information about a facet, as part of {@link de.commercetools.sphere.client.model.SearchResult}. */
@JsonTypeInfo(use=JsonTypeInfo.Id.NAME, include=JsonTypeInfo.As.PROPERTY, property="type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = TermsFacetResult.class, name = "terms"),
        @JsonSubTypes.Type(value = RangeFacetResult.class, name = "range"),
        @JsonSubTypes.Type(value = ValuesFacetResultRaw.class, name = "filter")
})
public interface FacetResult {
}
