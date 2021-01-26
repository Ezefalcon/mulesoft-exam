package service.exceptions;

public class CharacterLimitExceededException extends IllegalArgumentException {
    public CharacterLimitExceededException() {
        super("Character limit exceeded 100");
    }
}
