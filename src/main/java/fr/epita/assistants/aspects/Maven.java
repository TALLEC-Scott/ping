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
        List<Feature> features = new ArrayList<>();
        features.add(new Compile());
        features.add(new Clean());
        features.add(new Test());
        features.add(new Package());
        features.add(new Install());
        features.add(new Exec());
        features.add(new Tree());
        return features;
    }
    static public Feature.ExecutionReport _execute(String base_cmd, Project project, Object... params) {
        var root = project.getRootNode().getPath().toAbsolutePath();
        Process pr;
        int exitValue;
        StringBuilder stringBuilder = new StringBuilder("mvn.cmd ");
        // mvn.cmd and not mvn Cause Java calls the Win32 API function CreateProcess which
        // assumes a .exe extension contrary to the terminal's interpreter like bash or cmd
        stringBuilder.append(base_cmd);
        for (var param: params) {
            stringBuilder.append(" ");
            stringBuilder.append(param);
        }
        String cmd = stringBuilder.toString();

        try {
            pr = Runtime.getRuntime().exec(cmd, null, new File(root.toString()));
            exitValue = pr.waitFor();

        } catch (IOException | InterruptedException e)
        {
            return () -> false;
        }
        if (exitValue == 0)
            return () -> true;
        return () -> false;
    }
}
