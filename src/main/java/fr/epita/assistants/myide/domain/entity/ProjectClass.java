package fr.epita.assistants.myide.domain.entity;

import fr.epita.assistants.aspects.Any;
import fr.epita.assistants.aspects.Git;
import fr.epita.assistants.aspects.Maven;

import java.io.File;
import java.nio.file.Path;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

public class ProjectClass implements Project {

    private Node RootNode;

    public ProjectClass(Path rootNode) {
        String name = rootNode.getFileName().toString();
        Path parent = rootNode.getParent();
        RootNode = new NodeClass(parent, name, Node.Types.FOLDER);
    }




    @Override
    public Node getRootNode() {
        return RootNode;
    }

    @Override
    public Set<Aspect> getAspects() {
        Set<Aspect> Aspects = new HashSet<>();
        var path = RootNode.getPath();
        File git = new File(path + "/.git");
        File maven = new File(path + "/pom.xml");
        if (git.exists())
            Aspects.add(new Git());
        if (maven.exists())
            Aspects.add(new Maven());
        Aspects.add(new Any());

        return Aspects;
    }

    @Override
    public Optional<Feature> getFeature(Feature.Type featureType) {
        var Feats = getFeatures();
        for (var feature : Feats) {
            if (feature.type().equals(featureType))
                return Optional.ofNullable(feature);
        }
        return Optional.empty();
    }
}
