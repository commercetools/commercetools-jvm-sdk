package io.sphere.sdk.carts;

import io.sphere.sdk.models.Referenceable;
import io.sphere.sdk.states.State;
import org.fest.assertions.GenericAssert;

import java.util.Set;

import static io.sphere.sdk.utils.SetUtils.setOf;

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

    public LineItemLikeAssert containsItemStates(final ItemState itemState, final ItemState ... moreItemStates) {
        final Set<ItemState> itemStates = setOf(itemState, moreItemStates);
        return containsItemStates(itemStates);
    }

    public LineItemLikeAssert containsItemStates(final Set<ItemState> itemStates) {
        itemStates.forEach(givenItemState -> {
            if (!actual.getState().stream().anyMatch(iS -> iS.equals(givenItemState))) {
                failIfCustomMessageIsSet();
                throw failure(String.format("%S states %s do not contain %s", actual, actual.getState(), givenItemState));
            }
        });
        return this;
    }

    private boolean containsStateOf(final Referenceable<State> state) {
        return actual.getState().stream().anyMatch(itemState -> itemState.getState().equals(state.toReference()));
    }
}