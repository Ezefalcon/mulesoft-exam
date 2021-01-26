package domain;

import java.util.Arrays;
import java.util.List;

public class File extends FileSystem {

    public File(String name) {
        this.name = name;
    }

    @Override
    List<String> getFilesAndDirectoriesRecursively() {
        return Arrays.asList(this.name);
    }

    @Override
    boolean isDirectory() {
        return false;
    }

    @Override
    protected String getPath() {
        return this.path;
    }
}
