package ru.nikulin.exercises;

import ru.nikulin.graph.Graph;
import ru.nikulin.graph.IGraph;

import java.io.File;

public class Exercises {

    public static String lastPath = null;
    public static void exerciseLaunch() {
        //exerciseOne("B");
        //exerciseTwo();
        //exerciseThree();
        //exerciseFour();
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
        AdvancedMethods.hasRoad(testGraph,"D","A","F:D");
        System.out.println("Path is " + lastPath);
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

        private static Graph<String> ThreeFourGraphGenerator() {
            //заполнение графа
            var testGraph1 = new Graph<String>(true, false);
            testGraph1.addNode("A");
            testGraph1.addNode("B");
            testGraph1.addNode("C");
            testGraph1.addNode("D");
            testGraph1.addNode("E");
            testGraph1.addNode("F");
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
