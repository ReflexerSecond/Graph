package ru.nikulin.exercises;

public class Main {
    public static void main(String[] args) {
        System.out.println("\u001B[31m" + "-".repeat(50) + "Work Check" + "-".repeat(50) + "\u001B[0m" + "\n");
        WorkChecker.check();
        System.out.println("\u001B[31m" + "-".repeat(50) + "Exercises" + "-".repeat(50) + "\u001B[0m" + "\n");
        Exercises.exerciseLaunch();
    }
}
