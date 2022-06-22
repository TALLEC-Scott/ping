package fr.epita.assistants.myide.domain.entity;

import javax.validation.constraints.NotNull;
import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.PrivateKey;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class NodeClass implements Node{

    private String name;

    private Node.Type type;
    private Path path;

    public NodeClass(Node Parent, String name, Type type) {
        this.name = name;
        this.type = type;
        this.path = Paths.get(Parent.getPath().toString(), name);
    }

    @Override
    public Path getPath() {
        return path;
    }

    @Override
    public Type getType() {
        return (type.equals(Types.FILE)) ? Types.FILE : Types.FOLDER;
    }

    @Override
    public List<@NotNull Node> getChildren() {
        List<Node> childs = new ArrayList<>();
        if (isFolder()) {
            File folder = new File(path.toString());
            for (File fileEntry : Objects.requireNonNull(folder.listFiles())) {
                if (fileEntry.isDirectory()) {
                    childs.add(new NodeClass(this, fileEntry.getName(), Types.FOLDER));
                } else {
                    childs.add(new NodeClass(this, fileEntry.getName(), Types.FILE));
                }
            }
        }
        return childs;
    }

    @Override
    public String toString(){
        return path.toString();
    }
}
