package domain;

import java.util.*;
import java.util.stream.Collectors;

public class Directory extends FileSystem {

    private List<FileSystem> directories;

    public Directory(String name) {
        this.directories = new ArrayList<>();
        this.name = name;
        this.path = name;
    }

    @Override
    public List<String> getFilesAndDirectoriesRecursively() {
        List<String> filesPath = new ArrayList<>();
        filesPath.add(getPath());
        // Horrible way to add Directories after Files
        List<Directory> laterAdd = new ArrayList<>();
        for(FileSystem fileSystem: directories) {
            if(fileSystem.isDirectory()) {
                laterAdd.add((Directory) fileSystem);
            } else {
                filesPath.addAll(fileSystem.getFilesAndDirectoriesRecursively());
            }
        }
        for (Directory directory : laterAdd) {
            filesPath.addAll(directory.getFilesAndDirectoriesRecursively());
        }
        return filesPath;
    }

    public List<String> getFilesAndDirectories() {
        return this.directories.stream()
                .map(FileSystem::getName)
                .collect(Collectors.toList());
    }

    public void add(FileSystem fileSystem) {
        fileSystem.setParent(this);
        directories.add(fileSystem);
    }


    @Override
    boolean isDirectory() {
        return true;
    }

    protected String getPath(String path) {
        if(hasParent()) {
            path = parent.getPath(parent.name + "/" + path);
        }
        return path;
    }

    public String getPath() {
        return getPath(this.name);
    }

    private boolean hasParent() {
        return Objects.nonNull(this.parent);
    }

    public Optional<Directory> getDirectory(String dirname) {
        return this.directories.stream()
                .filter(dir -> dir.isDirectory() && dir.name.equals(dirname))
                .map(Directory.class::cast)
                .findFirst();
    }

    public boolean hasDirectory(String dirName) {
        return this.getDirectory(dirName).isPresent();
    }

    public boolean hasFile(String fileName) {
        return this.directories.stream()
                .anyMatch(dir -> !dir.isDirectory() && dir.name.equals(fileName));
    }
}
