package dongduk.cs.moaread.exception;

public class DuplicatedCategoryNameException extends Exception {
    public DuplicatedCategoryNameException() {};
    public DuplicatedCategoryNameException(String message) {
        super(message);
    }
}
