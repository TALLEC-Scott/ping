package fr.epita.assistants.myide.domain.service;

import fr.epita.assistants.myide.domain.entity.Node;
import fr.epita.assistants.myide.domain.entity.NodeClass;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;

public class NodeServiceClass implements NodeService {
    @Override
    public Node update(Node node, int from, int to, byte[] insertedContent)  {
        if (node.isFolder())
            throw new RuntimeException("cannot update a folder");
        Path fileName = node.getPath().toAbsolutePath();

        String content = null;
        try {
            content = Files.readString(fileName);
        } catch (IOException e) {
            e.printStackTrace();
        }

        for (int i = 0; i < content.length() || i < insertedContent.length; i++) {
            if (i >= from && i < to)
                content = content.substring(0, i) + insertedContent[i] + content.substring(i + 1);
        }
        FileWriter writer = null;
        try {
            writer = new FileWriter(new File(fileName.toString()));
            writer.write(content);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return node;
    }

    @Override
    public boolean delete(Node node) {
        File file = new File(node.getPath().toString());
        return file.delete();
    }

    @Override
    public Node create(Node folder, String name, Node.Type type)  {
        Node node = new NodeClass(folder, name, type);
        File file = new File(node.getPath().toString());
        try {
            file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return node;
    }

    @Override
    public Node move(Node nodeToMove, Node destinationFolder) {
        try {
            Files.move(nodeToMove.getPath(), destinationFolder.getPath(), REPLACE_EXISTING);
        } catch (IOException e) {
            e.printStackTrace();
        }

        // very dangerous cast, do not try this at home or at school (with Jhon Cena's voice)
        NodeClass sourceNode = (NodeClass) nodeToMove;
        return new NodeClass(destinationFolder, sourceNode.getName(), nodeToMove.getType());
    }
}
