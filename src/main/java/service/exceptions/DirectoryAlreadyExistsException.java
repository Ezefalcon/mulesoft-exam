package service.exceptions;

public class DirectoryAlreadyExistsException extends IllegalArgumentException {
    public DirectoryAlreadyExistsException() {
        super("Directory already exists");
    }
}
