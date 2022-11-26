package me.gamercoder215.starcosmetics.api.cosmetics.structure;

/**
 * Exception thrown when a Structure File is Malformed
 */
public class MalformedStructureException extends RuntimeException {

    /**
     * Constructs a new MalformedStructureException with the specified detail message.
     * @param message The detail message.
     */
    public MalformedStructureException(String message) {
        super(message);
    }

    /**
     * Constructs a new MalformedStructureException with the specified detail message and cause.
     * @param message the detail message
     * @param cause the cause
     */
    public MalformedStructureException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Constructs a new MalformedStructureException with the given cause.
     * @param cause Cause of the Exception
     */
    public MalformedStructureException(Throwable cause) {
        super(cause);
    }

    /**
     * Constructs a new MalformedStructureException.
     */
    public MalformedStructureException() {
        super();
    }

}
