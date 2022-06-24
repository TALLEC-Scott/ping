package fr.epita.assistants.features;

import fr.epita.assistants.myide.domain.entity.Feature;
import fr.epita.assistants.myide.domain.entity.Mandatory;
import fr.epita.assistants.myide.domain.entity.Project;
import fr.epita.assistants.utils.ThrowingRunnable;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;

import java.io.IOException;

public class Add implements Feature {
    @Override
    public ExecutionReport execute(Project project, Object... params) {

        Git git = null;
        try {
            git = Git.open(project.getRootNode().getPath().toFile());
        } catch (IOException e) {
            e.printStackTrace();
            return () -> false;
        }

        for (var arg : params){
            try {
                git.add().addFilepattern(arg.toString()).call();
            } catch (GitAPIException e) {
                e.printStackTrace();
                return () -> false;
            }
        }

        return () -> true;
    }

    @Override
    public Type type() {
        return Mandatory.Features.Git.ADD;
    }
}
