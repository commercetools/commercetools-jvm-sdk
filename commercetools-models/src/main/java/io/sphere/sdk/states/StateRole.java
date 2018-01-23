package io.sphere.sdk.states;

import com.fasterxml.jackson.annotation.JsonCreator;
import io.sphere.sdk.models.SphereEnumeration;

/**
 * StateRole.
 *
 * For the import and the export of values see also {@link SphereEnumeration}.
 *
 * @see io.sphere.sdk.states.commands.updateactions.AddRoles
 * @see io.sphere.sdk.states.commands.updateactions.SetRoles
 * @see io.sphere.sdk.states.commands.updateactions.RemoveRoles
 * @see State#getRoles()
 */
public enum StateRole implements SphereEnumeration {
    REVIEW_INCLUDED_IN_STATISTICS,
    RETURN;

    @JsonCreator
    public static StateRole ofSphereValue(final String value) {
        return SphereEnumeration.findBySphereName(values(), value).get();
    }
}
