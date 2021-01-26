package service.exceptions;

public class DirectoryNotFoundException extends IllegalArgumentException {
    public DirectoryNotFoundException() {
        super("Directory not found");
    }
}
