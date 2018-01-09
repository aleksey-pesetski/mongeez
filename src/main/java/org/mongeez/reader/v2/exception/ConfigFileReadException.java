package org.mongeez.reader.v2.exception;

/**
 * Exception throws when arising some problems with reading config file 
 * 
 * @author Aleksey Pesetski
 */
public class ConfigFileReadException extends RuntimeException {
    
    public ConfigFileReadException(Throwable cause) {
        super(cause);
    }

    public ConfigFileReadException(String message, Throwable cause) {
        super(message, cause);
    }
}
