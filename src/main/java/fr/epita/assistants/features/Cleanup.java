package fr.epita.assistants.features;

import fr.epita.assistants.myide.domain.entity.Feature;
import fr.epita.assistants.myide.domain.entity.Mandatory;
import fr.epita.assistants.myide.domain.entity.Project;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Cleanup implements Feature {

    public void findFile(File dir, List<String> files)
    {
        File[] list = dir.listFiles();
        if (list != null)
        {
            for (File elt : list)
            {
                if (elt.isDirectory())
                    findFile(elt, files);
                else
                {
                    for (int i = 0; i < files.size(); i++)
                    {
                        if (elt.getName().equals(files.get(i)))
                        {
                            File tmp = new File(Paths.get(dir.getAbsolutePath(), elt.getName()).toString());
                            tmp.delete();
                            break;
                        }
                    }
                }
            }
        }
    }

    @Override
    public ExecutionReport execute(Project project, Object... params) {
        Path root =  project.getRootNode().getPath();

        File trash_file = new File(Paths.get(root.toString() ,  ".myideignore").toString());
        File project_path = new File(root.toString());

        Scanner reader = null;
        try {
            reader = new Scanner(trash_file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return () -> false;
        }

        List<String> files = new ArrayList<>();
        while (reader.hasNextLine()) {
            String name = reader.nextLine();
            files.add(name);
        }

        findFile(project_path, files);

        return () -> true;
    }

    @Override
    public Type type() {
        return Mandatory.Features.Any.CLEANUP;
    }
}
