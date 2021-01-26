package service.exceptions;

public class FileAlreadyExistsException extends IllegalArgumentException {
    public FileAlreadyExistsException() {
        super("File already exists");
    }
}
