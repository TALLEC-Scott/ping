package fr.epita.assistants.features;

import fr.epita.assistants.aspects.Maven;
import fr.epita.assistants.myide.domain.entity.Feature;
import fr.epita.assistants.myide.domain.entity.Mandatory;
import fr.epita.assistants.myide.domain.entity.Project;

import javax.validation.constraints.NotNull;
import java.io.File;
import java.io.IOException;

public class Exec extends Maven implements Feature {
    @Override
    public ExecutionReport execute(Project project, Object... params) {

        return Maven._execute("exec:java", project, params);
    }

    @Override
    public Feature.@NotNull Type type() {
        return Mandatory.Features.Maven.EXEC;
    }
}
