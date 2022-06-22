package fr.epita.assistants.features;

import fr.epita.assistants.myide.domain.entity.Feature;
import fr.epita.assistants.myide.domain.entity.Mandatory;
import fr.epita.assistants.myide.domain.entity.Project;
import org.apache.maven.Maven;

import java.io.IOException;

public class Clean implements Feature {

    ExecutionReport report = new ExecutionReport() {
        @Override
        public boolean isSuccess() {
            return false;
        }
    };
    public ExecutionReport execute(Project project, Object... params) {
        Process pr;
        boolean isSucess = true;
        int exitValue = -1;
        //var root_path = Project.getRootNode()


        try {
            Runtime.getRuntime().exec("cd");
            pr = Runtime.getRuntime().exec("mvn clean install");
             exitValue =pr.waitFor();
             isSucess = (exitValue == 0);

        } catch (IOException e) {

            isSucess = false;
            //throw new RuntimeException(e);
        } catch (InterruptedException e) {
            isSucess = false;
            //throw new RuntimeException(e);
        }
        boolean finalIsSucess = isSucess;
        ExecutionReport report = new ExecutionReport() {
            @Override
            public boolean isSuccess() {
                return finalIsSucess;
            }
        };
        return report;
    }

    @Override
    public Type type() {
        return Mandatory.Features.Maven.CLEAN;
    }
}
