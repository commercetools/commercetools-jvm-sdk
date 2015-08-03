package io.sphere.sdk.commands;

import com.fasterxml.jackson.annotation.JsonCreator;
import org.junit.Test;

import java.util.Collections;
import java.util.List;


public class UpdateCommandDslImplTest {
    @Test
    public void showAssignmentPossibilities() throws Exception {
        final List<UpdateAction<String>> a = Collections.emptyList();
        final List<? extends UpdateAction<String>> b = Collections.emptyList();
        final List<? extends UpdateAction<String>> c = a;
//        final List<UpdateAction<String>> d = b; does not work
        final List<Foo> e = Collections.emptyList();
//        final List<UpdateAction<String>> f = e; does not work
        final List<? extends UpdateAction<String>> g = e;
    }

    static class Foo extends UpdateActionImpl<String> {
        @JsonCreator
        public Foo() {
            super("foo");
        }
    }
}