package ru.nikulin.exercises;

import ru.nikulin.graph.Graph;
import ru.nikulin.graph.IGraph;

import java.io.File;

public class WorkChecker {
    public static void check() {
        constructorCheck();
        addAndDeleteCheck();
        saveAndLoadCheck();
    }

    public static void constructorCheck() {
        System.out.println("\u001B[32mConstructors:\u001B[0m");
        show(" 1) default -> ", new Graph<>().toString());
        show(" 2) with constants -> ", new Graph<>(true, false).toString());
        show(" 3.1) [loaded from file] -> ", new Graph<>(new File("resources/File.txt"), String::toString).toString());
        var testGraph = new Graph<>(new File("resources/File2.txt"), Integer::parseInt);
        show(" 3.2) [loaded from file] -> ", testGraph.toString());
        show(" 4) Specialize -> ", new Graph<>(new Object()).toString());
        show(" 5) Copying (3.2) -> ", new Graph<>(testGraph).toString());
    }

    public static void addAndDeleteCheck() {
        IGraph testGraph = new Graph<>(new File("resources/File.txt"), String::toString);
        System.out.println("\u001B[32mAdd and delete:\u001B[0m");
        show("Test graph -> ", testGraph.toString());
        testGraph.addNode("Z");
        show("Add node \"Z\" -> ", testGraph.toString());
        testGraph.addEdge("Z", "C", 7);
        testGraph.addEdge("Z", "Z", 2);
        show("Add edge \"C -> Z\" with weight 7 and edge \"Z -> Z\" with weight 2 -> ", testGraph.toString());
        testGraph.deleteNode("C");
        show("Delete node \"C\" and connected edges -> ", testGraph.toString());
    }

    public static void saveAndLoadCheck() {
        System.out.println("\u001B[32mSave and load:\u001B[0m");
        var outputFile = new File("FileOutput.txt");
        new Graph<>(new File("resources/File.txt"), String::toString).saveToFile(outputFile);
        IGraph testGraph = new Graph<>(outputFile, String::toString);
        show("File.txt graph -> out/FileOutput.txt -> ", testGraph.toString());
    }

    private static void show(String message, String value) {
        System.out.println("\u001B[33m" + message + "\u001B[0m" + value + "\n");
    }
}
