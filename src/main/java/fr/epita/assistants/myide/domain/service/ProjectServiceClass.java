package fr.epita.assistants.myide.domain.service;

import fr.epita.assistants.myide.domain.entity.Feature;
import fr.epita.assistants.myide.domain.entity.Project;
import fr.epita.assistants.myide.domain.entity.ProjectClass;

import java.nio.file.Path;

public class ProjectServiceClass implements ProjectService{

    final NodeService nodeService;
    Project project;

    public ProjectServiceClass(NodeService nodeService) {
        this.nodeService = nodeService;
    }

    @Override
    public Project load(Path root) {
        this.project = new ProjectClass(root);
        return project;
    }

    @Override
    public Feature.ExecutionReport execute(Project project, Feature.Type featureType, Object... params) throws Exception {
        var check = project.getFeature(featureType);
        if (check.isEmpty())
            throw new Exception("feature not queried");
        var feature = check.get();
        return feature.execute(project, params);
    }

    @Override
    public NodeService getNodeService() {
        return this.nodeService;
    }
}
