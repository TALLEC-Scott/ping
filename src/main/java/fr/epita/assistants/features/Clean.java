package fr.epita.assistants.features;

import fr.epita.assistants.myide.domain.entity.Feature;
import fr.epita.assistants.myide.domain.entity.Mandatory;
import fr.epita.assistants.myide.domain.entity.Node;
import fr.epita.assistants.myide.domain.entity.Project;
import org.apache.maven.Maven;

import java.io.File;
import java.io.IOException;

public class Clean implements Feature {


    public ExecutionReport execute(Project project, Object... params) {
        var root = project.getRootNode().getPath().toAbsolutePath();
        Process pr;
        boolean isSucess = true;
        int exitValue;


        ExecutionReport report;
        try {
            pr = Runtime.getRuntime().exec("mvn clean", null, new File(root.toString()));
            exitValue = pr.waitFor();
            isSucess = (exitValue == 0);

        } catch (IOException | InterruptedException e) {

            isSucess = false;
            //throw new RuntimeException(e);
        } finally {
            boolean finalIsSucess = isSucess;
            report = new ExecutionReport() {
                @Override
                public boolean isSuccess() {
                    return finalIsSucess;
                }
            };
        }
        return report;

    }

    @Override
    public Type type() {
        return Mandatory.Features.Maven.CLEAN;
    }
}
