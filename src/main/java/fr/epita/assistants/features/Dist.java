package fr.epita.assistants.features;

import fr.epita.assistants.myide.domain.entity.Feature;
import fr.epita.assistants.myide.domain.entity.Mandatory;
import fr.epita.assistants.myide.domain.entity.Project;

import java.io.*;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class Dist implements Feature {

    private void zipFiles(File dir, ZipOutputStream zos, Path base_path)
    {
        File[] files_list = dir.listFiles();
        try {
            for (File elt : files_list) {
                if (elt.isDirectory())
                {
                    Path tmp = Paths.get(elt.getAbsolutePath());
                    ZipEntry ze = new ZipEntry(base_path.relativize(tmp)+ "\\");
                    zos.putNextEntry(ze);
                    zipFiles(elt, zos, base_path);
                    zos.closeEntry();
                }
                else
                {
                    FileInputStream fis = new FileInputStream(elt);
                    BufferedInputStream bis = new BufferedInputStream(fis, 1024);
                    URI base_p = base_path.toUri();
                    ZipEntry ze = new ZipEntry((base_p.relativize(elt.toURI())).toString());

                    zos.putNextEntry(ze);
                    byte data[] = new byte[1024];
                    int count;
                    while ((count = bis.read(data, 0, 1024)) != -1)
                    {
                        zos.write(data, 0, count);
                    }
                    bis.close();
                    zos.closeEntry();
                }
            }

        }catch (IOException e) {
            e.printStackTrace();
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

            File project_path = new File(root);
            FileOutputStream fos = new FileOutputStream(root.concat(".zip"));
            ZipOutputStream zos = new ZipOutputStream(fos);

            Path base_path = Paths.get(root);

            zipFiles(project_path, zos, base_path);
            zos.close();

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
