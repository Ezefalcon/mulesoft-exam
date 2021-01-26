package service;

import java.util.List;
import java.util.Scanner;

public abstract class CommandProcessor {

    /**
     * Starts and manages the application
     */
    public void start() {
        Scanner sc = new Scanner(System.in);
        String command = "";
        while(!command.equals("quit")) {
            try {
                command = sc.nextLine();
                String[] split = command.split("\\s");
                if(split.length > 0) {
                    if(split.length > 1) {
                        String com = split[0];
                        String param = split[1];
                        if(com.equals("mkdir")) {
                            mkdir(param);
                        } else if(com.equals("cd")) {
                            cd(param);
                        } else if(com.equals("touch")) {
                            touch(param);
                        } else if(com.equals("ls")) {
                            ls(split[1]).forEach(System.out::println);
                        } else {
                            System.out.println("Unsupported operation");
                        }
                    } else {
                        if(command.equals("pwd")) {
                            System.out.println(pwd());
                        } else if (command.equals("ls")) {
                            ls("").forEach(System.out::println);
                        } else {
                            System.out.println("Unsupported operation");
                        }
                    }
                }
            } catch (Exception exc) {
                System.out.println(exc.getMessage());
            }
        }
    }

    /**
     * Prints Full Path of current directory
     */
    abstract String pwd();

    /**
     * Prints everything in current directory and all subdirectories
     * If printing recursively, before printing contents, print the full path of
     * the current directory.
     * @param flag -r
     * @return
     */
    abstract List<String> ls(String flag);

    /**
     * Name directory to create. Character limit 100.
     * If directory exists prints Directory already exists
     * @param dirName
     */
    abstract void mkdir(String dirName);

    /**
     * Change directory
     * Prints Directory not found if the dir does not exists
     */
    abstract void cd(String dirname);

    /**
     * Creates a file in the current directory
     * @param fileName
     */
    abstract void touch(String fileName);
}
