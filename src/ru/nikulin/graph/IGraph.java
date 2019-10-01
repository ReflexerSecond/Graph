package ru.nikulin.graph;

import java.io.File;
import java.util.ArrayList;
import java.util.Set;

public interface IGraph<T> {
    ArrayList<Graph<T>.Edge<T>> getEdges(T node);

    Set<T> getNodes();

    boolean addNode(T node);

    boolean addEdge(T node, T targetNode, Integer weight);

    boolean deleteNode(T node);

    boolean deleteEdge(T node, Graph<T>.Edge<T> edge);

    ArrayList<T> showParents(T node);

    Graph<T> getNotDirected();

    boolean saveToFile(File file);
}
