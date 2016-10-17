package fi.jyu.imdb.exception;

public class IAmATeapotException extends RuntimeException{
	private static final long serialVersionUID = 1L;
	
	public IAmATeapotException(){
		super("I'm a teapot.");
	}
}
