package io.sphere.sdk.commands;

/**
 * Command to create a resource in commercetools based on a draft object and having a wither to replace the current draft.
 *
 * @param <T> the type of the result of the command
 * @param <D> the type of the draft (body of the command)
 * @param <C> the type of this element
 *
 */
public interface DraftBasedCreateCommandDsl<T, D, C> extends DraftBasedCreateCommand<T, D> {
    @Override
    D getDraft();

    C withDraft(final D draft);
}
