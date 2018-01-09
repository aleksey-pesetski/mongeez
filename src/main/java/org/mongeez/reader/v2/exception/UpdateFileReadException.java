package org.mongeez.reader.v2.exception;

/**
 * Exception throws when arising some problems with reading update file 
 * 
 * @author Aleksey Pesetski
 */
public class UpdateFileReadException extends RuntimeException {
    
    public UpdateFileReadException(Throwable cause) {
        super(cause);
    }

    public UpdateFileReadException(String message, Throwable cause) {
        super(message, cause);
    }
}
