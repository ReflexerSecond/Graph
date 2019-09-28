package ru.nikulin.exercises;

import com.sun.nio.sctp.PeerAddressChangeNotification;
import ru.nikulin.graph.Graph;
import ru.nikulin.graph.IGraph;

import java.io.File;
import java.util.ArrayList;

public class Exercises {

    public static String lastPath = null;

    public static void exerciseLaunch() {
        //exerciseOne("B");
        //exerciseTwo();
        //exerciseThree();
        //exerciseFour();
        exerciseFive();
    }

    public static void exerciseOne(String node) {
        AdvancedMethods.titleShow("Ia 9 Для данной вершины орграфа вывести на экран все «выходящие» соседние вершины.");
        IGraph testGraph = new Graph<>(new File("resources/File.txt"), String::toString);
        System.out.print(node + ": ");
        for (var i : testGraph.getEdges(node)) {
            System.out.print(i + " ");
        }
        System.out.println();
    }

    public static void exerciseTwo() {
        AdvancedMethods.titleShow("Ib 4 Вывести список смежности графа, являющегося пересечением двух заданных.");
        // used oriented graph without weight

        Graph<Integer> testGraph1 = new Graph<>(new File("resources/File2.txt"), Integer::parseInt);
        Graph<Integer> testGraph2 = new Graph<>(testGraph1);
        Graph<Integer> result = new Graph<>(true, false);

        testGraph2.deleteNode(1);
        testGraph2.addNode(4);
        testGraph2.addEdge(2, 4, null);

        for (var node : testGraph1.getNodes()) {
            if (testGraph2.getNodes().contains(node)) {
                result.addNode(node);
            }
        }

        for (var node : testGraph1.getNodes()) {
            if (testGraph2.getNodes().contains(node)) {
                for (var edge : testGraph1.getEdges(node)) {
                    if (testGraph2.getEdges(node).contains(edge)) {
                        result.addEdge(node, edge.getTargetNode(), null);
                        ;
                    }
                }
            }
        }

        System.out.println(testGraph1);
        System.out.println(testGraph2);
        System.out.println(result);
    }

    public static void exerciseThree() {
        AdvancedMethods.titleShow("II 4 Выяснить, является ли граф связным.");
        var testGraph1 = AdvancedMethods.ThreeFourGraphGenerator();

        System.out.println(testGraph1);
        AdvancedMethods.ThreeResult(testGraph1, "сильносвязный");

        testGraph1 = testGraph1.getNotOriented();
        System.out.println(testGraph1);
        AdvancedMethods.ThreeResult(testGraph1, "связный");
    }

    public static void exerciseFour() {
        AdvancedMethods.titleShow("II 9 Найти путь, соединяющий вершины u и v и не проходящий через заданное подмножество вершин V");
        //заполнение графа
        var testGraph = AdvancedMethods.ThreeFourGraphGenerator();
        System.out.println(testGraph);
        AdvancedMethods.hasRoad(testGraph, "D", "A", "F:D");
        System.out.println("Path is " + lastPath);
    }

    public static void exerciseFive() {
        AdvancedMethods.titleShow("III Примм. Дан взвешенный неориентированный граф из N вершин и M ребер. Требуется найти в нем каркас минимального веса.");
        var testGraph = new Graph<String>(false, true);
        String edges = "ABCDEFGH";
        for (int i = 0; i < edges.length(); i++) {
            testGraph.addNode(Character.toString(edges.charAt(i)));
        }
        testGraph.addEdge("A", "B", 6);
        testGraph.addEdge("A", "D", 25);
        testGraph.addEdge("A", "H", 19);
        testGraph.addEdge("B", "D", 11);
        testGraph.addEdge("B", "C", 17);
        testGraph.addEdge("C", "D", 8);
        testGraph.addEdge("D", "E", 2);
        testGraph.addEdge("E", "F", 21);
        testGraph.addEdge("E", "G", 14);
        testGraph.addEdge("G", "H", 9);

        System.out.println("Result: " + AdvancedMethods.getSkeletonPrimm(testGraph,"A"));
    }

    public static void exerciseSix() {

    }

    public static class AdvancedMethods {
        private static void ThreeResult(Graph<String> testGraph1, String message) {
            boolean resault = true;
            var temp = testGraph1.getNodes().toArray();
            for (var i = 0; i < temp.length; i++) {
                for (int j = i + 1; j < temp.length; j++) {
                    if (!(hasRoad(testGraph1, temp[i].toString(), temp[j].toString(), "") || hasRoad(testGraph1, temp[j].toString(), temp[i].toString(), ""))) {
                        resault = false;
                        break;
                    }
                }
                if (!resault) break;
            }
            System.out.println("Граф " + ((resault) ? "" : "не ") + message);
        }

        private static void titleShow(String message) {
            System.out.println("\u001B[33m" + message + "\u001B[0m" + "\n");
        }

        private static boolean hasRoad(Graph<String> graph, String node, String targetNode, String path) {
            if (path.length() == 0) path += node;
            if (node.equals(targetNode)) {
                lastPath = path;
                return true;
            } else if (graph.getEdges(node).size() >= 1)
                for (var i : graph.getEdges(node)) {
                    if (!path.contains(i.getTargetNode()))
                        if (hasRoad(graph, i.getTargetNode(), targetNode, path + i))
                            return true;
                }
            return false;
        }

        private static Graph<String> getSkeletonPrimm(Graph<String> graph, String startNode) {
            var resultGraph = new Graph<String>(false, true);
            Integer minWeight = 0;
            var tempNode = startNode;
            var tempEdge = resultGraph.edgeFactory(startNode);
            resultGraph.addNode(startNode);

            while (resultGraph.getNodes().size() < graph.getNodes().size()) {
                minWeight = Integer.MAX_VALUE;
                for (var node : resultGraph.getNodes()) {
                    for (var edge : graph.getEdges(node)) {
                        if ((!resultGraph.getNodes().contains(edge.getTargetNode())) && (edge.getWeight() <= minWeight)) {
                            minWeight = edge.getWeight();
                            tempEdge = edge;
                            tempNode = node;
                        }
                    }
                }
                resultGraph.addNode(tempEdge.getTargetNode());
                resultGraph.addEdge(tempNode, tempEdge.getTargetNode(), tempEdge.getWeight());
            }

            return resultGraph;
        }

        private static Graph<String> ThreeFourGraphGenerator() {
            //заполнение графа
            var testGraph1 = new Graph<String>(true, false);
            String edges = "ABCDEF";
            for (int i = 0; i < edges.length(); i++) {
                testGraph1.addNode(Character.toString(edges.charAt(i)));
            }
            //testGraph1.addEdge("A", "B", null);
            testGraph1.addEdge("B", "D", null);
            testGraph1.addEdge("D", "E", null);
            testGraph1.addEdge("E", "F", null);
            testGraph1.addEdge("E", "A", null);
            testGraph1.addEdge("F", "A", null);
            testGraph1.addEdge("D", "C", null);
            return testGraph1;
        }
    }
}
