package io.sphere.sdk.search;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY,
        property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = FilteredFacetResult.class, name = "filter"),
        @JsonSubTypes.Type(value = TermFacetResult.class, name = "terms"),
        @JsonSubTypes.Type(value = RangeFacetResult.class, name = "range") })
public interface FacetResult {
}
