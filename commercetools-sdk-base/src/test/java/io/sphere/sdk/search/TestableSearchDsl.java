package io.sphere.sdk.search;

import com.fasterxml.jackson.core.type.TypeReference;

public class TestableSearchDsl extends MetaModelSearchDslImpl<Object, TestableSearchDsl, Object, Object, Object, Object> {

        public TestableSearchDsl(){
            super("", new TypeReference<PagedSearchResult<Object>>() {}, new Object(), new Object(), new Object(), new Object(), TestableSearchDsl::new);
        }

        public TestableSearchDsl(final MetaModelSearchDslBuilder<Object, TestableSearchDsl, Object, Object, Object, Object> builder) {
            super(builder);
        }
    }