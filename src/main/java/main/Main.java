package main;

import domain.Directory;
import domain.File;
import service.CommandProcessor;
import service.CommandProcessorImpl;

public class Main {
    public static void main(String[] args) {
        Directory mainDirectory = new Directory("mulesoft");
        Directory src = new Directory("src");
        Directory java = new Directory("java");
        java.add(new File("test1"));
        java.add(new File("Command"));
        src.add(java);
        src.add(new File("A"));
        src.add(new File("B"));
        src.add(new File("C"));
        mainDirectory.add(src);

        CommandProcessor commandProcessor = new CommandProcessorImpl(mainDirectory);
        commandProcessor.start();

    }
}
