package fr.epita.assistants.features;

import fr.epita.assistants.aspects.Maven;
import fr.epita.assistants.myide.domain.entity.Feature;
import fr.epita.assistants.myide.domain.entity.Feature.ExecutionReport;
import fr.epita.assistants.myide.domain.entity.Project;

import java.io.File;
import java.io.IOException;

public class MavenExec{
    static public ExecutionReport _execute(String base_cmd, Project project, Object... params) {
        var root = project.getRootNode().getPath().toAbsolutePath();
        boolean isSuccess = true;
        int exitValue;
        Process pr;
        ExecutionReport report;
        StringBuilder stringBuilder = new StringBuilder(base_cmd);
        for (var param: params) {
            stringBuilder.append(" ");
            stringBuilder.append(param);
        }
        String cmd = stringBuilder.toString();
        try {
            pr = Runtime.getRuntime().exec(cmd, null, new File(root.toString()));
            exitValue = pr.waitFor();
            isSuccess = (exitValue == 0);
        } catch (IOException | InterruptedException e) {
            isSuccess = false;
        } finally {
            boolean finalIsSuccess = isSuccess;
            report = () -> finalIsSuccess;
        }
        return report;
    }
}
