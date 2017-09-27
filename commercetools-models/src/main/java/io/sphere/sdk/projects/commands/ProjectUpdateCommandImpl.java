package io.sphere.sdk.projects.commands;

import com.fasterxml.jackson.databind.JavaType;
import io.sphere.sdk.client.HttpRequestIntent;
import io.sphere.sdk.commands.CommandImpl;
import io.sphere.sdk.commands.UpdateAction;
import io.sphere.sdk.http.HttpMethod;
import io.sphere.sdk.json.SphereJsonUtils;
import io.sphere.sdk.projects.Project;

import java.util.List;

final class ProjectUpdateCommandImpl extends CommandImpl<Project> implements ProjectUpdateCommand{

    private final Long version;
    private final List<? extends UpdateAction<Project>> actions;

    ProjectUpdateCommandImpl(final Long version, final List<? extends UpdateAction<Project>> actions) {
        this.version = version;
        this.actions = actions;

    }

    static ProjectUpdateCommandImpl of(final Project project, final List<? extends UpdateAction<Project>> actions) {
        return new ProjectUpdateCommandImpl(project.getVersion(), actions);
    }

    public Long getVersion() {
        return version;
    }

    public List<? extends UpdateAction<Project>> getActions() {
        return actions;
    }


    @Override
    protected JavaType jacksonJavaType() {
        return SphereJsonUtils.convertToJavaType(Project.typeReference());
    }


    @Override
    public HttpRequestIntent httpRequestIntent() {
        return HttpRequestIntent.of(HttpMethod.POST, "/", SphereJsonUtils.toJsonString(this));
    }

}
