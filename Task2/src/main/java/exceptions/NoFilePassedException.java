package exceptions;

/**
 * exception thrown when no file name is passed
 */
public class NoFilePassedException extends Exception
{
    public NoFilePassedException(String message) {
        super(message);
    }
}
