package fr.epita.assistants.features;

import fr.epita.assistants.myide.domain.entity.Feature;
import fr.epita.assistants.myide.domain.entity.Mandatory;
import fr.epita.assistants.myide.domain.entity.Project;

public class Push implements Feature {
    @Override
    public ExecutionReport execute(Project project, Object... params) {
        return null;
    }

    @Override
    public Type type() {
        return Mandatory.Features.Git.PUSH;
    }
}
