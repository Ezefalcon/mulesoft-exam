package service;

import domain.Directory;
import domain.File;
import service.exceptions.CharacterLimitExceededException;
import service.exceptions.DirectoryAlreadyExistsException;
import service.exceptions.DirectoryNotFoundException;
import service.exceptions.FileAlreadyExistsException;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class CommandProcessorImpl extends CommandProcessor {

    private Directory currentDirectory;

    public CommandProcessorImpl(Directory root) {
        this.currentDirectory = root;
    }

    @Override
    public String pwd() {
        return this.currentDirectory.getPath();
    }

    @Override
    public List<String> ls(String flag) {
        if(flag.equals("-r")) {
            return this.currentDirectory.getFilesAndDirectoriesRecursively();
        } else if (flag.isEmpty()) {
            return this.currentDirectory.getFilesAndDirectories();
        }
        throw new UnsupportedOperationException("Unsupported operation");
    }

    @Override
    public void mkdir(String dirName) {
        if(!currentDirectory.hasDirectory(dirName)) {
            this.currentDirectory.add(new Directory(dirName));
        } else {
            throw new DirectoryAlreadyExistsException();
        }
    }

    @Override
    public void cd(String dirname) {
        if(dirname.equals(".") && Objects.nonNull(this.currentDirectory.getParent())) {
            this.currentDirectory = this.currentDirectory.getParent();
        } else {
            Optional<Directory> directory = this.currentDirectory.getDirectory(dirname);
            if(directory.isPresent()) {
                this.currentDirectory = directory.get();
            } else {
                throw new DirectoryNotFoundException();
            }
        }

    }

    @Override
    public void touch(String fileName) {
        if (fileName.length() <= 100) {
            if(!this.currentDirectory.hasFile(fileName)) {
                this.currentDirectory.add(new File(fileName));
            } else {
                throw new FileAlreadyExistsException();
            }
        } else {
            throw new CharacterLimitExceededException();
        }
    }
}
