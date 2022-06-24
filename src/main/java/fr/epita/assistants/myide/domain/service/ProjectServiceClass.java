package fr.epita.assistants.myide.domain.service;

import fr.epita.assistants.myide.domain.entity.Feature;
import fr.epita.assistants.myide.domain.entity.Project;
import fr.epita.assistants.myide.domain.entity.ProjectClass;

import java.nio.file.Path;

public class ProjectServiceClass implements ProjectService{

    private NodeService nodeService;
    private Project project;

    public ProjectServiceClass(NodeService nodeService) {
        this.nodeService = nodeService;
    }

    @Override
    public Project load(Path root) {
        this.project = new ProjectClass(root);
        return project;
    }

    @Override
    public Feature.ExecutionReport execute(Project project, Feature.Type featureType, Object... params) {
        return null;
    }

    @Override
    public NodeService getNodeService() {
        return this.nodeService;
    }
}
