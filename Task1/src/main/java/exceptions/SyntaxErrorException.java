package exceptions;

/**
 * Exception class used for syntax exceptions
 */
public class SyntaxErrorException extends Exception
{
    public SyntaxErrorException(String message) {
        super(message);
    }
}
