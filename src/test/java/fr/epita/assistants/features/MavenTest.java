package fr.epita.assistants.features;


import fr.epita.assistants.myide.domain.entity.Feature;
import fr.epita.assistants.myide.domain.entity.ProjectClass;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class MavenTest {


    void GenericExecTest(ProjectClass project, Feature feat, Object... params)
    {
        assertTrue(feat.execute(project, params).isSuccess(), feat + " Failed");

    }
    ProjectClass project;
    String currentDirectory = System.getProperty("user.dir");
    @BeforeEach
    void setUp()
    {

        Path path = Paths.get(currentDirectory);
        project = new ProjectClass(path);

    }

    @Test
    @DisplayName("Maven Tree Method")
    void TestTree()
    {
        String outputPath = currentDirectory + "\\src\\test\\" +"tree_output.txt";
        String outputParam = "-DoutputFile=" + outputPath;
        GenericExecTest(project, new Tree(), outputParam, "-DappendOutput=true");
    }

    @Test
    @DisplayName("Maven Exec Method")
    @Disabled("Needs pom.xml configured to test")
    void TestExec()
    {
        GenericExecTest(project, new Exec());
    }

    @Test
    @DisplayName("Maven Install Method")
    @Disabled("Self Referentiel leads to infinite recursion, have to create seperate project to test")
    void TestInstall()
    {
        GenericExecTest(project, new Install());
    }

    @Test
    @DisplayName("Maven Package Method")
    @Disabled("Self Referentiel leads to infinite recursion, have to create seperate project to test")
    void TestPackage()
    {
        GenericExecTest(project, new Package());
    }

    @Test
    @DisplayName("Maven Compile Method")
    void TestCompile()
    {
        GenericExecTest(project, new Compile());
    }

    @Test
    @DisplayName("Maven Test Method")
    @Disabled("Self Referentiel leads to infinite recursion, have to create seperate project to test")
    void TestTest()
    {
        GenericExecTest(project, new fr.epita.assistants.features.Test());
    }

    @Test
    @DisplayName("Maven Clean Method")
    @Disabled("Self Referentiel leads the build being ran and cleaned simultaneously")
    void TestClean()
    {
        GenericExecTest(project, new Clean());
    }



    /* config for exec
    <plugin>
  <groupId>org.codehaus.mojo</groupId>
  <artifactId>exec-maven-plugin</artifactId>
  <version>1.4.0</version>
  <configuration>
    <mainClass>org.dhappy.test.NeoTraverse</mainClass>
  </configuration>
</plugin>

     */
}
