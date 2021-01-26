package domain;

import java.util.List;

public abstract class FileSystem {
    protected String path;
    protected String name;
    protected Directory parent;

    protected void setPath(String path) {
        this.path = path;
    }

    protected void setParent(Directory parent) {
        this.parent = parent;
    }

    public String getName() {
        return name;
    }

    public Directory getParent() {
        return parent;
    }

    abstract List<String> getFilesAndDirectoriesRecursively();
    abstract boolean isDirectory();
    protected abstract String getPath();
}
