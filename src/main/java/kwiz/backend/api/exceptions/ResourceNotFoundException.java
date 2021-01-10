package kwiz.backend.api.exceptions;

public class ResourceNotFoundException extends RuntimeException{
    
    /**
     *
     */
    private static final long serialVersionUID = 1L;

    private static final String DEFAULT_MSG = "Either a module or a question you are requesting for is not found! Check your moduleId and questionIds again.";

    public ResourceNotFoundException() {
        super(DEFAULT_MSG);
    }

    public ResourceNotFoundException(String msg) {
        super(msg);
    }

}
