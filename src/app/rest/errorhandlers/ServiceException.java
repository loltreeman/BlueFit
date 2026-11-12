package app.rest.errorhandlers;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import app.dto.ErrorDTO;


// THIS WILL ACT AS YOUR DEFAULT EXCEPTION HANDLER SO IT WILL DISPLAY THE ERROR
// IN POSTMAN, as {"error": "..."}

@Provider
public class ServiceException extends Exception implements
                ExceptionMapper<Throwable>
{
    private static final long serialVersionUID = 1L;

    public ServiceException() {
        super("Something bad has happened");
    }

    public ServiceException(String string) {
        super(string);
    }

    @Override
    public Response toResponse(Throwable exception)
    {
    	// our own business-rule errors
    	if (exception instanceof ServiceException) {
    		return error(400, exception.getMessage());
    	}

    	// javax validation failures, usually wrapped by Spring/Hibernate on save
    	for (Throwable t = exception; t != null; t = t.getCause()) {
    		if (t instanceof ConstraintViolationException) {
    			StringBuilder sb = new StringBuilder();
    			for (ConstraintViolation<?> v : ((ConstraintViolationException) t).getConstraintViolations()) {
    				if (sb.length() > 0) {
    					sb.append(" ");
    				}
    				sb.append(v.getMessage());
    			}
    			return error(400, sb.toString());
    		}
    	}

    	// Jersey's own errors, e.g. 404 for an unknown URL
    	if (exception instanceof WebApplicationException) {
    		int status = ((WebApplicationException) exception).getResponse().getStatus();
    		return error(status, exception.getMessage());
    	}

    	exception.printStackTrace();
    	return error(500, "Something went wrong.");
    }

    private Response error(int status, String message) {
    	return Response.status(status).entity(new ErrorDTO(message)).type(MediaType.APPLICATION_JSON).build();
    }
}
