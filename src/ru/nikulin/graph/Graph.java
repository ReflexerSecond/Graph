/*
1
Для решения всех задач курса необходимо создать класс (или иерархию классов - на усмотрение разработчика), содержащий:
+1. Структуру для хранения списка смежности графа (не работать с графом через матрицы смежности, если в некоторых алгоритмах удобнее использовать список ребер - реализовать метод, преобразующий список смежности в список ребер);
+2. Конструкторы (не менее 3-х):
+• конструктор по умолчанию, создающий пустой граф
+• конструктор, заполняющий данные графа из файла
+• конструктор-копию (аккуратно, не все сразу делают именно копию)
+• специфические конструкторы для удобства тестирования
3. Методы:
+• добавляющие вершину,
+• добавляющие ребро (дугу),
+• удаляющие вершину,
+• удаляющие ребро (дугу),
+• выводящие список смежности в файл (в том числе в пригодном для чтения конструктором формате).
Не выполняйте некорректные операции, сообщайте об ошибках.
+4. Должны поддерживаться как ориентированные, так и неориентированные графы. Заранее предусмотрите возможность добавления меток и\или весов для дуг. Поддержка мультиграфа не требуется.
+5. Добавьте минималистичный интерфейс пользователя (не смешивая его с реализацией!), позволяющий добавлять и удалять вершины и рёбра (дуги) и просматривать текущий список смежности графа.
*/

package ru.nikulin.graph;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;
import java.util.Set;

public class Graph<T> implements IGraph<T> {

    //Var's

    private boolean isDirected;
    private boolean hasWeight;
    private final HashMap<T, ArrayList<Edge<T>>> nodesMap; //Node storage

    //Getters

    public boolean isDirected() { return isDirected; }

    public boolean isHasWeight() { return hasWeight; }

    public ArrayList<Edge<T>> getEdges(T node) {
        return nodesMap.get(node);
    }

    public Set<T> getNodes() {
        return nodesMap.keySet();
    }

    //Constructors

    public Graph() {
        this(false, true);
    }

    public Graph(boolean isDirected, boolean hasWeight) {
        this.isDirected = isDirected;
        this.hasWeight = hasWeight;
        this.nodesMap = new HashMap<>();
    }

    public Graph(File file, IStringToCurrentTypeConverter<T> converter) {
        //"converter" is a function, which parses string to the current type.
        this.nodesMap = new HashMap<>();
        System.out.println(loadFromFile(file, converter));
    }

    public Graph(Object obj) {
        this.nodesMap = new HashMap<>();
        specializedMethod(obj);
    }

    public Graph(Graph<T> graph) {
        //Copy constructor
        this.isDirected = graph.isDirected;
        this.hasWeight = graph.hasWeight;
        this.nodesMap = new HashMap<>(graph.nodesMap);
    }

    //Methods: Required

    public boolean addNode(T node) {
        // Checking for the correct data
        if (nodesMap.containsKey(node)) {
            return false;
        }
        // Making edge list and adding it to node storage
        nodesMap.put(node, new ArrayList<>());
        return true;
    }

    public boolean addEdge(T node, T targetNode, Integer weight) {
        //Correct data check
        if (!nodesMap.containsKey(node) || !nodesMap.containsKey(targetNode) ||
                (nodesMap.get(node).stream().anyMatch(x-> (x.getTargetNode() == targetNode)&&(x.getWeight()==weight)))) {
            return false;
        }
        //Not directed graph's edges has null weight
        Edge temp = (hasWeight) ? new Edge<>(targetNode, weight) : new Edge<>(targetNode);

        nodesMap.get(node).add(temp);

        if (!isDirected && node != targetNode) {
            temp = (hasWeight) ? new Edge<>(node, weight) : new Edge<>(node);
            nodesMap.get(targetNode).add(temp);
        }
        return true;
    }

    public boolean deleteNode(T node) {
        //Delete node
        if (!nodesMap.containsKey(node))
            return false;
        nodesMap.remove(node);
        //Delete edges, connected with that node
        for (var i : nodesMap.keySet()) {
            deleteEdge(i, new Edge<>(node));
        }
        return true;
    }

    public boolean deleteEdge(T node, Edge<T> edge) {
        if (!nodesMap.containsKey(node) || !nodesMap.get(node).contains(edge))
            return false;
        nodesMap.get(node).remove(edge);
        return true;
    }

    public boolean saveToFile(File file) {
        BufferedWriter bw;

        try {
            if (!file.exists()) file.createNewFile();
            bw = new BufferedWriter(new FileWriter(file));
            bw.write(isDirected + "\n" + hasWeight + "\n");
            for (var node : nodesMap.keySet()) {
                bw.write(node.toString() + " ");
                for (var edge : nodesMap.get(node)) {
                    bw.write(edge + " ");
                }
                bw.newLine();
            }
            bw.close();
        } catch (IOException e) {
            return false;
        }
        return true;
    }

    //Methods: Additional

    @Override
    public String toString() {
        return "This is" + ((isDirected) ? "" : " not") + " a directed graph with" + ((hasWeight) ? "" : "out") + " weight." + "\n" + "    " + nodesMap;
    }

    public ArrayList<T> showParents(T node) {
        var parentsArray = new ArrayList<T>();
        var tempEdge = new Edge<>(node);
        for (var i : nodesMap.keySet()) {
            if (nodesMap.get(i).contains(tempEdge)) {
                parentsArray.add(i);
            }
        }
        return parentsArray;
    }

    private String getLinesFromFile(File file, ArrayList<String> lines) {
        FileReader fs;
        try {
            fs = new FileReader(file);
        } catch (FileNotFoundException e) {
            return "File not found";
        }
        var buff = new BufferedReader(fs);
        String temp;
        try {
            while ((temp = buff.readLine()) != null) {
                lines.add(temp);
            }
            buff.close();
            fs.close();
        } catch (java.io.IOException e) {
            return "IOException";
        }
        return "OK";
    }

    protected String loadFromFile(File file, IStringToCurrentTypeConverter<T> converter) {
        ArrayList<String> lines = new ArrayList<>();
        if (!getLinesFromFile(file, lines).equals("OK")) return "File incorrect" + lines;
        if (lines.size() < 2) return "File size is incorrect. Must be 2 lines at least.";

        //Set const values
        isDirected = Boolean.parseBoolean(lines.get(0));
        hasWeight = Boolean.parseBoolean(lines.get(1));

        //Adding nodes and edges
        for (int i = 2; i < lines.size(); i++) {
            var temp = lines.get(i).split(" ");
            addNode(converter.convert(temp[0]));

            if (hasWeight) {
                //"Weighted" graph`s syntax is "C A:1 B:2"
                try {
                    for (int j = 1; j < temp.length; j++) {
                        addNode(converter.convert(temp[j].split(":")[0]));
                        addEdge(converter.convert(temp[0]), converter.convert(temp[j].split(":")[0]), Integer.parseInt(temp[j].split(":")[1]));
                    }
                } catch (ArrayIndexOutOfBoundsException e) {
                    nodesMap.clear();
                    return "Incorrect line syntax. Correct \"Node AnotherNode1:Weight1 AnotherNode2:Weight2\"";
                }
            } else {
                //With no weight it is "C A B"
                for (int j = 1; j < temp.length; j++) {
                    addNode(converter.convert(temp[j]));
                    addEdge(converter.convert(temp[0]), converter.convert(temp[j]), 0);
                }
            }
        }
        return "Reading: OK.";
    }

    protected void specializedMethod(Object obj) {
        System.out.println("Specialize method called. Please, override it to use specialized constructor as you want");
        //Override this
    }

    public Graph<T> getNotDirected() {
        if (!isDirected) return this;

        var temp = new Graph<>(this);

        for (var node : nodesMap.keySet()) {
            for (var edge : nodesMap.get(node)) {
                temp.addEdge(edge.targetNode, node, null);
            }
        }
        temp.isDirected = false;
        return temp;
    }

    public Edge<T> edgeFactory(T obj) {
        return new Edge<>(obj);
    }

    public Edge<T> edgeFactory(T obj, int weight) {
        return new Edge<>(obj, weight);
    }

    //Nested class

    public class Edge<T> {
        final T targetNode;
        Integer weight;

        Edge(T targetNode, int weight) {
            this.targetNode = targetNode;
            this.weight = weight;
        }

        Edge(T targetNode) {
            this.targetNode = targetNode;
        }

        public T getTargetNode() {
            return targetNode;
        }

        public Integer getWeight() throws NullPointerException {
            return weight;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o.getClass() == targetNode.getClass()) return Objects.equals(targetNode, o);
            if (o == null || getClass() != o.getClass()) return false;

            Edge<T> edge = (Edge<T>) o;
            return Objects.equals(targetNode, edge.targetNode);
        }

        @Override
        public String toString() {
            if (!hasWeight) {
                return targetNode.toString();
            } else {
                return targetNode + ":" + weight;
            }
        }
    }
}