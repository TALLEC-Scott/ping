package fr.epita.assistants.myide.domain.service;

import fr.epita.assistants.myide.domain.entity.Feature;
import fr.epita.assistants.myide.domain.entity.Project;
import fr.epita.assistants.myide.domain.entity.ProjectClass;

import java.nio.file.Path;

public class ProjectServiceClass implements ProjectService{

    final NodeService nodeService;

    public ProjectServiceClass() {
        this.nodeService = new NodeServiceClass();
    }

    @Override
    public Project load(Path root) {
        return new ProjectClass(root);
    }

    @Override
    public Feature.ExecutionReport execute(Project project, Feature.Type featureType, Object... params)  {
        var check = project.getFeature(featureType);
        if (check.isEmpty())
             return null;
        var feature = check.get();
        return feature.execute(project, params);
    }

    @Override
    public NodeService getNodeService() {
        return this.nodeService;
    }
}
