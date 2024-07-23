package fr.insee.eno.ws.exception;

/**
 * Generic error to be thrown when an exception occurs during the DDI to Lunatic transformation.
 */
public class DDIToLunaticException extends Exception {

    public DDIToLunaticException(Exception e) {
        super(e);
    }

}
