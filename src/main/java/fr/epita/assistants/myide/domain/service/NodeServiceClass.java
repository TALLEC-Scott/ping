package fr.epita.assistants.myide.domain.service;

import fr.epita.assistants.myide.domain.entity.Node;
import fr.epita.assistants.myide.domain.entity.NodeClass;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class NodeServiceClass implements NodeService {
    @Override
    public Node update(Node node, int from, int to, byte[] insertedContent) throws IOException {
        if (node.isFolder())
            throw new RuntimeException("cannot update a folder");
        Path fileName = node.getPath().toAbsolutePath();

        String content = Files.readString(fileName);

        for (int i = 0; i < content.length() || i < insertedContent.length; i++) {
            if (i >= from && i < to)
                content = content.substring(0, i) + insertedContent[i] + content.substring(i + 1);
        }
        FileWriter writer = new FileWriter(new File(fileName.toString()));
        writer.write(content);
        return null;
    }

    @Override
    public boolean delete(Node node) {
        File file = new File(node.getPath().toString());
        return file.delete();
    }

    @Override
    public Node create(Node folder, String name, Node.Type type) throws IOException {
        Node node = new NodeClass(folder, name, type);
        File file = new File(node.getPath().toString());
        file.createNewFile();
        return node;
    }

    @Override
    public Node move(Node nodeToMove, Node destinationFolder) {
        return null;
    }
}
