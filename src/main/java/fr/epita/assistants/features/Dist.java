package fr.epita.assistants.features;

import fr.epita.assistants.myide.domain.entity.Feature;
import fr.epita.assistants.myide.domain.entity.Mandatory;
import fr.epita.assistants.myide.domain.entity.Project;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class Dist implements Feature {

    public void archive_files(ZipOutputStream zip, File file) throws IOException {

        if (file.isFile()) {
            zip.putNextEntry(new ZipEntry(file.getPath()));
            byte[] data = Files.readAllBytes(Paths.get(file.getPath()));
            var len = data.length;
            zip.write(data, 0, len);
            zip.closeEntry();
        }
        else if (file.isDirectory()) {
            File[] files = file.listFiles();
            if (files == null)
                throw new IOException("Error while opening the directory");
            for (File elt : files) {
                zip.putNextEntry(new ZipEntry(elt.getPath()));
                zip.closeEntry();
                archive_files(zip, elt);
            }
        }
    }

    @Override
    public ExecutionReport execute(Project project, Object... params) {
        try {
            var root = project.getRootNode().getPath().toString();
            var feature = project.getFeature(Mandatory.Features.Any.CLEANUP);
            if (feature.isEmpty())
                throw new Exception("Missing feature ANY.cleanup");
            else
                feature.get()
                    .execute(project);
            File project_dir = new File(root);
            File[] files_list = project_dir.listFiles();
            if (files_list == null)
                throw new Exception("Project folder does not exist");
            ZipOutputStream zip = new ZipOutputStream(new FileOutputStream(root + "/" + project.getRootNode().toString() + ".zip"));
            for (File file : files_list)
                archive_files(zip, file);
            zip.close();
        } catch (Exception e) {
            e.printStackTrace();
            return () -> false;
        }

        return () -> true;
    }

    @Override
    public Type type() {
        return Mandatory.Features.Any.DIST;
    }
}
