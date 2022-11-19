package org.ant.exceptions;

public class DataCollectingException extends RuntimeException {

    public DataCollectingException(String message) {
        super(message);
    }

    public DataCollectingException(RuntimeException e) {
        super(e);
    }

}
