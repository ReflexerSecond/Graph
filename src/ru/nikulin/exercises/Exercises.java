package ru.nikulin.exercises;

import ru.nikulin.graph.Graph;
import ru.nikulin.graph.IGraph;

import java.io.File;
import java.util.*;

public class Exercises {

    public static String lastPath = null;

    public static void exerciseLaunch() {
        //exerciseOne("B");
        //exerciseTwo();
        //exerciseThree();
        //exerciseFour();
        //exerciseFive();
        //exerciseSix();
        //exerciseSeven();
        exerciseEight();
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
        var testGraph1 = AdvancedMethods.ThreeFourSixGraphGenerator();

        System.out.println(testGraph1);
        AdvancedMethods.ThreeResult(testGraph1, "сильносвязный");

        testGraph1 = testGraph1.getNotDirected();
        System.out.println(testGraph1);
        AdvancedMethods.ThreeResult(testGraph1, "связный");
    }

    public static void exerciseFour() {
        AdvancedMethods.titleShow("II 9 Найти путь, соединяющий вершины u и v и не проходящий через заданное подмножество вершин V");
        //заполнение графа
        var testGraph = AdvancedMethods.ThreeFourSixGraphGenerator();
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

        System.out.println("Result: " + AdvancedMethods.getSkeletonPrim(testGraph, "A"));
    }

    public static void exerciseSix() {
        AdvancedMethods.titleShow("Веса IV а. Дейкстра. Вывести длины кратчайших путей для всех пар вершин.");
        var nodes = "ABCDE";
        var testGraph = AdvancedMethods.SixSevenGraphGenerator("ABCDE", false);

        System.out.println(testGraph);
        for (int i = 0; i < nodes.length(); i++) {
            var result = AdvancedMethods.goHardLikeDijkstra(testGraph, Character.toString(nodes.charAt(i)));
            ArrayList<String> res = new ArrayList<>(result.keySet());
            for (int j = (!testGraph.isDirected()) ? 1 + i : 1; j < result.keySet().size(); j++) {
                if (result.get(res.get(j)) == Integer.MAX_VALUE)
                    System.out.println(nodes.charAt(i) + ": " + res.get(j) + " -> There is no way");
                else
                    System.out.println(nodes.charAt(i) + ": " + res.get(j) + " = " + result.get(res.get(j)));
            }
        }
    }

    public static void exerciseSeven() {
        AdvancedMethods.titleShow("Веса IV b. Флойд. Вывести длины кратчайших путей от u до v1 и v2.");
        var u = "A";
        var v1 = "C";
        var v2 = "D";
        var testGraph = new Graph<String>(true, true);
        var nodes = "ABCD";
        for (int i = 0; i < nodes.length(); i++) {
            testGraph.addNode(Character.toString(nodes.charAt(i)));
        }
        testGraph.addEdge("A", "B", 1);
        testGraph.addEdge("A", "C", 6);
        testGraph.addEdge("B", "C", 4);
        testGraph.addEdge("B", "D", 1);
        testGraph.addEdge("D", "C", 1);
        ArrayList<String> resTemp = new ArrayList<>(testGraph.getNodes());
        var result = AdvancedMethods.goHardLikeFloyd(testGraph, resTemp);
        System.out.println(testGraph + "\n" + "U(" + u + ") -> V1(" + v1 + ") = " + result[resTemp.indexOf(u)][resTemp.indexOf(v1)] + "\n" +
                "U(" + u + ") -> V1(" + v2 + ") = " + result[resTemp.indexOf(u)][resTemp.indexOf(v2)]);

        /*for (int i = 0; i < result.length; i++) {
            for (int j = 0; j < result.length; j++) {
                System.out.print(((result[i][j] == null) ? "N" : result[i][j]) + " ");
            }
            System.out.println();
        }*/
    }

    public static void exerciseEight() {
        AdvancedMethods.titleShow("Веса IV с. Форд-Беллман. 18 Вывести цикл отрицательного веса, если он есть");
        var testGraph = new Graph<Integer>(true, true);
        for (int i = 0; i < 5; i++) {
            testGraph.addNode(i);
        }
        testGraph.addEdge(0, 1, 2);
        testGraph.addEdge(0, 3, 7);
        testGraph.addEdge(0, 2, 6);
        testGraph.addEdge(1, 3, 3);
        testGraph.addEdge(1, 4, 6);
        testGraph.addEdge(2, 4, 1);
        testGraph.addEdge(3, 4, 5);
        //testGraph.addEdge(3, 4, -5);
        //testGraph.addEdge(4, 3, -1);
        //testGraph.addEdge(1, 1, -10);
        AdvancedMethods.findNegativeWeightCycleWithBellmanFord(testGraph, 0);
    }

    public static class AdvancedMethods {
        private static void ThreeResult(IGraph<String> testGraph1, String message) {
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

        private static boolean hasRoad(IGraph<String> graph, String node, String targetNode, String path) {
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

        private static Graph<String> getSkeletonPrim(IGraph<String> graph, String startNode) {
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

        private static Graph<String> ThreeFourSixGraphGenerator() {
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

        private static Graph<String> SixSevenGraphGenerator(String nodesT, boolean isDirected) {
            var testGraph = new Graph<String>(isDirected, true);
            String nodes = nodesT;
            for (int i = 0; i < nodes.length(); i++) {
                testGraph.addNode(Character.toString(nodes.charAt(i)));
            }
            testGraph.addEdge("A", "B", 5);
            testGraph.addEdge("A", "C", 2);
            testGraph.addEdge("B", "C", 2);
            testGraph.addEdge("C", "D", 8);
            testGraph.addEdge("B", "E", 7);
            testGraph.addEdge("E", "D", 10);
            return testGraph;
        }

        private static HashMap<String, Integer> goHardLikeDijkstra(IGraph<String> graph, String target) {
            //Инициализируем
            var result = new HashMap<String, Integer>();
            for (var node : graph.getNodes()) {
                result.put(node, Integer.MAX_VALUE);
            }
            result.put(target, 0);
            HashSet<String> temp = new HashSet<>(result.keySet());

            //Работа с графом. temp Массив непомеченных вершин
            while (temp.size() > 0) {

                //Находим непомеченный с минимальным весом
                var tempMin = temp.iterator().next();
                for (var i : temp) {
                    if (result.get(i) < result.get(tempMin))
                        tempMin = i;
                }
                //Заменяем большие веса на меньшие
                for (var edge : graph.getEdges(tempMin)) {
                    if ((result.get(tempMin) != Integer.MAX_VALUE) && ((edge.getWeight() + result.get(tempMin)) < result.get(edge.getTargetNode())))
                        result.put(edge.getTargetNode(), edge.getWeight() + result.get(tempMin));
                }
                //"Помечаем" вершину
                temp.remove(tempMin);
            }
            return result;
        }

        private static Integer[][] goHardLikeFloyd(IGraph<String> graph, ArrayList<String> nodes) {
            Integer[][] resultArray = new Integer[nodes.size()][nodes.size()];
            resultArray[0][0] = 0;
            for (var node : nodes) {
                for (var edge : graph.getEdges(node)) {
                    resultArray[nodes.indexOf(node)][nodes.indexOf(edge.getTargetNode())] = edge.getWeight();
                }
            }

            for (int i = 0; i < nodes.size(); i++) {
                for (int j = 0; j < nodes.size(); j++) {
                    if (resultArray[i][j] != null) {
                        for (int k = 0; k < nodes.size(); k++) {
                            if (k == j) {
                                resultArray[k][k] = 0;
                            } else if (resultArray[k][i] != null && k != i && j != i &&
                                    (resultArray[k][j] == null || (resultArray[k][j] > resultArray[k][i] + resultArray[i][j]))) {
                                resultArray[k][j] = resultArray[k][i] + resultArray[i][j];
                            }
                        }
                    }
                }
            }

            return resultArray;
        }

        private static void findNegativeWeightCycleWithBellmanFord(IGraph<Integer> testGraph, Integer source) {
            // ToDo Refactor me!!!
            // tempTable содержит вершину как ключ и (вес пути, путь) как значение

            var tempTable = new HashMap<Integer, AbstractMap.SimpleEntry<Integer, String>>();
            var edges = new ArrayList<AbstractMap.SimpleEntry<Integer, Graph<Integer>.Edge<Integer>>>();
            var temp = tempTable.values().toString();
            for (var i : testGraph.getNodes()) {
                tempTable.put(i, new AbstractMap.SimpleEntry<>(null, ""));
                for (var edge : testGraph.getEdges(i)) {
                    edges.add(new AbstractMap.SimpleEntry<>(i, edge));
                }
            }
            tempTable.put(source, new AbstractMap.SimpleEntry<>(0, ""));

            //собственно алгоритм тут
            // Раскомментить Чтобы увидеть шаги (1,2)
            //1 System.out.println("Формат: Вершина=ВесМинимальногоПути=МинимальныйПуть");
            Integer nodeWeight, targetNode, targetWeight;
            for (int i = 1; i < tempTable.keySet().size() + 1; i++) {

                //2 System.out.println(tempTable);
                for (var edge : edges) {

                    //Константы для удобства
                    nodeWeight = tempTable.get(edge.getKey()).getKey();
                    targetNode = edge.getValue().getTargetNode();
                    targetWeight = tempTable.get(targetNode).getKey();

                    //Попытка релаксации
                    if (nodeWeight != null && ((targetWeight == null) || ((nodeWeight + edge.getValue().getWeight() < targetWeight)))) {
                        tempTable.put(targetNode, new AbstractMap.SimpleEntry<>(nodeWeight + edge.getValue().getWeight(),
                                tempTable.get(edge.getKey()).getValue() + edge.getKey()));
                    }
                }
                //если значения не поменялись значит дальнейшие итерации бессмыслены

                if (temp.equals(tempTable.values().toString())) {
                    break;
                } else {
                    //цикл намеренно делает на одну операцию больше чтобы распознать цикл отрицательного веса
                    //цикл будет заметен по разнице в пути на прелпоследнем и последнем проходе у задействованных рёбер.
                    if (i == tempTable.size()) {
                        String[] tempForNodesPathes = temp.split(",");
                        var currentNodesPathes = tempTable.values().toString().split(",");
                        StringBuffer result = new StringBuffer();

                        for (int j = 0; j < tempForNodesPathes.length; j++) {
                            if (!tempForNodesPathes[j].equals(currentNodesPathes[j])) {
                                for (var letter : tempForNodesPathes[j].toCharArray()) {
                                    if ((tempForNodesPathes[j].substring(tempForNodesPathes[j].indexOf("=") + 1).replaceAll(Character.toString(letter), "").length()
                                            < tempForNodesPathes[j].substring(tempForNodesPathes[j].indexOf("=") + 1).length() - 1)
                                            && (result.indexOf(Character.toString(letter)) == -1)) {
                                        result.append(letter);
                                    }
                                }
                                if (result.length() > 0) break;
                            }
                        }
                        System.out.println("Цикл отрицательного веса: " + result);
                    }
                }
                temp = tempTable.values().toString();

            }
        }
    }
}
