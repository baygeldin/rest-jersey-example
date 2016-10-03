package fi.jyu.task3.exception;

public class DataNotFoundException extends RuntimeException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public DataNotFoundException(String errorMessage){
		super(errorMessage);
	}
}
