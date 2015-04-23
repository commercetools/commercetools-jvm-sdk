package io.sphere.sdk.carts;

import io.sphere.sdk.models.Referenceable;
import io.sphere.sdk.states.State;
import org.fest.assertions.GenericAssert;

public class LineItemLikeAssert extends GenericAssert<LineItemLikeAssert, LineItemLike> {
    public LineItemLikeAssert(final LineItemLike actual) {
        super(LineItemLikeAssert.class, actual);
    }

    public static LineItemLikeAssert assertThat(final LineItemLike actual) {
        return new LineItemLikeAssert(actual);
    }

    public LineItemLikeAssert containsNotState(final Referenceable<State> state) {
        if (containsStateOf(state)) {
            failIfCustomMessageIsSet();
            throw failure(String.format("%S states %s do contain %s", actual, actual.getState(), state));
        }
        return this;
    }

    public LineItemLikeAssert containsState(final Referenceable<State> state) {
        if (!containsStateOf(state)) {
            failIfCustomMessageIsSet();
            throw failure(String.format("%S states %s do not contain %s", actual, actual.getState(), state));
        }
        return this;
    }

    public LineItemLikeAssert containsItemState(final ItemState itemState) {
        if (!actual.getState().stream().anyMatch(iS -> iS.equals(itemState))) {
            failIfCustomMessageIsSet();
            throw failure(String.format("%S states %s do not contain %s", actual, actual.getState(), itemState));
        }
        return this;
    }

    private boolean containsStateOf(final Referenceable<State> state) {
        return actual.getState().stream().anyMatch(itemState -> itemState.getState().equals(state.toReference()));
    }
}