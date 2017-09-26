package io.sphere.sdk.projects.commands;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import io.sphere.sdk.commands.Command;
import io.sphere.sdk.commands.UpdateAction;
import io.sphere.sdk.projects.Project;

import java.util.Collections;
import java.util.List;

/**
 * Update commands for the {@link Project} entity
 *{@include.example io.sphere.sdk.projects.commands.ProjectUpdateActionsIntegrationTest#execution()}
 */
@JsonDeserialize(as = ProjectUpdateCommandImpl.class)
public interface ProjectUpdateCommand extends Command<Project> {


    Long getVersion() ;

    List<? extends UpdateAction<Project>> getActions();


    static ProjectUpdateCommand of(final Project project, final List<? extends UpdateAction<Project>> actions) {
        return ProjectUpdateCommandImpl.of(project, actions);
    }

    static ProjectUpdateCommand of(final Project project, final UpdateAction<Project> action) {
        return of(project, Collections.singletonList(action));
    }


}
