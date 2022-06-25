package fr.epita.assistants.aspects;

import fr.epita.assistants.features.*;
import fr.epita.assistants.features.Package;
import fr.epita.assistants.myide.domain.entity.Aspect;
import fr.epita.assistants.myide.domain.entity.Feature;
import fr.epita.assistants.myide.domain.entity.Feature.ExecutionReport;
import fr.epita.assistants.myide.domain.entity.Mandatory;
import fr.epita.assistants.myide.domain.entity.Project;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Maven implements Aspect {
    @Override
    public Type getType() {
        return Mandatory.Aspects.MAVEN;
    }

    @Override
    public List<Feature> getFeatureList() {
        List<Feature> ftrs = new ArrayList<>();
        ftrs.add(new Compile());
        ftrs.add(new Clean());
        ftrs.add(new Test());
        ftrs.add(new Package());
        ftrs.add(new Install());
        ftrs.add(new Exec());
        ftrs.add(new Tree());
        return ftrs;
    }

    static public ExecutionReport _execute(String base_cmd, Project project, Object... params) {
        var root = project.getRootNode().getPath().toAbsolutePath();
        int exitValue;
        Process pr;
        StringBuilder stringBuilder = new StringBuilder(base_cmd);
        for (var param: params) {
            stringBuilder.append(" ");
            stringBuilder.append(param);
        }
        String cmd = stringBuilder.toString();
        try {
            pr = Runtime.getRuntime().exec(cmd, null, new File(root.toString()));
            exitValue = pr.waitFor();
        } catch (IOException | InterruptedException e) {
            return () -> false;
        }
        if (exitValue != 0)
            return () -> false;
        return () -> true;
    }
}
