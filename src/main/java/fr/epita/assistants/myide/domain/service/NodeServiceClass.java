package fr.epita.assistants.myide.domain.service;

import fr.epita.assistants.myide.domain.entity.Node;
import fr.epita.assistants.myide.domain.entity.NodeClass;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.apache.commons.io.FileUtils;
import java.lang.StringBuffer;
import java.util.Arrays;

import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;

public class NodeServiceClass implements NodeService {
    @Override
    public Node update(Node node, int from, int to, byte[] insertedContent)  {
        if (node.isFolder())
            throw new RuntimeException("cannot update a folder");
        Path fileName = node.getPath().toAbsolutePath();
        String content = "";
        try {
            content = Files.readString(fileName);
        } catch (IOException e) {
            e.printStackTrace();
        }
        StringBuilder contBuf = new StringBuilder(content);
        contBuf.delete(from, to);
        String toInsert = new String(insertedContent);
        contBuf.insert(from, toInsert);
        content = contBuf.toString();

        System.out.println(content);

        FileWriter writer = null;
        try {
            writer = new FileWriter(fileName.toString());
            writer.write(content);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return node;
    }

    @Override
    public boolean delete(Node node) {
        File file = new File(node.getPath().toString());
        if (node.isFolder()) {
            try {
                FileUtils.deleteDirectory(file);
            } catch (IOException e) {
                return false;
            }
            return true;
        }
        return file.delete();
    }

    @Override
    public Node create(Node folder, String name, Node.Type type)  {
        Node node = new NodeClass(folder.getPath(), name, type);
        File file = new File(node.getPath().toString());
        try {
            if (node.isFolder())
                file.mkdir();
            else
                file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return node;
    }

    @Override
    public Node move(Node nodeToMove, Node destinationFolder) {
        try {
            Files.move(nodeToMove.getPath(), Paths.get(destinationFolder.getPath().toString(), nodeToMove.getPath().getFileName().toString()), REPLACE_EXISTING);

        } catch (IOException e) {
            e.printStackTrace();
        }

        // very dangerous cast, do not try this at home or at school (with Jhon Cena's voice)
        NodeClass sourceNode = (NodeClass) nodeToMove;
        return new NodeClass(destinationFolder.getPath(), sourceNode.getName(), nodeToMove.getType());
    }
}
